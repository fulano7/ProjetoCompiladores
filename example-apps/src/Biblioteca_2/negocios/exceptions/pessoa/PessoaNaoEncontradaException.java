package negocios.exceptions.pessoa;

public class PessoaNaoEncontradaException extends Exception {

	private String cpf;

	public PessoaNaoEncontradaException(String cpf) {
		super("A pessoa " + cpf + " não está cadastrada no sistema.");
		this.cpf = cpf;
	}

	public String getCpf() {
		return this.cpf;
	}

}
