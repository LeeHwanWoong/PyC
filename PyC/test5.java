import java.util.*;

public class test5{
	public static void main(String args[]){
		Scanner inputS = new Scanner(System.in);
		Calc Calc = new Calc();
		int a;
		int b;
		int result1;
		double result2;
		a=6;
		b=11;
		result1=Calc.sum(a,b);
		System.out.println(a+"+"+b+"="+result1);
		result1=Calc.sub(a,b);
		System.out.println(a+"-"+b+"="+result1);
		result1=Calc.mul(a,b);
		System.out.println(a+"*"+b+"="+result1);
		result2=Calc.div(a,b);
		System.out.println(a+"/"+b+"="+result2);
	}
}