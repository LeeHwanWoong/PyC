import java.util.*;

public class test2{
	public static void main(String args[]){
		Scanner inputS = new Scanner(System.in);
		int n;
		int reversedInteger;
		int remainder;
		int originalInteger;
		reversedInteger=0;
		System.out.println("Enter an integer");
		n=Integer.parseInt(inputS.nextLine());
		originalInteger=n;
		while(n !=0){
				remainder=n %10;
				reversedInteger=reversedInteger *10 +remainder;
				n=n /10;
		}
		if(originalInteger ==reversedInteger){
				System.out.println(originalInteger+"is a palindrome.");
		}
		else{
				System.out.println(originalInteger+"is not a palindrome.");
		}
	}
}