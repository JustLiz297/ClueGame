/*
 * Authors: Elizabeth (Liz) Boyle, Annelyse Baker
 * Description: Exception thrown when the files aren't correct
 */

/**
 * @author eboyle, annelysebaker
 *
 */
package clueGame;
/**
 * The custom exception that is thrown when the files don't load properly or weren't valid to pass in
 * @author eboyle, annelysebaker
 * @version 1.0
 *
 */
public class BadConfigFormatException extends Exception{

	public BadConfigFormatException() {
		super("Configuration Error");
		System.out.println(this.getMessage());
	}
	
	public BadConfigFormatException(String message) {
		super(message);
		System.out.println(message);
	}

	
	
}
