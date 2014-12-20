package negocios.exceptions.endereco;

public class EnderecoInvalidoException extends Exception {

	public EnderecoInvalidoException(String erro) {
		super("O campo [" + erro + "] está inválido.");
	}

}
