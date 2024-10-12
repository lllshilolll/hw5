package org.example;

import java.util.Deque;
import java.util.LinkedList;

import static org.example.ShuntingYardAlgorithm.*;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Требуется реализовать метод, который по входной строке будет вычислять математические выражения.
     * <br/>
     * Операции: +, -, *, / <br/>
     * Функции: sin, cos, sqr, pow <br/>
     * Разделители аргументов в функции: , <br/>
     * Поддержка скобок () для описания аргументов и для группировки операций <br/>
     * Пробел - разделитель токенов, пример валидной строки: "1 + 2 * ( 3 - 4 )" с результатом -1.0 <br/>
     * <br/>
     * sqr(x) = x^2 <br/>
     * pow(x,y) = x^y
     */
    //pop - Возвращает с удалением элемент из начала очереди
    //push - обавляет элемент в самое начало очереди
    //peek - возвращает первый элемент очереди или значение null, если она пуста
    double calculate(String expr) {
        String poliz = translate(expr);
        //разбить по пробелам
        String[] input = poliz.split(" ");
        Deque<Double> stack = new LinkedList<>();
        for (String cur : input) {
            //пока число кладем в стек
            if (isNumber(cur)) {
                stack.push(Integer.valueOf(cur).doubleValue());
            }

            if (FUNCTION.contains(cur)) {
                stack.push(doFunction(stack, cur));
            }
            //если оператор берем 2 числа из стека
            if (OPERATORS.contains(cur)) {
                if (!stack.isEmpty() && stack.size() >= 2) {
                    var a = stack.pop();
                    var b = stack.pop();
                    //кладем результат обратно в стек
                    stack.push(doOperation(a, b, cur));
                }else{
                    throw new IllegalArgumentException("Incorrect expression");
                }
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Incorrect expression");
        }
        return stack.pop();
    }

    static double doFunction(Deque<Double> stack, String func) {
        switch (func) {
            case "sin":
                return Math.sin(Math.toRadians(stack.pop()));
            case "cos":
                return Math.cos(Math.toRadians(stack.pop()));
            case "sqr":
                return Math.pow(stack.pop(), 2);
            case "pow": {
                var a = stack.pop();
                var b = stack.pop();
                return Math.pow(b, a);
            }
            default:
                return 0;
        }
    }

    static double doOperation(Double a, Double b, String operation) {
        switch (operation) {
            case "+":
                return b + a;
            case "-":
                return b - a;
            case "*":
                return b * a;
            case "/":
                return b / a;
            default:
                return 0;
        }
    }
}
