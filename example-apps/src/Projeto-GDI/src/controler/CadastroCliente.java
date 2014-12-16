package controler;

import models.Cliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CadastroCliente {

	static String username = "system";
	static String password = "senha aqui";
	
	// pra testar conexao:
	
	/*public static void main(String[] args){
		try {
			Connection con = connectToDatabase(username,password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nome FROM tb_cliente");

			while (rs.next()) {
				// int x = rs.getInt("a");
				String s = rs.getString("nome");
				System.out.println(s);
				// float f = rs.getFloat("c");
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	public static Connection connectToDatabase(String username, String password) throws ClassNotFoundException {

		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:hr/hr@localhost:1521/XE",
					username,
					password);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return con;
	}
	
	public void cadastrar(Cliente c) throws SQLException, ClassNotFoundException{
		Connection con = connectToDatabase(username, password);
		Statement stmt = con.createStatement();
		// INSERT INTO tb_cliente VALUES ('cpf','nome',v_telefone(tp_telefone('telefone1'),tp_telefone('telefone2'),tp_telefone('telefone3')),1);
		String[] telefones = c.getTelefones();
		String tel = telefones[0];
		String insercao = "INSERT INTO tb_cliente VALUES('"+c.getCpf()+"','"+c.getNome()+"',v_telefone(tp_telefone('"+tel+"')";
		// outros 2 telefones, se houver
		for(int i=1;i<3 && tel != null;i++){
			tel = telefones[i];
			if(tel == null) break;
			insercao += ",tp_telefone('"+tel+"')";
		}
		insercao += "),"+c.getIdProduto()+")";
		System.out.println(insercao);
		stmt.executeQuery(insercao);
		stmt.close();
		con.close();
	}

}
