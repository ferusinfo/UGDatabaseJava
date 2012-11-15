/**
 * Klasa do zarządzania graczami
 * @author Maciej 'ferus' Kolek
 * @copyright 2012
 */

/**
 * Import bibliotek
 */
import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Players {
	/**
	 * Połączenie z bazą
	 * @var DatabaseClass
	 */
	public DatabaseClass db;
	
	public Players()
	{
		db = new DatabaseClass("localhost","root","root","bazatestowa");
	}
	
	public void ShowAllPlayers() throws SQLException
	{
		ResultSet result = db.query("SELECT * FROM players ORDER BY player_points DESC");
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
	
	public boolean createTables()
	{
		boolean status = false;
		status = db.performQuery("CREATE TABLE IF NOT EXISTS `matches` ( `id_match` int(11) NOT NULL AUTO_INCREMENT,`match_player1` int(11) NOT NULL,`match_player2` int(11) NOT NULL, `match_player1_points` int(11) NOT NULL,`match_player2_points` int(11) NOT NULL, `match_won` int(11) NOT NULL,PRIMARY KEY (`id_match`))");
		status = db.performQuery("CREATE TABLE IF NOT EXISTS `players` (`id_player` int(11) NOT NULL AUTO_INCREMENT,`player_name` varchar(70) COLLATE utf8_bin NOT NULL,`player_lastname` varchar(70) COLLATE utf8_bin NOT NULL,`player_points` int(11) NOT NULL DEFAULT '0',`player_matches` int(11) NOT NULL DEFAULT '0',PRIMARY KEY (`id_player`))");
		return status;
	}
	
	public boolean dropTables()
	{
		return db.performQuery("DROP TABLE players, matches");
	}
}
