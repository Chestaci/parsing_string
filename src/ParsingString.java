import java.util.Stack;

public class ParsingString {
    public static void parsingString(String s) {
        char[] chars = s.toCharArray();
        if (validate(chars)) {
            StringBuilder count = new StringBuilder();     //для чисел СБ
            Stack<Integer> stack = new Stack<>();          //стэк чисел
            Stack<StringBuilder> stackSB = new Stack<>();  // стек СБ
            StringBuilder newString = new StringBuilder(); //основной СБ

            for (int i = 0; i < chars.length; i++) {

                if (Character.isLetter(chars[i])) {
                    newString.append(chars[i]);
                }
                if (Character.isDigit(chars[i])) {
                    count.append(chars[i]);
                }
                if (chars[i] == '[') {
                    stack.push(Integer.parseInt(String.valueOf(count)));
                    if (newString.length() == 0 && count.length() != 0){
                        stackSB.push(new StringBuilder());
                    }else {
                        stackSB.push(new StringBuilder(newString));
                        newString.setLength(0);
                    }
                    count.setLength(0);
                }

                if (chars[i] == ']') {
                    int n = stack.pop();
                    StringBuilder sbs = stackSB.pop();
                    String temp = new String(newString.toString());
                    newString.setLength(0);
                    for (int j = 0; j < n; j++) {
                        newString.append(temp);
                    }
                    if (sbs.length() != 0){
                        sbs.append(newString.toString());
                        newString.setLength(0);
                        newString.append(sbs.toString());
                        sbs.setLength(0);
                    }
                }
            }
            System.out.println(newString);
        }else {
            System.out.println("Неправильный формат строки!");
        }
    }

    public static boolean validate (char [] chars){
        boolean b = false;
        int count = 0;
        for (int i = 0; i < chars.length; i++){
            if (Character.isLetter(chars[i])) {
                String d = String.valueOf(chars[i]);
                if (d.matches("^[a-zA-Z]$")){
                    b = true;
                }else {
                    b = false;
                    System.out.println("Буквы должны быть латинскими!");
                    return b;
                }
            }
            if (Character.isDigit(chars[i]) || Character.isLetter(chars[i])
                    || (chars[i] == '[') || (chars[i] == ']')){
                b = true;
            } else {
                b = false;
                System.out.println("Недопустимые символы!");
                return b;
            }
            if (chars[i] == '['){
                count++;
            }
            if (chars[i] == ']'){
                count--;
            }
            if (i < chars.length - 1) {
                if (Character.isDigit(chars[i])) {
                    switch (chars[i+1]) {
                        case '[':
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            b = true;
                            break;
                        case ']':
                            b = false;
                            return b;
                        default:
                            b = false;
                            return b;
                    }
                    if (Character.isLetter(chars[i+1])){
                        b = false;
                        return b;
                    }
                }
                if ((chars[i] == '[') && (chars[i + 1] == ']')) {
                    b = false;
                    return b;
                }
            }
            if ((chars[0] == '[') || (chars[0] == ']')){
                b = false;
                return b;
            }
        }
        if (count == 0){
            b = true;
        } else {
            b = false;
            return b;
        }

        return b;
    }

    public static void main(String[] args) {
        parsingString("2[3[x]y]");
// xxxyxxxy

        parsingString("3[xyz]2[x]");
// xyzxyzxyzxx

        parsingString("3[xyz]4[xy]z");
// xyzxyzxyzxyxyxyxyz

        parsingString("2[2[2[x]y]z]");
// xxyxxyzxxyxxyz

        parsingString("2[3[x]y]3[xyz]2[x]3[xyz]4[xy]z");
// xxxyxxxyxyzxyzxyzxxxyzxyzxyzxyxyxyxyz

        parsingString("e2[y2[x]t]a2[z]t");
// eyxxtyxxtazzt

        parsingString("2[y2[x]t]");

// yxxtyxxt

        parsingString("e2[2[y2[x]]z]q");
// eyxxyxxzyxxyxxzq

    }
}
