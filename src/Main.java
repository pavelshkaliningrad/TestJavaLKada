import java.lang.reflect.Array;
import java.util.*;

public class Main {
    static final char[] arithOperation = {'+','-','/','*'};
    static final Map <String,Integer> romeNumber = new LinkedHashMap<String,Integer>(){
        {
            put("M", 1000);
            put("CM", 900);
            put("D", 500);
            put("CD", 400);
            put("C", 100);
            put("XC", 90);
            put("L", 50);
            put("XL", 40);
            put("X", 10);
            put("IX", 9);
            put("V", 5);
            put("IV", 4);
            put("I", 1);
        }};
    // Метод из тестового задания
    public static String calc (String input) throws Exception  {
        String result = new String() ;
        oneOperationValidation(input);// Вывывает исключение если в выражение не одна операция
        twoOperandValidation(input);// Вызывает исключение если в выражении не два операнда
        numberSystemValidation(input);//Вызывает исключение если операнды не соответствуют не одной системы счисления или если их система счисления разная
        numberMinMaxValidation(input);// Вызывает исключение если числа не от 1 до 10 в обоих системах счисления системе счисления

        if (getNumberSystem(firstOperand(input)) == "ArabNumberSystem") {
            result = calcArabNumber (input,getOperation(input));//Вычисление арабских цифр
            }
        else {
            result = calcRomeNumber(input,getOperation(input));//Вычисление римских цифр
        }

        return result;
    }
    //Проверка чтобы  была всего одна операция в выражении
    static void oneOperationValidation (String input) throws Exception {
        //Определяем количество операций в выращении (+,-,*,/)
        int countOperation;
        countOperation = 0;
        for (char i:
                input.toCharArray()) {
            for (char j:
                    arithOperation) {
                if (i == j) {
                    countOperation++;
                }
            }
        }
        // Если количество операции не равно одному то вызываем исключение
        if (countOperation != 1) {
            throw new Exception();
        }
    }
    // Получить операцию выражения в виде символа (+-/*)
    static char getOperation (String input) {
        char result;
        result = '0';
        for (char i:
                input.toCharArray()) {
            for (char j:
                    arithOperation) {
                if (i == j) {
                    result = j;
                }
            }
        }
        return result;
    }
    //Проверить если ли у выражения 2 операнда
    static void twoOperandValidation (String input) throws Exception {
        String operation;
        operation = String.valueOf(getOperation(input));// Получаем знак операции (+-*/)
        //Если знак операции не стоит первый в строке и не стоит последний в строке то получается что у выражения есть 2 операнда
        if (input.startsWith(operation) | input.endsWith(operation)){
            throw  new Exception();
        }
    }
    //Проверяет чтобы операнды соответствовали хотя бы одной системы счисления и чтобы операнды имели одинаковую систему счисления
    static void numberSystemValidation (String input) throws Exception {
        String fistOperandSystem;
        String secondOperandSystem;
        fistOperandSystem = getNumberSystem(firstOperand(input)); //Возвращает систему счисления первого операнда
        secondOperandSystem = getNumberSystem(secondOperand(input));//Возвращает систему счисления второго операнда
        //Если хотя бы один операнд не имеет системы счисления то выбрасывается исключение
        if (fistOperandSystem == "NotNumberSystem" | secondOperandSystem == "NotNumberSystem") {
            throw new Exception();
        }
        //Если операнды имеют развые системы счисления то выбрасывается исключение
        if (fistOperandSystem != secondOperandSystem) {
            throw new Exception();
        }
    }
    static String getNumberSystem (String operand) {
        String result;
        int countRomeNumber;
        String key = new String();
        countRomeNumber = 0;
        result = "NotNumberSystem";
        try {
            int test = Integer.parseInt(operand);
            result = "ArabNumberSystem";//Если не выбросило исключение то это араская система счисления (в пределах тестого задания)
        }
        catch (NumberFormatException e) {
            for (int i = 0; i<operand.length();i++)
            {   key = Character.toString(operand.charAt(i));
                key = key.toUpperCase();
                if (romeNumber.containsKey(key)) {
                    countRomeNumber++;
                }
            }
            if (countRomeNumber == operand.length()) {
                result = "RomeNumberSystem";
            }
        }
        return  result;
    }
    static String firstOperand(String input) {
        String result = new String();
        int indexOperation;
        indexOperation = input.indexOf(getOperation(input));
        result = input.substring(0,indexOperation);
        return result;
    }
    static String secondOperand(String input) {
        String result = new String();
        int indexOperation;
        indexOperation = input.indexOf(getOperation(input));
        result = input.substring(indexOperation+1,input.length());
        return result;
    }
    // Вызывает исключение если числа не от 1 до 10
    static void numberMinMaxValidation(String input) throws Exception {
        String operandsSystem;
        String firstOperand;
        String secondOperand;
        boolean firstNumberRomeValid;
        boolean secondNumberRomeValid;
        final String [] romeNumbers = {"I","II","III","IV","V","VI","VII","VIII","IX","X"};
        final int min = 1;
        final int max = 10;
        firstNumberRomeValid = false;
        secondNumberRomeValid = false;
        operandsSystem = getNumberSystem(firstOperand(input));
        firstOperand = firstOperand(input);
        secondOperand = secondOperand(input);
        //Если операнды в арабской системе счисления, то проверяется соответствуют ли они условию задания от 1 до 10
        if (operandsSystem == "ArabNumberSystem") {
            if (Integer.parseInt(firstOperand) < min | Integer.parseInt(firstOperand) > max) throw new Exception();
            if (Integer.parseInt(secondOperand) < min | Integer.parseInt(secondOperand) > max) throw new Exception();
        }
        //Если операнды в римской системе счисления, то проверяется соответствуют ли они условию задания от 1 до 10
        if (operandsSystem == "RomeNumberSystem") {
            for (String i :
                    romeNumbers) {
                //Проверяем первый операнд
                if (i.equals(firstOperand.toUpperCase())) {
                    firstNumberRomeValid = true;
                }
                //Проверяем второй операнд
                if (i.equals(secondOperand.toUpperCase())) {
                    secondNumberRomeValid = true;
                }
            }
            if ((firstNumberRomeValid == false) |(secondNumberRomeValid == false)) {
                throw new Exception();
            }
        }
    }
    //Вычисление арабских цифр
    static String calcArabNumber (String input, char operator) {
        String result = new String();
        String firstOperand;
        String secondOperand;
        firstOperand = firstOperand(input);
        secondOperand = secondOperand(input);
        switch (operator) {
            case '+': result = Integer.toString( Integer.parseInt(firstOperand) + Integer.parseInt(secondOperand));break;
            case '-': result = Integer.toString( Integer.parseInt(firstOperand) - Integer.parseInt(secondOperand));break;
            case '/': result = Integer.toString( Integer.parseInt(firstOperand) / Integer.parseInt(secondOperand));break;
            case '*': result = Integer.toString( Integer.parseInt(firstOperand) * Integer.parseInt(secondOperand));break;
        }
        return result;
    }
    // Вычисление римских цифр
    static String calcRomeNumber (String input, char operator) throws Exception {
        String result = new String();
        int preResult;
        String firstOperand;
        String secondOperand;
        firstOperand = firstOperand(input);
        secondOperand = secondOperand(input);
        preResult = -1;
        switch (operator) {
            case '+': preResult = romeToArab(firstOperand) + romeToArab(secondOperand);break;
            case '-': preResult = romeToArab(firstOperand) - romeToArab(secondOperand);break;
            case '/': preResult = romeToArab(firstOperand) / romeToArab(secondOperand);break;
            case '*': preResult = romeToArab(firstOperand) * romeToArab(secondOperand);break;
        }
        if (preResult<=0) { //Римских цифры не должны быть меньше либо равны нулю
            throw new Exception();
        }
        else {
            result = arabToRome(preResult);
        }
        return result;
    }
    //Перевод из римских цифр в арабские
    static int romeToArab (String romeOperand) {
        int result;
        int countCharRome;
        result = 0;
        countCharRome = romeOperand.length();
        romeOperand = romeOperand.toUpperCase();
        for (int i = 0 ;i<countCharRome;i++) {
            for (String j :
                    romeNumber.keySet()) {
                if(romeOperand.startsWith(j)) {
                   result+=romeNumber.get(j);
                   if (romeOperand.length()>j.length()) {
                       romeOperand = romeOperand.substring(j.length());
                   } else {
                       romeOperand="";
                   }
                    break;
                }
            }
        }
        return result;

    }
    //Перевод из арабских цифр в римские
    static  String arabToRome (int arabOperand) {
        String result = new String();
        int number;
        for (int i :
                romeNumber.values()) {
            number = (arabOperand/i);
            arabOperand-= number*i;
            for (Map.Entry<String, Integer> e :
                    romeNumber.entrySet()) {
                if (e.getValue() == i) {
                    for (int j = 1;j<=number ;j++) {
                        result+= e.getKey();
                    }
                }
            }
        }
        return result;
    }


}
