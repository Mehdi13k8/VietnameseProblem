import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.io.FileWriter;

public class vietMathProblem {
    static public class resultMathProblem {
        int[] result;
        String[] calculation;
        long duration;

        public resultMathProblem(int[] result, String[] calculation, long duration) {
            this.result = result;
            this.calculation = calculation;
            this.duration = duration;
        }

        public int[] getResult() {
            return result;
        }

        public void setResult(int[] result) {
            this.result = result;
        }

        public String[] getCalculation() {
            return calculation;
        }

        public void setCalculation(String[] calculation) {
            this.calculation = calculation;
        }

    }

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

        // number of possibilities for the X and numberStock from arrayToString
        int[] numberStock = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        // number of X in the arrayToString
        int numberOfX = 0;
        for (int i = 0; i < arrayToString.length; i++) {
            if (arrayToString[i].equals("X")) {
                numberOfX++;
            }
        }
        int possibilities = numberStock.length * numberOfX;

        // store the result of the calculation in an array, with timer to find the best
        // result of the calculation and store also the calculation string (int[],
        // String[], long[])
        ArrayList<resultMathProblem> results = new ArrayList<resultMathProblem>();

        // for (int j = 0; j < possibilities; j++) {
        while (results.size() < possibilities) {
            int j = 0;
            // timer to find the best result
            long startTime = System.nanoTime();
            int[] numberStockCpy = Arrays.copyOf(numberStock, numberStock.length);
            String[] arrayToStringCpy = Arrays.copyOf(arrayToString, arrayToString.length);
            for (int i = 0; i < arrayToStringCpy.length; i++) {
                if (arrayToStringCpy[i].equals("X")) {
                    int randomIndex = (int) (Math.random() * numberStockCpy.length);
                    arrayToStringCpy[i] = Integer.toString(numberStockCpy[randomIndex]);
                    // swap the last element with the random index
                    numberStockCpy[randomIndex] = numberStockCpy[numberStockCpy.length - 1];
                    // delete the last element
                    numberStockCpy = Arrays.copyOf(numberStockCpy, numberStockCpy.length - 1);
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
            // timer to find the best result
            long endTime = System.nanoTime();
            long duration = (endTime - startTime);
            // check if result is already in the found results array of objects so increment
            // j
            boolean found = false;
            for (int i = 0; i < results.size(); i++) {
                if (Arrays.toString(results.get(i).getCalculation()) == Arrays.toString(arrayToStringCpy)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                continue;
            } else {
                results.add(new resultMathProblem(new int[] { definitiveResult }, arrayToStringCpy, duration));
            }
            // save all the results in a file
            try {
                // write the results in a file
                FileWriter myWriter = new FileWriter("results.txt");
                for (int i = 0; i < results.size(); i++) {
                    myWriter.write("Result: " + results.get(i).getResult()[0] + " Calculation: "
                            + Arrays.toString(results.get(i).getCalculation()) + " Duration: " + results.get(i).duration
                            + "\n");
                }
                myWriter.close();
            } catch (Exception e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }

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
