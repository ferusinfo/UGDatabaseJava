/**
 * Obiekt gracza
 * @author Maciej 'ferus' Kolek
 * @copyright 2012
 */
public class Player {
	public int id;
	public String name;
	public String lastname;
	public int points;
	public int matches;
	
	/**
	 * Konstruktor
	 * @param id
	 * @param name
	 * @param lastname
	 * @param points
	 * @param matches
	 */
	public Player(int id, String name, String lastname, int points, int matches)
	{
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.points = points;
		this.matches = matches;
	}
}
