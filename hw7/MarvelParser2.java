package hw7;
import java.io.*;
import java.util.*;

/**
 * Parser utility to load the Marvel Comics dataset.
 */
public class MarvelParser2 {
    /**
     * A checked exception class for bad data files
     */
    @SuppressWarnings("serial")
    public static class MalformedDataException extends Exception {
        public MalformedDataException() { }

        public MalformedDataException(String message) {
            super(message);
        }

        public MalformedDataException(Throwable cause) {
            super(cause);
        }

        public MalformedDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }

  /**
   * Reads the Marvel Universe dataset.
   * Each line of the input file contains a character name and a comic
   * book the character appeared in, separated by a tab character
   * 
   * @requires filename is a valid file path
   * @param filename the file that will be read
   * @param characters list in which all character names will be stored;
   *          typically empty when the routine is called
   * @param characterLins map from characters to the number of times they appear in 
   * 		the same comic book with another character; typically empty when 
   * 		the routine is called
   * @modifies characters, books
   * @effects fills characters with a list of all unique character names
   * @effects fills books with a map from each comic book to all characters
   *          appearing in it
   * @throws MalformedDataException if the file is not well-formed:
   *          each line contains exactly two tokens separated by a tab,
   *          or else starting with a # symbol to indicate a comment line.
   */
  public static void parseData(String filename, Set<String> characters,
      Map<String, Map<String, Double>> characterLinks) throws MalformedDataException {
    // Why does this method accept the Collections to be filled as
    // parameters rather than making them a return value? To allows us to
    // "return" two different Collections. If only one or neither Collection
    // needs to be returned to the caller, feel free to rewrite this method
    // without the parameters. Generally this is better style.
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(filename));
        Map<String, List<String>> books = new HashMap<>();

        // Construct the collections of characters and books, one
        // <character, book> pair at a time.
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {

            // Ignore comment lines.
            if (inputLine.startsWith("#")) {
                continue;
            }

            // Parse the data, stripping out quotation marks and throwing
            // an exception for malformed lines.
            inputLine = inputLine.replace("\"", "");
            String[] tokens = inputLine.split("\t");
            if (tokens.length != 2) {
                throw new MalformedDataException("Line should contain exactly one tab: "
                                                 + inputLine);
            }

            String character = tokens[0];
            String book = tokens[1];

            // Add the parsed data to the character and book collections.
            characters.add(character);
            if (!books.containsKey(book)) {
                books.put(book, new ArrayList<String>());
            } else {
            	List<String> charInBook = books.get(book);
            	if (!characterLinks.containsKey(character)) {
            		characterLinks.put(character, new HashMap<String, Double>());
            	}
            	Map<String, Double> associatedChars = characterLinks.get(character);
            	for (String ch: charInBook) {
            		if (!associatedChars.containsKey(ch)) {
            			associatedChars.put(ch, 0.0);
            		}
            		associatedChars.put(ch, associatedChars.get(ch) + 1);
            		
            		if (!characterLinks.containsKey(ch)) {
                		characterLinks.put(ch, new HashMap<String, Double>());
                	}
            		Map<String, Double> otherChar = characterLinks.get(ch);
            		if (!otherChar.containsKey(character)) {
            			otherChar.put(character, 0.0);
            		}
            		otherChar.put(character, otherChar.get(character) + 1);
            	}
            }
            books.get(book).add(character);
        }
    } catch (IOException e) {
        System.err.println(e.toString());
        e.printStackTrace(System.err);
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println(e.toString());
                e.printStackTrace(System.err);
            }
        }
    }
  }

}
