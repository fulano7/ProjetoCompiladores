package restaurante;

public class ClienteJaCadastradoException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6490933969455438764L;

	public ClienteJaCadastradoException(){
		super("O cliente ja esta cadastrado.");
	}
}
