package negocios.exceptions.livro;

public class LivroIndisponivelException extends Exception {

	public LivroIndisponivelException(String codigo) {
		super("O livro [" + codigo + "] encontra-se indisponível no momento.");
	}
}
