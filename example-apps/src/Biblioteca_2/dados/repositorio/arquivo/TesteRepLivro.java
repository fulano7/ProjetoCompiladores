package dados.repositorio.arquivo;

import java.io.IOException;

import negocios.exceptions.livro.LivroJaCadastradoException;
import negocios.exceptions.livro.LivroNaoEncontradoException;
import dados.entidades.Livro;
import dados.repositorio.interfaces.RepositorioLivro;

public class TesteRepLivro {
	
	public static void main (String []args){
		
		
		RepositorioLivro rep = new RepositorioLivroArquivo();
		
		Livro livro1 = new Livro("222", "O poema", "David B.", "poemas", "Coletânea com mais de 100 poemas");
		Livro livro2 = new Livro("231", "A viagem", "Nathan R.", "Aventura", "A vida de Nathan, o viajante");
		
		
	
		try {
			rep.inserirLivro(livro1);
		} catch (LivroJaCadastradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		
		
		
	}
}
