import java.util.*;
import java.io.*;

// Node, List, Tree
class _Array<T> extends ArrayList<T> {

    _Array() {
        super();
    }

    public void sort() {
        if (this.get(0) instanceof Integer) {
            ArrayList<Integer> array = new ArrayList<>();
            array.addAll((_Array<Integer>) this);
            array.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return (o1 > o2) ? 1 : -1;
                }
            });
            this.clear();
            for (int i = 0; i < array.size(); i++) this.add((T)array.get(i));
        } else throw new IllegalStateException("pyc only provide integer sorting.");
    }

    public T min() {
        if (this.size() == 0) return null;
        if (this.get(0) instanceof Integer) {
            ArrayList<Integer> array = new ArrayList<>();
            array.addAll((_Array<Integer>) this);
            array.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return (o1 > o2) ? 1 : -1;
                }
            });
            return (T) array.get(0);
        } else throw new IllegalStateException("pyc only provide integer sorting.");
    }

    public T max() {
        if (this.size() == 0) return null;
        if (this.get(0) instanceof Integer) {
            ArrayList<Integer> array = new ArrayList<>();
            array.addAll((_Array<Integer>) this);
            array.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return (o1 < o2) ? 1 : -1;
                }
            });
            return (T) array.get(0);
        } else throw new IllegalStateException("pyc only provide integer sorting.");
    }

    public _Array<T> insert(int index, T element) {
        this.remove(index);
        this.add(index, element);
        return this;
    }

    public _Array<T> push(T element) {
        this.add(element);
        return this;
    }

    public T pop() {
        return this.remove(0);
    }
}

class _Queue<T> {

    private _Node<T> head;
    private _Node<T> tail;

    _Queue() {
        head = tail = null;
    }

    _Queue(T element) {
        head = tail = new _Node<T>(element);
    }

    public _Queue<T> push(T element) {
        if (head == null) {
            head = tail = new _Node<T>(element);
            return this;
        }
        _Node<T> node = new _Node<T>(element);
        tail.next = node;
        tail = tail.next;
        return this;
    }

    public T pop() {
        if (head == null) return null;
        _Node<T> node = head;
        head = head.next;
        return node.getElement();
    }
}

class _Node<T> {

    protected T element;
    protected _Node<T> prev, next;
    protected _Node<T> parent;

    _Node() {
        this(null);
    }

    _Node(T element) {
        this.element = element;
        this.prev = this.next = null;
    }

    public T getElement() {
        return this.element;
    }

    public _Node<T> setElement(T element) {
        this.element = element;
        return this;
    }

    public _Node<T> left() {
        return this.prev;
    }

    public _Node<T> right() {
        return this.next;
    }

    public _Node<T> parent() {
        return this.parent;
    }

    @Override
    public String toString() {
        return ""+element;
    }
}

class _List<T> extends _Node<T> {

    private _Node<T> head, tail;
    private int size;

    _List() {
        super();
        this.head = this.tail = null;
        size = 0;
    }

    _List(T element) {
        super(element);
        this.head = this.tail = this;
        size = 1;
    }

    public int size() {
        return size;
    }

    public _Node<T> head() {
        return this.head;
    }

    public _Node<T> tail() {
        return this.tail;
    }

    public _List<T> push(T element) {
        _Node<T> node = new _Node<T>(element);
        if (this.head == null) this.head = this.tail = node;
        if (this.tail != null) this.tail.next = node;
        node.prev = this.tail;
        this.tail = node;
        size += 1;
        return this;
    }

    public _Node<T> pop() {
        if (this.head == null) return null;
        _Node<T> element = this.head;
        this.head = this.head.next;
        size -= 1;
        return element;
    }
}

// Tree
class _Tree<T> extends _Node<T> {

    private _Node<T> root;
    private _Node<T> last;

    _Tree() {
        super();
        this.root = this.last = null;
    }

    _Tree(T element) {
        super(element);
        this.root = this.last = this;
    }

    public _Node<T> root() {
        return this.root;
    }

    public _Node<T> tree(int depth, int nth) {
        int position = (int) Math.pow(2, depth) + nth;
        _Node<T> node = root;
        while (position > 1) {
            if (node == null) throw new IndexOutOfBoundsException("tree size: 0, expected index: " + position);
            node = (position % 2 == 0) ? node.left() : node.right();
            position /= 2;
        }
        return node;
    }

    public _Tree<T> push(T element) {
        if (root == null) {
            root = last = new _Node<>(element);
            return this;
        }
        _Node<T> parent = getLastParentNode();
        if (parent.left() == null) {
            this.last = parent.prev = new _Node<>(element);
            this.last.parent = parent;
        }
        else if (parent.right() == null) {
            this.last = parent.next = new _Node<>(element);
            this.last.parent = parent;
        } else {
            System.out.println("must not reach here");
        }
        return this;
    }

    public _Node<T> pop() {
        if (root == null) return null;
        _Node<T> last = this.last;
        if (root == last) root = this.last = null;
        else {
            if (last == last.parent.left()) last.parent.prev = null;
            else if (last == last.parent.right()) last.parent.next = null;
            this.last = getLastChildNode();
        }
        return last;
    }

    private _Node<T> getLastParentNode() {
        if (root == null) return null;
        ArrayList<_Node<T>> queue = new ArrayList<>();
        queue.add(root);
        _Node<T> node = null;
        while (!queue.isEmpty()) {
            node = queue.remove(0);
            if (node.left() == null || node.right() == null) break;
            queue.add(node.left());
            queue.add(node.right());
        }
        return node;
    }

    private _Node<T> getLastChildNode() {
        if (root == null) return null;
        ArrayList<_Node<T>> queue = new ArrayList<>();
        queue.add(root);
        _Node<T> node = null;
        while (!queue.isEmpty()) {
            _Node<T> temp = queue.remove(0);
            if (temp == null) continue;
            node = temp;
            queue.add(node.left());
            queue.add(node.right());
        }
        return node;
    }
}

public class CodeGenerator{
	public static String[] splitarr;

	public static String getJavaType(String type) {
        if (type.equals(Token.listTok.value())) return "_List";
        if (type.equals(Token.arrayTok.value())) return "_Array";
        if (type.equals(Token.treeTok.value())) return "_Tree";
        if (type.equals(Token.stackTok.value())) return "Stack";
        if (type.equals(Token.queTok.value())) return "_Queue";
        if (type.equals(Token.nodeTok.value())) return "_Node";
        if (type.equals(Type.BOOL.getId())) return "boolean";
        if (type.equals(Type.STRING.getId())) return "String";
        if (type.equals(Type.FLOAT.getId())) return "double";
        return type;
    }

    public static String getJavaWrapperClass(String type) {
        if (type.equals(Type.BOOL.getId())) return "Boolean";
        if (type.equals(Type.INT.getId())) return "Integer";
        if (type.equals(Type.CHAR.getId())) return "Character";
        if (type.equals(Type.FLOAT.getId())) return "Float";
        if (type.equals(Type.STRING.getId())) return "String";
        if (type.equals(Type.FLOAT.getId())) return "Double";
        return type;
    }

    public static String getJavaCostructorClass(String type) {
        if (type.equals(Token.listTok.value())) return "_List";
        if (type.equals(Token.arrayTok.value())) return "_Array";
        if (type.equals(Token.treeTok.value())) return "_Tree";
        if (type.equals(Token.stackTok.value())) return "Stack";
        if (type.equals(Token.queTok.value())) return "_Queue";
        if (type.equals(Token.nodeTok.value())) return "_Node";
        return type;
    }

    public static String sortGenerator(int i) {
        // 3.Variable: a
        String line = splitarr[i++];
        int _indent = indent(splitarr[i-1])-1;
        String indent = "";
        for (int index = 0 ; index < _indent; index++) indent += "\t";
        String[] splitline = line.split(": ");
        String variable = splitline[1].trim();
        return getSortComparator(indent, variable);
    }

    public static String getSortComparator(String indent, String variable) {   // ASC
        String type = "Integer";   // TODO("retrieve from typemap")
        String result = "";
        result += indent + String.format("%s.sort();\n",variable);
        return result;
    }

	public static String declarationAnalyze(String declaration) {
		String temp = "";
        declaration = declaration.replace("| 1.Declarations = {", "").replace("}", "").trim();
        ArrayList<String> declarations = new ArrayList<>(Arrays.asList(declaration.split("\\|")));

        for (String decl: declarations) {
            decl = decl.trim().replace("<", "").replace(">", "");
            ArrayList<String> decls = new ArrayList<>(Arrays.asList(decl.split(", ")));
            String variable = decls.get(0);
            String type = CodeGenerator.getJavaType(decls.get(1));
            String innerType = (decls.size() == 3) ? CodeGenerator.getJavaWrapperClass(decls.get(2)) : null;
            String constructorType = (decls.size() == 3) ? CodeGenerator.getJavaCostructorClass(decls.get(1)) : null;
            if (innerType == null) temp += "\t\t" +String.format("%s %s;", type, variable) + "\n";
            else temp += "\t\t" + String.format("%s<%s> %s = new %s<>();", type, innerType, variable, constructorType) + "\n";
        }
        return temp;
    }

    public static String expressionAnalyze(int i){
    	String expression = splitarr[i];
    	if(expression.trim().contains("String")){
    		String[] temp = splitarr[i].split(":");
    		String ss = temp[temp.length-1];
            String ltrim = ss.replaceAll("^\\s+","");
            String rtrim = ss.replaceAll("\\s+$","");
    		return "\"" + ltrim + "\"";
    	}
        else if(expression.trim().contains("MaxMin")){
            String ret = "";
            i++;
            String[] temp = splitarr[i].split("\\.");
            String ret1 = "";
            ret1 += temp[temp.length-1].trim().replace(")","");
            i++;
            temp = splitarr[i].split(":");
            ret += temp[temp.length-1].trim() + "." + ret1+")";
            return ret;
        }
        else if(expression.trim().contains("RetModule")){
            String ret = "";
            i++;
            int check = indent(splitarr[i]);
            String[] temp = splitarr[i].split(":");
            ret += temp[1].trim();
            i++;
            temp = splitarr[i].split(":");
            ret += "."+temp[1].trim()+"(";
            for(i = i+1;i<splitarr.length;i++){
                if(check>indent(splitarr[i])){
                    break;
                }
                else if(check == indent(splitarr[i])){
                    ret+=expressionAnalyze(i)+",";
                }
            }
            ret = ret.substring(0, ret.length()-1);
            ret += ")";
            return ret;
        }
    	else if(expression.trim().contains("Value")){
       		String[] temp = splitarr[i].split(":");
       		String ss = temp[temp.length-1];
       		ss = ss.trim();
       		if(ss.contains("null")){
       			ss = ss.replace("null","");
       		}
    		if(expression.trim().contains("String")){
	    		return "\"" + ss + "\"";
    		}
    		else if(expression.trim().contains("Char")){
    			return "\'"+ss+"\'";
    		}
    		else{
    			return ss;
    		}
    	}
    	else if(expression.trim().contains("Input")){
    		return "inputS.nextLine()";
    	}
    	else if(expression.trim().contains("Variable")){
            String[] temp = splitarr[i].split(" ");
            String rightHand = temp[temp.length-1];
            if (rightHand.contains("[")) {
                String[] rightHands = rightHand.trim().split("\\[");
                String variable = rightHands[0];
                String index = rightHands[1].replace("]", "");
                return variable + ".get(" + index + ")";
            } else {
                return temp[temp.length-1];
            }
        }
        else if(expression.trim().contains("Unary")){
            String ret = "";
            String[] temp = splitarr[i+1].split(":");
            String op = temp[1].trim();
            if(op.equals("int")){
                op = "Integer.parseInt";
            }
            else if(op.equals("float")){
                op = "Double.parseDouble";
            }
            ret += op+"("+expressionAnalyze(i+2)+")";
            return ret;
        }
    	else if(expression.trim().contains("Binary")){
			i++;
            int check = indent(splitarr[i]);
			String ret = "";
			String[] temp = splitarr[i].split(":");
			String op = temp[1];
			int s,e=0;
			s = i+1;
            for(i=i+2; i<splitarr.length;i++){
				if(check == indent(splitarr[i])){
					e = i;
					break;
				}
			}
			if(splitarr[s].contains("String") || splitarr[e].contains("String")){
				if(op.contains("==")){
					return expressionAnalyze(s)+".equals("+expressionAnalyze(e)+")";
				}else if(op.contains("!=")){
					return "!" + expressionAnalyze(s)+".equals("+expressionAnalyze(e)+")";
				}
                else{
                    return expressionAnalyze(s) + op + expressionAnalyze(e);
                }
			}
			else{
				return expressionAnalyze(s) + op + expressionAnalyze(e);
			}
      	}
      	else if(expression.trim().contains("Tree")){
      		String[] temp = splitarr[i+1].split(":");
      		String v = temp[temp.length-1].trim();
      		String[] temp2 = splitarr[i+2].split(":");
      		String i1 = temp2[temp2.length-1].trim();
      		String[] temp3 = splitarr[i+3].split(":");
      		String i2 = temp3[temp3.length-1].trim();

      		return v+".tree(" + i1+","+i2+")";
      	}
    	else if(expression.trim().contains("Method")){
    		String v = expressionAnalyze(i+1);
    		String[] temp = splitarr[i+2].split("\\.");
    		return v+ "."+temp[1];
    	}
    	else if(expression.trim().contains("Unary")){
    		String[] temp = splitarr[i+1].split(" ");
    		String v = expressionAnalyze(i+2);
    		if(temp[temp.length-1].contains("neg")){
    			return "-"+v;
    		}
    		else{
    			return "!"+v;
    		}
    	}
    	return "";
    }

    public static int indent(String temp){
    	for(int i = 0;i<temp.length();i++){
    		if(temp.charAt(i)>='0' && temp.charAt(i)<='9'){
    			return Character.getNumericValue(temp.charAt(i));
    		}
    	}
    	return -1;
    }

    public static String statementAnalyze(int i){
    	String statement = splitarr[i];
    	if(statement.trim().matches(".*Assignment.*")){
            i++;
            String ret = "";
            String[] temp = splitarr[i].split(" ");
            for(int j = 1;j<temp.length;j++){
                if(temp[j].contains("|")){
                    ret+="\t";
                }
            }
            // case 1) temp[temp.length-1] = a[0];
            String leftHand = temp[temp.length-1];
            if (leftHand.contains("[")) {
                String[] leftHands = leftHand.trim().split("\\[");
                String variable = leftHands[0];
                String index = leftHands[1].replace("]", "");
                ret += variable + ".insert(" + index + ", " + expressionAnalyze(i+1) + ");\n";
            } else {
                ret += temp[temp.length-1] + "=";
                ret += expressionAnalyze(i+1) + ";\n";
            }
            //ret += temp[temp.length-1] + "=";
            //ret += expressionAnalyze(i+1) + ";\n";
            return ret;
        }
        else if(statement.trim().contains("Break")){
            int ind = indent(splitarr[i]);
            String ret = "";
            for(int q = 0;q<ind;q++){
                ret+="\t";
            }
            return ret+"break;\n";
        }
        else if(statement.trim().contains("Module")){
            int ind = indent(splitarr[i]);
            String ret = "";
            for(int q = 0;q<ind;q++){
                ret+="\t";
            }
            i++;
            int check = indent(splitarr[i]);
            String[] temp = splitarr[i].split(":");
            ret += temp[1].trim();
            i++;
            temp = splitarr[i].split(":");
            ret += "."+temp[1].trim()+"(";
            for(i = i+1;i<splitarr.length;i++){
                if(check>indent(splitarr[i])){
                    break;
                }
                else if(check == indent(splitarr[i])){
                    ret+=expressionAnalyze(i)+",";
                }
            }
            ret = ret.substring(0, ret.length()-1);
            ret += ");\n";
            return ret;
        }
    	else if(statement.trim().contains("Push")){
        	i++;
          	String ret = "";
          	String[] temp = splitarr[i].split(" ");
          	for(int j = 1;j<temp.length;j++){
             	if(temp[j].contains("|")){
                	ret+="\t";
             	}
          	}
          	ret += temp[temp.length-1] + ".push("+expressionAnalyze(i+1)+");\n";
          	return ret;
      	}
    	else if(statement.trim().contains("Sort")){
    		return sortGenerator(i+1);

    	}
    	else if(statement.trim().contains("Loop")){
    		String ret = "";
    		int check = indent(splitarr[i]);
    		for(int k = 0;k<check;k++){
    			ret+="\t";
    		}
    		String ind = ret;
            i++;
    		ret += "while("+expressionAnalyze(i)+"){\n";
    		check++;
    		for(i = i+1;i<splitarr.length;i++){
    			if(indent(splitarr[i]) == check && splitarr[i].trim().contains("Block")){
    				break;
    			}
    		}
    		check++;
    		for(i = i+1;i<splitarr.length;i++){
    			if(indent(splitarr[i]) == check){
    				ret += statementAnalyze(i);
    			}
                if(indent(splitarr[i]) == check-1){
                    break;
                }

    		}
    		ret+=ind+"}\n";
    		return ret;
    	}
    	else if(statement.trim().contains("Conditional")){
    		i++;
    		int check = indent(splitarr[i]);
    		String ret = "";
    		String[] temp = splitarr[i].split(" ");

    		for(int j = 1;j<temp.length;j++){
    			if(temp[j].contains("|")){
    				ret+="\t";
    			}
    		}
    		String ind = ret;
    		ret += "if("+expressionAnalyze(i)+"){\n";

    		for(i = i+1;i<splitarr.length;i++){
    			if(indent(splitarr[i]) == check){
    				break;
    			}
    		}

    		for(i = i+1;i<splitarr.length;i++){
                if(indent(splitarr[i]) <= check){
                    break;
                }
    			if(indent(splitarr[i]) == check+1){
    				ret += statementAnalyze(i);
    			}
    		}
    		ret+=ind+"}\n";
            if(indent(splitarr[i]) == check && splitarr[i].trim().contains("Block")){
                ret += ind+"else{\n";
                for(i = i+1;i<splitarr.length;i++){
                    if(indent(splitarr[i]) < check){
                        break;
                    }
                    if(indent(splitarr[i]) == check+1){
                        ret += statementAnalyze(i);
                    }
                }
                ret+=ind+"}\n";
            }
			return ret;
       }
       else if(statement.trim().contains("Output")){
            String ret = "";
            String[] temp = splitarr[i+1].split(" ");
            for(int j = 1;j<temp.length;j++){
                if(temp[j].contains("|")){
                    ret+="\t";
                }
            }
            ret+="System.out.println(";
            int check = indent(splitarr[i])+1;
            for(i = i+1;i<splitarr.length;i++){
                if(check == indent(splitarr[i])){
                    ret+=expressionAnalyze(i) + "+";
                }
                if(check > indent(splitarr[i])){
                    if(ret.length() >0 && ret.charAt(ret.length()-1)=='+'){
                        ret = ret.substring(0, ret.length()-1);
                    }
                    break;
                }
            }
            if(ret.length() >0 && ret.charAt(ret.length()-1)=='+'){
                        ret = ret.substring(0, ret.length()-1);
            }
            ret +=");\n";

            return ret;
       }
    	return "";
    }

    public static String require(String temp){
    	temp = temp.replace("| 1.Requires = ","");
    	temp = temp.replace("{","");
    	temp = temp.replace("}","");
        temp = temp.trim();

    	if(temp.equals("")){
    		return "";
    	}
    	String[] ss = temp.split("\\|");
    	String ret = "";
    	for(int i = 0;i<ss.length;i++){
    		ss[i] = ss[i].trim();
    		ret += "import "+ss[i]+";\n";
    	}
    	return ret;
    }

	public static void main(String args[]){
		String dec = "";
		CodeGenerator c = new CodeGenerator();
		Parser parser = new Parser(new Lexer(args[0]));
		String filename = "";
		for(int i = 0;i<args[0].length()-4;i++){
			filename += args[0].charAt(i);
		}
		Program prog = parser.program();

        System.out.println("------------------------------------------");

		splitarr = prog.display().split("\n");
        System.out.println("------------------------------------------");

        StaticTypeCheck typecheck = new StaticTypeCheck();
        //typecheck.V(prog);

		dec += declarationAnalyze(splitarr[3]);

		String str = "import java.util.*;\n";

		//str += require(splitarr[2]);

		str += "\n";

		str += "public class "+filename+"{\n\tpublic static void main(String args[]){\n\t\tScanner inputS = new Scanner(System.in);\n";

        for(Req r : prog.module){
            str += "\t\t"+r.root+" " +r.root + " = new "+r.root+"();\n";
        }

		str += dec;

		for(int i = 5;i<splitarr.length;i++){
			if(indent(splitarr[i]) == 2){
				str += statementAnalyze(i);
			}
		}

		str += "\t}\n}";

        str = str.replace("null","");

		try{
			BufferedWriter out = new BufferedWriter(new FileWriter(filename+".java"));
			out.write(str);
			out.close();
		}catch(IOException e){
			System.out.println(e);
			System.exit(1);
		}
	}
}