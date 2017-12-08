import java.util.*;

public class test3{
	public static void main(String args[]){
		Scanner inputS = new Scanner(System.in);
		_Tree<Integer> rt = new _Tree<>();
		_Node<Integer> a1 = new _Node<>();
		_Node<Integer> b1 = new _Node<>();
		_Node<Integer> b2 = new _Node<>();
		_Node<Integer> c1 = new _Node<>();
		_Node<Integer> c2 = new _Node<>();
		_Node<Integer> c3 = new _Node<>();
		_Node<Integer> c4 = new _Node<>();
		_Node<Integer> print1 = new _Node<>();
		_Node<Integer> print2 = new _Node<>();
		_Node<Integer> print3 = new _Node<>();
		_Node<Integer> print4 = new _Node<>();
		_Node<Integer> print5 = new _Node<>();
		_Node<Integer> print6 = new _Node<>();
		_Node<Integer> print7 = new _Node<>();
		rt.push(50);
		rt.push(30);
		rt.push(70);
		rt.push(20);
		rt.push(40);
		rt.push(60);
		rt.push(80);
		a1=rt.tree(0,0);
		b1=a1.left();
		b2=a1.right();
		c1=b1.left();
		c2=b1.right();
		c3=b2.left();
		c4=b2.right();
		print1=rt.tree(0,0);
		print2=print1.left();
		print3=print1.right();
		print4=print2.left();
		print5=print2.right();
		print6=print3.left();
		print7=print3.right();
		System.out.println("\t\t\t"+print1);
		System.out.println("\n");
		System.out.println("\t\t"+"/"+"\t\t\\");
		System.out.println("\n");
		System.out.println("\t"+print2+"\t\t\t\t"+print3);
		System.out.println("\n");
		System.out.println("\t"+"/"+"\t"+"\\"+"\t\t\t"+"/"+"\t"+"\\");
		System.out.println("\n");
		System.out.println(""+print4+"\t\t\t"+print5+"\t"+print6+"\t\t\t"+print7);
	}
}