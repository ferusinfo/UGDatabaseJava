/**
 * Główna klasa programu
 * @author Maciej 'ferus' Kolek
 * @copyright 2012
 */

/**
 * Import bibliotek
 */
import java.io.*;
import java.sql.*;
import java.util.*;
//MySQL driver
import com.mysql.jdbc.*;

/**
 * Main program class
 */
public class MainClass {

	/**
	 * Main method
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		boolean a = true;
		while(a)
		{
			showMenu();
		}
	}
	
	/**
	 * Show menu, fool!
	 */
	private static void showMenu(){
		
		System.out.println("=== M E N U ===");
		System.out.println("1 - Przegląd graczy");
		System.out.println("2 - Dodawanie gracza");
		System.out.println("3 - Usuwanie gracza");
		System.out.println("4 - Rozpoczęcie meczu");
		System.out.println("5 - Lista meczy");
		System.out.println("6 - Stwórz tabele");
		System.out.println("7 - Zrzuć tabele");
		System.out.println("0 - Wyjście z programu");
		System.out.println("===============");
		
		Scanner scanOption = new Scanner(System.in);
		int chosen = scanOption.nextInt();
		
		//Declare players object
		Players players = new Players();
		
		switch(chosen)
		{
			case 1:
				try {
					players.ShowAllPlayers();
					System.out.println();
					System.out.println();
				} catch (SQLException e){
					e.printStackTrace();
				}
			break;
			case 2:
				System.out.println("Imie: ");
				String player_name = scanOption.next();
				System.out.println("Nazwisko: ");
				String player_lastname = scanOption.next();
				
				boolean status = players.addPlayer(player_name, player_lastname);
				String statusString = status ? "Poprawnie dodano." : "Wystapil blad.";
				System.out.println(statusString + " Powrót do menu...");
			break;
			case 3:
				boolean delStatus = false;
				while (!delStatus) {
					System.out.println("Podaj ID: ");
					int player_id = scanOption.nextInt();
					delStatus = players.delPlayer(player_id);
				}
				System.out.println("Usunięto poprawnie. Powrót do menu...");
			break;
			case 4:
				Match match = new Match();
				match.addMatch();
			break;
			case 5:
				try {
					Match match1 = new Match();
					match1.getLatestMatches();
				}catch (SQLException e){
					e.printStackTrace();
				}
			break;
			case 6:
				players.createTables();
			break;
			case 7:
				players.dropTables();
			break;
			case 0:
				players.db.disconnect();
				System.exit(1);
			break;
			default:
			break;
		}
	}

}
