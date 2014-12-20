package dados.repositorio.arquivo;

import java.io.IOException;

import negocios.ManipularLivro;
import negocios.ManipularPessoa;
import negocios.ManipularSala;
import negocios.exceptions.RealizarLoginException;
import negocios.exceptions.livro.LivroJaCadastradoException;
import negocios.exceptions.pessoa.PessoaJaCadastradaException;
import negocios.exceptions.sala.SalaJaCadastradaException;
import negocios.exceptions.sala.SalaNaoEncontradaException;
import dados.entidades.Aluno;
import dados.entidades.Endereco;
import dados.entidades.Livro;
import dados.entidades.Pessoa;
import dados.entidades.Sala;
import dados.repositorio.interfaces.RepositorioLivro;
import dados.repositorio.interfaces.RepositorioPessoa;
import dados.repositorio.interfaces.RepositorioSala;

public class TesteRepArquivo {
	public static void main (String arg[]) {
		
		
		RepositorioSala rep = new RepositorioSalaArquivo();
		
		
		Sala sala = new Sala("21", 13);
		Sala sala2 = new Sala("22", 11);
		Sala sala3 = new Sala("23",17);
		
		sala.setDisponivel(false);
		sala.setTotalEmprestimos(2);
		sala.setCpfResponsavel("07583611490");
		sala.setDataDevolucao("22/07/1994");
		try {
			Sala resposta = rep.procurarSala(sala.getCodigo());
			
		rep.atualizarSala(sala);	
			
			System.out.println(sala.getCapacidade());
			System.out.println(sala.getDataDevolucao());
			System.out.println(sala.getTotalEmprestimos());
			System.out.println(sala.getCpfResponsavel());
		System.out.println(sala.isDisponivel())	;
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SalaNaoEncontradaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
	}

}
