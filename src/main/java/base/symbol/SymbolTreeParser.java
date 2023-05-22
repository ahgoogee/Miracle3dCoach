package base.symbol;


import java.util.ArrayList;
import java.util.List;

/**
 * 符号树解析器
 * 将服务器原始消息转化为符号数结构
 */
public class SymbolTreeParser {
    public SymbolTreeParser() {
    }

    public SymbolNode parse(String input) throws IllegalSymbolInputException {
        if (input != null && input.length() != 0) {
            if (input.charAt(0) == '(' && input.charAt(input.length() - 1) == ')') {
                return parseReal(input);
            } else {
                throw new IllegalSymbolInputException("未嵌入大括号中的输入：" + input);
            }
        } else {
            throw new IllegalSymbolInputException("空字符串");
        }
    }

    private static SymbolNode parseReal(String string) throws IllegalSymbolInputException {
        char[] input = string.toCharArray();
        List<Object> tmpchildren = new ArrayList(5);
        int index = 0;
        int level = 0;

        int startIndex;
        for(startIndex = 0; index < input.length && level >= 0; ++index) {
            switch (input[index]) {
                case ' ':
                    if (level == 0) {
                        if (index > startIndex) {
                            tmpchildren.add(string.substring(startIndex, index));
                        }

                        startIndex = index + 1;
                    }
                    break;
                case '(':
                    if (level == 0) {
                        if (index > startIndex) {
                            tmpchildren.add(string.substring(startIndex, index));
                        }

                        startIndex = index + 1;
                    }

                    ++level;
                    break;
                case ')':
                    --level;
                    if (level == 0) {
                        tmpchildren.add(parseReal(string.substring(startIndex, index)));
                        startIndex = index + 1;
                    }
            }
        }

        if (index > startIndex) {
            tmpchildren.add(string.substring(startIndex, index));
        }

        if (level != 0) {
            IllegalSymbolInputException up = new IllegalSymbolInputException("输入中缺少括号：" + string);
            throw up;
        } else {
            return new SymbolNode(tmpchildren);
        }
    }
}
