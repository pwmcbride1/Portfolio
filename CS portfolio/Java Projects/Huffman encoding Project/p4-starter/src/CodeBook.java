import java.util.HashMap;

/**
 * TODO: There are two methods for you to implement in this class.
 *
 * A CodeBook maps characters to bit strings. The code is specific to a 
 * text string so as to achieve maximal compression.
 * 
 * Once constructed, the book is used to decode single characters in the 
 * code. This is more efficient than looking up the code in the Huffman
 * tree each time it is needed.
 * 
 * A CodeBook creates a HuffmanTree as an artifact, and provides a getter
 * to make the tree available. 
 */

public class CodeBook {
  private HashMap<Character, String> book = new HashMap<>();
  private HuffmanTree ht;
  private FrequencyTable freqs;
  private int n = 1;

  /**
   * Creates a CodeBook based on the probabilities of English letters.
   * Source: Wikipedia.
   */
  public CodeBook() {
    this.freqs = new FrequencyTable();
    String alpha = "abcdefghijklmnopqrstuvwxyz";
    double[] probs = new double[] { 
        11.602, 4.702, 3.511, 2.670, 2.007, 3.779, 1.950,
        7.232, 6.286, 0.597, 0.590, 2.705, 4.383, 2.365,  
        6.264, 2.545, 0.173, 1.653, 7.755, 16.671, 1.487,  
        0.649, 6.753, 0.017, 1.620, 0.034,
    };
    for (int i = 0; i < alpha.length(); i++)
      freqs.put(alpha.charAt(i), (int) (probs[i] * 1000));
    ht = new HuffmanTree(freqs);
    readCodeFromTree();
  }

  /**
   * Creates a CodeBook optimized for the supplied text string.
   */
  public CodeBook(String text) {
    freqs = new FrequencyTable(text);
    n = text.length();
    ht = new HuffmanTree(freqs);
    readCodeFromTree();
  }

  /**
   * TODO
   * 
   * Reads the code represented by the HuffmanTree and stores each
   * association in the map.
   */
  
  /**
   * iterates through all of the chars (ie keys) in the frequency table
   * it then calls the codebooks huffman tree to make a string of bits for the character
   * puts the char and bit string into the book hashmap
   */
  private void readCodeFromTree() {
	  for(char x : freqs.keySet()) {
		  String bit = ht.lookup(x);
		  book.put(x, bit);
	  }
  }
  
  /**
   * Returns the HuffmanTree associated with this CodeBook.
   */
  public HuffmanTree getHuffmanTree() {
    return ht;
  }

  /**
   * Returns the size of the alphabet associated with this book.
   */
  public int size() {
    return book.size();
  }

  /**
   * Returns a measure of the effectiveness of this code, as a sum of
   * normalized char frequencies weighted by the length of their encodings.
   */
  public double getWeightedAverage() {
    double sum = 0;
    for (char ch : freqs.keySet()) 
      sum += book.get(ch).length() * ((double) freqs.get(ch) / n);
    return sum;
  }

  /**
   * Returns a textual representation of this code.
   */
  public String toString() {
    return book.toString();
  }

  /**
   * TODO
   * 
   * Returns the bit string encoding for the given character by looking up
   * the character in the map. Important: do not search the HuffmanTree here!
   */
  
  /**
   * throws an encodeexception if the given character isn't in the book hashmap
   * returns the bit string for the given char by using the book.get method
   * @param ch
   * @return
   */
  public String encodeChar(Character ch) {
    if(!book.containsKey(ch)) {
    	String s = ch.toString();
    	throw new EncodeException(ch);
    }
    return book.get(ch);
  }
}

/**
 * Exception to signal a problem with decoding.
 */
class DecodeException extends RuntimeException {
  DecodeException(String bits) {
    super("Cannot decode " + bits);
  }
}

/**
 * Exception to signal a problem with encoding.
 */
class EncodeException extends RuntimeException {
  EncodeException(Character ch) {
    super("Cannot encode " + ch);
  }
}
