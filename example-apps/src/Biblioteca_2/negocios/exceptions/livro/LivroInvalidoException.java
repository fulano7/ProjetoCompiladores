package negocios.exceptions.livro;

public class LivroInvalidoException extends Exception {

	public LivroInvalidoException(String erro) {
		super("O campo [" + erro + "] está inválido.");
	}
}
