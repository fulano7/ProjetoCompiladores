package br.ufpe.cin.restaurante;

import java.math.BigDecimal;

public class Produto {
	private String codigo;
	private String nome;
	private String descricao;
	private char tamanho;
	private BigDecimal valor;
	
	public Produto(String codigo, String nome, String descricao, char tamanho, BigDecimal valor){
		this.codigo = codigo;
		this.nome = nome;
		this.descricao = descricao;
		this.tamanho = tamanho;
		this.valor = valor;
	}

	public String getCodigo() {
		return codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public char getTamanho() {
		return tamanho;
	}

	public void setTamanho(char tamanho) {
		this.tamanho = tamanho;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
}
