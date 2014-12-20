package negocios.exceptions.livro;

public class LivroNaoEncontradoException extends Exception {

	private String codigo;

	public LivroNaoEncontradoException(String codigo) {
		super("O livro [" + codigo + "] não está cadastrado no sistema");
		this.codigo = codigo;
	}

	public String getCodigo() {
		return this.codigo;
	}
}
