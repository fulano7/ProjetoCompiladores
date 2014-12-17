package restaurante;

import java.math.BigDecimal;

public class Pedido {
	private String codigo;
	private Cliente cliente;
	private Produto[] produtos;
	private String data;
	private int quantidade;
	private BigDecimal valor;

	public Pedido(String codigo, Cliente cliente, Produto[] produtos,
			String data, int quantidade, BigDecimal valor) {
		this.cliente = cliente;
		this.codigo = codigo;
		this.data = data;
		this.produtos = produtos;
		this.quantidade = quantidade;
		this.valor = valor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Produto[] getProdutos() {
		return produtos;
	}

	public void setProdutos(Produto[] produtos) {
		this.produtos = produtos;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
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

	public String getCodigo() {
		return codigo;
	}

}
