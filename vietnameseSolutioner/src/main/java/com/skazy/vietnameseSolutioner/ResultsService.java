package com.skazy.vietnameseSolutioner;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResultsService {
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

    @Autowired
    private ResultsRepository repository;

    public void performCalculationAndSave() {
        // delete all the results in the database
        repository.deleteAll();
        String[] arrayToString = { "X", "+", "13", "*", "X", "/", "X", "+",
                "X", "+", "12", "*", "X", "-", "X", "-", "11", "+", "X", "*", "X",
                "/", "X", "-", "10", "=", "66" };

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
            // save the results in the database
            for (int i = 0; i < results.size(); i++) {
                repository.save(
                        new Results(Arrays.toString(results.get(i).getCalculation()), results.get(i).getResult()[0],
                                (int) results.get(i).duration, true));
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // check if the result is good of calculation
    public String checkResult(Long id) {
        Results result = repository.findById(id).orElseThrow(() -> new Error("Result not found"));
        // remove [, ] and spaces from the string and split the string
        String[] arrayToString = result.getCalculation().replaceAll("\\[", "").replaceAll("\\]", "").replaceAll(" ", "")
                .split(",");
        int definitiveResult = 0;
        ArrayList<String> pile = new ArrayList<String>();

        // create a pile from for rpn algorithm
        // fill the pile for the rpn algorithm
        for (int i = 0; i < arrayToString.length; i++) {
            if (arrayToString[i].equals("+") || arrayToString[i].equals("-")) {
                // if next element is a number
                if (arrayToString[i + 1].matches("\\d+")) {
                    pile.add(arrayToString[i + 1]);
                    pile.add(arrayToString[i]);
                    i++;
                } else {
                    pile.add(arrayToString[i]);
                }
            } else if (arrayToString[i].equals("*") || arrayToString[i].equals("/")) {
                // if next element is a number
                if (arrayToString[i + 1].matches("\\d+")) {
                    pile.add(arrayToString[i + 1]);
                    pile.add(arrayToString[i]);
                    i++;
                } else {
                    pile.add(arrayToString[i]);
                }
            } else {
                pile.add(arrayToString[i]);
            }
        }
        System.out.println(pile);

        // // calculate the result
        for (int i = 0; i < pile.size(); i++) {
            if (pile.get(i).equals("+")) {
                int resultVal = Integer.parseInt(pile.get(i - 2)) + Integer.parseInt(pile.get(i - 1));
                pile.set(i - 2, Integer.toString(resultVal));
                pile.remove(i);
                pile.remove(i - 1);
                i = 0;
            } else if (pile.get(i).equals("-")) {
                int resultVal = Integer.parseInt(pile.get(i - 2)) - Integer.parseInt(pile.get(i - 1));
                pile.set(i - 2, Integer.toString(resultVal));
                pile.remove(i);
                pile.remove(i - 1);
                i = 0;
            } else if (pile.get(i).equals("*")) {
                int resultVal = Integer.parseInt(pile.get(i - 2)) * Integer.parseInt(pile.get(i - 1));
                pile.set(i - 2, Integer.toString(resultVal));
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
                    int resultVal = Integer.parseInt(pile.get(i - 2)) / Integer.parseInt(pile.get(i - 1));
                    pile.set(i - 2, Integer.toString(resultVal));
                    pile.remove(i);
                    pile.remove(i - 1);
                    i = 0;
                }
            }
        }
        definitiveResult = Integer.parseInt(pile.get(0));
        // reset the pile
        pile.clear();
        System.out.println("The definitive result is: " + definitiveResult + " The result is: " + result.getResult());
        if (definitiveResult == result.getResult()) {
            // set the result to true
            result.setIsCorrect(true);
            repository.save(result);
            return "The result is correct";
        } else {
            // set the result to false
            result.setIsCorrect(false);
            repository.save(result);
            return "The result is incorrect";
        }
    }
}
