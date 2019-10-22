/**
 * TODO #1
 */

public class StringMatch {

  /**
   * TODO
   * 
   * Returns the result of running the naive algorithm to match pattern in text.
   * 
   * @param pattern - given string that we are looking through text for
   * @param text    - given string we are looking in for a pattern
   * @return          A result that contains the starting index of the match and the number
   *                  of comps made
   */
  public static Result matchNaive(String pattern, String text) {  
	  
	int timesRan = 0;
	int i = 0;
	int j = 0;
	int m = pattern.length();
	int n = text.length();
	if(pattern == "") {
		return new Result(0, 0);
	}
	
	while(i < m && j <= (n-m) + i) {
		timesRan++;
		if(pattern.charAt(i) == text.charAt(j)) {
			i = i + 1;
			j = j + 1;
		}
		else {
			j = j - i + 1;
			i = 0;
		}
		if(i == m) {
			return new Result(j - i, timesRan);
		}
	}
	
    return new Result(-1, timesRan);
  }
  
  /**
   * TODO
   * 
   * Populates flink with the failure links for the KMP machine associated with the
   * given pattern, and returns the cost in terms of the number of character comparisons.
   * 
   * @param pattern - given string that we are looking through text for
   * @param flink   - given array that will be used to store fail links associated with characters
   * @return          returns the number of comps made during building
   * 
   */
  public static int buildKMP(String pattern, int[] flink) {
	  
	  int i = 2;
	  int m = pattern.length();
	  int j;
	  int comps = 0;
	  
	  if(flink == null) {
		  throw new IllegalArgumentException();
	  }
	  
	  if(m >= 0) {
		  flink[0] = -1;
	  }
	  if(m >= 1) {
		  flink[1] = 0;
	  }
	  
	  while(i <= m) {
		 j = flink[i - 1];
		 while(j != -1 && pattern.charAt(j) != pattern.charAt(i - 1)) {
			 comps++;
			 j = flink[j];
		 }
		 if(j != -1) {
			 comps++;
		 }
		 flink[i] = j + 1;
		 i++;
	  }
	  return comps;
  }
  
  /**
   * TODO
   * 
   * Returns the result of running the KMP machine specified by flink (built for the
   * given pattern) on the text.
   * 
   * @param pattern - given string that we are looking through text for
   * @param text    - given string we are looking in for a pattern
   * @param flink   - given array that will be used to store fail links associated with characters
   * @return          A result that contains the starting index of the match and the number
   *                  of comps made
   * 
   */
  public static Result runKMP(String pattern, String text, int[] flink) {
	
	  if(pattern.length() == 0) {
			return new Result (0, 0);
		}
	int comps = 0;
	int state = -1;
	int j = -1;
	int m = pattern.length();
	int n = text.length();
	
	char x = '\0';
	
	while(true) {
		if(state != -1) {
			comps++;
		}
		if(state == -1 || x == pattern.charAt(state)) {
			state = state + 1;
			if(state == m) {
				return new Result((j-m)+1, comps);
			}
			j = j + 1;
			if(j == n) {
				return new Result(-1, comps);
			}
			x = text.charAt(j);
		}
		else {
			state = flink[state];
		}
	}   
  }
  
  /**
   * Returns the result of running the KMP algorithm to match pattern in text. The number
   * of comparisons includes the cost of building the machine from the pattern.
   */
  public static Result matchKMP(String pattern, String text) {
    int m = pattern.length();
    int[] flink = new int[m + 1];
    int comps = buildKMP(pattern, flink);
    Result ans = runKMP(pattern, text, flink);
    return new Result(ans.pos, comps + ans.comps);
  }
  
  /**
   * TODO
   * 
   * Populates delta1 with the shift values associated with each character in the
   * alphabet. Assume delta1 is large enough to hold any ASCII value.
   * 
   * @param pattern - given string that we are looking through text for
   * @param delta1  - given array that will be used to store shift values associated with characters
   * 
   */
  public static void buildDelta1(String pattern, int[] delta1) {
    for(int i = 0; i < delta1.length; i++) {
    	delta1[i] = pattern.length();
    }
    
    for(int i = 0; i < pattern.length(); i++) {
    	delta1[pattern.charAt(i)] = pattern.length() - 1 - i;
    }
    
  }

  /**
   * TODO
   * 
   * Returns the result of running the simplified Boyer-Moore algorithm using the
   * delta1 table from the pre-processing phase.
   * 
   * @param pattern - given string that we are looking through text for
   * @param text    - given string we are looking in for a pattern
   * @param delta1  - given array that will be used to store shift values associated with characters
   * @return          A result that contains the starting index of the match and the number
   *                  of comps made
   * 
   */
  public static Result runBoyerMoore(String pattern, String text, int[] delta1) {
	  
	int m = pattern.length();
	int n = text.length();
	if (m == 0) {
		return new Result(0, 0);
	}
	
	int i = m-1;
	int j = m-1;
	int comps = 0;
	int matchCount = 0;
	
	while(j < n) {
		matchCount =0;
		int temp = j;
		i = m -1;
		while(i > -1) {
			comps++;
			if(pattern.charAt(i) == text.charAt(temp)) {
				i--;
				temp--;
				matchCount++;
			}
			else {
				break;
			}
		}
		if(m == matchCount) {
			return new Result((j-m) +1 ,comps);
		}
		j += Math.max(1, delta1[text.charAt(temp)] - matchCount);
	}
	
    return new Result(-1, comps);
  }
  
  /**
   * Returns the result of running the simplified Boyer-Moore algorithm to match 
   * pattern in text. 
   */
  public static Result matchBoyerMoore(String pattern, String text) {
    int[] delta1 = new int[Constants.SIGMA_SIZE];
    buildDelta1(pattern, delta1);
    return runBoyerMoore(pattern, text, delta1);
  }
  
  public static void main(String[] args) {
	  System.out.println("testing buildKMP");
	  int [] a = new int[5];
	  buildKMP("a", a);
	  System.out.println(buildKMP("a", a));
	  a = new int[5];
	  System.out.println(runKMP("a", "a", a).comps);
	  
  }

}
