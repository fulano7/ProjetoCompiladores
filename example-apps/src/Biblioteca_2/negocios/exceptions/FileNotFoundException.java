package negocios.exceptions;

public class FileNotFoundException extends Exception {

	private String nome;

	public FileNotFoundException(String nome) {
		super("O arquivo " + nome + " não foi encontrado");
		this.nome = nome;
	}

	public String getNome() {
		return this.nome;
	}

}
