package br.ufpe.cin.dados;

import java.util.Iterator;

import br.ufpe.cin.exception.ProdutoJaCadastradoException;
import br.ufpe.cin.exception.ProdutoNaoCadastradoException;
import br.ufpe.cin.restaurante.Produto;

public class RepositorioProdutosArray implements IRepositorio<Produto>{
	
	private Produto[] produtos;
	private int indice;

	public RepositorioProdutosArray(int tamanhoArray) {
		this.produtos = new Produto[tamanhoArray];
		this.indice = 0;
	}

	public void inserir(Produto produto)
			throws ProdutoJaCadastradoException {
		if (this.procurar(produto.getCodigo()) == null) {
			produtos[indice] = produto;
			indice++;
		} else {
			throw new ProdutoJaCadastradoException();
		}
	}

	public void remover(Produto produto)
			throws ProdutoNaoCadastradoException {
		if (this.procurar(produto.getCodigo()) == null) {
			throw new ProdutoNaoCadastradoException();
		} else {
			produtos[this.getPosicao(produto)] = produtos[indice - 1];
			produtos[indice - 1] = null;
			indice--;

		}
	}

	private Produto procurar(String codigo) {
		Produto retorno = null;
		boolean achou = false;
		for (int i = 0; i < indice && !achou; i++) {
			if (produtos[i].getCodigo().equals(codigo)) {
				retorno = produtos[i];
			}
		}
		return retorno;
	}

	private int getPosicao(Produto produto) {
		int resposta = 0;
		for (int i = 0; i < indice; i++) {
			if (produtos[i].getCodigo() == produto.getCodigo()) {
				resposta = i;
			}
		}
		return resposta;
	}

	@Override
	public Iterator<Produto> procurar(Produto valor) {
		// TODO Auto-generated method stub
		return null;
	}
}
