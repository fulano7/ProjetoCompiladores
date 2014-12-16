package br.ufpe.cin.dados;

import java.util.Iterator;

import br.ufpe.cin.exception.ClienteJaCadastradoException;
import br.ufpe.cin.exception.ClienteNaoCadastradoException;
import br.ufpe.cin.exception.PedidoNaoExistenteException;
import br.ufpe.cin.exception.ProdutoJaCadastradoException;
import br.ufpe.cin.exception.ProdutoNaoCadastradoException;

public interface IRepositorio<T> {
	void inserir(T valor) throws ClienteJaCadastradoException,
			ProdutoJaCadastradoException;

	void remover(T valor) throws ClienteNaoCadastradoException,
			PedidoNaoExistenteException, ProdutoNaoCadastradoException;

	Iterator<T> procurar(T valor);
}
