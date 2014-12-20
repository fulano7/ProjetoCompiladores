package negocios.exceptions.livro;

public class AcervoIndisponivelException extends Exception {

	public AcervoIndisponivelException(String codigo) {
		super("O livro [" + codigo + "] não está disponível no acervo.");
	}
}
