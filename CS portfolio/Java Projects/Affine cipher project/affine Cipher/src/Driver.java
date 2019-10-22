import java.util.Scanner;

public class Driver {

	public static void main(String[] args) {
		
		affinecipher affine = new affinecipher();
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a message: ");
		String message = sc.next();
		System.out.println(message);
		// affinecipher2 is my second working attempt at the affine cipher
		affinecipher2 a2 = new affinecipher2();
		String x = affinecipher2.encrypt(message);
		System.out.println(x);
		System.out.println(affinecipher2.decrypt(x));
		
		
		
		sc.close();
	}
}
