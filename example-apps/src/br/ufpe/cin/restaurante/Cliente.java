package br.ufpe.cin.restaurante;

public class Cliente {

	private String cpf;
	private String telefone;
	private String endereco;
	private String nome;
	private String observacoes;
	
	public Cliente(String cpf, String telefone, String nome, String endereco, String observacoes){
		this.cpf = cpf;
		this.endereco = endereco;
		this.nome = nome;
		this.observacoes = observacoes;
		this.telefone = telefone;
	}

	public String getTelefone() {
		return telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
}
