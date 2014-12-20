package negocios.exceptions.sala;

public class SalaNaoEncontradaException extends Exception {

	private String codigo;

	public SalaNaoEncontradaException(String codigo) {
		super("A sala [" + codigo + "] não está cadastrada no sistema.");
		this.codigo = codigo;
	}

	public String getCodigo() {
		return this.codigo;
	}
}
