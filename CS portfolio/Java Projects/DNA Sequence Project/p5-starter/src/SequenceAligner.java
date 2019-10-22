import java.util.Random;

/**
 * TODO: Implement the bestResult(), fillCache(), getResult(), and traceback() methods, in
 * that order. This is the biggest part of this project.
 * 
 * @author Patrick McBride
 */

public class SequenceAligner {
  private static Random gen = new Random();

  private String x, y;
  private int n, m;
  private String alignedX, alignedY;
  private Result[][] cache;
  private Judge judge;

  public Result[][] getCache(){
	  return this.cache;
  }
  
  /**
   * Generates a pair of random DNA strands, where x is of length n and
   * y has some length between n/2 and 3n/2, and aligns them using the 
   * default judge.
   */
  public SequenceAligner(int n) {
    this(randomDNA(n), randomDNA(n - gen.nextInt(n / 2) * (gen.nextInt(2) * 2 - 1)));
  }

  /**
   * Aligns the given strands using the default judge.
   */
  public SequenceAligner(String x, String y) {
    this(x, y, new Judge());
  }
  
  /**
   * Aligns the given strands using the specified judge.
   */
  public SequenceAligner(String x, String y, Judge judge) {
    this.x = x.toUpperCase();
    this.y = y.toUpperCase();
    this.judge = judge;
    n = x.length();
    m = y.length();
    cache = new Result[n + 1][m + 1];
    fillCache();
    traceback();
  }

  /**
   * Returns the x strand.
   */
  public String getX() {
    return x;
  }

  /**
   * Returns the y strand.
   */
  public String getY() {
    return y;
  }
  
  /**
   * Returns the judge associated with this pair.
   */
  public Judge getJudge() {
    return judge;
  }
  
  /**
   * Returns the aligned version of the x strand.
   */
  public String getAlignedX() {
    return alignedX;
  }

  /**
   * Returns the aligned version of the y strand.
   */
  public String getAlignedY() {
    return alignedY;
  }

  /**
   * TODO
   *
   * Returns the Result corresponding to the biggest payoff among the scores
   * for the three different directions.
   *
   * Tiebreaking Rule: So that your code will identify the same alignment
   * as is expected in Testing, we establish the following preferred order
   * of operations: M (diag), I (left), D (up). This only applies when you
   * are picking the operation with the biggest payoff and two or more
   * operations have the same max score.
   */

  
  /**
   * Series of if statements that check for the highest score among the three given 
   * directions. it makes them into a new result with the highest score and the 
   * direction then returns that result
   * @param diag
   * @param left
   * @param up
   * @return
   */
  public static Result bestResult(int diag, int left, int up) {
	if(diag > left && diag > up) {
		return new Result(diag, Direction.DIAGONAL);
	}
	else if(left > diag && left > up) {
		return new Result(left, Direction.LEFT);
	}
	else if(up > diag && up > left) {
		return new Result(up, Direction.UP);
	}
	else if(diag == left && diag > up) {
		return new Result(diag, Direction.DIAGONAL);
	}
	else if(diag == up && diag > left) {
		return new Result(diag, Direction.DIAGONAL);
	}
	else if(left == up && left > diag) {
		return new Result(left, Direction.LEFT);
	}
	else {
		return new Result(diag, Direction.DIAGONAL);
	}
  }

  /**
   *  TODO: Solve the alignment problem using bottom-up dynamic programming
   *  algorithm described in lecture. When you're done, cache[i][j] will hold
   *  the result of solving the alignment problem for the first i characters
   *  in x and the first j characters in y.
   *  
   *  Your algorithm must run in O(n * m) time, where n is the length of x
   *  and m is the length of y.
   *  
   *  Ordering convention: So that your code will identify the same alignment
   *  as is expected in Testing, we establish the following preferred order
   *  of operations: M (diag), I (left), D (up). This only applies when you
   *  are picking the operation with the biggest payoff and two or more  
   *  operations have the same max score. 
   */
  
  /**
   * starts by filling the 0, 0 spot of the array
   * then runs two for loops to fill in the first row and column because they are special
   * cases. Then it loops through all of the other coords and fills them with the best result.
   * bestResult is given the score for each of the directions. Diagonal is the score plus the score at that point.
   * left is the score plus the gap cost. Up is the score plus the gap cost.
   */
  private void fillCache() {
	  cache[0][0] = new Result(0);
	  for(int i = 1; i<= n; i++) {
		  cache[i][0] = new Result(judge.getGapCost() * i, Direction.UP);
	  }
	  for(int j = 1; j <= m; j++) {
		  cache[0][j] = new Result(judge.getGapCost() * j, Direction.LEFT);
	  }
	  	for(int i = 1; i <= n; i++) {
			  for(int j = 1; j <= m; j++) {
				  cache[i][j] = bestResult(cache[i-1][j-1].getScore() + judge.score(getX().charAt(i-1), getY().charAt(j-1)), cache[i][j-1].getScore() + judge.getGapCost(), cache[i-1][j].getScore() + judge.getGapCost());
				}
			 }
	  }
  
  /**
   * TODO: Returns the result of solving the alignment problem for the 
   * first i characters in x and the first j characters in y. You can
   * find the result in O(1) time by looking in your cache.
   */
  
  /**
   * simply gets the result at the given coord of the cache
   * @param i
   * @param j
   * @return
   */
  public Result getResult(int i, int j) {
    return cache[i][j];
  }
  
  /**
   * TODO: Mark the path by tracing back through parent pointers, starting
   * with the Result in the lower right corner of the cache. Run Result.markPath()
   * on each Result along the path. The GUI will highlight all such marked cells
   * when you check 'Show path'. As you're tracing back along the path, build 
   * the aligned strings in alignedX and alignedY (using Constants.GAP_CHAR
   * to denote a gap in the strand).
   * 
   * Your algorithm must run in O(n + m) time, where n is the length of x
   * and m is the length of y. 
   */
  
  /**
   * Starts by adding a space to the beginning of x and y.
   * Then instantiates the alignedX and alignedY strings
   * then sets i to the length of x and j to the length of y
   * then while the direction of the parent of your current location isn't none
   * you have to find what the parent is and mark its path, set alignedX and alignedY to be
   * the character at that location plus the string that proceeds that character and then 
   * decrements i and j depending on what the direction of the parent was. if the parent
   * direction is diagonal you decrement both i and j. if the parent direction is left
   * you decrement only j. if the parent direction is up you decrement only i.
   * then finally after the wile loop has terminated you'll be at 0, 0 so you mark that location
   */
  private void traceback() {
	  String x = " " + this.x;
	  String y = " " + this.y;
	  alignedX = "";
	  alignedY = "";
	  int i = n;
	  int j = m;
	  while(cache[i][j].getParent() != Direction.NONE) {
		  if(cache[i][j].getParent() == Direction.DIAGONAL) {
			  cache[i][j].markPath();
			  alignedX = x.charAt(i) + alignedX;
			  alignedY = y.charAt(j) + alignedY;
			  i--;
			  j--;
		  }
		  else if(cache[i][j].getParent() == Direction.LEFT) {
			  cache[i][j].markPath();
			  alignedX = Constants.GAP_CHAR + alignedX;
			  alignedY = y.charAt(j) + alignedY;
			  j--;
		  }
		  else {
			  cache[i][j].markPath();
			  alignedX = x.charAt(i) + alignedX;
			  alignedY = Constants.GAP_CHAR + alignedY;
			  i--;
		  }
	  }
	  cache[0][0].markPath();
	  
  }

  /**
   * Returns true iff these strands are seemingly aligned.
   */
  public boolean isAligned() {
    return alignedX != null && alignedY != null &&
        alignedX.length() == alignedY.length();
  }
  
  /**
   * Returns the score associated with the current alignment.
   */
  public int getScore() {
    if (isAligned())
      return judge.score(alignedX, alignedY);
    return 0;
  }

  /**
   * Returns a nice textual version of this alignment.
   */
  public String toString() {
    if (!isAligned())
      return "[X=" + x + ",Y=" + y + "]";
    final char GAP_SYM = '.', MATCH_SYM = '|', MISMATCH_SYM = ':';
    StringBuilder ans = new StringBuilder();
    ans.append(alignedX).append('\n');
    int n = alignedX.length();
    for (int i = 0; i < n; i++)
      if (alignedX.charAt(i) == Constants.GAP_CHAR || alignedY.charAt(i) == Constants.GAP_CHAR)
        ans.append(GAP_SYM);
      else if (alignedX.charAt(i) == alignedY.charAt(i))
        ans.append(MATCH_SYM);
      else
        ans.append(MISMATCH_SYM);
    ans.append('\n').append(alignedY).append('\n').append("score = ").append(getScore());
    return ans.toString();
  }

  /**
   * Returns a DNA strand of length n with randomly selected nucleotides.
   */
  private static String randomDNA(int n) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < n; i++)
      sb.append("ACGT".charAt(gen.nextInt(4)));
    return sb.toString();
  }

}
