import java.util.Collections;
import java.util.Comparator;

/**
 * TODO: Complete the implementation of this class.
 * 
 * A HuffmanTree represents a variable-length code such that the shorter the
 * bit pattern associated with a character, the more frequently that character
 * appears in the text to be encoded.
 */

public class HuffmanTree {
  
  class Node {
    protected char key;
    protected int priority;
    protected Node left, right;
    
    public Node(int priority, char key) {
      this(priority, key, null, null);
    }
    
    public Node(int priority, Node left, Node right) {
      this(priority, '\0', left, right);
    }
    
    public Node(int priority, char key, Node left, Node right) {
      this.key = key;
      this.priority = priority;
      this.left = left;
      this.right = right;
    }
    
    public boolean isLeaf() {
      return left == null && right == null;
    }
  }
  
  protected Node root;
  
  /**
   * TODO
   * 
   * Creates a HuffmanTree from the given frequencies of letters in the
   * alphabet using the algorithm described in lecture.
   */
  
  /**
   * creates huffman tree with the defined comparator
   * instantiates a "forest" heap
   * adds all the nodes into the forest heap as roots
   * then runs algorithm on the two lowest priority nodes in the queue
   * adds their frequencies together then makes a new node with those two nodes as its children
   * then adds them back into the forest until there is only one root
   * @param charFreqs
   */
  public HuffmanTree(FrequencyTable charFreqs) {
    Comparator<Node> comparator = (x, y) -> {
      /**
       *  TODO: x and y are Nodes
       *  x comes before y if x's priority is less than y's priority
       */
      return x.priority - y.priority;
    };  
    
    
    PriorityQueue<Node> forest = new Heap<Node>(comparator);

    /**
     * TODO: Complete the implementation of Huffman's Algorithm.
     * Start by populating forest with leaves.
     */
    if(charFreqs.size() == 0) {
    	return;
    }
    else {
    	for(char x : charFreqs.keySet()) {
    		Node n = new Node(charFreqs.get(x), x);
            forest.insert(n);
    		}
        
    	while(forest.size() > 1) {
    		Node least1 = forest.delete();
    		Node least2 = forest.delete();
    		Node compound = new Node(least1.priority + least2.priority, least1, least2);
    		forest.insert(compound);
    	}
    	root = forest.peek();
    }
  }
  
  
  /**
   * TODO
   * 
   * Returns the character associated with the prefix of bits.
   * 
   * @throws DecodeException if bits does not match a character in the tree.
   */
  
  /**
   * iterates through all the characters in the given bit string 
   * decodeChar "follows" 0 - left and 1 - right until it hits the leaf which will be 
   * a character then it returns that char
   * @param bits
   * @return
   */
  public char decodeChar(String bits) {
	  if(root == null) {
		  throw new DecodeException(bits);
	  }
	  char character = 0;
	  Node current = root;
	  char[] chars = bits.toCharArray();
	  for(char x : chars) {
		  if(x == '0' && current.left != null) {
			  current = current.left;
		  }
		  else if(x == '1' && current.right != null) {
			  current = current.right;
		  }
		  if(current.left == null && current.right == null) {
			  character = current.key;
		  }
	  }
	  return character;
  }
    
  /**
   * TODO
   * 
   * Returns the bit string associated with the given character. Must
   * search the tree for a leaf containing the character. Every left
   * turn corresponds to a 0 in the code. Every right turn corresponds
   * to a 1. This function is used by CodeBook to populate the map.
   * 
   * @throws EncodeException if the character does not appear in the tree.
   */
  
  /**
   * uses a helper to get the bit string of the given char
   * lookuphelper makes a string that keep tracks of what turns you make when 
   * following the path to the char (ie 0 - left 1 - right)
   * then it returns the bit string of the char
   * @param ch
   * @return
   */
  public String lookup(char ch) {
    return lookupHelper(root, ch, "");
  }
  
  public String lookupHelper(Node root, char ch, String x) {
	  String result;
	  if(root == null) {
		  return "";
	  }
	  if(!root.isLeaf()) {
		  if((result = lookupHelper(root.left, ch, x + '0')) == null) {
			  result = lookupHelper(root.right, ch, x + '1');
		  }
	  }
	  else {
		  result = (ch == root.key) ? x : null;
	  }
	  return result;
  }
}

