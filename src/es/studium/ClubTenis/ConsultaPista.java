package es.studium.ClubTenis;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;

public class ConsultaPista implements WindowListener, ActionListener
{
	Frame consultaPista = new Frame("Consulta de Pistas");
	
	TextArea listado = new TextArea(8,30);

	Utilidades utilidades = new Utilidades();
	
	Connection connection = null;
	String contenido = null;
	
	public ConsultaPista()
	{
		consultaPista.setLayout(new FlowLayout());
		consultaPista.setSize(300, 200);
		consultaPista.setResizable(false);
		consultaPista.addWindowListener(this);
		consultaPista.setLocationRelativeTo(null);
		// Conectar a la bd y sacar la información
		connection = utilidades.conectar();
		contenido = utilidades.listadoPistas(connection);
		listado.append(contenido);
		consultaPista.add(listado);
		
		consultaPista.setVisible(true);
	}
	
	public void windowActivated(WindowEvent windowEvent){}
	public void windowClosed(WindowEvent windowEvent) {}
	public void windowClosing(WindowEvent arg0)
	{
		consultaPista.setVisible(false);
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}

	public void actionPerformed(ActionEvent evento)
	{
		// Utilidades futuras
	}
}
