package negocios.exceptions.livro;

public class LivroComEmprestimoException extends Exception {

	public LivroComEmprestimoException(String codigo) {
		super("Não foi possível excluir o livro [" + codigo
				+ "] porquê ele está emprestado.");
	}
}
