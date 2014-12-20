package dados.lista;

import dados.entidades.Aluno;
import dados.entidades.Funcionario;
import dados.entidades.Pessoa;
import dados.IteratorPessoa;

public class TesteLista {

	public static void main(String[] args) {

		ListaPessoa lista = new ListaPessoa();
		for (int i = 0; i < 20; i += 1) {
			String name = "" + (i * 100);
			Funcionario a = new Funcionario(name, name, name);
			Node no = new Node(a);
			lista.insert(no);
		}

		Funcionario a = new Funcionario("CAIO CALADO", "CAIO CALADO",
				"CAIO CALADO");
		Node no = new Node(a);
		lista.set(1, no);

		System.out.println(lista.print());
		System.out.println(lista.size());

	}
}
