package compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {

    private final String codeFile;
    private final ArrayList<Token> tokens = new ArrayList<>();
    private static final Map<String, Pattern> lexemes = new HashMap<>();

    public Lexer(String codeFile) {
        this.codeFile = codeFile;
        run();
    }

    static {
        lexemes.put("VAR", Pattern.compile("^[a-z_][a-zA-Z0-9_]*$"));
        lexemes.put("DIGIT", Pattern.compile("^\\d*$"));
        lexemes.put("ASSIGN_OP", Pattern.compile("^=$"));
        lexemes.put("OP", Pattern.compile("^(-|\\+|\\*|/)$"));
        lexemes.put("L_BC", Pattern.compile("^\\($"));
        lexemes.put("R_BC", Pattern.compile("^\\)$"));
        lexemes.put("ENDL", Pattern.compile("^;$"));
        lexemes.put("COMPARE_OP", Pattern.compile("^(~|<|>|!=)$"));
        lexemes.put("IF", Pattern.compile("^If$"));
        lexemes.put("ELSE", Pattern.compile("^Else$"));
        lexemes.put("WHILE", Pattern.compile("^While$"));
        lexemes.put("DO", Pattern.compile("^Do$"));
        lexemes.put("FOR", Pattern.compile("^For$"));
        lexemes.put("DIV", Pattern.compile("^,$"));
        lexemes.put("PRINT", Pattern.compile("^Print$"));
    }

    private void run() {
        String tokenStart = "";
        for (int i = 0; i < codeFile.length(); i++) {

            if (codeFile.toCharArray()[i] == ' ') {
                continue;
            }

            tokenStart += codeFile.toCharArray()[i];
            String tokenEnd = " ";

            if (i < codeFile.length() - 1) {
                tokenEnd = tokenStart + codeFile.toCharArray()[i + 1];
            }

            for (String key: lexemes.keySet()) {
                Pattern p = lexemes.get(key);
                Matcher m_1 = p.matcher(tokenStart);
                Matcher m_2 = p.matcher(tokenEnd);

                if (m_1.find() && !m_2.find()) {
                    tokens.add(new Token(key, tokenStart));
                    tokenStart = "";
                    break;
                }
            }
        }
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    @Override
    public String toString() {
        return "Lexer" + tokens;
    }
}
