import java.util.ArrayList;
import java.util.List;

/**
 * TODO #2
 */

public class MatchBot extends TwitterBot {
  /**
   * Constructs a MatchBot to operate on the last numTweets of the given user.
   */
  public MatchBot(String user, int numTweets) {
    super(user, numTweets);
  }
  
  /**
   * TODO
   * 
   * Employs the KMP string matching algorithm to add all tweets containing 
   * the given pattern to the provided list. Returns the total number of 
   * character comparisons performed.
   * 
   * @param pattern - given string that we are looking through text for
   * @param ans     - given list that will hold the tweets that contain the given pattern
   * @return          returns the number of comps made
   * 
   */
  public int searchTweetsKMP(String pattern, List<String> ans) {
	  
	  
	int comps = 0;
	int[] a = new int[pattern.length() + 1];
	comps += StringMatch.buildKMP(pattern, a);
	for(String x : tweets) {
		Result stringmatch = StringMatch.runKMP(pattern, x.toLowerCase(), a);
		comps += stringmatch.comps;
		if(stringmatch.pos != -1) {
			ans.add(x);
		}
	}
    return comps;
  }
  
  /**
   * TODO
   * 
   * Employs the naive string matching algorithm to find all tweets containing 
   * the given pattern to the provided list. Returns the total number of 
   * character comparisons performed.
   * 
   * @param pattern - given string that we are looking through text for
   * @param ans     - given list that will hold the tweets that contain the given pattern
   * @return          returns the number of comps made
   * 
   */
  public int searchTweetsNaive(String pattern, List<String> ans) {
	    
	    int comps = 0;
		for(String x : tweets) {
			Result stringmatch = StringMatch.matchNaive(pattern, x.toLowerCase());
			comps += stringmatch.comps;
			if(stringmatch.pos != -1) {
				ans.add(x);
			}
		}
	    return comps;
  }
  
  
  
  public static void main(String... args) {
    String handle = "acflinch", pattern = "be";
    MatchBot bot = new MatchBot(handle, 2000);
   
    // Search all tweets for the pattern.
    List<String> ansNaive = new ArrayList<>();
    int compsNaive = bot.searchTweetsNaive(pattern, ansNaive); 
    List<String> ansKMP = new ArrayList<>();
    int compsKMP = bot.searchTweetsKMP(pattern, ansKMP);  
    List<String> ansBM = new ArrayList<>();
    int compsBM = bot.searchBoyerMoore(pattern, ansBM);
    System.out.println("naive comps = " + compsNaive + ", KMP comps = " + compsKMP + ", BM comps = " + compsBM);

    for (int i = 0; i < ansKMP.size(); i++) {
      String tweet = ansKMP.get(i);
      assert tweet.equals(ansNaive.get(i));
      System.out.println(i++ + ". " + tweet);
      System.out.println(pattern + " appears at index " + 
          tweet.toLowerCase().indexOf(pattern.toLowerCase()));
    }
    
    for (int i = 0; i < ansBM.size(); i++) {
        String tweet = ansBM.get(i);
        assert tweet.equals(ansNaive.get(i));
        System.out.println(i++ + ". " + tweet);
        System.out.println(pattern + " appears at index " + 
            tweet.toLowerCase().indexOf(pattern.toLowerCase()));
    }
  }
  
/**
   * Employs the Boyer-Moore string matching algorithm to find all tweets containing 
   * the given pattern to the provided list. Returns the total number of 
   * character comparisons performed.
   * 
   * @param pattern - given string that we are looking through text for
   * @param ans     - given list that will hold the tweets that contain the given pattern
   * @return          returns the number of comps made
 */
  public int searchBoyerMoore(String pattern, List<String> ans) {
	  int comps = 0;
		for(String x : tweets) {
			Result stringmatch = StringMatch.matchNaive(pattern, x.toLowerCase());
			comps += stringmatch.comps;
			if(stringmatch.pos != -1) {
				ans.add(x);
			}
		}
	    return comps;
  }
}
