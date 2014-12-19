package pgprojeto2;

class Objeto{
	Ponto pontos_vista[], pontos_tela[], normais_triangulos[], normais_vertices[];
	int triangulos[][];
	
	public Objeto(Ponto[] pontos_visao, Ponto[] pontos_tela,
			Ponto[] normais_triangulos, Ponto[] normais_vertices,
			int[][] triangulos) {
		this.pontos_vista = pontos_visao;
		this.pontos_tela = pontos_tela;
		this.normais_triangulos = normais_triangulos;
		this.normais_vertices = normais_vertices;
		this.triangulos = triangulos;
	}
	
}
