package negocios.exceptions;

public class RealizarLoginException extends Exception {

	public RealizarLoginException(){
		super("Para realizar esta ação você deve está logado no sistema");
	}
}
