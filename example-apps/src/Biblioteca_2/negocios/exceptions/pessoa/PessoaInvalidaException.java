package negocios.exceptions.pessoa;

public class PessoaInvalidaException extends Exception {

	public PessoaInvalidaException(String cpf) {
		super("A pessoa [" + cpf
				+ "] não tem permissão para realizar esta ação.");
	}

	public PessoaInvalidaException(String cpf, char tipoExcecao) {
		super("A pessoa [" + cpf + "] não pode ser excluida.");
	}

}
