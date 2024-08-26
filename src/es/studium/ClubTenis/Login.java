package es.studium.ClubTenis;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login implements WindowListener, ActionListener
{
	// Atributos de clase
	Frame ventanaLogin = new Frame("Login");

	Dialog errorLogin = new Dialog(ventanaLogin,"ERROR", true);
	Label lblUsuario = new Label("Usuario:");
	Label lblClave = new Label("Clave:");
	TextField txtUsuario = new TextField(20); 
	TextField txtClave = new TextField(20);
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");
	
	Utilidades utilidades = new Utilidades();
	
	Connection connection = null;
	String sentencia = null;
	ResultSet rs = null;

	// Constructor
	public Login()
	{
		ventanaLogin.setLayout(new FlowLayout());
		ventanaLogin.setSize(270, 150);
		ventanaLogin.setResizable(false);
		ventanaLogin.setLocationRelativeTo(null);
		ventanaLogin.addWindowListener(this);
		btnAceptar.addActionListener(this);
		btnLimpiar.addActionListener(this);
		ventanaLogin.add(lblUsuario);
		ventanaLogin.add(txtUsuario);
		ventanaLogin.add(lblClave);
		txtClave.setEchoChar('*');
		ventanaLogin.add(txtClave);
		ventanaLogin.add(btnAceptar);
		ventanaLogin.add(btnLimpiar);
		ventanaLogin.setVisible(true);
	}

	public void windowActivated(WindowEvent windowEvent){}
	public void windowClosed(WindowEvent windowEvent) {}
	public void windowClosing(WindowEvent arg0)
	{
		if(errorLogin.isActive())
		{
			errorLogin.setVisible(false);
		}
		else
		{
			System.exit(0);
		}
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}

	public void actionPerformed(ActionEvent evento)
	{
		if(evento.getSource().equals(btnLimpiar)) 
		{
			// Tareas del botón Limpiar
			txtUsuario.selectAll();
			txtUsuario.setText("");
			txtClave.selectAll();
			txtClave.setText("");
			txtUsuario.requestFocus();
		}
		else if(evento.getSource().equals(btnAceptar)) 
		{
			// Tareas del botón Aceptar
			String cadenaEncriptada = utilidades.getSHA256(txtClave.getText());
			sentencia = "SELECT * FROM usuarios WHERE ";
			sentencia += "nombreUsuario = '"+txtUsuario.getText()+"'";
			sentencia += " AND claveUsuario = '"+cadenaEncriptada+"'";
			try
			{
				connection = utilidades.conectar();
				rs = utilidades.consultar(connection, sentencia);
				if(rs.next())
				{
					new MenuPrincipal();
					utilidades.desconectar(connection);
					ventanaLogin.setVisible(false);
				}
				else
				{
					errorLogin.setLayout(new FlowLayout());
					errorLogin.setSize(170, 100);
					errorLogin.setResizable(false);
					errorLogin.addWindowListener(this);
					errorLogin.add(new Label("Credenciales Incorrectas"));
					Button btnVolver = new Button("Volver");
					btnVolver.addActionListener(this);
					errorLogin.add(btnVolver);
					errorLogin.setLocationRelativeTo(null);
					errorLogin.setVisible(true);
				}
			}
			catch (SQLException sqle)
			{
				System.out.println("Error 1 Login-"+sqle.getMessage());
			}
			finally
			{
				utilidades.desconectar(connection);
			}
		}
		else
		{
			// Tareas del Volver
			errorLogin.setVisible(false);
		}
	}

	public static void main(String[] args)
	{
		new Login(); // Instanciar/Crear un objeto
	}
}