import java.util.*;

class Indenter {
   public int level;// 트리의 레벨
   public Indenter(int nextlevel) { level = nextlevel; }    //indenter 생성자

   public String display(String message) {
      String tab = "";
      if(!message.equals("Program (abstract syntax): ")){
          System.out.println();
      }
      for (int i=0; i<level; i++)
          tab = tab + "| ";
      System.out.print(tab+ level+ "."+ message); //현재 레벨의 메시지를 출력해주는 함수, 레벨당 tab을 해서 구별할 수 있게 해준다. 트리 출력을 위한 클래스
      return "\n" + tab+ level+ "."+ message;
   }
}


class Program {
    Declarations decpart;
    Block body;
    Reqs module;
    String temp = "";
    Program (Reqs r, Declarations d, Block b) {
        module = r;
        decpart = d;
        body = b;
    }
    public String display () {
        int level = 0;
        Indenter indent = new Indenter(level);
        temp += indent.display("Program (abstract syntax): ");
    	temp += module.display(level+1);
        temp += decpart.display(level+1);
        temp += body.display(level+1)+"\n";
        System.out.println();

        return temp;
    }
}

class Reqs extends ArrayList<Req>{
    String temp = "";
    public String display (int level) {
        Indenter indent = new Indenter(level);
        temp += indent.display("Requires = { ");
        String sep = "";
        for (Req dcl : this) {
            System.out.print(sep);
            temp += sep;
            temp += dcl.display();
            sep = " | ";
        }
        System.out.print(" }");
        temp += " }";
        return temp;
    }
}

class Req{//Req = root
    String root,temp = "";
    Req(String t){
        root = t;
    }
    public String display(){
        System.out.print(root);
        temp += root;
        return temp;
    }
}

class DS{//DS =
    protected String id;
    public Type t;
    protected DS(Type tt, String tid){
        t = tt;
        id = tid;
    }
    public String getId(){return id;}
}

class Declarations extends ArrayList<Declaration> {
	String temp = "";
    // Declarations = Declaration*

    public String display (int level) {
        Indenter indent = new Indenter(level);
        temp += indent.display("Declarations = { ");
        String sep = "";
        for (Declaration dcl : this) {
            System.out.print(sep);
            temp += sep + dcl.display();
            sep = " | ";
        }
        System.out.print(" }");
        temp += " }";
        return temp;
    }
}//선언부 출력하는 부분

class Declaration {
    // Declaration = Variable v; Type t
    Variable v;
    String temp = "";
    Type t;
    DS ds;

    Declaration( ) { }
    Declaration (String id, Type type) {
        v = new Variable(id); t = type; ds = null;
    } // declaration  생성자
    Declaration(String id, DS tds){
    	v = new Variable(id);
    	ds = tds;
    	t = ds.t;
    }

    public String display () {
    	if(ds == null){
       		System.out.print("<" + v + ", " + t.getId() + ">");
       		temp += "<" + v + ", " + t.getId() + ">";
       	}
       	else{
       		System.out.print("<" + v + ", " + ds.getId() + ", " + ds.t.getId() + ">");
       		temp += "<" + v + ", " + ds.getId() + ", " + ds.t.getId() + ">";
       	}
       	return temp;
    }
}//선언부 출력하는 부분

class Type {
    // Type = int | bool | char | float | string
    final static Type INT = new Type("int");
    final static Type BOOL = new Type("bool");
    final static Type FLOAT = new Type("float");
    final static Type CHAR = new Type("char");
    final static Type VOID = new Type("void");
    final static Type STRING = new Type("string");
    final static Type UNDEFINED = new Type("undefined");
    final static Type UNUSED = new Type("unused");

    protected String id;
    protected Type (String t) { id = t; }
    public String getId ( ) { return id; }
}//Type 클래스

abstract class Statement {
    // Statement = Skip | Block | Assignment | Conditional | Loop | Sort
    String temp = "";
    public String display (int level) {
         Indenter indent = new Indenter(level);
         temp += indent.display(getClass().toString().substring(6) + ": ");
         return temp;
    }
}//Statement 클래스 , 저장해야할 변수가 없으므로 추상 클래스로 선언한다.
//Skip, Block, Assignment, Conditional, Loop 클래스는 Statement상속

class BreakStatement extends Statement{
    public String display(int level){
        return super.display(level);
    }
}
class Output extends Statement{
	String temp = "";
	ArrayList<Expression> outputdata = new ArrayList<Expression>();

	Output(){}

	public void add(Expression e){
		outputdata.add(e);
	}
	public String display(int level){
		temp += super.display(level);
		for(Expression o : outputdata){
            if(o instanceof Variable){
                Variable t = (Variable)o;
                temp += t.display(level+1);
            }
            else{
                StringValue t = (StringValue)o;
                temp+=t.display(level+1);
            }
		}
		return temp;
	}
}

class Push extends Statement{
	Variable v;
	String temp = "";
	Expression e;
	Push(Variable x, Expression y){
		v = x;
		e = y;
	}
	public String display(int level){
		temp += super.display(level);
		temp += v.display(level+1);
		temp += e.display(level+1);

		return temp;
	}
}

class Module extends Statement{
    String module;
    String method;
    String temp = "";
    ArrayList<Expression> parameter = new ArrayList<Expression>();

    Module(String mo,String me){
        this.module = mo;
        this.method = me;
    }
    public void add(Expression e){
        parameter.add(e);
    }

    public String display(int level){
        Indenter ind = new Indenter(level+1);
        temp += super.display(level);
        temp += ind.display("Module : "+module);
        temp += ind.display("Method : "+method);
        for(Expression t : parameter){
            temp += t.display(level+1);
        }

        return temp;
    }
}

class Sort extends Statement{
	Variable v;
	String temp;
	Sort(String t){
		v = new Variable(t);
	}
	public String display (int level) {
       temp += super.display(level);//부모클래스를 display해준다.
       temp += v.display(level+1);

       return temp;
    }
}

class Skip extends Statement {
	String temp = "";
    public String display (int level) {
       temp += super.display(level);
       return temp;
    }
}// Skip , ;

class Block extends Statement {
	String temp = "";
    // Block = Statement*
    public ArrayList<Statement> members = new ArrayList<Statement>();

    public String display(int level) {
        temp += super.display(level);
        for (Statement s : members){
            temp += s.display(level+1);
        }
        return temp;
    }
}// Block,  { }

class Assignment extends Statement {
    // Assignment = Variable target; Expression source
    Variable target;
    Expression source;
    String temp = "";

    Assignment (Variable t, Expression e) {
        target = t;
        source = e;
    }//Assignment 생성자

    public String display (int level) {
       temp += super.display(level);//부모클래스를 display해준다.
       temp += target.display(level+1);
       temp += source.display(level+1);
       return temp;
    }
}       //Assignment 는 Statement를 상속 받는다.

class Conditional extends Statement {
// Conditional = Expression test; Statement thenbranch, elsebranch
    Expression test;
    Statement thenbranch, elsebranch;
    String temp = "";

    Conditional (Expression t, Statement tp) {
        test = t; thenbranch = tp; elsebranch = new Skip( );
    } //Conditional 생성자, elsebranch가 없을 경우 Skip객체를 생성한다.

    Conditional (Expression t, Statement tp, Statement ep) {
        test = t; thenbranch = tp; elsebranch = ep;
    } //Conditional 생성자

    public String display (int level) {
       temp += super.display(level);
       temp += test.display(level+1);
       temp += thenbranch.display(level+1);
       assert elsebranch != null : "else branch cannot be null";
       temp += elsebranch.display(level+1); // 출력부분

       return temp;
    }
}

class Loop extends Statement {
// Loop = Expression test; Statement body
    Expression test;
    Statement body;
    String temp = "";

    Loop (Expression t, Statement b) {
        test = t; body = b; //loop생성자
    }

    public String display (int level) {
       temp += super.display(level);
       temp += test.display(level+1);
       temp += body.display(level+1);

       return temp;
    }
} //while문 클래스 statement를 상속받는다.

abstract class Expression {
    // Expression = Variable | Value | Binary | Unary
    String temp = "";

    public String display (int level) {
         Indenter indent = new Indenter(level);
         temp += indent.display(getClass().toString().substring(6) + ": ");
         return temp;
    }
} //추상 expression클래스

class Compare extends Expression{
	Expression v1, v2;
	String temp = "";
	Compare(Expression x, Expression y){
		v1 = x;
		v2 = y;
	}
	public String display(int level){
		temp += super.display(level);
		temp += v1.display(level+1);
		temp += v2.display(level+1);

		return temp;
	}
}

class MaxMin extends Expression{
	String maxmin,temp = "";
	Expression e;
	MaxMin(String t, Expression et){
		maxmin = t;
		e = et;
	}
	public String display(int level){
		Indenter ind = new Indenter(level+1);
		temp += super.display(level);
		temp += ind.display(maxmin+"()");
		temp += e.display(level+1);

		return temp;
	}
}

class Method extends Expression{
    String method,temp = "";
    Variable v;
    Method(String tm, Variable vt){
        method = tm;
        v = vt;
    }
    public String display(int level){
        Indenter ind = new Indenter(level+1);
        temp += super.display(level);
        temp += v.display(level+1);
        temp += ind.display(method+"()");

        return temp;
    }
}

class Variable extends Expression {
   //Variable은 string형 아이디
    private String id;
    String temp = "";

    Variable (String s) { id = s; } //생성자

    public String id( )  { return id; }
    public String toString( ) { return id; }
    public boolean equals (Object obj) {
        String s = ((Variable) obj).id;
        return id.equals(s);
    }
    public int hashCode ( ) { return id.hashCode( ); } //해당객체의 고유한 값을 리턴해주는 hashcode 함수

    public String display (int level) {
        temp += super.display(level) + id;
        System.out.print(id);

        return temp;
    }
}//Expression을 사옥받는 Variable 클래스

class Input extends Expression{
    String temp = "";
    Input(){
    }
    public String display(int level){
        temp += super.display(level) + "()";
        System.out.print("()");
        return temp;
    }
}

class RetModule extends Expression{
    String module;
    String method;
    String temp = "";
    ArrayList<Expression> parameter = new ArrayList<Expression>();

    RetModule(String mo,String me){
        this.module = mo;
        this.method = me;
    }
    public void add(Expression e){
        parameter.add(e);
    }

    public String display(int level){
        Indenter ind = new Indenter(level+1);
        temp += super.display(level);
        temp += ind.display("Module : "+module);
        temp += ind.display("Method : "+method);
        for(Expression t : parameter){
            temp += t.display(level+1);
        }

        return temp;
    }
}

class Tree extends Expression{
    Variable v;
    String temp = "";
    ArrayList<Value> t = new ArrayList<Value>();
    Tree(Variable p, Value q){
        v = p;
        t.add(q);
    }
    public void add(Value x){
        t.add(x);
    }
    public String display(int level){
        temp += super.display(level);
        temp += v.display(level+1);
        for(Value x : t){
            temp += x.display(level+1);
        }
        return temp;
    }
}

class Binary extends Expression {
// Binary = Operator op; Expression term1, term2
    Operator op;
    Expression term1, term2; //두개 항(term) 계산
    String temp = "";

    Binary (Operator o, Expression l, Expression r) {
        op = o; term1 = l; term2 = r;
    } // binary생성자

    public String display (int level) {
       temp += super.display(level);
       temp += op.display(level+1);
       temp += term1.display(level+1);
       temp += term2.display(level+1);

       return temp;
    }
} //binary 는 expression을 상속받는다. 두개의 연산을 수행한다.

abstract class Value extends Expression {
// Value = IntValue | BoolValue | CharValue | FloatValue
    protected Type type;
    protected boolean undef = true;

    int intValue ( ) {
        assert false : "should never reach here"; //assert [boolean 식] : 표현식, boolean이 참이면 프로그램을 계속돌리고 아니면 assertion error를 발생시킨다.
        return 0;
    }

    String stringValue(){
    	return "";
    }

    boolean boolValue ( ) {
        assert false : "should never reach here";
        return false;
    }

    char charValue ( ) {
        assert false : "should never reach here";
        return ' ';
    }

    float floatValue ( ) {
        assert false : "should never reach here";
        return 0.0f;
    }

    boolean isUndef( ) { return undef; }

    Type type ( ) { return type; }

    static Value mkValue (Type type) {
    	if(type == Type.STRING) return new StringValue();
        if (type == Type.INT) return new IntValue( );
        if (type == Type.BOOL) return new BoolValue( );
        if (type == Type.CHAR) return new CharValue( );
        if (type == Type.FLOAT) return new FloatValue( ); //Type이 Int,Bool, Char, Float면 IntValue(), BoolValue(), CharValue(), FloatValue()
        throw new IllegalArgumentException("Illegal type in mkValue");
    }
} //Expression 을 상속받는 Value 클래스

class StringValue extends Value{
	public String value;
	String temp = "";

	StringValue(){type = Type.STRING;}
	StringValue(String t){this(); value = t; undef = false;}

	String stringValue(){
		assert !undef : "reference to undefined string value";
        return value;
	}

	public String display(int level){
		temp += super.display(level) + value;
		System.out.print(value);

		return temp;
	}
}

class IntValue extends Value {
    public int value = 0;
    String temp = "";

    IntValue ( ) { type = Type.INT; } //생성자

    IntValue (int v) { this( ); value = v; undef = false; } //생성자

    int intValue ( ) {
        assert !undef : "reference to undefined int value";
        return value;
    }

    public String toString( ) {
        if (undef)  return "undef";
        return "" + value;
    }

    public String display (int level) {
        temp += super.display(level) + Integer.toString(value);
        System.out.print(value);//int값 출력
        return temp;
    }
}

class BoolValue extends Value {
    public boolean value = false;
    String temp = "";

    BoolValue ( ) { type = Type.BOOL; }

    BoolValue (boolean v) { this( ); value = v; undef = false; }

    boolean boolValue ( ) {
        assert !undef : "reference to undefined bool value";
        return value;
    }

    int intValue ( ) {
        assert !undef : "reference to undefined bool value";
        return value ? 1 : 0;
    }

    public String toString( ) {
        if (undef)  return "undef";
        return "" + value;
    }

    public String display (int level) {
        temp += super.display(level) + String.valueOf(value);
        System.out.print(value); //Boolean값 출력

        return temp;
    }
}

class CharValue extends Value {
    public char value = ' ';
    String temp = "";

    CharValue ( ) { type = Type.CHAR; }

    CharValue (char v) { this( ); value = v; undef = false; }

    char charValue ( ) {
        assert !undef : "reference to undefined char value";
        return value;
    }

    public String toString( ) {
        if (undef)  return "undef";
        return "" + value;
    }

    public String display (int level) {
        temp += super.display(level) + value;
        System.out.print(value); //Char값 출력
        return temp;
    }
}

class FloatValue extends Value {
    public float value = 0;
    String temp = "";

    FloatValue ( ) { type = Type.FLOAT; }

    FloatValue (float v) { this( ); value = v; undef = false; }

    float floatValue ( ) {
        assert !undef : "reference to undefined float value";
        return value;
    }

    public String toString( ) {
        if (undef)  return "undef";
        return "" + value;
    }

    public String display (int level) {
        temp += super.display(level) + Float.toString(value);
        System.out.print(value); //Float값 출력
        return temp;
    }
}

class Unary extends Expression {
    // Unary = Operator op; Expression term
    Operator op;
    Expression term;
    String temp = "";

    Unary (Operator o, Expression e) {
        op = o.val.equals("-") ? new Operator("neg"): o;
        term = e;
    } // unary

    public String display (int level) {
       temp += super.display(level);
       temp += op.display(level+1);
       temp += term.display(level+1);

       return temp;
    }
} // Unary 클래스는 expression을 상속받는다. 앞에 -,! 이 붙는 연산 unary

class Operator {
    // Operator = BooleanOp | RelationalOp | ArithmeticOp | UnaryOp
    // BooleanOp = && | ||
    final static String AND = "&&";
    final static String OR = "||";
    // RelationalOp = < | <= | == | != | >= | >
    final static String LT = "<";
    final static String LE = "<=";
    final static String EQ = "==";
    final static String NE = "!=";
    final static String GT = ">";
    final static String GE = ">=";
    // ArithmeticOp = + | - | * | /
    final static String PLUS = "+";
    final static String MINUS = "-";
    final static String TIMES = "*";
    final static String DIV = "/";
    final static String PERCENT = "%";
    // UnaryOp = !
    final static String NOT = "!";
    final static String NEG = "-";
    // CastOp = int | float | char
    final static String INT = "int";
    final static String FLOAT = "float";
    final static String CHAR = "char";
    // Typed Operators
    // RelationalOp = < | <= | == | != | >= | >
    final static String INT_LT = "INT<";
    final static String INT_LE = "INT<=";
    final static String INT_EQ = "INT==";
    final static String INT_NE = "INT!=";
    final static String INT_GT = "INT>";
    final static String INT_GE = "INT>=";
    // ArithmeticOp = + | - | * | /
    final static String INT_PLUS = "INT+";
    final static String INT_MINUS = "INT-";
    final static String INT_TIMES = "INT*";
    final static String INT_DIV = "INT/";
    final static String INT_PERCENT = "INT%";
    // UnaryOp = !
    final static String INT_NEG = "-";
    // RelationalOp = < | <= | == | != | >= | >
    final static String FLOAT_LT = "FLOAT<";
    final static String FLOAT_LE = "FLOAT<=";
    final static String FLOAT_EQ = "FLOAT==";
    final static String FLOAT_NE = "FLOAT!=";
    final static String FLOAT_GT = "FLOAT>";
    final static String FLOAT_GE = "FLOAT>=";
    final static String FLOAT_PERCENT = "FLOAT%";
    // ArithmeticOp = + | - | * | /
    final static String FLOAT_PLUS = "FLOAT+";
    final static String FLOAT_MINUS = "FLOAT-";
    final static String FLOAT_TIMES = "FLOAT*";
    final static String FLOAT_DIV = "FLOAT/";
    // UnaryOp = !
    final static String FLOAT_NEG = "-";
    // RelationalOp = < | <= | == | != | >= | >
    final static String CHAR_LT = "CHAR<";
    final static String CHAR_LE = "CHAR<=";
    final static String CHAR_EQ = "CHAR==";
    final static String CHAR_NE = "CHAR!=";
    final static String CHAR_GT = "CHAR>";
    final static String CHAR_GE = "CHAR>=";
    // RelationalOp = < | <= | == | != | >= | >
    final static String BOOL_LT = "BOOL<";
    final static String BOOL_LE = "BOOL<=";
    final static String BOOL_EQ = "BOOL==";
    final static String BOOL_NE = "BOOL!=";
    final static String BOOL_GT = "BOOL>";
    final static String BOOL_GE = "BOOL>=";
    // Type specific cast
    final static String I2F = "I2F";
    final static String F2I = "F2I";
    final static String C2I = "C2I";
    final static String I2C = "I2C";

    String val, temp = "";

    Operator (String s) { val = s; }

    public String toString( ) { return val; }
    public boolean equals(Object obj) { return val.equals(obj); }

    boolean BooleanOp ( ) { return val.equals(AND) || val.equals(OR); }
    boolean RelationalOp ( ) {
        return val.equals(LT) || val.equals(LE) || val.equals(EQ)
            || val.equals(NE) || val.equals(GT) || val.equals(GE);
    }
    boolean ArithmeticOp ( ) {
        return val.equals(PLUS) || val.equals(MINUS)
            || val.equals(TIMES) || val.equals(DIV) || val.equals(PERCENT);
    }
    boolean NotOp ( ) { return val.equals(NOT) ; }
    boolean NegateOp ( ) { return val.equals(NEG) ; }
    boolean intOp ( ) { return val.equals(INT); }
    boolean floatOp ( ) { return val.equals(FLOAT); }
    boolean charOp ( ) { return val.equals(CHAR); }

    final static String intMap[ ] [ ] = {
        {PLUS, INT_PLUS}, {MINUS, INT_MINUS},
        {TIMES, INT_TIMES}, {DIV, INT_DIV},{PERCENT,INT_PERCENT},
        {EQ, INT_EQ}, {NE, INT_NE}, {LT, INT_LT},
        {LE, INT_LE}, {GT, INT_GT}, {GE, INT_GE},
        {NEG, INT_NEG}, {FLOAT, I2F}, {CHAR, I2C}
    };

    final static String floatMap[ ] [ ] = {
        {PLUS, FLOAT_PLUS}, {MINUS, FLOAT_MINUS},
        {TIMES, FLOAT_TIMES}, {DIV, FLOAT_DIV},{PERCENT,FLOAT_PERCENT},
        {EQ, FLOAT_EQ}, {NE, FLOAT_NE}, {LT, FLOAT_LT},
        {LE, FLOAT_LE}, {GT, FLOAT_GT}, {GE, FLOAT_GE},
        {NEG, FLOAT_NEG}, {INT, F2I}
    };

    final static String charMap[ ] [ ] = {
        {EQ, CHAR_EQ}, {NE, CHAR_NE}, {LT, CHAR_LT},
        {LE, CHAR_LE}, {GT, CHAR_GT}, {GE, CHAR_GE},
        {INT, C2I}
    };

    final static String boolMap[ ] [ ] = {
        {EQ, BOOL_EQ}, {NE, BOOL_NE}, {LT, BOOL_LT},
        {LE, BOOL_LE}, {GT, BOOL_GT}, {GE, BOOL_GE},
    };

    final static private Operator map (String[][] tmap, String op) {
        for (int i = 0; i < tmap.length; i++)
            if (tmap[i][0].equals(op))
                return new Operator(tmap[i][1]);
        assert false : "should never reach here";
        return null;
    }

    final static public Operator intMap (String op) {
        return map (intMap, op);
    }

    final static public Operator floatMap (String op) {
        return map (floatMap, op);
    }

    final static public Operator charMap (String op) {
        return map (charMap, op);
    }

    final static public Operator boolMap (String op) {
        return map (boolMap, op);
    }

    public String display (int level) {
         Indenter indent = new Indenter(level);
         temp += indent.display(getClass().toString().substring(6) + ": " + val);
         return temp;
    }
}