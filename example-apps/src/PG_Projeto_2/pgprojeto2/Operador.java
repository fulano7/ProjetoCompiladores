package pgprojeto2;

public class Operador {

	static double produto_interno(Ponto u, Ponto v) {
		return (u.x * v.x) + (u.y * v.y) + (u.z * v.z);
	}

	static void normalizar(Ponto v){
		double produto_interno = produto_interno(v, v);
		double norma = Math.sqrt(produto_interno);
		v.x /= norma;
		v.y /= norma;
		v.z /= norma;
	}

	static Ponto produto_vetorial(Ponto u, Ponto v) {
		double i, j, k;
		i = (u.y*v.z) - (u.z*v.y);
		j = (u.z*v.x) - (u.x*v.z);
		k = (u.x*v.y) - (u.y*v.x);

		return new Ponto(i, j, k);
	}

	static double[] produto_fosco(double[] c1, double[] c2) {
		double[] retorno = { c1[0] * c2[0], c1[1] * c2[1], c1[2] * c2[2] };
		return retorno;
	}

	static double[] calcula_alfa_beta_gama(Ponto p, Ponto p1, Ponto p2, Ponto p3) {
		
		double x = p.x, y = p.y, x1 = p1.x, x2 = p2.x, x3 = p3.x, y1 = p1.y, y2 = p2.y, y3 = p3.y;
		double alfa, beta, gama;

		double denom = ((x1 - x3) * (y2 - y3) - (y1 - y3) * (x2 - x3)); 
		alfa = ((y2 - y3) * (x - x3) - ((x3 - x2) * (y3 - y))) / denom;
		beta = (((y3 - y1) * (x - x3)) - ((x1 - x3) * (y3 - y))) / denom;
		gama = 1.0 - alfa - beta;

		double[] retorno = { alfa, beta, gama };
		return retorno;
	}

	public static boolean inverter_normal(Ponto a, Ponto Normal, Ponto ponto_plano, Ponto centro){
		double x = a.x - ponto_plano.x;
		double y = a.y - ponto_plano.y;
		double z = a.z - ponto_plano.z;

		double lado_a = produto_interno(Normal, new Ponto(x, y, z));
		//lado_a fica sendo um sinal indicando o lado:
		lado_a = Math.signum(lado_a);
		
		x = centro.x - ponto_plano.x;
		y = centro.y - ponto_plano.y;
		z = centro.z - ponto_plano.z;

		double lado_centro = produto_interno(Normal, new Ponto(x, y, z));
		//lado_centro fica sendo um sinal indicando o lado:
		lado_centro = Math.signum(lado_centro);

		if (lado_a == lado_centro){		//ambos estão no mesmo lado:
			return true;	//mande inverter a normal
		} else if (lado_centro == 0){		//o centro do objeto pertence ao plano:
			if (lado_a < 0){				//se o centro do objeto pertencer ao plano, forçe a normal a estar do lado positivo do plano
				return true;
			} else {
				return false;
			}
		} else {
			return false;		//a normal já está apontando "para fora" do objeto, nao inverta
		}
	}

	public static boolean quaseProximo(double a, double b) {
		return Math.round(a) == Math.round(b);
	}

	public static int encontraLado(double ax, double ay, double bx, double by,
			double cx, double cy) {
		if (quaseProximo(bx - ax, 0)) { // vertical line
			if (cx < bx) {
				return by > ay ? 1 : -1;
			}
			if (cx > bx) {
				return by > ay ? -1 : 1;
			}
			return 0;
		}
		if (quaseProximo(by - ay, 0)) { // horizontal line
			if (cy < by) {
				return bx > ax ? -1 : 1;
			}
			if (cy > by) {
				return bx > ax ? 1 : -1;
			}
			return 0;
		}
		double coefic = (by - ay) / (bx - ax);
		double intersectY = ay - ax * coefic;
		double solocaoC = (coefic * cx) + intersectY;
		if (coefic != 0) {
			if (cy > solocaoC) {
				return bx > ax ? 1 : -1;
			}
			if (cy < solocaoC) {
				return bx > ax ? -1 : 1;
			}
			return 0;
		}
		return 0;
	}
}
