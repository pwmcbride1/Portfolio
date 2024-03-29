

/**
 * TODO: There are two methods for you to implement in this class.
 * 
 * A Zipper is used to encode/decode text strings based on a supplied code book.
 * Additionally, a Zipper is capable of compressing a bit string into a string of
 * characters, as well as the inverse (i.e., decompressing a character string
 * into a bit string).
 */

public class Zipper {
  private CodeBook book;   // used for encoding
  private HuffmanTree ht;  // used for decoding
  
  /**
   * Create a Zipper from the provided code book.
   */
  public Zipper(CodeBook book) {
    this.book = book;
    ht = book.getHuffmanTree();
  }
  
  /**
   * TODO: Use a StringBuilder, not a String, to build up the result.
   * 
   * Returns the bit string encoding of the given plain text.
   */
  
  /**
   * splits the given string into an array of characters then iterates through them all
   * encode gets the huffman trees bit string and appends that to the string builder
   * after encoding every character the method returns the stringbuilder as a string
   * @param plainText
   * @return
   */
  public String encode(String plainText) {
    StringBuilder sb = new StringBuilder();
    char[] character = plainText.toCharArray();
    for(char x : character) {
    	sb.append(book.encodeChar(x));
    }
    return sb.toString();
  }
  
  /**
   * TODO: Use a StringBuilder, not a String, to build up the result.
   * 
   * Returns the text string corresponding to the given bit string.
   */  
  
  /**
   * decode takes each character in the char array and keeps track of which direction to take
   * once it gets to the leaf in its path it appends the char to the stringbuilder
   * the method then returns the stringbuilder as a string
   * @param bits
   * @return
   */
  public String decode(String bits) {
    StringBuilder sb = new StringBuilder();
	  HuffmanTree.Node current = ht.root;
	  char[] chars = bits.toCharArray();
	  for(char x : chars) {
		  if(x == '0' && current.left != null) {
			  current = current.left;
		  }
		  else if(x == '1' && current.right != null) {
			  current = current.right;
		  }
		  if(current.left == null && current.right == null) {
			  sb.append(current.key);
			  current = ht.root;
		  }
	  }
	  return sb.toString();
  }
  
  /**
   * Returns the result of compressing a string of bits into a string of
   * 8-bit characters.
   */
  public String compress(String bits) {
    int n = bits.length();
    // The last byte may not be full. We'll need to know how many bits are in
    // the last byte, so that we can decompress properly later. We will prepend
    // the compressed string with a head byte that holds the size of the last
    // byte.
    int lastBiteSize = n % Constants.BITESIZE;
    if (lastBiteSize == 0)
      lastBiteSize = Constants.BITESIZE;  // the last byte is full
    String headByte = Util.padLeft(Integer.toBinaryString(lastBiteSize), Constants.BITESIZE);
    StringBuilder chars = new StringBuilder();
    chars.append(Util.bitsToAscii(headByte));
    for (int i = 0; i < n; i += Constants.BITESIZE) {
      String block = bits.substring(i, Math.min(n, i + Constants.BITESIZE));
      chars.append(Util.bitsToAscii(block));
    }
    return chars.toString();
  }
  
  /**
   * Returns the result of expanding the given compressed text into a bit string.
   */
  public String decompress(String compressedText) {
    // Process the head byte to retrieve the size of the last byte.
    int lastBiteSize = (int) compressedText.charAt(0);
    String bits = "";
    int n = compressedText.length();
    for (int i = 1; i < n - 1; i++) 
      bits += Util.asciiToBits(compressedText.charAt(i), Constants.BITESIZE);
    bits += Util.asciiToBits(compressedText.charAt(n - 1), lastBiteSize);
    return bits.toString();
  } 
}
