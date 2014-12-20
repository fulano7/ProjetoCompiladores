package negocios.exceptions.pessoa;

public class AlunoComEmprestimoException extends Exception {

	public AlunoComEmprestimoException(String cpf, int total) {
		super("Não foi possível remover o aluno [" + cpf
				+ "] pois ele contêm " + total + " livro(s) emprestado(s).");
	}
}
