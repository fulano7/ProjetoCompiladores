package dados.repositorio.lista;

import negocios.exceptions.pessoa.PessoaNaoEncontradaException;
import negocios.exceptions.pessoa.PessoaJaCadastradaException;
import dados.entidades.Pessoa;
import dados.repositorio.interfaces.RepositorioPessoa;
import dados.IteratorPessoa;
import dados.IterablePessoa;
import dados.lista.ListaPessoa;
import dados.lista.Node;

public class RepositorioPessoaLista implements IterablePessoa,
		RepositorioPessoa {

	private ListaPessoa cadastro;
	private int indice;

	public RepositorioPessoaLista() {
		cadastro = new ListaPessoa();
		indice = 0;
	}

	private int getIndice(String cpf) {

		int indice = -1;

		if (!cadastro.isEmpty()) {
			int i = 0;
			while (i < this.indice
					&& !(cadastro.get(i).getCpf().equals(cpf))) {
				i += 1;
			}

			if (i < this.indice) {
				indice = i;
			}
		}
		return indice;
	}

	@Override
	public void inserirPessoa(Pessoa pessoa) throws PessoaJaCadastradaException {
		int indice = getIndice(pessoa.getCpf());
		if (indice == -1 || indice >= this.indice) {
			Node no = new Node(pessoa);
			cadastro.insert(no);
			this.indice += 1;
		} else {
			throw new PessoaJaCadastradaException(pessoa.getCpf());
		}

	}

	@Override
	public void removerPessoa(String cpf) throws PessoaNaoEncontradaException {
		int indice = getIndice(cpf);

		if (indice == -1 || indice >= this.indice) {
			throw new PessoaNaoEncontradaException(cpf);
		} else {
			cadastro.remove(indice);
			this.indice -= 1;
		}

	}

	@Override
	public Pessoa procurarPessoa(String cpf)
			throws PessoaNaoEncontradaException {
		Pessoa pessoa = null;

		int indice = getIndice(cpf);
		if (indice == -1 || indice >= this.indice) {
			throw new PessoaNaoEncontradaException(cpf);
		} else {
			pessoa = cadastro.get(indice);
		}
		return pessoa;
	}

	@Override
	public void atualizarPessoa(Pessoa pessoa)
			throws PessoaNaoEncontradaException {
		int indice = getIndice(pessoa.getCpf());

		if (indice == -1 || indice >= this.indice) {
			throw new PessoaNaoEncontradaException(pessoa.getCpf());
		} else {
			Node no = new Node(pessoa);
			cadastro.set(indice, no);
		}

	}

	@Override
	public IteratorPessoa getIterator() {
		IteratorPessoa aux = new IteratorPessoa(cadastro);
		return aux;
	}

}
