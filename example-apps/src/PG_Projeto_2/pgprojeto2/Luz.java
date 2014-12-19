package pgprojeto2;

public class Luz {
	Ponto Pl, Ia;
	double ka, kd, ks, n;
	double[] Ip;
	double[] Od;
	double[] Id;

	Luz(Ponto Pl, Ponto Ia, double ka, double kd, double ks, double n,
			double[] Ip, double[] Od, double[] Id) {
		this.Pl = Pl;
		this.Ia = Ia;
		this.ka = ka;
		this.kd = kd;
		this.ks = ks;
		this.n = n;
		this.Ip = Ip;
		this.Od = Od;
		this.Id = Id;
	}
}
