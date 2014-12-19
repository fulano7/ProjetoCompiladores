package views;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import models.Cliente;
import controler.CadastroCliente;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;

public class TelaCadastro {

	private JFrame frmProjetoGdi;
	private JTextField clientName;
	private JTextField clientCPF;
	private static CadastroCliente cad;

	/**
	 * Create the application.
	 */
	public TelaCadastro() {
		initialize();
	}
	
	// Controlador só precisa ser inicializado uma vez
	static {
		cad = new CadastroCliente();
	}
	
	public void setVisible(boolean v){
		this.frmProjetoGdi.setVisible(v);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmProjetoGdi = new JFrame();
		frmProjetoGdi.setTitle("Projeto GDI Nunca Mais!");
		frmProjetoGdi.setBounds(100, 100, 600, 352);
		frmProjetoGdi.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmProjetoGdi.getContentPane().setLayout(null);
		frmProjetoGdi.setVisible(true);
		
		clientName = new JTextField();
		clientName.setBounds(10, 53, 178, 20);
		frmProjetoGdi.getContentPane().add(clientName);
		clientName.setColumns(15);
		
		JLabel lblBuscarCliente = new JLabel("Cadastrar Cliente");
		lblBuscarCliente.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblBuscarCliente.setBounds(10, 11, 227, 14);
		frmProjetoGdi.getContentPane().add(lblBuscarCliente);

		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(10, 36, 46, 14);
		frmProjetoGdi.getContentPane().add(lblNome);
		
		clientCPF = new JTextField();
		clientCPF.setColumns(15);
		clientCPF.setBounds(10, 101, 178, 20);
		frmProjetoGdi.getContentPane().add(clientCPF);
		
		JLabel lblCpf = new JLabel("CPF");
		lblCpf.setBounds(10, 84, 46, 14);
		frmProjetoGdi.getContentPane().add(lblCpf);
		
		JLabel lblTelefone = new JLabel("Telefone 1");
		lblTelefone.setBounds(10, 132, 178, 14);
		frmProjetoGdi.getContentPane().add(lblTelefone);
		
		final JFormattedTextField clientTel1 = new JFormattedTextField();
		clientTel1.setBounds(10, 157, 178, 20);
		frmProjetoGdi.getContentPane().add(clientTel1);
		
		final JFormattedTextField clientTel2 = new JFormattedTextField();
		clientTel2.setBounds(204, 157, 178, 20);
		frmProjetoGdi.getContentPane().add(clientTel2);
		
		JLabel lblTelefone_2 = new JLabel("Telefone 2");
		lblTelefone_2.setBounds(204, 132, 178, 14);
		frmProjetoGdi.getContentPane().add(lblTelefone_2);
		
		final JFormattedTextField clientTel3 = new JFormattedTextField();
		clientTel3.setBounds(396, 157, 178, 20);
		frmProjetoGdi.getContentPane().add(clientTel3);
		
		JLabel lblTelefone_3 = new JLabel("Telefone 3");
		lblTelefone_3.setBounds(396, 132, 178, 14);
		frmProjetoGdi.getContentPane().add(lblTelefone_3);
		
		final JSpinner productID = new JSpinner();
		productID.setBounds(10, 213, 178, 20);
		frmProjetoGdi.getContentPane().add(productID);
		
		JLabel lblTelefone_1 = new JLabel("ID do produto");
		lblTelefone_1.setBounds(10, 188, 178, 14);
		frmProjetoGdi.getContentPane().add(lblTelefone_1);
		
		JButton btnCadastarCliente = new JButton("Cadastar Cliente");
		btnCadastarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] telefones = new String[3];
				telefones[0] = clientTel1.getText();
				if (clientTel2.getText() != ""){
					telefones[1] = clientTel2.getText();
				}
				if (clientTel3.getText() != ""){
					telefones[2] = clientTel3.getText();
				}
				
				Object o = productID.getValue();
				Number n = (Number) o;
				int idProduto = n.intValue();
				
				Cliente novoCliente = new Cliente(clientCPF.getText(),clientName.getText(),telefones,idProduto);
				
				try {
					cad.cadastrar(novoCliente);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnCadastarCliente.setBounds(10, 268, 178, 23);
		frmProjetoGdi.getContentPane().add(btnCadastarCliente);
		
	}
}
