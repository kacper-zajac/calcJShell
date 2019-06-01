package org.openjfx.calc;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;

import java.util.List;

public class Model {
    JShell jshell;

    public Model() {
        jshell = JShell.create();
    }
    
  private boolean isInteger(String value) {
	  Double doubleValue = Double.parseDouble(value);
	  Integer integerValue = doubleValue.intValue();
	  return (doubleValue.equals(integerValue.doubleValue()));
  }

    private String calculateJShell(String expression) throws ArithmeticException {
        List<SnippetEvent> events = jshell.eval(expression);

        if (events.isEmpty()) {
            throw new ArithmeticException("Expression is invalid.");
        }

        return events.get(0).value();
    }

    public String calculate(String first, String operator, String second) throws ArithmeticException {

        if (!operator.equals("^")) {
            if (!first.contains(".")) first += ".0";
            if (Double.parseDouble(second) == 0.0)
                if (operator.equals("%") || operator.equals("/"))
                    throw new ArithmeticException("You cannot divide by zero!");
            return calculateJShell(first + operator + second);
        }
        if(!isInteger(first)) throw new ArithmeticException("Power value must be an integer!");
        String expression = first;
        for (int i = Integer.parseInt(second) - 1; i > 0; i--)
            expression += "*" + first;
        return calculateJShell(expression);
    }

    public String calculate(String number) {
        if (!isInteger(number))
            throw new NumberFormatException("You need an integer to calculate factorial.");

        Double value1 = Double.parseDouble(number);
        Integer value = value1.intValue();
        if (value < 0)
            throw new NumberFormatException("You need a non-negative number to calculate factorial.");

        if (value == 0) return "1";

        String expression = value.toString();
        for (int numb = value - 1; numb > 0; numb--) {
            expression += "*" + numb;
        }

        return calculateJShell(expression);
    }
}
