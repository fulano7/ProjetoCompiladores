package testes;

import java.util.Iterator;

import classesBase.Aluno;
import classesBase.Endereco;
import classesBase.Pessoa;
import classesBase.Turma;

import dados.RepositorioArrayPessoa;

public class testeIteratorArray {

	
	public static void main(String[] args){
		Endereco end = new Endereco("sddsad", "sdasd", "Sadasd", "sdsad",
				"asd", "", "asdasd");
		Turma turma = new Turma("tumrma1");
		Pessoa bruna = new Aluno("43536787656", "Bruna","", "7727724", "F","", end,
				"Luiz", "Nancy", turma);
		
		RepositorioArrayPessoa repositorio = new RepositorioArrayPessoa();
		repositorio.inserir(bruna);
		Iterator<Pessoa> it = repositorio.getIterator();
		while(it.hasNext()){
			System.out.println(it.next().getNome());
		}
	}
	
	
	
}

