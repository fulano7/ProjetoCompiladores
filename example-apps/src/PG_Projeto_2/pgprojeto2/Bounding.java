package pgprojeto2;

public class Bounding {
	double x_topo;
	double x_base;
	double y_topo;
	double y_base;
	double z_topo;
	double z_base;
	public Bounding(Objeto objeto){
		x_base = objeto.pontos_vista[0].x;
		y_base = objeto.pontos_vista[0].x;
		z_base = objeto.pontos_vista[0].x;
		x_topo = x_base;
		y_topo = y_base;
		z_topo = z_base;
		for (int i = 0; i < objeto.pontos_vista.length; i++) {
			//setando x_base e x_topo
			if(objeto.pontos_vista[i].x < x_base){
				x_base = objeto.pontos_vista[i].x;
			}
			if(objeto.pontos_vista[i].x > x_topo){
				x_topo = objeto.pontos_vista[i].x;
			}
			//setando y_base e y_topo como os respect valores menores e maiores dos pontos nas coord
			if(objeto.pontos_vista[i].y < y_base){
				y_base = objeto.pontos_vista[i].y;
			}			
			if(objeto.pontos_vista[i].y > y_topo){
				y_topo = objeto.pontos_vista[i].y;
			}
			//setando z_base e z_topo
			if(objeto.pontos_vista[i].z < z_base){
				z_base = objeto.pontos_vista[i].z;
			}
			if(objeto.pontos_vista[i].z > z_topo){
				z_topo = objeto.pontos_vista[i].z;
			}
			
			
		}
	}
}
