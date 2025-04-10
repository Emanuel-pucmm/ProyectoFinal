package visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import logical.User;
import logico.SerieNacional;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				FileInputStream beisbol;
				FileOutputStream beisbol2;
				ObjectInputStream beisbolRead;
				ObjectOutputStream beisbolWrite;
				try {
					beisbol = new FileInputStream ("beisbol.dat");
					beisbolRead = new ObjectInputStream(beisbol);
					SerieNacional temp = (SerieNacional)beisbolRead.readObject();
					SerieNacional.setControl(temp);
					beisbol.close();
					beisbolRead.close();
				} catch (FileNotFoundException e) {
					try {
						beisbol2 = new  FileOutputStream("beisbol.dat");
						beisbolWrite = new ObjectOutputStream(beisbol2);
						User aux = new User("Administrador", "Admin", "Admin");
						SerieNacional.getInstance().regUser(aux);
						beisbolWrite.writeObject(SerieNacional.getInstance());
						beisbol2.close();
						beisbolWrite.close();
					} catch (FileNotFoundException e1) {
					} catch (IOException e1) {
						// TODO Auto-generated catch block
					}
				} catch (IOException e) {
					
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setBounds(39, 39, 46, 14);
		panel.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contrase\u00F1a:");
		lblContrasea.setBounds(39, 98, 105, 14);
		panel.add(lblContrasea);
		
		textField = new JTextField();
		textField.setBounds(39, 64, 191, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(39, 128, 191, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (SerieNacional.getInstance().confirmLogin(textField.getText(), textField_1.getText())) {
					   VisualPrincipal frame = new VisualPrincipal();
					   dispose();
					   frame.setVisible(true);
					}
				
			}
		});
		btnLogin.setBounds(37, 175, 89, 23);
		panel.add(btnLogin);
	}
}
