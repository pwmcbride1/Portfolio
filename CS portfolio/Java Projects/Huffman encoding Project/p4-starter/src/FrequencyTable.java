import java.util.HashMap;

/**
 * TODO: Complete the implementation of this class.
 */

public class FrequencyTable extends HashMap<Character, Integer> {
  /**
   * Constructs an empty table.
   */
  public FrequencyTable() {
    super();
  }
    
  /**
   * TODO: Make use of get() and put().
   * 
   * Constructs a table of character counts from the given text string.
   */
  
  /**
   * Takes a string then creates a hashmap of the characters
   * then it takes all the characters in the given string
   * it gets the char at that location then checks to see if it has a frequency count 
   * if it doesn't it adds the char and its frequency (which is one) to the table
   * if the char is in the table it adds one to the value of the character
   * @param text
   */
  public FrequencyTable(String text) {
	  super();
	  for(int i = 0; i < text.length(); i++) {
		  char c = text.charAt(i);
		  Integer val = this.get(c);
		  if(val != null) {
			  this.put(c, new Integer(val + 1));
		  }
		  else {
			  this.put(c, 1);
		  }
	  }
  }
  
  
  /**
   * TODO
   * 
   * Returns the count associated with the given character. In the case that
   * there is no association of ch in the map, return 0.
   */
  
  /**
   * iterates through the frequency table and checks for the given character at that index
   * it then sets the count integer to the value associated with the character
   * returns the Integer
   */
  @Override
  public Integer get(Object ch) {
	  Integer count = 0;
	  for(int i = 0; i < this.size(); i++) {
		  if(this.containsKey(ch)) {
			  count = super.get(ch);
		  }
	  }
	return count;
  }
}
