package es.studium.ClubTenis;

import java.awt.Button;
import java.awt.Choice;
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

public class AltaCuota implements WindowListener, ActionListener
{
	Frame altaCuota = new Frame("Alta de Cuota");

	Label lblFechaCuota = new Label("Fecha Cuota:");
	TextField txtFechaCuota = new TextField(10);
	Label lblImporte = new Label("Importe:");
	TextField txtImporte = new TextField(10);
	Label lblFechaPago = new Label("Fecha Pago:");
	TextField txtFechaPago = new TextField(10);
	Label lblTipo = new Label("Tipo:");
	TextField txtTipo = new TextField(10);
	Label lblSocio = new Label("Socio:");
	Choice choSocio = new Choice();
	
	Button btnAceptar = new Button("Aceptar");
	Button btnLimpiar = new Button("Limpiar");

	Dialog mensaje = new Dialog(altaCuota,"Mensaje", true);
	Button btnVolver = new Button("Volver");
	Label etiqueta = new Label("Faltan datos");

	Utilidades utilidades = new Utilidades();

	Connection connection = null;
	Boolean altaCorrecta = false;
	int idSocioFK;

	public AltaCuota()
	{
		altaCuota.setLayout(new GridLayout(6,2));
		altaCuota.setSize(300, 200);
		altaCuota.setResizable(false);
		altaCuota.addWindowListener(this);
		altaCuota.setLocationRelativeTo(null);

		altaCuota.add(lblFechaCuota);
		altaCuota.add(txtFechaCuota);
		altaCuota.add(lblImporte);
		altaCuota.add(txtImporte);
		altaCuota.add(lblFechaPago);
		altaCuota.add(txtFechaPago);
		altaCuota.add(lblTipo);
		altaCuota.add(txtTipo);
		altaCuota.add(lblSocio);
		choSocio = utilidades.rellenarChoiceSocios();
		altaCuota.add(choSocio);
		btnAceptar.addActionListener(this);
		altaCuota.add(btnAceptar);
		btnLimpiar.addActionListener(this);
		altaCuota.add(btnLimpiar);

		altaCuota.setVisible(true);
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
			altaCuota.setVisible(false);
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
			if(utilidades.validarVacio(txtFechaCuota)&&utilidades.validarVacio(txtImporte)&&utilidades.validarVacio(txtTipo)&&utilidades.validarChoiceVacio(choSocio))
			{
				String[] cadena = choSocio.getSelectedItem().split("-");
				idSocioFK = Integer.parseInt(cadena[0]);
				altaCorrecta = utilidades.altaCuota(txtFechaCuota, txtImporte, txtFechaPago, txtTipo, idSocioFK);
				utilidades.desconectar(connection);
				if(altaCorrecta)
				{
					etiqueta.setText("Alta Correcta");
					txtFechaCuota.setText("");
					txtImporte.setText("");
					txtFechaPago.setText("");
					txtTipo.setText("");
					choSocio.select(0);
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
			txtFechaCuota.requestFocus();
			mensaje.setVisible(true);
		}
		else if(objeto.equals(btnLimpiar))
		{
			txtFechaCuota.setText("");
			txtImporte.setText("");
			txtFechaPago.setText("");
			txtTipo.setText("");
			choSocio.select(0);
			txtFechaCuota.requestFocus();
		}
		else if(objeto.equals(btnVolver))
		{
			btnVolver.removeActionListener(this);
			mensaje.setVisible(false);
		}
	}
}