package negocios.exceptions.pessoa;

public class TelefoneInvalidoException extends Exception{
	
	private String telefone;
	
	
	public TelefoneInvalidoException (String telefone){
		super("O número de telefone [" + telefone +"] está inválido");
		
	}

}
