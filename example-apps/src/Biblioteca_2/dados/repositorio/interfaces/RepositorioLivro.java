package dados.repositorio.interfaces;

import negocios.exceptions.livro.LivroJaCadastradoException;
import negocios.exceptions.livro.LivroNaoEncontradoException;
import java.io.IOException;
import dados.entidades.Livro;
import dados.IteratorLivro;

public interface RepositorioLivro {

	void inserirLivro(Livro livro) throws LivroJaCadastradoException,
			IOException;

	void removerLivro(String codigo) throws LivroNaoEncontradoException,
			IOException;

	Livro procurarLivro(String codigo) throws LivroNaoEncontradoException,
			IOException;

	void atualizarLivro(Livro livro) throws LivroNaoEncontradoException,
			IOException;

	IteratorLivro getIterator();

}
