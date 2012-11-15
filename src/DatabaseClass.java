/**
 * Klasa do polaczenia z baza i wykonywania na niej jakis czynnosci
 * @author Maciej 'ferus' Kolek
 * @copyright 2012
 */

import java.io.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.jdbc.*;

public class DatabaseClass{
	/**
	 * Host do bazy
	 * @var String
	 */
	private String host;
	
	/**
	 * User bazy
	 * @var String
	 */
	private String user;
	
	/**
	 * Hasło do bazy
	 * @var String
	 */
	private String password;
	
	/**
	 * Wybrana baza danych
	 * @var String
	 */
	private String db;
	
	/**
	 * Połączenie
	 * @var Connection
	 */
	private Connection conn;
	
	/**
	 * Statement do wykonywania czynnosci
	 * @var Statemen
	 */
	private Statement st;
	
	/**
	 * Konstruktor
	 * @param host String host
	 * @param user String user 
	 * @param password String hasło
	 * @param db String wybrana baza
	 */
	public DatabaseClass(String host, String user, String password, String db)
	{
		this.host = host;
		this.user = user;
		this.password = password;
		this.db = db;
		
		connect();
	}
	
	/**
	 * Łączenie z bazą
	 */
	private void connect()
	{
        String url = "jdbc:mysql://" + host + "/" + db;
        String user = this.user;
        String password = this.password;
        
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        	conn = DriverManager.getConnection(url, user, password);
        	conn.setAutoCommit(false);
        	st = (Statement) conn.createStatement();
        } catch (Exception e) {
        	System.out.println("Could not connect to database.");
			System.out.println(e);
		}
	}
	
	/**
	 * Odłączanie
	 */
	public void disconnect()
	{
		try {
			conn.close();
		}
		catch (Exception e)
		{
			System.out.println("Could not disconnect.");
		}
	}
	
	/**
	 * Metoda do selectów
	 * @param query String SQL
	 * @return ResultSet result
	 */
	public ResultSet query(String query)
	{
		ResultSet result = null;
		try {
			result = st.executeQuery(query);
		}
		catch (Exception e){
			System.out.println(e);
		}
		
		return result;
	}
	
	/**
	 * Metoda do wykonywania operacji na bazie (INSERT, UPDATE, DELETE)
	 * @param query String SQL do wykonania
	 * @return boolean status dodania
	 */
	public boolean performQuery(String query)
	{
		boolean boolResult = false;
		int result = 0;
		try {
			result = st.executeUpdate(query);
		}
		catch (Exception e){
			System.out.println(e);
		}
		boolResult = (0 != result) ? true : false;
		return boolResult;
	}
}