package br.ufpe.cin.exception;

public class PedidoNaoExistenteException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9143022182046227046L;

	public PedidoNaoExistenteException(){
		super("Pedido nao existente.");
	}
}
