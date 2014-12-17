package restaurante;

public class ClienteNaoCadastradoException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4855606300053472516L;

	public  ClienteNaoCadastradoException(){
	super("Cliente nao cadastrado.");
	}
}
