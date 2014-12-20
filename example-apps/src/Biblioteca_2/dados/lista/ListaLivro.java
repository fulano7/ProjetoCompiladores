package dados.lista;

import dados.entidades.Livro;
import dados.entidades.Pessoa;

public class ListaLivro extends Lista {

	public ListaLivro() {
		super();
	}

	public Livro get(int index) {
		Livro livro = null;
		
		if (!isEmpty()) {
			if (index <= this.index) {
				if (index == 0) {
					// remover o root
					livro = (Livro) this.root.getObjeto();
				} else {
					//remover qualquer posição
					int i = 0;
					Node aux = this.root;
					while (aux != null && i != index) {
						i+= 1;
						aux = aux.getNext();
					}
					livro = (Livro) aux.getObjeto();					
				}
			}
		}		
		return livro;
	}
}
