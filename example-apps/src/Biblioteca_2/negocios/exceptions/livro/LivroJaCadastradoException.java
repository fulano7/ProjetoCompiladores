package negocios.exceptions.livro;

public class LivroJaCadastradoException extends Exception {

	private String codigo;

	public LivroJaCadastradoException(String codigo) {
		super("O livro " + codigo + " já está cadastrado no sistema");
		this.codigo = codigo;
	}

	public String getCodigo() {
		return this.codigo;
	}

}
