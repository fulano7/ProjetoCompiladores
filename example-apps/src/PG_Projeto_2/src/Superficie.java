import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.util.Arrays;

import javax.swing.JPanel;

public class Superficie extends JPanel {
	Camera camera;
	Objeto objeto;
	Luz lux;
	Box box; //bounding box
	int pixels[];
	int objIndex;
	Bounding box2;

	double z_buffer[][];
	boolean is_superficie;
	Graphics2D plataforma2d;

	private static final long serialVersionUID = 2L;

	public Superficie(Camera camera, Objeto objeto, Luz lux, Box box, Bounding box2) {
		this.camera = camera;
		this.objeto = objeto;
		this.lux = lux;
		this.box = box;
		this.box2 = box2;
		this.objIndex = 0;
	}

	public int[] mapping(Ponto P){		
		//fator fx
		double fx, fy, fz;
		if((P.x - box2.x_base) > 0){
			fx = (double) (P.x - box2.x_base)/(box2.x_topo - box2.x_base);
		}
		else{
			fx = Double.MIN_VALUE;
		}
		//fator fy
		if((P.y - box2.y_base) > 0){
			fy = (double) (P.y - box2.y_base)/(box2.y_topo - box2.y_base);
		}
		else{
			fy = Double.MIN_VALUE;
		}
		//fator fz
		if((P.z - box2.z_base) > 0){
			fz = (double) (P.z - box2.z_base)/(box2.z_topo - box2.z_base);
		}
		else{
			fz = Double.MIN_VALUE;
		}

		//pega a quantidade de imagens no box
		int qtdImagens = box.imagem.length;

		//converte o fz para inteiro multiplicado pela qtde de imagens (indica qual imagem a ser mapeada)
		fz =  Math.ceil(fz*qtdImagens);	
		
//		System.out.println(fx);
//		System.out.println(fy);
//		System.out.println(fz);

		int fzInt = (int) fz; 

		if(fzInt == 0) fzInt = 1; //caso fz seja 0
		if(fzInt > qtdImagens) fzInt = qtdImagens;
		
		int resY = box.imagem[fzInt-1].getHeight(); int resX = box.imagem[fzInt-1].getWidth();

		fy = Math.ceil(fy*resY); fx = Math.ceil(fx*resX);

		//acessa valor de fy inteiro pela resolucao da imagem fzInt
		int fyInt = (int) fy;		
		//acessa valor de fx inteiro pela resolucao da imagem fzInt no comprimento
		int fxInt = (int) fx;

		int[] F = {fxInt, fyInt, fzInt};

		return F; 
	}

	public void zoom(){
		if(camera.d > 0){
			camera.C.z -= 10;
			camera.N.z -= 10;
			camera.U.z -= 10;
			camera.V.z -= 10;
			camera.d -= 10;
		}
	}

	//	public void mappingText(){
	//		int pixels[] = box.imagem.getRGB(0, 0, box.comp, box.alt, null, 0, box.comp);
	//		//indice do ponto corrente no objeto
	//		int objIndex = 0;
	//		//enquanto nao terminou de varrer para todos os ptos de visao
	//		while(objIndex < objeto.pontos_vista.length){
	//			for (int i = 0; i < box.comp; i++) {
	//				for (int j = 0; j < box.alt; j++) {
	//					//					fx *= box.imagem.getWidth();
	//					//
	//					//					//fator fy
	//					//					double fy = (double) (box.pontos[i][j].y - box.y_base())/(box.y_topo() - box.y_base());
	//					//					fy *= box.imagem.getHeight();
	//
	//					//posicao correspondente do pixel do ponto na textura
	//					//int posTexelX = (int) (objeto.pontos_visao[objIndex].x*fx);				
	//					//int posTexelY = (int) (objeto.pontos_visao[objIndex].y*fy);
	//
	//					//capturado o inteiro do componente na matriz de pixels da imagem					
	//					//					int index = (int)(box.comp*fx + fy);
	//					//					if(index < pixels.length){
	//					int componente = pixels[box.comp*j + i];					
	//					Color cor = new Color(componente);
	//
	//					//setando componentes RGB para o Od do pto
	//					if(objIndex >= objeto.pontos_vista.length){
	//						break;
	//					}
	//					objeto.pontos_vista[objIndex].Od[0] = (double) cor.getRed()/255;
	//					objeto.pontos_vista[objIndex].Od[1] = (double) cor.getGreen()/255;
	//					objeto.pontos_vista[objIndex].Od[2] = (double) cor.getBlue()/255;
	//					objIndex++;
	//
	//				}
	//			}
	//		}
	//	}

	private void scanline(int c, Ponto P1, Ponto P2, Ponto P3, double y,
			double x_min, double x_max) {
		Ponto P;

		for (int x = (int) Math.round(x_min); x <= Math.round(x_max); x++) {
			double[] temp = Operador.calcula_alfa_beta_gama(new Ponto(x, y, 0),
					P1, P2, P3);
			double alfa = temp[0];
			double beta = temp[1];
			double gama = temp[2];

			// pega os pontos de visao:
			Ponto P1_visao, P2_visao, P3_visao;

			P1_visao = objeto.pontos_vista[P1.indice];
			P2_visao = objeto.pontos_vista[P2.indice];
			P3_visao = objeto.pontos_vista[P3.indice];

			// calcula o ponto em coordenadas de visao:

			P = new Ponto(alfa * P1_visao.x + beta * P2_visao.x + gama
					* P3_visao.x, alfa * P1_visao.y + beta * P2_visao.y + gama
					* P3_visao.y, alfa * P1_visao.z + beta * P2_visao.z + gama
					* P3_visao.z);	


			//recebendo fatores de transformacao de textura na bounding box
			
			//Baricentro
			double Bari_X = ((P1_visao.x ) + (P2_visao.x ) + (P3_visao.x )) / 3;
			double Bari_Y = ((P1_visao.y ) + (P2_visao.y ) + (P3_visao.y )) / 3;
			double Bari_Z = ((P1_visao.z ) + (P2_visao.z ) + (P3_visao.z )) / 3;
			
			Ponto bari = new Ponto(Bari_X, Bari_Y, Bari_Z);
			
			double p1Dist_X = Math.pow((( alfa) - 0.33), 2);
			double p1Dist_Y = Math.pow((( beta) - 0.33), 2);
			double p1Dist_Z = Math.pow((( gama) - 0.33), 2);
			
			double result = Math.sqrt(p1Dist_X + p1Dist_Y + p1Dist_Z);

			//calculando Od da textura para cada ponto no scan
			if(box != null){
				int[] F = mapping(P);
				//pega a cor da fz-esima imagem, acessando a cor RGB na posicao fx, fy
				if(F[0] > box.imagem[F[2]-1].getWidth()){
					F[0] = box.imagem[F[2]-1].getWidth();
				}
				if(F[1] > box.imagem[F[2]-1].getHeight()){
					F[1] = box.imagem[F[2]-1].getHeight();
				}
				Color cor = new Color(box.imagem[F[2]-1].getRGB(F[0]-1, F[1]-1));
				P.Od[0] = (double) cor.getRed()/255;
				P.Od[1] = (double) cor.getGreen()/255;
				P.Od[2] = (double) cor.getBlue()/255;
			}
//
//			if (result >= 0 && result <= 0.1) {
//				P.Od[0] = 1.0;
//				P.Od[1] = 0.0;
//				P.Od[2] = 0.0;
//			}else if (result >= 0.2 && result <= 0.3) {
//				P.Od[0] = 0.0;
//				P.Od[1] = 1.0;
//				P.Od[2] = 0.0;
//			}else if(result > 0.3){
//				P.Od[0] = 0.0;
//				P.Od[1] = 0.0;
//				P.Od[2] = 1.0;
//			}
			
			// continua se esse ponto for mais na frente do que o que já havia
			// sido pintado:
			if (x >= 0 && y >= 0 && x < z_buffer.length
					&& y < z_buffer[0].length) {
				if (P.z < z_buffer[(int) x][(int) y] && P.z > 0) {
					// System.out.println(" :   " + alfa + beta + gama);
					// atualiza o z-buffer:
					z_buffer[(int) x][(int) y] = P.z;
					Ponto N = null;

					// cria os vetores N1, N2 e N3:
					Ponto N1 = objeto.normais_vertices[P1.indice];
					Ponto N2 = objeto.normais_vertices[P2.indice];
					Ponto N3 = objeto.normais_vertices[P3.indice];

					// calcula o vetor N:
					N = new Ponto(alfa * N1.x + beta * N2.x + gama * N3.x, alfa
							* N1.y + beta * N2.y + gama * N3.y, alfa * N1.z
							+ beta * N2.z + gama * N3.z);

					// normaliza N:
					Operador.normalizar(N);

					Ponto V = new Ponto((-1) * P.x, (-1) * P.y, (-1) * P.z);
					// Se N não estiver apontando para o observador:

					if (Operador.produto_interno(V, N) < 0) {
						// inverte N:
						N.x *= -1;
						N.y *= -1;
						N.z *= -1;
					}

					double[] cor = pincel(P, N);
					plataforma2d.setColor(new Color((int) cor[0], (int) cor[1],
							(int) cor[2]));
					plataforma2d.drawLine((int) x, (int) y, (int) x, (int) y);

				}
			}
		}
	}

	private void doDrawing(Graphics g) {
		plataforma2d = (Graphics2D) g;

		Dimension size = getSize();
		// objeto parametrizado para laterais da janela:
		Insets insets = getInsets();
		//largura e altura da TELA:
		int w = size.width - insets.left - insets.right;
		int h = size.height - insets.top - insets.bottom;
		// ------------------------------------------------------------------------
		// ajusta as coordenadas de tela:
		for (int c = 0; c < objeto.pontos_tela.length; c++) { // objeto.pontos_tela.length
			objeto.pontos_tela[c].x = (int) ((objeto.pontos_tela[c].x + 1) * w / 2);
			objeto.pontos_tela[c].y = (int) ((1 - objeto.pontos_tela[c].y) * h / 2);
		}

		// ------------------------------------------------------------------------
		// inicializa o z-buffer:
		z_buffer = new double[w + 10][h + 10];
		for (int c = 0; c < w; c++) {
			for (int i = 0; i < h; i++) {
				z_buffer[c][i] = Double.MAX_VALUE;
			}
		}
		
		// ---------------- SCANNER
		// -----------------------------------------------
		int limite;
		limite = objeto.triangulos.length;

		for (int c = 0; c < limite; c++) {
			boolean reta_change = false;
			Ponto P1 = null, P2, P3;
			double x_min, x_max, y_max, y_min;
			Ponto[] vertices = new Ponto[3];
			// inicializa um array de vertices, diz se eh uma superficie
			vertices[0] = objeto.pontos_tela[objeto.triangulos[c][0]];
			vertices[1] = objeto.pontos_tela[objeto.triangulos[c][1]];
			vertices[2] = objeto.pontos_tela[objeto.triangulos[c][2]];
			// ordena pelo y em ordem crescente
			Arrays.sort(vertices);
			// P1 é o vértice de menor y:
			P1 = vertices[0];

			if (Operador.encontraLado(P1.x, (-1) * P1.y, vertices[1].x, (-1)
					* vertices[1].y, vertices[2].x, (-1) * vertices[2].y) > 0) {
				P2 = vertices[1];
				P3 = vertices[2];
			} else if (Operador.encontraLado(P1.x, (-1) * P1.y, vertices[1].x, (-1)
					* vertices[1].y, vertices[2].x, (-1) * vertices[2].y) < 0) {
				P2 = vertices[2];
				P3 = vertices[1];
			} else {
				if (vertices[1].x < P1.x && vertices[2].x < P1.x) { //  P1 à direita de P2 e P3
					P2 = vertices[1];
					P3 = vertices[2];
				} else if (vertices[1].x > P1.x && vertices[2].x > P1.x) { // P1 à esq de P2 e P3
					P2 = vertices[2];
					P3 = vertices[1];					

				} else { //se nao calcula P2 e P3 pelo valor de x na coluna
					if (vertices[1].x < vertices[2].x) {
						P2 = vertices[1];
						P3 = vertices[2];
					} else {
						P2 = vertices[2];
						P3 = vertices[1];
					}
				}
			}
			//verifica se ptos formam um triangulo
			boolean not_triangle = ((int) P1.x == (int) P2.x && (int) P1.y == (int) P2.y)
					|| ((int) P1.x == (int) P3.x && (int) P1.y == (int) P3.y)
					|| ((int) P2.x == (int) P3.x && (int) P2.y == (int) P3.y);

			if (!not_triangle) {
				y_max = vertices[2].y;
				y_min = P1.y;

				double a1, a2, a3;
				a1 = ((double) ((int) P2.y - (int) P1.y) / (double) ((int) P2.x - (int) P1.x));
				// calcula a2:
				a2 = ((double) ((int) P3.y - (int) P1.y) / (double) ((int) P3.x - (int) P1.x));
				// calcula a3:
				a3 = ((double) ((int) P3.y - (int) P2.y) / (double) ((int) P3.x - (int) P2.x));
				// valores de x_min e x_max inicio
				x_min = x_max = P1.x;

				if (Math.abs(P1.y - P2.y) == 0) { // P1 e P2 estao na mesma
					// linha horizontal:
					reta_change = true;
					x_min = Math.min(P1.x, P2.x);
					x_max = Math.max(P1.x, P2.x);
					a1 = a3;
				} else if (Math.abs(P1.y - P3.y) == 0) { // P1 e P3 estao na mesma linha
					reta_change = true;
					x_min = Math.min(P1.x, P3.x);
					x_max = Math.max(P1.x, P3.x);
					a2 = a3;
				}

				for (int y = (int) y_min; y <= (int) y_max; y++) {
					// verifica se o ponto do meio foi alcançado
					scanline(c, P1, P2, P3, y, x_min, x_max);
					if (!reta_change && (y == (int) P2.y || y == (int) P3.y)) {
						if (Math.abs(y - P2.y) == 0) {
							a1 = a3;
						} else {
							a2 = a3;
						}
						reta_change = true;
					}
					if (a1 != Double.POSITIVE_INFINITY
							&& a1 != Double.NEGATIVE_INFINITY && a1 != 0
							&& a1 != Double.NaN) {
						x_min += 1 / a1;
					}

					if (a2 != Double.POSITIVE_INFINITY
							&& a2 != Double.NEGATIVE_INFINITY && a2 != 0
							&& a2 != Double.NaN) {
						x_max += 1 / a2;
					}
				}
			}
		}
	}

	private double[] pincel(Ponto ponto, Ponto normal) {

		double[] id = new double[3];
		double[] is = new double[3];
		double[] retorno = new double[3];
		Ponto vetorL = new Ponto(0, 0, 0);
		double[] ia2 = new double[3];

		Ponto opostoP = new Ponto((-1) * ponto.x, (-1) * ponto.y, (-1) * ponto.z);
		Operador.normalizar(opostoP);

		// IA
		ia2[0] = lux.ka * lux.Ia.x;
		ia2[1] = lux.ka * lux.Ia.y;
		ia2[2] = lux.ka * lux.Ia.z;

		// vetorL = Ponto de luz - ponto (PL - P)
		vetorL.x = lux.Pl.x - ponto.x;
		vetorL.y = lux.Pl.y - ponto.y;
		vetorL.z = lux.Pl.z - ponto.z;

		// normaliza vetorL
		Operador.normalizar(vetorL);

		// Calcular Id
		if (Operador.produto_interno(vetorL, normal) > 0) {

			if(box != null){ //com textura, usa Od do ponto do objeto
				id[0] = Operador.produto_fosco(lux.Ip, ponto.Od)[0]
						* (lux.kd * Operador.produto_interno(vetorL, normal));
				id[1] = Operador.produto_fosco(lux.Ip, ponto.Od)[1]
						* (lux.kd * Operador.produto_interno(vetorL, normal));
				id[2] = Operador.produto_fosco(lux.Ip, ponto.Od)[2]
						* (lux.kd * Operador.produto_interno(vetorL, normal));

			}
			else{ //sem textura, usa Od dos atributos da camera
				id[0] = Operador.produto_fosco(lux.Ip, lux.Od)[0]
						* (lux.kd * Operador.produto_interno(vetorL, normal));
				id[1] = Operador.produto_fosco(lux.Ip, lux.Od)[1]
						* (lux.kd * Operador.produto_interno(vetorL, normal));
				id[2] = Operador.produto_fosco(lux.Ip, lux.Od)[2]
						* (lux.kd * Operador.produto_interno(vetorL, normal));
			}
		}

		double coef = (Operador.produto_interno(vetorL, normal) / Operador
				.produto_interno(normal, normal));

		Ponto vetorR = new Ponto(0, 0, 0);
		vetorR.x = (coef * normal.x) * 2;
		vetorR.y = (coef * normal.y) * 2;
		vetorR.z = (coef * normal.z) * 2;

		vetorR.x -= vetorL.x;
		vetorR.y -= vetorL.y;
		vetorR.z -= vetorL.z;

		Operador.normalizar(vetorR);

		double rugosidade = Operador.produto_interno(vetorR, opostoP); //coeficiente de rugosidade

		rugosidade = Math.max(rugosidade, 0);

		rugosidade = Math.pow(rugosidade, lux.n);
		rugosidade *= lux.ks;

		//calculo do indice de specularidade
		is[0] = lux.Ip[0] * rugosidade;
		is[1] = lux.Ip[1] * rugosidade;
		is[2] = lux.Ip[2] * rugosidade;

		retorno[0] = Math.min(is[0] + id[0] + ia2[0], 255);
		retorno[1] = Math.min(is[1] + id[1] + ia2[1], 255);
		retorno[2] = Math.min(is[2] + id[2] + ia2[2], 255);

		return retorno;
	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g);
	}
}
