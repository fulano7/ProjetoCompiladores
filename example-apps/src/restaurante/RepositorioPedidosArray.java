package restaurante;

import java.util.Iterator;


public class RepositorioPedidosArray {
	private Pedido[] pedidos;
	private int indice;
	//  implements IRepositorio<Pedido>

	public RepositorioPedidosArray(int tamanhoArray) {
		this.pedidos = new Pedido[tamanhoArray];
		this.indice = 0;
	}

	public void inserir(Pedido pedido) {
		this.pedidos[indice] = pedido;
		this.indice++;
	}

	public void remover(Pedido pedido)
			throws PedidoNaoExistenteException {
		if (this.procurar(pedido.getCodigo()) == null) {
			throw new PedidoNaoExistenteException();
		} else {
			pedidos[this.getPosicao(pedido)] = pedidos[indice - 1];
			pedidos[indice - 1] = null;
			indice--;

		}
	}

	private Pedido procurar(String codigo) {
		Pedido retorno = null;
		boolean achou = false;
		for (int i = 0; i < indice && !achou; i++) {
			if (pedidos[i].getCodigo().equals(codigo)) {
				retorno = pedidos[i];
			}
		}
		return retorno;
	}

	private int getPosicao(Pedido pedido) {
		int posicao = 0;
		for (int i = 0; i < indice; i++) {
			if (pedidos[i].getCodigo() == pedido.getCodigo()) {
				posicao = i;
			}
		}
		return posicao;
	}

	// @Override
	public Iterator<Pedido> procurar(Pedido valor) {
		// TODO Auto-generated method stub
		return null;
	}
}
