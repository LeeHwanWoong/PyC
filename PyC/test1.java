import java.util.*;

public class test1{
	public static void main(String args[]){
		Scanner inputS = new Scanner(System.in);
		int low;
		int high;
		int i;
		int j;
		int k;
		int temp1;
		int temp2;
		int remainder;
		int n;
		int result;
		n=1;
		result=0;
		System.out.println("Enter two numbers(intervals)");
		low=Integer.parseInt(inputS.nextLine());
		high=Integer.parseInt(inputS.nextLine());
		System.out.println("Armstrong numbers between"+low+"an"+high+"are");
		i=low +2;
		while(i <high){
				i=i +1;
				temp2=i;
				temp1=i;
				while(temp1 !=0){
						temp1=temp1 /10;
						n=n +1;
				}
				while(temp2 !=0){
						remainder=temp2 %10;
						j=remainder;
						k=0;
						while(k <n -1){
								k=k +1;
								j=j *remainder;
						}
						result=result +j;
						temp2=temp2 /10;
				}
				if(result ==i){
						System.out.println(i);
				}
				n=0;
				result=0;
		}
	}
}