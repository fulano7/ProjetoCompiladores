
public class Ponto implements Comparable<Ponto> {
	double x, y, z, s, t;
	int indice;
	int i, j;
	double Od[];
	
	Ponto(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		Od = new double[3];
		Od[0] = 0.0; Od[1] = 0.0; Od[1] = 0.0;
	}

	public int compareTo(Ponto p) {
		return (int) Math.signum(y - ((Ponto) p).y);
	}
	
	public String para_string(){
		return "(" + this.x + ", " + this.y + ", " + this.z + ")";
	}
}
