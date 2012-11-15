import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class Match {
	/**
	 * Nożyczki
	 * @var int
	 */
	public int Scissors = 1;
	
	/**
	 * Papier
	 * @var int
	 */
	public int Paper = 2;
	
	/**
	 * Kamień
	 * @var int
	 */
	public int Rock = 3;
	
	/**
	 * Mapowanie co bije jaki przedmiot
	 * @var HashMap
	 */
	private HashMap<Integer, Integer> beatsList = new HashMap<Integer, Integer>();
	
	/**
	 * Mapowanie intów do stringów przedmiotu
	 * @var HashMap
	 */
	private HashMap<Integer, String> choiceDict = new HashMap<Integer, String>();
	
	/**
	 * Ilość punktów które należą się za wygraną
	 * @var int
	 */
	private int winPoints = 10;
	
	/**
	 * Ilość punktów które należą się za przegraną
	 * @var int
	 */
	private int loosePoints = 5;
	
	/**
	 * ID pierwszego gracza
	 * @var int
	 */
	private int firstPlayer;
	
	/**
	 * ID drugiego gracza
	 * @var int
	 */
	private int secondPlayer;
	
	/**
	 * Obiekt pierwszego gracza
	 * @var Player
	 */
	private Player firstPlayerObj;
	
	/**
	 * Obiekt drugiego gracza
	 * @var Player
	 */
	private Player secondPlayerObj;
	
	/**
	 * Ilość punktów pierwszego gracza w danym meczu
	 * @var int
	 */
	private int firstScore;
	
	/**
	 * Ilość puntków drugiego gracza w danym meczu
	 * @var int
	 */
	private int secondScore;
	
	/**
	 * Połączenie z bazą
	 * @var DatabaseClass
	 */
	private DatabaseClass db;
	
	/**
	 * Konstruktor
	 */
	public Match()
	{
		beatsList.put(Scissors,2);
		beatsList.put(Paper, 3);
		beatsList.put(Rock, 1);
		
		choiceDict.put(Scissors,"Nożyczki");
		choiceDict.put(Paper,"Papier");
		choiceDict.put(Rock,"Kamień");
		
		db = new DatabaseClass("localhost","root","root","bazatestowa");
	}
	
	/**
	 * Zmiana ilości punktów
	 * @param id_player int ID gracza
	 * @param points int Ilość puntków do ustawienia
	 */
	private void addPoints(int id_player, int points)
	{
		db.performQuery("UPDATE players SET player_points = " + points + " WHERE id_player = " + id_player);
	}
	
	/**
	 * Zmiana ilości rozegranych meczy
	 * @param id_player int ID gracza
	 * @param count int ilość gier naszego gracza
	 */
	private void updateMatchCount(int id_player, int count)
	{
		db.performQuery("UPDATE players SET player_matches = " + count + " WHERE id_player = " + id_player);
	}
	
	/**
	 * Dodawanie i generowanie wyniku meczu.
	 */
	public void addMatch()
	{
		Players players = new Players();
		
		Scanner scan = new Scanner(System.in);
		Player resultPlayer = null;
		
		while (resultPlayer == null)
		{
			System.out.println("Wybierz pierwszego gracza:");
			firstPlayer =  scan.nextInt();
			try {
				resultPlayer = players.getPlayer(firstPlayer);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		firstPlayerObj = resultPlayer;
		resultPlayer = null;
		
		while (resultPlayer == null)
		{
			System.out.println("Wybierz drugiego gracza:");
			secondPlayer =  scan.nextInt();
			try {
				resultPlayer = players.getPlayer(secondPlayer);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		secondPlayerObj = resultPlayer;
		
		firstScore = 0;
		secondScore = 0;
		
		String firstPL = firstPlayerObj.name + " " + firstPlayerObj.lastname;
		String secondPL = secondPlayerObj.name + " " + secondPlayerObj.lastname;
		
		while (firstScore < 2 && secondScore <2)
		{
			int firstChoice = generateChoice();
			int secondChoice = generateChoice();
			
			System.out.println("W tym losowaniu wylosowano:");
			System.out.println(firstPL + ": " + choiceDict.get(firstChoice));
			System.out.println(secondPL + ": " + choiceDict.get(secondChoice));
			
			if ((Integer) beatsList.get(firstChoice) == secondChoice)
			{
				firstScore++;
				System.out.println("Wygrywa: " + firstPL);
			}
			else if ((Integer) beatsList.get(secondChoice) == firstChoice)
			{
				secondScore++;
				System.out.println("Wygrywa: " + secondPL);
			}
			else
			{
				System.out.println("Remis.");
			}
			System.out.println();
			System.out.println();
		}
		
		int firstPoints = firstPlayerObj.points;
		int secondPoints = secondPlayerObj.points;
		int winner = 0;
		
		if (firstScore == 2)
		{
			winner = firstPlayerObj.id;
			firstPoints += winPoints;
			secondPoints += loosePoints;
			System.out.println("Cały mecz wygrywa: " + firstPL);
		}
		else
		{
			winner = secondPlayerObj.id;
			firstPoints += loosePoints;
			secondPoints += winPoints;
			System.out.println("Cały mecz wygrywa: " + secondPL);
		}
		
		addMatchToDB(firstPlayerObj.id, secondPlayerObj.id, firstScore, secondScore, winner);
		addPoints(firstPlayerObj.id, firstPoints);
		addPoints(secondPlayerObj.id, secondPoints);
		updateMatchCount(firstPlayerObj.id, firstPlayerObj.matches + 1);
		updateMatchCount(secondPlayerObj.id, secondPlayerObj.matches + 1);
	}

	/**
	 * Dodawanie meczu do bazy
	 * @param id int ID pierwszego gracza
	 * @param id2 int ID drugiego gracza
	 * @param firstScore int punkty pierwszego gracza
	 * @param secondScore int punkty drugiego gracza
	 * @param winner int ID zwyciezcy
	 * @return boolean status dodawania do bazy
	 */
	private boolean addMatchToDB(int id, int id2, int firstScore,
			int secondScore, int winner) {
		return db.performQuery("INSERT INTO matches (match_player1, match_player2, match_player1_points, match_player2_points, match_won) " +
				"VALUES ("+ id + "," + id2 + "," + firstScore + "," + secondScore + "," + winner + ")");
	}
	
	/**
	 * Generacja pomiedzy wybranymi przedmiotami
	 * @return int wybrany przedmiot
	 */
	private int generateChoice()
	{
		Random rand = new Random();
		return rand.nextInt(3 - 1 + 1) + 1;
	}
	
	/**
	 * Pobieranie ostatnich meczy
	 * @throws SQLException
	 */
	public void getLatestMatches() throws SQLException
	{
		ResultSet result;
		result = db.query("SELECT p1.player_name, p1.player_lastname, p2.player_name, p2.player_lastname, m.match_player1_points, m.match_player2_points FROM " +
				"matches as m JOIN players as p1 ON match_player1 = p1.id_player JOIN players as p2 ON match_player2 = p2.id_player ORDER BY id_match ASC");
		if(result != null) {
			int i = 0;
			
			 while (result.next()) {
			  String player1 = result.getString("p1.player_name") + " " + result.getString("p1.player_lastname");
			  String player2 = result.getString("p2.player_name") + " " + result.getString("p2.player_lastname");
			  int player1Points = result.getInt("m.match_player1_points");
			  int player2Points = result.getInt("m.match_player2_points");
			  i++;
			  
			  System.out.println(i + ". " + player1 + " vs. " + player2 + " [" + player1Points + ":" + player2Points +"]");
			  System.out.println();    
			 } 
		}
		else
		{
			System.out.println("Brak meczy.");
		}
	}
}
