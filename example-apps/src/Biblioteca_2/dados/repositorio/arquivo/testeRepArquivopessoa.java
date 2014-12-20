package dados.repositorio.arquivo;

import java.io.IOException;

import negocios.exceptions.pessoa.PessoaJaCadastradaException;
import negocios.exceptions.pessoa.PessoaNaoEncontradaException;
import dados.entidades.Aluno;
import dados.entidades.Endereco;
import dados.entidades.Funcionario;
import dados.entidades.Pessoa;
import dados.repositorio.interfaces.RepositorioPessoa;

public class testeRepArquivopessoa {

	
	public static void main (String arg[]) {
		
		RepositorioPessoa rep = new RepositorioPessoaArquivo();
		
		
		Endereco endereco = new Endereco("Rua das calçadas", "30", "102", "84627222");
		
		Pessoa pessoa = new Aluno("Daniela", " 07583611490",endereco, " 35677567",
				"111");
		
		Endereco endereco2 = new Endereco("Rua das tormentas", "22", "205", "934857731");
		
		Pessoa pessoa2 = new Funcionario("Roberto", "2191386482", endereco2,"83666382", "123" );
		
		//try {
		//	rep.inserirPessoa(pessoa2);
		//} catch (PessoaJaCadastradaException | IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
	//	}
		
		
		//try {
		//	rep.inserirPessoa(pessoa);
	//	} catch (PessoaJaCadastradaException | IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
	//	}
		
		
		try {
			rep.removerPessoa(pessoa.getCpf());
		} catch (PessoaNaoEncontradaException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
