package es.studium.ClubTenis;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MenuPrincipal implements WindowListener, ActionListener
{
	Frame menuPrincipal = new Frame("Club Tenis");
	
	MenuBar barraMenu = new MenuBar();

	Menu mnuPistas = new Menu("Pistas");
	Menu mnuSocios = new Menu("Socios");
	Menu mnuCuotas = new Menu("Cuotas");
	Menu mnuReservas = new Menu("Reservas");

	MenuItem mniConsultaPista = new MenuItem("Consultar Pistas Existentes");
	MenuItem mniAltaPista = new MenuItem("Pista Nueva");
	MenuItem mniBajaPista = new MenuItem("Eliminar Pista Existente");
	MenuItem mniModificacionPista = new MenuItem("Modificar Pista");

	MenuItem mniConsultaSocio = new MenuItem("Consultar Socios Existentes");
	MenuItem mniAltaSocio = new MenuItem("Socio Nuevo");
	MenuItem mniBajaSocio = new MenuItem("Eliminar Socio Existente");
	MenuItem mniModificacionSocio = new MenuItem("Modificar Socio");

	MenuItem mniConsultaCuota = new MenuItem("Consultar Cuotas Existentes");
	MenuItem mniAltaCuota = new MenuItem("Cuota Nueva");
	MenuItem mniBajaCuota = new MenuItem("Eliminar Cuota Existente");
	MenuItem mniModificacionCuota = new MenuItem("Modificar Cuota");
	
	MenuItem mniConsultaReserva = new MenuItem("Consultar Reservas Existentes");
	MenuItem mniAltaReserva = new MenuItem("Reserva Nueva");
	MenuItem mniBajaReserva = new MenuItem("Eliminar Reserva Existente");
	MenuItem mniModificacionReserva = new MenuItem("Modificar Reserva");

	public MenuPrincipal()
	{
		menuPrincipal.setLayout(new FlowLayout());
		menuPrincipal.setSize(300, 200);
		menuPrincipal.setResizable(false);
		menuPrincipal.addWindowListener(this);
		menuPrincipal.setLocationRelativeTo(null);
		
		mniAltaPista.addActionListener(this);
		mniBajaPista.addActionListener(this);
		mniConsultaPista.addActionListener(this);
		mniModificacionPista.addActionListener(this);
		
		mnuPistas.add(mniConsultaPista);
		mnuPistas.add(mniAltaPista);
		mnuPistas.add(mniBajaPista);
		mnuPistas.add(mniModificacionPista);
		
		mnuSocios.add(mniConsultaSocio);
		mnuSocios.add(mniAltaSocio);
		mnuSocios.add(mniBajaSocio);
		mnuSocios.add(mniModificacionSocio);
		
		mniAltaCuota.addActionListener(this);
		mniBajaCuota.addActionListener(this);
		mniConsultaCuota.addActionListener(this);
		mniModificacionCuota.addActionListener(this);
		
		mnuCuotas.add(mniConsultaCuota);
		mnuCuotas.add(mniAltaCuota);
		mnuCuotas.add(mniBajaCuota);
		mnuCuotas.add(mniModificacionCuota);
		
		mnuReservas.add(mniConsultaReserva);
		mnuReservas.add(mniAltaReserva);
		mnuReservas.add(mniBajaReserva);
		mnuReservas.add(mniModificacionReserva);
		
		barraMenu.add(mnuPistas);
		barraMenu.add(mnuSocios);
		barraMenu.add(mnuCuotas);
		barraMenu.add(mnuReservas);
		menuPrincipal.setMenuBar(barraMenu);
		
		menuPrincipal.setVisible(true);
	}

	public void windowActivated(WindowEvent windowEvent){}
	public void windowClosed(WindowEvent windowEvent) {}
	public void windowClosing(WindowEvent arg0)
	{
		System.exit(0);
	}
	public void windowDeactivated(WindowEvent windowEvent) {}
	public void windowDeiconified(WindowEvent windowEvent) {}
	public void windowIconified(WindowEvent windowEvent) {}
	public void windowOpened(WindowEvent windowEvent) {}

	public void actionPerformed(ActionEvent evento)
	{
		Object objeto = evento.getSource();
		if(objeto.equals(mniConsultaPista))
		{
			new ConsultaPista();	
		}
		else if(objeto.equals(mniAltaPista)) 
		{
			new AltaPista();
		}
		else if(objeto.equals(mniBajaPista)) 
		{
			new BajaPista();
		}
		else if(objeto.equals(mniModificacionPista)) 
		{
			new ModificacionPista();
		}
		else if(objeto.equals(mniAltaCuota)) 
		{
			new AltaCuota();
		}
	}
}