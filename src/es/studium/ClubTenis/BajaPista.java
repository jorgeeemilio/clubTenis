package es.studium.ClubTenis;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;

public class BajaPista implements WindowListener, ActionListener
{
	Frame bajaPista = new Frame("Eliminar Pista");

	Dialog mensaje = new Dialog(bajaPista,"Mensaje", true);
	Dialog confirmacion = new Dialog(bajaPista,"Confirmar", true);
	Label etiqueta = new Label("No se puede Eliminar");
	
	Choice choPistas = new Choice();
	Button btnEliminar = new Button("Eliminar");
	Button btnSi = new Button("Sí");
	Button btnNo = new Button("No");
	
	Utilidades utilidades = new Utilidades();
	
	Connection connection = null;
	Boolean bajaCorrecta = false;
	int idPista;
	
	public BajaPista()
	{
		bajaPista.setLayout(new GridLayout(2,1));
		bajaPista.setSize(300, 130);
		bajaPista.setResizable(false);
		bajaPista.addWindowListener(this);
		bajaPista.setLocationRelativeTo(null);
		
		mensaje.setLayout(new FlowLayout());
		mensaje.setSize(200, 100);
		mensaje.setResizable(false);
		mensaje.addWindowListener(this);
		mensaje.setLocationRelativeTo(null);
		
		confirmacion.setLayout(new FlowLayout());
		confirmacion.setSize(150, 100);
		confirmacion.setResizable(false);
		confirmacion.addWindowListener(this);
		btnSi.addActionListener(this);
		btnNo.addActionListener(this);

		connection = utilidades.conectar();
		choPistas = utilidades.rellenarChoicePistas(connection);
		utilidades.desconectar(connection);
		bajaPista.add(choPistas);
		
		btnEliminar.addActionListener(this);
		bajaPista.add(btnEliminar);

		bajaPista.setVisible(true);
	}
	
	public void windowActivated(WindowEvent windowEvent){}
	public void windowClosed(WindowEvent windowEvent) {}
	public void windowClosing(WindowEvent arg0)
	{
		if(mensaje.isActive()&&(etiqueta.getText().equals("Debes seleccionar una pista...")))
		{
			mensaje.setVisible(false);
		}
		else if(mensaje.isActive())
		{
			mensaje.setVisible(false);
			bajaPista.setVisible(false);
		}
		else if(confirmacion.isActive())
		{
			confirmacion.setVisible(false);
		}
		else
		{
			bajaPista.setVisible(false);
		}
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}

	public void actionPerformed(ActionEvent evento)
	{
		Object objeto = evento.getSource();
		if(objeto.equals(btnEliminar))
		{
			// Validación
			if(choPistas.getSelectedIndex()!=0)
			{
				etiqueta.setText("¿Segur@ de Eliminar?");
				confirmacion.add(etiqueta);
				confirmacion.add(btnSi);
				confirmacion.add(btnNo);
				confirmacion.setLocationRelativeTo(null);
				confirmacion.setVisible(true);
			}
			else
			{
				etiqueta.setText("Debes seleccionar una pista...");
				mensaje.add(etiqueta);
				mensaje.setVisible(true);
			}
		}
		else if(objeto.equals(btnSi))
		{
			connection = utilidades.conectar();
			String[] cadena = choPistas.getSelectedItem().split("-");
			bajaCorrecta = utilidades.bajaPista(connection, cadena[0]);
			utilidades.desconectar(connection);
			if(bajaCorrecta)
			{
				etiqueta.setText("Baja Correcta");
			}
			else
			{
				etiqueta.setText("No se puede Eliminar");
			}
			confirmacion.setVisible(false);
			mensaje.add(etiqueta);
			mensaje.setVisible(true);
		}
		else if(objeto.equals(btnNo))
		{
			mensaje.setVisible(false);
			confirmacion.setVisible(false);
		}
	}
}