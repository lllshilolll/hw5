package org.example;

import java.util.*;

import static java.util.Arrays.asList;

public class ShuntingYardAlgorithm {

    static List<String> OPERATORS = asList("+", "-", "*", "/");
    static List<String> FUNCTION = asList("sin", "cos", "sqr", "pow");
    static String SEPARATOR = ",";

    /**
     * Алгоритм сортировочной станции
     * без функций
     *
     * @param inputString
     * @return
     * @see <a href="https://ru.wikipedia.org/wiki/Алгоритм_сортировочной_станции">https://ru.wikipedia.org/wiki/Алгоритм_сортировочной_станции</a>
     */
    static String translate(String inputString) {
        String[] input = inputString.split(" ");
        List<String> output = new ArrayList<>();
        Deque<String> stack = new LinkedList<>();
        for (String cur : input) {
            //Если токен — число, то добавить его в очередь вывода.
            if (isNumber(cur)) {
                output.add(cur);
            }
            if (cur.length() > 1) {
                //Если токен — функция, то поместить его в стек.
                if (FUNCTION.contains(cur)) {
                    stack.push(cur);
                }
            }
            //Если токен — разделитель аргументов функции (например, запятая)
            if (cur.equals(SEPARATOR)) {
                //Пока токен на вершине стека не открывающая скобка:
                //Переложить оператор из стека в выходную очередь.
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.add(stack.pop());
                }
                //Если стек закончился до того, как был встречен токен открывающая скобка,
                // то в выражении пропущен разделитель аргументов функции (запятая),
                // либо пропущена открывающая скобка.
            }

            //Если токен — оператор op1, то:
            if (OPERATORS.contains(cur)) {
                //Пока присутствует на вершине стека токен оператор op2, чей приоритет выше или равен приоритету op1,
                // и при равенстве приоритетов op1 является левоассоциативным:
                while (!stack.isEmpty() && !stack.peek().equals("(") && priority(stack.peek()) >= priority(cur)) {
                    //Переложить op2 из стека в выходную очередь;
                    output.add(stack.pop());
                }
                //Положить op1 в стек.
                stack.push(cur);
            }
            //Если токен — открывающая скобка, то положить его в стек.
            if (cur.equals("(")) {
                stack.push(cur);
            }
            //Если токен — закрывающая скобка:
            if (cur.equals(")")) {
                //Пока токен на вершине стека не открывающая скобка
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    //Переложить оператор из стека в выходную очередь.
                    output.add(stack.pop());
                }
                //Если стек закончился до того, как был встречен токен открывающая скобка, то в выражении пропущена скобка.
                if (stack.isEmpty()) {
                    throw new IllegalArgumentException("Missing '(' in expression");
                }
                //Выкинуть открывающую скобку из стека, но не добавлять в очередь вывода.
                stack.pop();
            }
        }
        //Если больше не осталось токенов на входе:
        //Пока есть токены операторы в стеке:
        while (!stack.isEmpty()) {
            //Если токен оператор на вершине стека — открывающая скобка, то в выражении пропущена скобка.
            if (stack.peek().equals("(")) {
                throw new IllegalArgumentException("Missing ')' in expression");
            }
            //Переложить оператор из стека в выходную очередь.
            output.add(stack.pop());
        }


        return String.join(" ", output);
    }

    static boolean isNumber(String number) {
        try {
            Integer.valueOf(number);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static int priority(String operator) {

        return (operator.equals("+") || operator.equals("-")) ? 1 : 2;
    }
}