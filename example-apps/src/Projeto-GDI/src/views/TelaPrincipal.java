package views;

import java.awt.EventQueue;
import views.TelaCadastro;
import views.TelaConsultas;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	static class AcaoBtnCadastrar implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			new TelaCadastro();
		}
		
	}

	/**
	 * Create the frame.
	 */
	public TelaPrincipal() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		JButton btnBuscarCliente = new JButton("Buscar Cliente");
		btnBuscarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TelaConsultas();
			}
		});
		btnBuscarCliente.setBounds(265, 68, 236, 80);
		contentPane.add(btnBuscarCliente);
		
		JButton btnCadastrarCliente= new JButton("Cadastrar Cliente");
		btnCadastrarCliente.addActionListener(new AcaoBtnCadastrar());
		btnCadastrarCliente.setBounds(265, 218, 236, 80);
		contentPane.add(btnCadastrarCliente);
	}
}
