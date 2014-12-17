package restaurante;

import java.util.Iterator;

public interface IRepositorio<T> {
	void inserir(T valor) throws ClienteJaCadastradoException,
			ProdutoJaCadastradoException;

	void remover(T valor) throws ClienteNaoCadastradoException,
			PedidoNaoExistenteException, ProdutoNaoCadastradoException;

	Iterator<T> procurar(T valor);
}
