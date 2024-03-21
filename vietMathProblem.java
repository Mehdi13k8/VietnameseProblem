import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class vietMathProblem {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java vietMathProblem <string>");
            return;
        }
        String str = args[0];
        int len = str.length();
        if (len < 1) {
            System.out.println("String is empty");
            return;
        } else {
            numberStringFunction(str);
        }
    }

    public static void findGoodCombinations(String enteredNumbers, String[] arrayToString) {
        // Your implementation here
        ArrayList<String> pile = new ArrayList<String>();
        int definitiveResult = 0;
        // copy the arrayToString to avoid modifying the original array

        while (definitiveResult != 66) {
            String[] arrayToStringCpy = Arrays.copyOf(arrayToString, arrayToString.length);
            int[] numberStock = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
            // System.out.println("The result is: " + Arrays.toString(arrayToString));
            for (int i = 0; i < arrayToStringCpy.length; i++) {
                if (arrayToStringCpy[i].equals("X")) {
                    int randomIndex = (int) (Math.random() * numberStock.length);
                    arrayToStringCpy[i] = Integer.toString(numberStock[randomIndex]);
                    // swap the last element with the random index
                    numberStock[randomIndex] = numberStock[numberStock.length - 1];
                    // delete the last element
                    numberStock = Arrays.copyOf(numberStock, numberStock.length - 1);
                }
            }
            // remove the 2 last elements
            arrayToStringCpy = Arrays.copyOf(arrayToStringCpy, arrayToStringCpy.length - 2);

            // (exemple [1, +, 13, *, 0, /, 6, +, 3, +, 12, *, 9, -, 2, -, 11, +, 4, *, 8,
            // /, 5, -, 10])
            
            // create a pile from for rpn algorithm
            // fill the pile for the rpn algorithm
            for (int i = 0; i < arrayToStringCpy.length; i++) {
                if (arrayToStringCpy[i].equals("+") || arrayToStringCpy[i].equals("-")) {
                    // if next element is a number
                    if (arrayToStringCpy[i + 1].matches("\\d+")) {
                        pile.add(arrayToStringCpy[i + 1]);
                        pile.add(arrayToStringCpy[i]);
                        i++;
                    } else {
                        pile.add(arrayToStringCpy[i]);
                    }
                } else if (arrayToStringCpy[i].equals("*") || arrayToStringCpy[i].equals("/")) {
                    // if next element is a number
                    if (arrayToStringCpy[i + 1].matches("\\d+")) {
                        pile.add(arrayToStringCpy[i + 1]);
                        pile.add(arrayToStringCpy[i]);
                        i++;
                    } else {
                        pile.add(arrayToStringCpy[i]);
                    }
                } else {
                    pile.add(arrayToStringCpy[i]);
                }
            }

            // // calculate the result
            for (int i = 0; i < pile.size(); i++) {
                if (pile.get(i).equals("+")) {
                    int result = Integer.parseInt(pile.get(i - 2)) + Integer.parseInt(pile.get(i - 1));
                    pile.set(i - 2, Integer.toString(result));
                    pile.remove(i);
                    pile.remove(i - 1);
                    i = 0;
                } else if (pile.get(i).equals("-")) {
                    int result = Integer.parseInt(pile.get(i - 2)) - Integer.parseInt(pile.get(i - 1));
                    pile.set(i - 2, Integer.toString(result));
                    pile.remove(i);
                    pile.remove(i - 1);
                    i = 0;
                } else if (pile.get(i).equals("*")) {
                    int result = Integer.parseInt(pile.get(i - 2)) * Integer.parseInt(pile.get(i - 1));
                    pile.set(i - 2, Integer.toString(result));
                    pile.remove(i);
                    pile.remove(i - 1);
                    i = 0;
                } else if (pile.get(i).equals("/")) {
                    // add protection for division by 0
                    if (Integer.parseInt(pile.get(i - 1)) == 0) {
                        pile.set(i - 2, "0");
                        pile.remove(i);
                        pile.remove(i - 1);
                        i = 0;
                    } else {
                        int result = Integer.parseInt(pile.get(i - 2)) / Integer.parseInt(pile.get(i - 1));
                        pile.set(i - 2, Integer.toString(result));
                        pile.remove(i);
                        pile.remove(i - 1);
                        i = 0;
                    }
                }
            }
            definitiveResult = Integer.parseInt(pile.get(0));
            // reset the pile
            pile.clear();
        }
        System.out.println("The result is: " + definitiveResult);

        // print pile
        // System.out.println("The pile is: " + Arrays.toString(pile));

        // print the result

        return;
    }

    public static String vietnameseAlgorithm(String enteredNumbers, String[] originalCalculus) {
        // Number stock to use in the algorithm
        // StringBuilder arrayToString = new StringBuilder();

        // for (String s : originalCalculus) {
        // arrayToString.append(s);
        // }

        findGoodCombinations(enteredNumbers, originalCalculus);

        // Return enteredNumbers as per the original C code
        return enteredNumbers;
    }

    public static String numberStringFunction(String enteredNumbers) {
        String[] numberString = { "X", "+", "13", "*", "X", "/", "X", "+",
                "X", "+", "12", "*", "X", "-", "X", "-", "11", "+", "X", "*", "X",
                "/", "X", "-", "10", "=", "66" };

        return vietnameseAlgorithm(enteredNumbers, numberString);
    }
}
