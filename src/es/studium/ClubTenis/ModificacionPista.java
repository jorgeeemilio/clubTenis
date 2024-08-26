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
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModificacionPista  implements WindowListener, ActionListener
{
	Frame modificaPista = new Frame("Modificar Pista");
	Frame editaPista = new Frame("Modificar Pista");

	Dialog mensaje = new Dialog(modificaPista,"Mensaje", true);
	Button btnVolver = new Button("Volver");
	Label etiqueta = new Label("Faltan datos");

	Choice choPistas = new Choice();
	Button btnEditar = new Button("Editar");

	Label lblDenominacion = new Label("Denominación:");
	TextField txtDenominacion = new TextField(10);
	Label lblTipo = new Label("Tipo:");
	TextField txtTipo = new TextField(10);
	Button btnModificar = new Button("Modificar");

	Utilidades utilidades = new Utilidades();

	Connection connection = null;
	Boolean modificacionCorrecta = false;
	int idPista;
	ResultSet rs = null;

	public ModificacionPista()
	{
		modificaPista.setLayout(new GridLayout(2,1));
		modificaPista.setSize(300, 130);
		modificaPista.setResizable(false);
		modificaPista.addWindowListener(this);
		modificaPista.setLocationRelativeTo(null);

		mensaje.setLayout(new FlowLayout());
		mensaje.setSize(200, 100);
		mensaje.setResizable(false);
		mensaje.addWindowListener(this);
		mensaje.setLocationRelativeTo(null);

		editaPista.setLayout(new GridLayout(3,2));
		editaPista.setSize(300, 130);
		editaPista.setResizable(false);
		editaPista.addWindowListener(this);
		editaPista.setLocationRelativeTo(null);

		editaPista.add(lblDenominacion);
		editaPista.add(txtDenominacion);
		editaPista.add(lblTipo);
		editaPista.add(txtTipo);
		btnModificar.addActionListener(this);
		editaPista.add(btnModificar);

		connection = utilidades.conectar();
		choPistas = utilidades.rellenarChoicePistas(connection);
		utilidades.desconectar(connection);
		modificaPista.add(choPistas);

		btnEditar.addActionListener(this);
		modificaPista.add(btnEditar);

		modificaPista.setVisible(true);
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
			modificaPista.setVisible(false);
		}
		else if(editaPista.isActive())
		{
			editaPista.setVisible(false);
		}
		else
		{
			modificaPista.setVisible(false);
		}
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}

	public void actionPerformed(ActionEvent evento)
	{
		Object objeto = evento.getSource();
		if(objeto.equals(btnEditar))
		{
			// Validación
			if(choPistas.getSelectedIndex()!=0)
			{
				// Conectar a la BD para sacar los datos de la pista seleccionada a editar
				connection = utilidades.conectar();
				String[] cadena = choPistas.getSelectedItem().split("-");
				idPista = Integer.parseInt(cadena[0]);
				rs = utilidades.obtenerDatosPista(connection, cadena[0]);
				try
				{
					rs.next();
					txtDenominacion.setText(rs.getString("denominacionPista"));
					txtTipo.setText(rs.getString("tipoPista"));
					editaPista.setVisible(true);
				}
				catch (SQLException sqle)
				{
					System.out.println("Error ModificacionPista-"+sqle.getMessage());
				}
			}
			else
			{
				etiqueta.setText("Debes seleccionar una pista...");
				mensaje.add(etiqueta);
				mensaje.setVisible(true);
			}
		}
		else if(objeto.equals(btnModificar))
		{
			// Validación: Ambos campos rellenos
			if(utilidades.validarVacio(txtDenominacion)&&utilidades.validarVacio(txtTipo))
			{
				connection = utilidades.conectar();
				modificacionCorrecta = utilidades.modificarPista(connection, idPista, txtDenominacion, txtTipo);
				utilidades.desconectar(connection);
				if(modificacionCorrecta)
				{
					etiqueta.setText("Modificación Correcta");
					txtDenominacion.setText("");
					txtTipo.setText("");
				}
				else
				{
					etiqueta.setText("Error en Modificación");
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
		else if(objeto.equals(btnVolver))
		{
			btnVolver.removeActionListener(this);
			mensaje.setVisible(false);
			editaPista.setVisible(false);
			modificaPista.setVisible(false);
		}
	}
}
