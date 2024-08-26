package es.studium.ClubTenis;

import java.awt.Choice;
import java.awt.TextField;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Utilidades
{
	// Conexión a la base de datos
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/clubTenis";
	String usuario = "adminTenis";
	String clave = "remoto";
	String sentencia = "";

	Statement statement = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;

	public Connection conectar()
	{
		Connection connection = null;
		try
		{
			//Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			//Establecer la conexión con la BD Empresa
			connection = DriverManager.getConnection(url, usuario, clave);
		}
		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1 Utilidades-"+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2 Utilidades-"+sqle.getMessage());
		}
		return connection;			
	}

	public void desconectar(Connection c)
	{
		try
		{
			if(c!=null)
			{
				c.close();
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error 3 Utilidades-"+e.getMessage());
		}
	}

	public String getSHA256(String data)
	{
		StringBuffer sb = new StringBuffer();
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(data.getBytes());
			byte byteData[] = md.digest();
			for (int i = 0; i < byteData.length; i++) 
			{
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100,
						16).substring(1));
			}
		} 
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return sb.toString();
	}

	public ResultSet consultar(Connection connection, String sentencia)
	{
		try
		{
			// Crear una sentencia
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 4 Utilidades-"+sqle.getMessage());
		}
		return rs;
	}

	public String listadoPistas(Connection connection)
	{
		String contenido = "";
		try
		{
			// Crear una sentencia
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			rs = statement.executeQuery("SELECT * FROM pistas ORDER BY denominacionPista");
			while(rs.next()) 
			{
				contenido += rs.getInt("idPista") + "\t" + rs.getString("denominacionPista")
				+ "\t" + rs.getString("tipoPista") + "\n";
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 5 Utilidades-"+sqle.getMessage());
		}
		return contenido;
	}

	public boolean validarVacio(TextField textfield)
	{
		if(textfield.getText().equals(""))
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public boolean altaPista(Connection connection, TextField txtDenominacion, TextField txtTipo)
	{
		boolean respuesta = false;
		try
		{
			// Ejecutar la sentencia SQL
			preparedStatement = connection.prepareStatement("INSERT INTO pistas VALUES(null, ?, ?)");
			preparedStatement.setString(1, txtDenominacion.getText());
			preparedStatement.setString(2, txtTipo.getText());
			preparedStatement.executeUpdate();
			respuesta = true;
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 6 Utilidades-"+sqle.getMessage());
			respuesta = false;
		}
		return respuesta;
	}

	public Choice rellenarChoicePistas(Connection connection)
	{
		Choice choice = new Choice();
		choice.add("Seleccionar una Pista...");
		try
		{
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			rs = statement.executeQuery("SELECT * FROM pistas ORDER BY denominacionPista");
			while(rs.next()) 
			{
				choice.add(rs.getInt("idPista") + "-" + rs.getString("denominacionPista")
				+ "-" + rs.getString("tipoPista"));
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 7 Utilidades-"+sqle.getMessage());
		}
		return choice;
	}

	public Boolean bajaPista(Connection connection, String cadena)
	{
		Boolean respuesta = false;
		try
		{
			// Ejecutar la sentencia SQL
			preparedStatement = connection.prepareStatement("DELETE FROM pistas WHERE idPista = ?");
			preparedStatement.setString(1, cadena);
			preparedStatement.executeUpdate();
			respuesta = true;
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 8 Utilidades-"+sqle.getMessage());
			respuesta = false;
		}
		return respuesta;
	}

	public ResultSet obtenerDatosPista(Connection connection, String idPista)
	{
		try
		{
			// Ejecutar la sentencia SQL
			preparedStatement = connection.prepareStatement("SELECT * FROM pistas WHERE idPista = ?");
			preparedStatement.setString(1, idPista);
			rs = preparedStatement.executeQuery();
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 9 Utilidades-"+sqle.getMessage());
		}
		return rs;
	}

	public Boolean modificarPista(Connection connection, int idPista, TextField txtDenominacion, TextField txtTipo)
	{
		Boolean respuesta = false;
		try
		{
			// Ejecutar la sentencia SQL
			preparedStatement = connection.prepareStatement("UPDATE pistas SET denominacionPista = ?, tipoPista = ? WHERE idPista = ?");
			preparedStatement.setString(1, txtDenominacion.getText());
			preparedStatement.setString(2, txtTipo.getText());
			preparedStatement.setInt(3, idPista);
			preparedStatement.executeUpdate();
			respuesta = true;
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 10 Utilidades-"+sqle.getMessage());
			respuesta = false;
		}
		return respuesta;
	}

	public Choice rellenarChoiceSocios()
	{
		Connection connection = conectar();
		Choice choice = new Choice();
		choice.add("Seleccionar un Socio...");
		try
		{
			statement = connection.createStatement();
			// Crear un objeto ResultSet para guardar lo obtenido
			// y ejecutar la sentencia SQL
			rs = statement.executeQuery("SELECT idSocio, apellidosSocio, nombreSocio FROM socios ORDER BY apellidosSocio, nombreSocio");
			while(rs.next()) 
			{
				choice.add(rs.getInt("idSocio") + "-" + rs.getString("apellidosSocio")
				+ "-" + rs.getString("nombreSocio"));
			}
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 11 Utilidades-"+sqle.getMessage());
		}
		desconectar(connection);
		return choice;
	}

	public boolean validarChoiceVacio(Choice choSocio)
	{
		boolean correcto = false;
		if(choSocio.getSelectedIndex()!=0)
		{
			correcto = true;
		}
		return correcto;
	}

	public Boolean altaCuota(TextField txtFechaCuota, TextField txtImporte, TextField txtFechaPago, TextField txtTipo,
			int idSocioFK)
	{
		Connection connection = conectar();
		boolean correcto = false;
		try
		{
			// Ejecutar la sentencia SQL
			preparedStatement = connection.prepareStatement("INSERT INTO cuotas VALUES(null, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, cambiarFechaMySQL(txtFechaCuota.getText()));
			preparedStatement.setString(2, txtImporte.getText());
			preparedStatement.setString(3, cambiarFechaMySQL(txtFechaPago.getText()));
			preparedStatement.setString(4, txtTipo.getText());
			preparedStatement.setInt(5, idSocioFK);
			preparedStatement.executeUpdate();
			correcto = true;
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 12 Utilidades-"+sqle.getMessage());
			correcto = false;
		}
		desconectar(connection);
		return correcto;
	}

	private String cambiarFechaMySQL(String fechaEuropea)
	{
		String fechaMySQL = null;
		if(fechaEuropea.length()!=0) // La Fecha de Pago puede estar vacía 
		{
			String[] cadena = fechaEuropea.split("/");
			fechaMySQL = cadena[2]+"-"+cadena[1]+"-"+cadena[0];
		}
		return fechaMySQL;
	}
}