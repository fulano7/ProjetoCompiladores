package models;

public class Cliente {
	
	private String nome, cpf;
	private String[] telefones;
	private int idProduto;
	
	public Cliente(String cpf, String nome, String[] telefones, int idProduto){
		this.nome = nome;
		this.cpf = cpf;
		this.telefones = telefones;
		this.idProduto = idProduto;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public String getCpf(){
		return this.cpf;
	}
	
	public String[] getTelefones(){
		return this.telefones;
	}
	
	public int getIdProduto(){
		return this.idProduto;
	}
	
}
