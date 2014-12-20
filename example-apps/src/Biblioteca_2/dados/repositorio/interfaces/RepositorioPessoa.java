package dados.repositorio.interfaces;

import java.io.IOException;

import negocios.exceptions.pessoa.PessoaJaCadastradaException;
import negocios.exceptions.pessoa.PessoaNaoEncontradaException;
import dados.entidades.Pessoa;
import dados.IteratorPessoa;

public interface RepositorioPessoa {

	void inserirPessoa(Pessoa pessoa) throws PessoaJaCadastradaException,
			IOException;

	void removerPessoa(String cpf) throws PessoaNaoEncontradaException,
			IOException;

	Pessoa procurarPessoa(String cpf) throws PessoaNaoEncontradaException,
			IOException;

	void atualizarPessoa(Pessoa pessoa) throws PessoaNaoEncontradaException,
			IOException;

	IteratorPessoa getIterator();

}
