package es.studium.ClubTenis;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;

public class AltaPista implements WindowListener, ActionListener
{
	Frame altaPista = new Frame("Alta de Pista");

	Label lblDenominacion = new Label("Denominación:");
	TextField txtDenominacion = new TextField(10);
	Label lblTipo = new Label("Tipo:");
	TextField txtTipo = new TextField(10);
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	Dialog mensaje = new Dialog(altaPista,"Mensaje", true);
	Button btnVolver = new Button("Volver");
	Label etiqueta = new Label("Faltan datos");

	Utilidades utilidades = new Utilidades();

	Connection connection = null;
	Boolean altaCorrecta = false;

	public AltaPista()
	{
		altaPista.setLayout(new GridLayout(3,2));
		altaPista.setSize(300, 130);
		altaPista.setResizable(false);
		altaPista.addWindowListener(this);
		altaPista.setLocationRelativeTo(null);

		altaPista.add(lblDenominacion);
		altaPista.add(txtDenominacion);
		altaPista.add(lblTipo);
		altaPista.add(txtTipo);
		btnAceptar.addActionListener(this);
		altaPista.add(btnAceptar);
		btnLimpiar.addActionListener(this);
		altaPista.add(btnLimpiar);

		altaPista.setVisible(true);
	}

	public void windowActivated(WindowEvent windowEvent){}
	public void windowClosed(WindowEvent windowEvent) {}
	public void windowClosing(WindowEvent arg0)
	{
		if(mensaje.isActive())
		{
			mensaje.setVisible(false);
		}
		else
		{
			altaPista.setVisible(false);
		}
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}

	public void actionPerformed(ActionEvent evento)
	{
		Object objeto = evento.getSource();
		if(objeto.equals(btnAceptar))
		{
			mensaje.setLayout(new FlowLayout());
			mensaje.setSize(170, 100);
			mensaje.setResizable(false);
			mensaje.addWindowListener(this);
			
			// Validación: Ambos campos rellenos
			if(utilidades.validarVacio(txtDenominacion)&&utilidades.validarVacio(txtTipo))
			{
				connection = utilidades.conectar();
				altaCorrecta = utilidades.altaPista(connection, txtDenominacion, txtTipo);
				utilidades.desconectar(connection);
				if(altaCorrecta)
				{
					etiqueta.setText("Alta Correcta");
					txtDenominacion.setText("");
					txtTipo.setText("");
				}
				else
				{
					etiqueta.setText("Error en Alta");
				}
			}
			else
			{
				etiqueta.setText("Faltan Datos");
			}
			mensaje.add(etiqueta);
			btnVolver.addActionListener(this);
			mensaje.add(btnVolver);
			mensaje.setLocationRelativeTo(null);
			txtDenominacion.requestFocus();
			mensaje.setVisible(true);
		}
		else if(objeto.equals(btnLimpiar))
		{
			txtDenominacion.setText("");
			txtTipo.setText("");
			txtDenominacion.requestFocus();
		}
		else if(objeto.equals(btnVolver))
		{
			btnVolver.removeActionListener(this);
			mensaje.setVisible(false);
		}
	}
}
