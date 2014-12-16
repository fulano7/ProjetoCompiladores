package views;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaConsultas {

	private JFrame frmProjetoGdi;
	private JTextField clientName;
	private JTextField clientCPF;

	/**
	 * Create the application.
	 */
	public TelaConsultas() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmProjetoGdi = new JFrame();
		frmProjetoGdi.setTitle("Projeto GDI Nunca Mais!");
		frmProjetoGdi.setBounds(100, 100, 600, 400);
		frmProjetoGdi.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmProjetoGdi.getContentPane().setLayout(null);
		frmProjetoGdi.setVisible(true);
		
		clientName = new JTextField();
		clientName.setBounds(10, 32, 178, 20);
		clientName.setText("Por nome...");
		frmProjetoGdi.getContentPane().add(clientName);
		clientName.setColumns(15);
		
		JButton bucar = new JButton("Buscar");
		bucar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		bucar.setBounds(198, 31, 89, 23);
		frmProjetoGdi.getContentPane().add(bucar);
		
		JLabel lblBuscarCliente = new JLabel("Buscar Cliente");
		lblBuscarCliente.setBounds(10, 11, 125, 14);
		frmProjetoGdi.getContentPane().add(lblBuscarCliente);
		
		clientCPF = new JTextField();
		clientCPF.setText("Por CPF...");
		clientCPF.setColumns(15);
		clientCPF.setBounds(10, 64, 178, 20);
		frmProjetoGdi.getContentPane().add(clientCPF);
		
		JButton button = new JButton("Buscar");
		button.setBounds(198, 63, 89, 23);
		frmProjetoGdi.getContentPane().add(button);
		
	}
}
