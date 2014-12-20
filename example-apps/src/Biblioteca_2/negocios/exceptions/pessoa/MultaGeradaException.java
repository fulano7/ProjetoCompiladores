package negocios.exceptions.pessoa;



public class MultaGeradaException extends Exception {

	public MultaGeradaException(String valor) {

		super(
				"Atraso! Para realizar a devolução deste item é necessário o pagamento da quantia ["
						+ valor + "].");
	}
}
