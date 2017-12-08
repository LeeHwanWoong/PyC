import java.io.*;

public class Lexer {

    private boolean isEof = false;
    private char ch = ' ';
    private BufferedReader input;
    private String line = "";
    private int lineno = 0;
    private int col = 1;
    private final String letters = "abcdefghijklmnopqrstuvwxyz"
        + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String digits = "0123456789";
    private final char eolnCh = '\n';
    private final char eofCh = '\004';


    public Lexer (String fileName) {
        try {
            input = new BufferedReader (new FileReader(fileName));
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found: " + fileName);
            System.exit(1);
        }
    }

    private char nextChar() {
        if (ch == eofCh)
            error("Attempt to read past end of file");
        col++;
        if (col >= line.length()) {
            try {
                line = input.readLine( );
            } catch (IOException e) {
                System.err.println(e);
                System.exit(1);
            }
            if (line == null)
                line = "" + eofCh;
            else {
                lineno++;
                line += eolnCh;
            }
            col = 0;
        }
        return line.charAt(col);
    }


    public Token next( ) {
        do {
            if (isLetter(ch)) {
                String spelling = concat(letters + digits);
                return Token.keyword(spelling);
            } else if (isDigit(ch)) {
                String number = concat(digits);
                if (ch != '.')
                    return Token.mkIntLiteral(number);
                number += concat(digits);
                return Token.mkFloatLiteral(number);
            } else switch (ch) {
            case ' ': case '\t': case '\r': case eolnCh:
                ch = nextChar();
                break;

            case '/':
                ch = nextChar();
                if (ch != '/')  return Token.divideTok;

                do {
                    ch = nextChar();
                } while (ch != eolnCh);
                ch = nextChar();
                break;

            case '\'':
                char ch1 = nextChar();
                nextChar();
                ch = nextChar();
                return Token.mkCharLiteral("" + ch1);

            case '\"':
                String temp = "";
                ch = nextChar();
                while(ch != '\"'){
                    temp += ch;
                    ch = nextChar();
                }
                ch = nextChar();
                return Token.mkStringLiteral(temp);

            case eofCh: return Token.eofTok;

            case '+': ch = nextChar();
                return Token.plusTok;

            case '-': ch = nextChar();
            	return Token.minusTok;
            case '*': ch = nextChar();
            	return Token.multiplyTok;
            case '(': ch = nextChar();
            	return Token.leftParenTok;
            case ')': ch = nextChar();
            	return Token.rightParenTok;
            case '{': ch = nextChar();
            	return Token.leftBraceTok;
            case '}': ch = nextChar();
            	return Token.rightBraceTok;
            case ';': ch = nextChar();
            	return Token.semicolonTok;
            case ',': ch = nextChar();
            	return Token.commaTok;
            case '[': ch = nextChar();
            	return Token.leftBracketTok;
            case ']': ch = nextChar();
            	return Token.rightBracketTok;
            case '.' : ch = nextChar(); return Token.dotTok;
            case '&': check('&'); return Token.andTok;
            case '|': check('|'); return Token.orTok;
            case '%': ch = nextChar();return Token.percentTok;
            case '=':
                return chkOpt('=', Token.assignTok,
                                   Token.eqeqTok);
            case '<':
            	return chkOpt('=',	Token.ltTok,
            						Token.lteqTok);
            case '>':
            	return chkOpt('=',	Token.gtTok,
            						Token.gteqTok);
            case '!':
            	return chkOpt('=',	Token.notTok,
            						Token.noteqTok);

            default:  error("Illegal character " + ch);
            }
        } while (true);
    }


    private boolean isLetter(char c) {
        return (c>='a' && c<='z' || c>='A' && c<='Z');
    }

    private boolean isDigit(char c) {
        return (c>='0' && c<='9');
        // 숫자인지 여부를 묻는 함수이다. c가 0부터 9사이인지 여부를 확인해 boolean값을 리턴해준다.
    }

    private void check(char c) {
        ch = nextChar();
        if (ch != c)
            error("Illegal character, expecting " + c);
        ch = nextChar();
    }

    private Token chkOpt(char c, Token one, Token two) {
        ch = nextChar();
        if(ch != c){
        	return one;
        }
        else{
        	return two;
        }// 두개의 char를 합쳐서 하나의 의미를 갖는 경우 사용된다. 즉 >=,<=,==,!=과 같이 두개의 char가 합쳐저서 하나의 의미를 같는 경우
        // 다음 char의 값을 확인한 후에 그 값이 '='기호일 경우 합쳐진 새로운 의미의 토큰을 리턴해주고 아닐경우 그냥 이전의 값만 해당 토큰으로 리턴해준다.
    }

    private String concat(String set) {
        String r = "";
        do {
            r += ch;
            ch = nextChar();
        } while (set.indexOf(ch) >= 0);
        return r;
    }

    public void error (String msg) {
        System.err.print(line);
        System.err.println("Error: column " + col + " " + msg);
        System.exit(1);
    }

    static public void main ( String[] argv ) {
        Lexer lexer = new Lexer(argv[0]);
        Token tok = lexer.next( );
        while (tok != Token.eofTok) {
            System.out.println(tok.toString());
            tok = lexer.next( );
        }
    } // main

}

