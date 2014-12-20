package dados.repositorio.interfaces;

import java.io.IOException;

import negocios.exceptions.sala.SalaJaCadastradaException;
import negocios.exceptions.sala.SalaNaoEncontradaException;
import dados.entidades.Sala;
import dados.IteratorSala;

public interface RepositorioSala {

	void inserirSala(Sala sala) throws SalaJaCadastradaException, IOException;

	void removerSala(String codigo) throws SalaNaoEncontradaException,
			IOException;

	Sala procurarSala(String codigo) throws SalaNaoEncontradaException,
			IOException;

	void atualizarSala(Sala sala) throws SalaNaoEncontradaException,
			IOException;

	IteratorSala getIterator();

}
