
class Camera{
	Ponto C, V, N, U;
	double d, hx, hy;
	
	public Camera(Ponto C, Ponto V, Ponto N, Ponto U, double d, double hx, double hy) {
		this.C = C;
		this.V = V;
		this.N = N;
		this.U = U;
		this.d = d;
		this.hx = hx;
		this.hy = hy;
	}
}
