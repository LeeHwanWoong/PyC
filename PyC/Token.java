public class Token {

    private static final int KEYWORDS = TokenType.Eof.ordinal();

    private static final String[] reserved = new String[KEYWORDS];
    private static Token[] token = new Token[KEYWORDS];

    public static final Token eofTok = new Token(TokenType.Eof, "<<EOF>>");
    public static final Token boolTok = new Token(TokenType.Bool, "bool");
    public static final Token charTok = new Token(TokenType.Char, "char");
    public static final Token elseTok = new Token(TokenType.Else, "else");
    public static final Token falseTok = new Token(TokenType.False, "false");
    public static final Token floatTok = new Token(TokenType.Float, "float");
    public static final Token ifTok = new Token(TokenType.If, "if");
    public static final Token intTok = new Token(TokenType.Int, "int");
    public static final Token mainTok = new Token(TokenType.Main, "main");
    public static final Token trueTok = new Token(TokenType.True, "true");
    public static final Token whileTok = new Token(TokenType.While, "while");
    public static final Token leftBraceTok = new Token(TokenType.LeftBrace, "{");
    public static final Token rightBraceTok = new Token(TokenType.RightBrace, "}");
    public static final Token leftBracketTok = new Token(TokenType.LeftBracket, "[");
    public static final Token rightBracketTok = new Token(TokenType.RightBracket, "]");
    public static final Token leftParenTok = new Token(TokenType.LeftParen, "(");
    public static final Token rightParenTok = new Token(TokenType.RightParen, ")");
    public static final Token semicolonTok = new Token(TokenType.Semicolon, ";");
    public static final Token commaTok = new Token(TokenType.Comma, ",");
    public static final Token assignTok = new Token(TokenType.Assign, "=");
    public static final Token eqeqTok = new Token(TokenType.Equals, "==");
    public static final Token ltTok = new Token(TokenType.Less, "<");
    public static final Token lteqTok = new Token(TokenType.LessEqual, "<=");
    public static final Token gtTok = new Token(TokenType.Greater, ">");
    public static final Token gteqTok = new Token(TokenType.GreaterEqual, ">=");
    public static final Token notTok = new Token(TokenType.Not, "!");
    public static final Token noteqTok = new Token(TokenType.NotEqual, "!=");
    public static final Token plusTok = new Token(TokenType.Plus, "+");
    public static final Token minusTok = new Token(TokenType.Minus, "-");
    public static final Token multiplyTok = new Token(TokenType.Multiply, "*");
    public static final Token divideTok = new Token(TokenType.Divide, "/");
    public static final Token percentTok = new Token(TokenType.Percent,"%");
    public static final Token andTok = new Token(TokenType.And, "&&");
    public static final Token orTok = new Token(TokenType.Or, "||");
    public static final Token reqTok = new Token(TokenType.Require, "require");
    public static final Token listTok = new Token(TokenType.List, "list");
    public static final Token arrayTok = new Token(TokenType.Array, "array");
    public static final Token treeTok = new Token(TokenType.Tree, "tree");
    public static final Token stackTok = new Token(TokenType.Stack, "stack");
    public static final Token queTok = new Token(TokenType.Queue, "queue");
    public static final Token nodeTok = new Token(TokenType.Node, "node");
    public static final Token sortTok = new Token(TokenType.Sorting, "sorting");
    public static final Token inputTok = new Token(TokenType.Input, "input");
    public static final Token outputTok = new Token(TokenType.Output, "output");
    public static final Token pushTok = new Token(TokenType.Push, "push");
    public static final Token compTok = new Token(TokenType.Compare,"compare");
    public static final Token popTok = new Token(TokenType.Pop, "pop");
    public static final Token rightTok = new Token(TokenType.Right, "right");
    public static final Token leftTok = new Token(TokenType.Left, "left");
    public static final Token headTok = new Token(TokenType.Head, "head");
    public static final Token tailTok = new Token(TokenType.Tail, "tail");
    public static final Token rootTok = new Token(TokenType.Root, "root");
    public static final Token parentTok = new Token(TokenType.Parent, "parent");
    public static final Token maxTok = new Token(TokenType.Max, "max");
    public static final Token minTok = new Token(TokenType.Min, "min");
    public static final Token stringTok = new Token(TokenType.String, "string");
    public static final Token dotTok = new Token(TokenType.Dot, ".");
    public static final Token bigTok = new Token(TokenType.Big, "\"");
    public static final Token breakTok = new Token(TokenType.Break,"break");

    private TokenType type;
    private String value = "";

    private Token (TokenType t, String v) {
        type = t;
        value = v;
        if (t.compareTo(TokenType.Eof) < 0) {
            int ti = t.ordinal();
            reserved[ti] = v;
            token[ti] = this;
        }
    }

    public TokenType type( ) { return type; }

    public String value( ) { return value; }

    public static Token keyword  ( String name ) {
        char ch = name.charAt(0);
        if (ch >= 'A' && ch <= 'Z') return mkIdentTok(name);
        for (int i = 0; i < KEYWORDS; i++)
           if (name.equals(reserved[i]))  return token[i];
        return mkIdentTok(name);
    }

    public static Token mkIdentTok (String name) {
        return new Token(TokenType.Identifier, name);
    }

    public static Token mkStringLiteral (String name){
        return new Token(TokenType.StringLiteral, name);
    }

    public static Token mkIntLiteral (String name) {
        return new Token(TokenType.IntLiteral, name);
    }

    public static Token mkFloatLiteral (String name) {
        return new Token(TokenType.FloatLiteral, name);
    }

    public static Token mkCharLiteral (String name) {
        return new Token(TokenType.CharLiteral, name);
    }

    public String toString ( ) {
        if (type.compareTo(TokenType.Identifier) < 0) return value;
        return type + "\t" + value;
    }

    public static void main (String[] args) {
        System.out.println(eofTok);
        System.out.println(whileTok);
    }
}
