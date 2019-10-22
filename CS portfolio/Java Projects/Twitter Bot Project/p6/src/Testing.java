import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class Testing {
  private static Random random = new Random();
  private static String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
  
  
  // Tests for the Naive Algorithm
  @Test
  public void testEmptyNaive() {
	  System.out.println("testEmptyNaive");	  
	  Result stringmatch = StringMatch.matchNaive("", "");
	  assertEquals(0, stringmatch.pos);
	  assertEquals(0, stringmatch.comps);
	  stringmatch = StringMatch.matchNaive("", "ab");
	  assertEquals(0, stringmatch.pos);
	  assertEquals(0, stringmatch.comps);
  }
  
  @Test
  public void testOneCharNaive() {
	  System.out.println("testOneCharNaive");
	  Result stringmatch = StringMatch.matchNaive("a", "a");
	  assertEquals(0, stringmatch.pos);
	  assertEquals(1, stringmatch.comps);
	  stringmatch = StringMatch.matchNaive("a", "b");
	  assertEquals(-1, stringmatch.pos);
	  assertEquals(1, stringmatch.comps);
  }
  
  @Test
  public void testRepeatNaive() {
	    System.out.println("testRepeatNaive");
	    Result stringmatch = StringMatch.matchNaive("aaa", "aaaa");
	    assertEquals(0, stringmatch.pos);
	    assertEquals(3, stringmatch.comps);
	    stringmatch = StringMatch.matchNaive("aaa", "abaaba");
	    assertEquals(-1, stringmatch.pos);
	    assertEquals(8, stringmatch.comps);
	    stringmatch = StringMatch.matchNaive("abab", "abacababc");
	    assertEquals(4, stringmatch.pos);
	    assertEquals(12, stringmatch.comps);
	    stringmatch = StringMatch.matchNaive("abab", "babacaba");
	    assertEquals(-1, stringmatch.pos);
	    assertEquals(9, stringmatch.comps);
	  }
  
  @Test
  public void testPartialRepeatNaive() {
	  System.out.println("testPartialRepeat");
	    Result stringmatch = StringMatch.matchNaive("aaacaaaaac", "aaacacaacaaacaaaacaaaaac");
	    assertEquals(14, stringmatch.pos);
	    assertEquals(50, stringmatch.comps);
	    stringmatch = StringMatch.matchNaive("ababcababdabababcababdaba", "ababcababdabababcababdaba");
	    assertEquals(0, stringmatch.pos);
	    assertEquals(25, stringmatch.comps);
  }
  
  @Test
  public void testRandomlyNaive() {
	  System.out.println("testRandomlyNaive");
	  for (int i = 0; i < 100; i++) {
	      String pattern = makeRandomPattern();
	      for (int j = 0; j < 100; j++) {
	        String text = makeRandomText(pattern);
	        Result ansNaive = StringMatch.matchNaive(pattern, text);
	        int expected = text.indexOf(pattern);
	        assertEquals(expected, ansNaive.pos);
	      }
	    }
  }
  
  // Tests for buildKMP method in StringMatch
  //@Test
  public void testBuildKMPEmpty() {
	  System.out.println("testEmptyBuildKMP");
	  int [] a = new int[5];
	  int stringmatch = StringMatch.buildKMP("", a);
	  assertEquals(1, stringmatch);
  }
  
  @Test
  public void testBuildKMPRepeat() {
	  System.out.println("testBuildKMPRepeat");
	  int [] a = new int[50];
	  int stringmatch = StringMatch.buildKMP("aaaa", a);
	  assertEquals(3, stringmatch);
	  stringmatch = StringMatch.buildKMP("aaaaaaaaa", a);
	  assertEquals(8, stringmatch);
  }
  
  //@Test
  public void testBuildKMPPartialRepeat() {
	  System.out.println("testBuildKMPPartialRepeat");
	  int [] a = new int[50];
	  int stringmatch = StringMatch.buildKMP("aaacaaaaac", a);
	  assertEquals(9, stringmatch);
  }
  
  //Tests for runKMP method in StringMatch
  @Test
  public void testRunKMPEmpty() {
	  System.out.println("testRunKMPEmpty");
	  int [] a = new int [50];
	  Result stringmatch = StringMatch.runKMP("", "", a);
	  assertEquals(0, stringmatch.pos);
	  assertEquals(0, stringmatch.comps);
	  a = new int[50];
	  stringmatch = StringMatch.runKMP("", "a", a);
	  assertEquals(0, stringmatch.pos);
	  assertEquals(0,stringmatch.comps);
  }
  
  @Test
  public void testRunKMPOneChar() {
	  System.out.println("testRunKMPOneChar");
	  int [] a = new int [50];
	  Result stringmatch = StringMatch.runKMP("a", "a", a);
	  assertEquals(0, stringmatch.pos);
	  assertEquals(1, stringmatch.comps);
	  a = new int[50];
	  stringmatch = StringMatch.matchKMP("a", "b");
	  assertEquals(-1, stringmatch.pos);
	  assertEquals(1, stringmatch.comps);
  }
  
  @Test
  public void testNaive() {
    String[] pats = new String[] {
        "AAAA",
        "BAAA",
        "AAAB",
        "AAAC",
        "ABAB",
    };
    String text = "AAAAAAAAABAAAAAAAAAB";
    assertEquals(20, text.length());
    Result[] results = new Result[] {
        new Result(0, 4),
        new Result(9, 13),
        new Result(6, 28),
        new Result(-1, 62),
        new Result(-1, 35),
    };
    int i = 0;
    for (String pat : pats) {
      Result res = StringMatch.matchNaive(pat, text);
      assertEquals(res.pos, results[i].pos);
      assertEquals(res.comps, results[i].comps);
      i++;
    }
  }

  @Test 
  public void smallMachines() {
    String[] pats = new String[] {
        "",
        "A",
        "AB",
        "AA",
        "AAAA",
        "BAAA",
        "AAAB",
        "AAAC",
        "ABAB",
        "ABCD",
        "ABBA",
        "AABC",
        "ABAAB",
        "AABAACAABABA",
        "ABRACADABRA",
    };
    int[][] flinks = new int[][] {
        { -1 },
        { -1, 0 },
        { -1, 0, 0 },
        { -1, 0, 1 },
        { -1, 0, 1, 2, 3 },
        { -1, 0, 0, 0, 0 },
        { -1, 0, 1, 2, 0 },
        { -1, 0, 1, 2, 0 },
        { -1, 0, 0, 1, 2 },
        { -1, 0, 0, 0, 0 },
        { -1, 0, 0, 0, 1 },
        { -1, 0, 1, 0, 0 },
        { -1, 0, 0, 1, 1, 2 },
        { -1, 0, 1, 0, 1, 2, 0, 1, 2, 3, 4, 0, 1 },
        { -1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 3, 4 },
    };
    int[] comps = new int[] { 0, 0, 1, 1, 3, 3, 5, 5, 3, 3, 3, 4, 5, 16, 12 };
    int i = 0;
    for (String pat : pats) {
      int[] flink = new int[pat.length() + 1];
      assertEquals(comps[i], StringMatch.buildKMP(pat, flink));
      assertArrayEquals(flinks[i], flink);
      i++;
    }
  }

  @Test
  public void lec13bKMP() {
    String[] pats = new String[] {
        "AABC",
        "ABCDE",
        "AABAACAABABA",
        "ABRACADABRA",
    };
    int[][] flinks = new int[][] {
        { -1, 0, 1, 0, 0 },
        { -1, 0, 0, 0, 0, 0 },
        { -1, 0, 1, 0, 1, 2, 0, 1, 2, 3, 4, 0, 1 },
        { -1, 0, 0, 0, 1, 0, 1, 0, 1, 2, 3, 4 },
    };
    String text = "AAAAAABRACADABAAAAAAAAAAAAAAAAAAAAAAABCAAAAAAAAAAABAABAAAAAAAAAAAAAAA";
    Result results[] = new Result[] {
        new Result(35, 68),
        new Result(-1, 128),
        new Result(-1, 123),
        new Result(-1, 126),
    };
    int i = 0;
    for (String pat : pats) {
      Result res = StringMatch.runKMP(pat, text, flinks[i]);
      assertEquals(results[i].pos, res.pos);
      assertEquals(results[i].comps, res.comps);
      i++;
    }
  }

  @Test
  public void testFlinks() {
    String pattern = "AABAACAABABA";
    int m = pattern.length();
    int[] flink = new int[m + 1];
    StringMatch.buildKMP(pattern, flink);
    //    System.out.println(java.util.Arrays.toString(flink));
    assertArrayEquals(flink, new int[] { -1, 0, 1, 0, 1, 2, 0, 1, 2, 3, 4, 0, 1 });
    assertTrue(verifyMachine(pattern, flink));
  }

  @Test 
  public void testEmpty() {
    match("", "");
    match("", "ab");
  }

  @Test 
  public void testOneChar() {
    match("a", "a");
    match("a", "b");
  }

  @Test 
  public void testRepeat() {
    match("aaa", "aaaaa");
    match("aaa", "abaaba");
    match("abab", "abacababc");
    match("abab", "babacaba");
  }

  @Test 
  public void testPartialRepeat() {
    match("aaacaaaaac", "aaacacaacaaacaaaacaaaaac");
    match("ababcababdabababcababdaba", "ababcababdabababcababdaba");
  }

  @Test 
  public void testRandomly() {
    for (int i = 0; i < 100; i++) {
      String pattern = makeRandomPattern();
      for (int j = 0; j < 100; j++) {
        String text = makeRandomText(pattern);
        match(pattern, text);
      }
    }
  }

  /* Helper functions */

  private static String makeRandomPattern() {
    StringBuilder sb = new StringBuilder();
    int steps = random.nextInt(10) + 1;
    for (int i = 0; i < steps; i++) {
      if (sb.length() == 0 || random.nextBoolean()) {  // Add literal
        int len = random.nextInt(5) + 1;
        for (int j = 0; j < len; j++)
          sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
      } 
      else {  // Repeat prefix
        int len = random.nextInt(sb.length()) + 1;
        int reps = random.nextInt(3) + 1;
        if (sb.length() + len * reps > 1000)
          break;
        for (int j = 0; j < reps; j++)
          sb.append(sb.substring(0, len));
      }
    }
    return sb.toString();
  }

  private static String makeRandomText(String pattern) {
    StringBuilder sb = new StringBuilder();
    int steps = random.nextInt(100);
    for (int i = 0; i < steps && sb.length() < 10000; i++) {
      if (random.nextDouble() < 0.7) {  // Add prefix of pattern
        int len = random.nextInt(pattern.length()) + 1;
        sb.append(pattern.substring(0, len));
      } 
      else {  // Add literal
        int len = random.nextInt(30) + 1;
        for (int j = 0; j < len; j++)
          sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
      }
    }
    return sb.toString();
  }

  private static void match(String pattern, String text) {
    // run all three algorithms and test for correctness
    Result ansNaive = StringMatch.matchNaive(pattern, text);
    int expected = text.indexOf(pattern);
    assertEquals(expected, ansNaive.pos);
    Result ansKMP = StringMatch.matchKMP(pattern, text);
    assertEquals(expected, ansKMP.pos);
    Result ansBoyerMoore = StringMatch.matchBoyerMoore(pattern, text);
    assertEquals(expected, ansBoyerMoore.pos);
  }

  //--------- For Debugging ---------------
  public static boolean verifyMachine(String pattern, int[] flink) {
    int m = pattern.length();
    if (flink.length != pattern.length() + 1) 
      return false;  // bad length
    if (flink[0] != -1)
      return false;
    for (int i = 2; i < m; i++) {
      if (flink[i] < 0 || flink[i] >= i) 
        return false;  // link out of range
      // Search for the nearest state whose label is a suffix of i's label
      String iLabel = pattern.substring(0, i);  
      int j = i - 1;
      while (j >= 0 && !iLabel.endsWith(pattern.substring(0, j))) 
        j--;
      if (flink[i] != j) 
        return false;  // fails to the wrong state
    }
    return true;
  }
}
