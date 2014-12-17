package restaurante;

public class ProdutoNaoCadastradoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7251989337883106223L;

	public ProdutoNaoCadastradoException() {
		super("Produto nao cadastrado.");
	}
}
