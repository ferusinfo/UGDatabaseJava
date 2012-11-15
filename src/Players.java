import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Players {
	public DatabaseClass db;
	
	public Players()
	{
		db = new DatabaseClass("localhost","root","root","bazatestowa");
	}
	
	public void ShowAllPlayers() throws SQLException
	{
		ResultSet result = db.query("SELECT * FROM players");
		if(result != null) {
			 while (result.next()) {
				 int id_player = result.getInt("id_player");
			      String nameString = result.getString("player_name");
			      String lastnameString = result.getString("player_lastname");
			      int points = result.getInt("player_points");
			      int matches = result.getInt("player_matches");
			      
			      Player player = new Player(id_player, nameString, lastnameString, points, matches);
			      System.out.println("["+player.id+"] " + player.name + " " + player.lastname + " (Punktów: " + player.points +") {Meczy rozegranych : " + player.matches + "}");
			    }
			 
		}
		
	}
	
	public Player getPlayer(int id_player) throws SQLException
	{
		Player returnPlayer = null;
		ResultSet result = db.query("SELECT * FROM players WHERE id_player = " + id_player);
		if(result != null) {
			 while (result.next()) {

			      String nameString = result.getString("player_name");
			      String lastnameString = result.getString("player_lastname");
			      int points = result.getInt("player_points");
			      int matches = result.getInt("player_matches");
			      
			      returnPlayer = new Player(id_player, nameString, lastnameString, points, matches);
			    }
			 return returnPlayer;
		}
		return null;	
	}
	
	public boolean addPlayer(String player_name, String player_lastname)
	{
		return db.performQuery("INSERT INTO players (player_name, player_lastname) VALUES ('"+player_name+"','"+ player_lastname + "')");
	}
	
	public boolean delPlayer(int id_player)
	{
		return db.performQuery("DELETE FROM players WHERE id_player = '"+ id_player +"'");
	}
}
