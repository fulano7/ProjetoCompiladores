package negocios.exceptions.pessoa;

public class PessoaJaCadastradaException extends Exception {

	private String cpf;

	public PessoaJaCadastradaException(String cpf) {
		super("A pessoa " + cpf + " já está cadastrada no sistema.");
		this.cpf = cpf;
	}

	public String getCpf() {
		return this.cpf;
	}

}
