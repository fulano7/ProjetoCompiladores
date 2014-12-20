package negocios.exceptions.sala;

public class SalaComEmprestimoException extends Exception {

	public SalaComEmprestimoException(String codigo) {
		super("Não foi possível excluir a sala [" + codigo
				+ "] porque ela está emprestada.");
	}
}
