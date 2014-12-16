import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Core extends JFrame {
	static Core core;
	static boolean objeto_temporario = false;
	static boolean superficie_temporaria = false;
	static Object atual;

	private static final long serialVersionUID = 1L;

	public Core(Camera camera, Objeto objeto, Luz lux, Box box, Bounding box2) {
		iniciaInterface(camera, objeto, lux, box, box2);
	}

	private void iniciaInterface(Camera camera, Objeto objeto, Luz lux, Box box, Bounding box2) {
		setTitle("Projeto 2 PG");
		add(new Superficie(camera, objeto, lux, box, box2));
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private static Camera leitura_camera(BufferedReader paramCamera)
			throws NumberFormatException, IOException {
		Ponto C, V, N, U;
		double d = 0, hx = 0, hy = 0, norma;
		String x, y, z, linha;
		String[] temp;
		// pega as informações da camera:
		if (paramCamera.ready()) {
			// lê as coordenadas do vetor C:
			linha = paramCamera.readLine();
			temp = linha.split(" ");
			x = temp[0];
			y = temp[1];
			z = temp[2];
			C = new Ponto(Double.valueOf(x), Double.valueOf(y),
					Double.valueOf(z));
			// lê as coordenadas do vetor N:
			linha = paramCamera.readLine();
			temp = linha.split(" ");
			x = temp[0];
			y = temp[1];
			z = temp[2];
			N = new Ponto(Double.valueOf(x), Double.valueOf(y),
					Double.valueOf(z));
			// lê as coordenadas do vetor V:
			linha = paramCamera.readLine();
			temp = linha.split(" ");
			x = temp[0];
			y = temp[1];
			z = temp[2];
			V = new Ponto(Double.valueOf(x), Double.valueOf(y),
					Double.valueOf(z));
			// lê d, hx e hy:
			linha = paramCamera.readLine();
			temp = linha.split(" ");
			d = Double.valueOf(temp[0]);
			hx = Double.valueOf(temp[1]);
			hy = Double.valueOf(temp[2]);

			// fecha o arquivo:
			paramCamera.close();

			// normaliza N:
			norma = Math.sqrt(Operador.produto_interno(N, N));
			N.x = N.x / norma;
			N.y = N.y / norma;
			N.z = N.z / norma;
			// ortogonaliza V:
			double coef = (Operador.produto_interno(V, N) / Operador
					.produto_interno(N, N));

			V.x = V.x - (coef * N.x);
			V.y = V.y - (coef * N.y);
			V.z = V.z - (coef * N.z);
			// normaliza V:
			norma = Math.sqrt(Operador.produto_interno(V, V));
			V.x = V.x / norma;
			V.y = V.y / norma;
			V.z = V.z / norma;
			// calcula U:
			U = Operador.produto_vetorial(N, V);

			Camera infoCam = new Camera(C, V, N, U, d, hx, hy);

			return infoCam;
		} else {
			System.out.println("erro abertura de arquivo");
			return null;
		}

	}

	private static Objeto leitura_objeto(Camera camera,
			BufferedReader paramObjeto) throws IOException {
		Ponto pontos_visao[] = null, pontos[] = null;
		int qnt_pontos, qnt_triangulos, triangulos[][] = null;
		Ponto normais_triangulos[] = null, normais_vertices[] = null;
		double esq, dir, cima, baixo, perto, longe;
		String x, y, z, linha, temp[];
		if (paramObjeto.ready()) {
			// lê as quantidades de pontos e de triangulos:
			linha = paramObjeto.readLine();
			qnt_pontos = Integer
					.valueOf(linha.substring(0, linha.indexOf(' ')));
			qnt_triangulos = Integer
					.valueOf(linha.substring(linha.indexOf(' ') + 1));

			// inicia os respectivos arrays:
			pontos = new Ponto[qnt_pontos];
			pontos_visao = new Ponto[qnt_pontos];
			triangulos = new int[qnt_triangulos][3];
			// lê todos os pontos:
			for (int c = 0; c < qnt_pontos; c++) {
				linha = paramObjeto.readLine();
				temp = linha.split(" ");
				if (linha.charAt(0) == ' ') {
					x = temp[1];
					y = temp[2];
					z = temp[3];
				} else {
					x = temp[0];
					y = temp[1];
					z = temp[2];
				}
				pontos[c] = new Ponto(Double.valueOf(x), Double.valueOf(y),
						Double.valueOf(z));
				pontos[c].indice = c;
			}
			// lê todos os triangulos:
			for (int c = 0; c < qnt_triangulos; c++) {
				do {
					linha = paramObjeto.readLine();
				} while (linha.isEmpty());

				temp = linha.split(" ");
				if (linha.charAt(0) == ' ') {
					x = temp[1];
					y = temp[2];
					z = temp[3];
				} else {
					x = temp[0];
					y = temp[1];
					z = temp[2];
				}// guarda o indice de cada vertice no array de pontos:
				triangulos[c][0] = Integer.valueOf(x) - 1;
				triangulos[c][1] = Integer.valueOf(y) - 1;
				triangulos[c][2] = Integer.valueOf(z) - 1;
			}

			// converte os pontos para coordenadas de visão:
			for (int c = 0; c < qnt_pontos; c++) {
				// calcula P - C:
				pontos[c].x -= camera.C.x;
				pontos[c].y -= camera.C.y;
				pontos[c].z -= camera.C.z;

				// multiplica P-C pela matriz de mudança de base:
				double x_visao = (pontos[c].x * camera.U.x)
						+ (pontos[c].y * camera.U.y)
						+ (pontos[c].z * camera.U.z);
				double y_visao = (pontos[c].x * camera.V.x)
						+ (pontos[c].y * camera.V.y)
						+ (pontos[c].z * camera.V.z);
				double z_visao = (pontos[c].x * camera.N.x)
						+ (pontos[c].y * camera.N.y)
						+ (pontos[c].z * camera.N.z);

				pontos_visao[c] = new Ponto(x_visao, y_visao, z_visao);
				pontos_visao[c].indice = c;
			}

			//inicializa ptos extremos
			esq = pontos_visao[0].x;
			dir = esq;
			cima = pontos_visao[0].y;
			baixo = cima;
			perto = pontos_visao[0].z;
			longe = perto;

			for (int c = 0; c < qnt_pontos; c++) {
				if (pontos_visao[c].x < esq) {
					esq = pontos_visao[c].x;
				} else if (pontos_visao[c].x > dir) {
					dir = pontos_visao[c].x;
				} else if (pontos_visao[c].y < baixo) {
					baixo = pontos_visao[c].y;
				} else if (pontos_visao[c].y > cima) {
					cima = pontos_visao[c].y;
				} else if (pontos_visao[c].z < perto) {
					perto = pontos_visao[c].z;
				} else if (pontos_visao[c].z > longe) {
					longe = pontos_visao[c].z;
				}
			}

			// pontos em coordenadas de tela:
			for (int c = 0; c < qnt_pontos; c++) {
				double x_temp, y_temp, z_temp;
				x_temp = pontos_visao[c].x;
				y_temp = pontos_visao[c].y;
				z_temp = pontos_visao[c].z;

				pontos[c].x = (camera.d / camera.hx) * (x_temp / z_temp);
				pontos[c].y = (camera.d / camera.hy) * (y_temp / z_temp);
				// coordenadas em 2D, temos z = 0:
				pontos[c].z = 0;
				pontos[c].indice = c;
			}
			paramObjeto.close();

			Ponto v1 = new Ponto(0.0, 0.0, 0.0), v2 = new Ponto(1.0, 1.0, 1.0);
			normais_triangulos = new Ponto[qnt_triangulos];
			normais_vertices = new Ponto[qnt_pontos];
			// inicia as normais de todos os vertices como 0:
			for (int c = 0; c < qnt_pontos; c++) {
				normais_vertices[c] = new Ponto(0.0, 0.0, 0.0);
			}

			// calcula a normal de todos os triangulos:
			for (int c = 0; c < qnt_triangulos; c++) {
				Ponto p1 = pontos_visao[triangulos[c][0]];
				Ponto p2 = pontos_visao[triangulos[c][1]];
				Ponto p3 = pontos_visao[triangulos[c][2]];

				// calcula o vetor v1:
				v1.x = p2.x - p1.x;
				v1.y = p2.y - p1.y;
				v1.z = p2.z - p1.z;
				// calcula o vetor v2:
				v2.x = p3.x - p1.x;
				v2.y = p3.y - p1.y;
				v2.z = p3.z - p1.z;

				// calcula a normal, que é o produto vetorial entre v1 e v2:
				normais_triangulos[c] = Operador.produto_vetorial(v1, v2);

				// normaliza a normal do triangulo:
				Operador.normalizar(normais_triangulos[c]);

				//de forma mais eficiente, pela soma das normais dos vizinhos
				// soma a normal do triangulo às normais dos vertices vizinhos que o formam
				normais_vertices[triangulos[c][0]].x += normais_triangulos[c].x;
				normais_vertices[triangulos[c][0]].y += normais_triangulos[c].y;
				normais_vertices[triangulos[c][0]].z += normais_triangulos[c].z;

				normais_vertices[triangulos[c][1]].x += normais_triangulos[c].x;
				normais_vertices[triangulos[c][1]].y += normais_triangulos[c].y;
				normais_vertices[triangulos[c][1]].z += normais_triangulos[c].z;

				normais_vertices[triangulos[c][2]].x += normais_triangulos[c].x;
				normais_vertices[triangulos[c][2]].y += normais_triangulos[c].y;
				normais_vertices[triangulos[c][2]].z += normais_triangulos[c].z;
			}

			// normaliza as normais dos vértices:
			for (int c = 0; c < qnt_pontos; c++) {
				Operador.normalizar(normais_vertices[c]);
			}

			Objeto info = new Objeto(pontos_visao, pontos,
					normais_triangulos, normais_vertices, triangulos);
			return info;
		} else {
			System.out.println("nao abriu o objeto");
			return null;
		}
	}

	private static Luz leitura_luz(BufferedReader pIluminacao)
			throws NumberFormatException, IOException {
		String x, y, z, linha, temp[];
		if (pIluminacao.ready()) {
			Ponto pl, ia;
			double ka, kd, ks, n;
			double[] ip = new double[3];
			double[] od = new double[3];
			double[] id = new double[3];
			// lê as coordenadas do ponto de luz:
			linha = pIluminacao.readLine();
			temp = linha.split(" ");
			x = temp[0];
			y = temp[1];
			z = temp[2];
			pl = new Ponto(Double.valueOf(x), Double.valueOf(y),
					Double.valueOf(z));
			// lê Ka:
			linha = pIluminacao.readLine();
			ka = Double.valueOf(linha);
			// lê o vetor de cor ambiental Ia:
			linha = pIluminacao.readLine();
			temp = linha.split(" ");
			x = temp[0];
			y = temp[1];
			z = temp[2];
			ia = new Ponto(Double.valueOf(x), Double.valueOf(y),
					Double.valueOf(z));
			// lê Kd:
			linha = pIluminacao.readLine();
			kd = Double.valueOf(linha);
			// lê o Od:
			linha = pIluminacao.readLine();
			temp = linha.split(" ");
			x = temp[0];
			y = temp[1];
			z = temp[2];
			od[0] = Double.valueOf(x);
			od[1] = Double.valueOf(y);
			od[2] = Double.valueOf(z);
			// lê Ks:
			linha = pIluminacao.readLine();
			ks = Double.valueOf(linha);
			// lê a cor da fonte de luz Il:
			linha = pIluminacao.readLine();
			temp = linha.split(" ");
			x = temp[0];
			y = temp[1];
			z = temp[2];
			ip[0] = Double.valueOf(x);
			ip[1] = Double.valueOf(y);
			ip[2] = Double.valueOf(z);
			// lê a constante de rugosidade n:
			linha = pIluminacao.readLine();
			n = Double.valueOf(linha);

			Luz lux_info = new Luz(pl, ia, ka, kd, ks, n, ip, od, id);
			return lux_info;
		} else {
			System.out.println("nao abriu a iluminacao");
			return null;
		}
	}

	public static Box inic_box(BufferedImage[] imagem){
		Box box = new Box(imagem);
		return box;
	}

	public static Bounding inic_box2(Objeto objeto){
		Bounding box = new Bounding(objeto);
		return box;
	}

	public static void main(String[] args) throws NumberFormatException,
	IOException {
		final Camera camera = null;
		final Objeto objeto = null;
		final Luz lux = null;
		final Box box = null;
		final Bounding box2 = null;

		final BufferedReader pCamera = null;
		final BufferedReader pIluminacao = null;
		final BufferedReader pObjeto = null;		

		//lendo imagem da textura		
		final BufferedImage[] pTextura = null;

		JFrame novo = new JFrame();
		novo.setTitle("Arquivos de Configuração");
		novo.setSize(1000, 100);
		novo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		novo.setLocationRelativeTo(null);
		novo.setResizable(false);
		novo.setLocation(0, 0);

		JPanel panel = new JPanel();

		JButton but = new JButton("Vai Objeto");
		JButton butTex = new JButton("Selecionar Textura");

		final JLabel cam = new JLabel("Camera");
		JLabel objet = new JLabel("Objeto");
		JLabel tex = new JLabel("Textura");

		final JTextField cameraTxt = new JTextField(25);
		final JTextField objTxt = new JTextField(25);
		final JTextField textTxt = new JTextField("", 25);
		textTxt.setEditable(false);

		final JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(true);

		cameraTxt.setSize(100, 100);

		panel.add(cam);
		panel.add(cameraTxt);
		panel.add(objet);
		panel.add(objTxt);
		panel.add(tex);
		panel.add(textTxt);
		panel.add(but);
		panel.add(butTex);

		novo.add(panel);

		novo.setVisible(true);

		butTex.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try{
					button2();
					if(chooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION){
						return;
					}	
					File[] files = chooser.getSelectedFiles();
					String url = "";
					for (int i = 0; i < files.length; i++) {
						StringBuffer buffer = new StringBuffer("");
						buffer.append(files[i].toString());
						url = buffer.substring(0);
					}
					textTxt.setText(url);
					//						textTxt.setText("teste"); 
					//				}  catch (FileNotFoundException e) {
					//					// TODO Auto-generated catch block
					//					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					//				} catch (IOException e) {
					//					// TODO Auto-generated catch block
					//					e.printStackTrace();
				}
			}
		});

		// caso clique no botão 1
		but.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					button1(pCamera, pIluminacao, pObjeto, pTextura, textTxt.getText(), cameraTxt.getText(),
							objTxt.getText(), camera, objeto, box, box2, lux);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}

	public static String button2(){
		String entradaImagem = "dd";
		return entradaImagem;

	}

	public static void button1(BufferedReader cameraParam,
			BufferedReader iluninacaoParam, BufferedReader objetoParam, BufferedImage[] texturaParam, String urlImage,
			String cameraIn, String objetoIn, Camera camera,
			Objeto objeto, Box box, Bounding box2, Luz luz) throws NumberFormatException,
			IOException {

		String entradaCamera = "entradas\\Cameras\\" + cameraIn + ".cfg";
		String entradaObjeto = "entradas\\Objetos\\" + objetoIn + ".byu";

		cameraParam = new BufferedReader(new FileReader(entradaCamera));
		iluninacaoParam = new BufferedReader(new FileReader("entradas\\iluminacao.txt"));
		objetoParam = new BufferedReader(new FileReader(entradaObjeto));

		if(!urlImage.equalsIgnoreCase("")){ //modificacao 00:13 dia 19
			String[] urls = urlImage.split("C:");
			int numImagens = urls.length-1;
			texturaParam = new BufferedImage[numImagens];

			for (int i = 1; i < urls.length; i++) {
				String temp = urls[i];
				StringBuffer buffer = new StringBuffer("");
				buffer.append("C:");
				buffer.append(temp);
				urls[i] = buffer.substring(0);
				texturaParam[i-1] = ImageIO.read(new File(urls[i]));;
			}
		}


		camera = leitura_camera(cameraParam);

		objeto = leitura_objeto(camera, objetoParam);
		luz = leitura_luz(iluninacaoParam);

		if(texturaParam != null){
			box = inic_box(texturaParam);
			box2 = inic_box2(objeto);
		}
		
		luz.Pl.x -= camera.C.x;
		luz.Pl.y -= camera.C.y;
		luz.Pl.z -= camera.C.z;

		//		if(pTextura != null){
		//			int txW = pTextura.getWidth();
		//			int txH = pTextura.getHeight();
		//			int[] pixels = pTextura.getRGB(0, 0, txH, txW, null, 0, txW);
		//			Color cor = new Color(pixels[0]);
		//
		//			lux.Od[0] = (double)cor.getRed()/255;
		//			lux.Od[1] = (double)cor.getGreen()/255;
		//			lux.Od[2] = (double)cor.getBlue()/255;
		//		}

		// multiplica P-C pela matriz de mudança de base:
		double x_visao = (luz.Pl.x * camera.U.x) + (luz.Pl.y * camera.U.y)
				+ (luz.Pl.z * camera.U.z);
		double y_visao = (luz.Pl.x * camera.V.x) + (luz.Pl.y * camera.V.y)
				+ (luz.Pl.z * camera.V.z);
		double z_visao = (luz.Pl.x * camera.N.x) + (luz.Pl.y * camera.N.y)
				+ (luz.Pl.z * camera.N.z);

		luz.Pl.x = x_visao;
		luz.Pl.y = y_visao;
		luz.Pl.z = z_visao;

		if (objeto_temporario){
			core.dispose();
		} else if (superficie_temporaria){
			core.dispose();
			superficie_temporaria = false;
		}

		objeto_temporario = true;
		core = new Core(camera, objeto, luz, box, box2);
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				core.setLocation(300, 0);
				core.setVisible(true);

			}
		});
	}
}
