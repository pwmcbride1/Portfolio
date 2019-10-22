import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * TODO: Complete the implementation of this class.
 * 
 * The keys in the heap must be stored in an array.
 * 
 * There may be duplicate keys in the heap.
 * 
 * The constructor takes an argument that specifies how objects in the 
 * heap are to be compared. This argument is a java.util.Comparator, 
 * which has a compare() method that has the same signature and behavior 
 * as the compareTo() method found in the Comparable interface. 
 * 
 * Here are some examples of a Comparator<String>:
 *    (s, t) -> s.compareTo(t);
 *    (s, t) -> t.length() - s.length();
 *    (s, t) -> t.toLowerCase().compareTo(s.toLowerCase());
 *    (s, t) -> s.length() <= 3 ? -1 : 1;  
 */

public class Heap<E> implements PriorityQueue<E> {
  protected List<E> keys;
  private Comparator<E> comparator;
  
   /**
   * TODO
   * 
   * Creates a heap whose elements are prioritized by the comparator.
   */
  
  /**
   * heap constructor that instantiates the keys list to an arraylist and 
   * sets the comparator to be the given comparator
   * @param comparator
   */
  public Heap(Comparator<E> comparator) {
	  this.comparator = comparator;
	  keys = new ArrayList<E>();
	  }

  /**
   * Returns the comparator on which the keys in this heap are prioritized.
   */
  public Comparator<E> comparator() {
    return comparator;
  }

  /**
   * TODO
   * 
   * Returns the top of this heap. This will be the highest priority key. 
   * @throws NoSuchElementException if the heap is empty.
   */
  
  /**
   * returns the first element in the list
   * keys is sorted by the comparator
   */
  public E peek() throws NoSuchElementException {
	  if(keys.size() == 0) {
		  throw new NoSuchElementException();
	  }
	  keys.sort(comparator);
    return keys.get(0);
  }

  /**
   * TODO
   * 
   * Inserts the given key into this heap. Uses siftUp().
   */
  
  /**
   * adds the given key to the end of the keys list then sifts the key up
   * to maintain the heap property
   */
  public void insert(E key) {
	  keys.add(key);
	  int i = keys.size() - 1;
	  siftUp(i);
  }

  /**
   * TODO
   * 
   * Removes and returns the highest priority key in this heap.
   * @throws NoSuchElementException if the heap is empty.
   */
  
  /**
   * Still times out on autograder
   * swaps the root with the last element of the list
   * then deletes the last element of the list
   * then returns the deleted element
   * this keeps delete from taking too much time because it doesn't have to 
   * iterate through the key list if the last element is removed
   * the new root of the heap is then sifted down to the right spot
   */
  public E delete() throws NoSuchElementException {
	  if(keys.isEmpty()) {
		  throw new NoSuchElementException();
	  }
	  E root = keys.get(0);
	  swap(0, keys.size() -1);
	 keys.remove(keys.size() - 1);
	  siftDown(0);
    return root;
  }

  /**
   * TODO
   * 
   * Restores the heap property by sifting the key at position p down
   * into the heap.
   */
  
  /**
   * sifts a given element down
   * gets the leftchild of the element then checks to see if the element should be swapped
   * they should be swapped if the element doesn't maintain the heap property
   * this then recurses on the leftchild at the new location in the heap
   * same thing happens with the rightchild 
   * @param p
   */
  public void siftDown(int p) {
	  int left = getLeft(p);
	  int size = keys.size() - 1;
	  if(left > size) {
		  return;
	  }
	  else if(this.comparator.compare(keys.get(left), keys.get(p)) < 0) {
		swap(p, left);
		siftDown(left);
	  }
	  int right = getRight(p);
	  if(right > size) {
		  return;
	  }
	  else if(this.comparator.compare(keys.get(right), keys.get(p)) < 0) {
		  swap(p, right);
		  siftDown(right);
	  }
  }
  
  /**
   * TODO
   * 
   * Restores the heap property by sifting the key at position q up
   * into the heap. (Used by insert()).
   */
  
  /**
   * moves the int index up throught the heap until the heap property is satisfied
   * only sifts up if the parent is greater or less than (depending on min or max heap)
   * then sifts up on the parent in its new location
   * @param q
   */
  public void siftUp(int q) {
	  if(q == 0) {
		  return;
	  }
	  if(q > keys.size() - 1) {
		  return;
	  }
	  int parent = getParent(q);
	  if(this.comparator.compare(keys.get(parent), keys.get(q)) > 0) {
		  swap(q, parent);
		  siftUp(parent);
	  }
  }

  /**
   * TODO
   * 
   * Exchanges the elements in the heap at the given indices in keys.
   */
  
  /**
   * sets the a temp E to hold one of the elements then adds and removes those elements
   * into their new spots
   * @param i
   * @param j
   */
  public void swap(int i, int j) {
	  if(i > keys.size() - 1 || j > keys.size() - 1) {
		  return;
	  }
	  E temp = keys.get(j);
	  keys.add(j, keys.get(i));
	  keys.remove(j + 1);
	  keys.add(i, temp);
	  keys.remove(i + 1);
  }
  
  /**
   * Returns the number of keys in this heap.
   */
  public int size() {
    return keys.size();
  }

  /**
   * Returns a textual representation of this heap.
   */
  public String toString() {
    return keys.toString();
  }
  
  /**
   * TODO
   * 
   * Returns the index of the left child of p.
   */
  
  /**
   * returns leftchild of given index based on formula
   * @param p
   * @return
   */
  public static int getLeft(int p) {
    return (2 * p) + 1;
  }

  /**
   * TODO
   * 
   * Returns the index of the right child of p.
   */
  
  /**
   * returns rightchild of given index based on formula
   * @param p
   * @return
   */
  public static int getRight(int p) {
    return (p * 2) + 2;
  }

  /**
   * TODO
   * 
   * Returns the index of the parent of p.
   */
  
  /**
   * returns parent of given index based on formula 
   * @param p
   * @return
   */
  public static int getParent(int p) {
    return (p - 1)/2;
  }
}
