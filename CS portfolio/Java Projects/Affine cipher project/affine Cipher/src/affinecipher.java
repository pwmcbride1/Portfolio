import java.util.Scanner;


/*
 * Patrick McBride
 * 
 * I was have troubles with getting the decrypt to work properly as well as I believe the 
 * encrypt. I believe I have all the right bounds but there may have been a slight error in
 * my calculations.
 */
public class affinecipher {
	
	/*
	 * 
	 */
	private static int key1 = 17;
	private static int key2 = 20;
	
	public affinecipher() {
	}
	
	public static String encrypt(String msg) {
		String cipher = "";
		
		for(int i = 0; i < msg.length(); i++) {
			if(msg.charAt(i) != ' ') {
				
			/*
			 * this does the calculation to bring the number into the range of the asci alphabet
			 */
			cipher = cipher + (char) ((((key1 * (msg.charAt(i)-'A')) + key2)% 68)+'A');
			}
			else {
				cipher += msg.charAt(i);
			}
		}
		return cipher;
	}
	
	public static String decrypt(String cipher) {
		String plainTxt = "";
		int x = 0;
		int flag = 0;
		
		for(int i = 0; i < 68; i++) {
			flag = (key1 * i) % 68;
			if(flag == 1) {
				x = i;
				}
		}
		for(int i = 0; i < cipher.length(); i++) {
			
			if(cipher.charAt(i) != ' ') {
			/*
			 * runs the decryption formula and then adds 'A' to bring the number into range 
			 * of the asci alphabet
			 */
			plainTxt = plainTxt + (char) (((((cipher.charAt(i)+'A' - key2) * x)% 68))+'A');
			}
			else {
				plainTxt += cipher.charAt(i);
			}
		}
		return plainTxt;
	}
}
