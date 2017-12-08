import java.util.*;

public class test4{
	public static void main(String args[]){
		Scanner inputS = new Scanner(System.in);
		_Array<Integer> arr = new _Array<>();
		Stack<Integer> stk = new Stack<>();
		_Queue<Integer> que = new _Queue<>();
		_List<Integer> lst = new _List<>();
		_Node<Integer> tnode = new _Node<>();
		int i;
		int temp;
		int mx;
		int mn;
		int j;
		j=Integer.parseInt(inputS.nextLine());
		i=0;
		while(i <j){
				temp=Integer.parseInt(inputS.nextLine());
				arr.push(temp);
				stk.push(temp);
				que.push(temp);
				lst.push(temp);
				i=i +1;
		}
		mx=arr.max();
		mn=arr.min();
		System.out.println("input is "+arr);
		System.out.println("mininum is "+mn);
		System.out.println("maximum is "+mx);
		arr.sort();
		System.out.println("sorting result is "+arr);
		tnode=lst.head();
		System.out.println("head is "+tnode);
		tnode=tnode.right();
		System.out.println("head's right is "+tnode);
		tnode=lst.tail();
		System.out.println("tail is "+tnode);
		tnode=tnode.left();
		System.out.println("tail's left is "+tnode);
		i=0;
		while(i <j){
				temp=stk.pop();
				System.out.println("stack is "+temp);
				i=i +1;
		}
		i=0;
		while(i <j){
				temp=que.pop();
				System.out.println("queue is "+temp);
				i=i +1;
		}
	}
}