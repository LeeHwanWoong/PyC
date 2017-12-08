import java.util.*;

public class TypeMap extends HashMap<Variable, Type> {
	public void display()
	{
		System.out.println("-----------------TypeMap------------------");
		for(Variable v : this.keySet())
			System.out.println("    " + v.toString() + " :: " + this.get(v).getId());
	}
}