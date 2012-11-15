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
	private String host;
	private String user;
	private String password;
	private String db;
	private Connection conn;
	private Statement st;
	
	public DatabaseClass(String host, String user, String password, String db)
	{
		this.host = host;
		this.user = user;
		this.password = password;
		this.db = db;
		
		connect();
	}
	
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
        //System.out.println("Connected to db.");
	}
	
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