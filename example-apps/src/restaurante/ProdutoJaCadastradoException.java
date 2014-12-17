package restaurante;

public class ProdutoJaCadastradoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3967223092703092831L;

	public ProdutoJaCadastradoException() {
		super("Produto ja cadastrado.");
	}
}
