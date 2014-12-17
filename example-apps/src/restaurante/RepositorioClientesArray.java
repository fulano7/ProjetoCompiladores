package restaurante;

import java.util.Iterator;

public class RepositorioClientesArray implements IRepositorio<Cliente> {
	private Cliente[] clientes;
	private int indice;

	public RepositorioClientesArray(int tamanhoArray) {
		this.clientes = new Cliente[tamanhoArray];
		this.indice = 0;
	}

	public void inserir(Cliente cliente)
			throws ClienteJaCadastradoException {
		if (this.procurar(cliente.getTelefone()) == null) {
			clientes[indice] = cliente;
			indice++;
		} else {
			throw new ClienteJaCadastradoException();
		}
	}

	public void remover(Cliente cliente) throws ClienteNaoCadastradoException {
		if (this.procurar(cliente.getTelefone()) == null) {
			throw new ClienteNaoCadastradoException();
		} else {
			clientes[this.getPosicao(cliente)] = clientes[indice - 1];
			clientes[indice - 1] = null;
			indice--;

		}
	}

	private Cliente procurar(String telefone) {
		Cliente retorno = null;
		boolean achou = false;
		for (int i = 0; i < indice && !achou; i++) {
			if (clientes[i].getTelefone().equals(telefone)) {
				retorno = clientes[i];
			}
		}
		return retorno;
	}

	private int getPosicao(Cliente cliente) {
		int resposta = 0;
		for (int i = 0; i < indice; i++) {
			if (clientes[i].getTelefone() == cliente.getTelefone()) {
				resposta = i;
			}
		}
		return resposta;
	}

	@Override
	public Iterator<Cliente> procurar(Cliente valor) {
		// TODO Auto-generated method stub
		return null;
	}
}
