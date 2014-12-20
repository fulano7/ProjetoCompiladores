package negocios.exceptions.sala;

public class SalaJaCadastradaException extends Exception {

	private String codigo;

	public SalaJaCadastradaException(String codigo) {
		super("A sala [" + codigo + "] já está cadastrada no sistema.");
		this.codigo = codigo;
	}

	public String getCodigo() {
		return this.codigo;
	}
}
