
public class affinecipher2 {
	
	private static int key1 = 15;
	private static int key2 = 20;
	
	public affinecipher2() {
		
	}
	
	/*
	 * method will encode msg based on ascii value
	 * 
	 * the digits are also there respective ascii values
	 * 
	 * the special characters have been pre selected be the encode method
	 */
	public static int[] encode(String msg) {
		int[] answer = new int[msg.length()];
		for(int i = 0; i < msg.length(); i++) {
			if(Character.isLowerCase(msg.charAt(i)) || Character.isUpperCase(msg.charAt(i))) {
				answer[i] = (int) msg.charAt(i) - 65;
			}
			else if(Character.isDigit(msg.charAt(i))) {
				answer[i] = msg.charAt(i) + 58;
			}
			else if(msg.charAt(i) == ',') {
				answer[i] = 68;
			}
			else if(msg.charAt(i) == '.') {
				answer[i] = 69;
			}
			else if(msg.charAt(i) == ' ') {
				answer[i] = 70;
			}
			else if(msg.charAt(i) == ';') {
				answer[i] = 71;
			}
			else if(msg.charAt(i) == ':') {
				answer[i] = 72;
			}
			else if(msg.charAt(i) == '!') {
				answer[i] = 73;
			}
			else {
				System.out.println("Failed to encode");
			}
			
		}
		return answer;
	}
	
	/*
	 * decode takes the array of ints and translates it back to text 
	 */
	public static String decode(int[] msg) {
		String result = "";
		for(int i = 0; i < msg.length; i++) {
			if(msg[i] >= 0 && msg[i] <= 25) {
				result = result + (char) (msg[i] + 65);
			}
			else if(msg[i] >= 32 && msg[i] <= 57) {
				result = result + (char) (msg[i] + 65);
			}
			else if(msg[i] == 58) {
				result = result + '0';
			}
			else if(msg[i] == 59) {
				result = result + '1';
			}
			else if(msg[i] == 60) {
				result = result + '2';
			}
			else if(msg[i] == 61) {
				result = result + '3';
			}
			else if(msg[i] == 62) {
				result = result + '4';
			}
			else if(msg[i] == 63) {
				result = result + '5';
			}
			else if(msg[i] == 64) {
				result = result + '6';
			}
			else if(msg[i] == 65) {
				result = result + '7';
			}
			else if(msg[i] == 66) {
				result = result + '8';
			}
			else if(msg[i] == 67) {
				result = result + '9';
			}
			else if(msg[i] == 68) {
				result = result + ',';
			}
			else if(msg[i] == 69) {
				result = result + '.';
			}
			else if(msg[i] == 70) {
				result = result + ' ';
			}
			else if(msg[i] == 71) {
				result = result + ';';
			}
			else if(msg[i] == 72) {
				result = result + ':';
			}
			else if(msg[i] == 73) {
				result = result + '!';
			}
		}
		return result;
	}
	
	/*
	 * encrypt takes the message, encodes it, then encrypts in with the given keys
	 * and the (ax + b) mod m function then its decoded
	 */
	public static String encrypt(String msg) {
		int[] msgarray = encode(msg);
		int[] result = new int [msgarray.length];
		for(int i = 0; i < msgarray.length; i++) {
				
			result[i] = (((key1 * msgarray[i]) + key2)% 68);
		}
		return decode(result);
	}
	
	/*
	 * decrypt encodes the given string then does the reverse of encrypt (a^-1 (c - b)) mod m
	 * then it decodes the message 
	 */
	public static String decrypt(String msg) {
		int [] msgarray = encode(msg);
		
		
		int ainverse = 0;
		int flag = 0;
		
		for(int i = 0; i < 68; i++) {
			flag = (key1 * i) % 68;
			if(flag == 1) {
				ainverse = i;
				System.out.println(ainverse);
				}
		}
		
		int[] result = new int [msgarray.length];
		for(int k = 0; k < msgarray.length; k++) {
			result[k] = (ainverse * (msgarray[k] - key2))%68;
			// some keys are negative after subtracting b so we add m to all the negative
			// values
			if(result[k] < 0) {
				result[k] = result[k] + 68;
			}
		}
		return decode(result);
	}
}
