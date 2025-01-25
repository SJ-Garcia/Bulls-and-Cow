package bullscows;

import java.util.Random;
import java.util.Scanner;

public class BullsAndCow {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String secretCode, guess;
        int length, symbols, bulls, cows;
        int turns = 1;

        System.out.println("Input the length of the secret code:");
        guess = scanner.nextLine();
        try {
            length = Integer.parseInt(guess);
        } catch (Exception e) {
            System.out.println("Error: \"" + guess + "\" isn't a valid number.");
            return;
        }


        System.out.println("Input the number of possible symbols in the code:");
        guess = scanner.nextLine();
        try {
            symbols = Integer.parseInt(guess);
        } catch (Exception e) {
            System.out.println("Error: \"" + guess + "\" isn't a valid number.");
            return;
        }

        secretCode = generateSecretCode(length, symbols);
        while (secretCode != null) {
            System.out.println("Turn " + turns + ". Answer:");
            guess = scanner.nextLine();
            bulls = countBulls(guess, secretCode);

            if (bulls == length) {
                printGrade( length, 0);
                System.out.println("Congratulations! You guessed the secret code.");
                break;
            } else {
                cows = countCows(guess, secretCode);
                printGrade(bulls, cows);
            }

            turns++;
        }

        scanner.close();
    }

    static void printGrade(int bulls, int cows) {
        StringBuilder grade = new StringBuilder();

        grade.append("Grade: ");
        if (cows >= 1 || bulls >= 1) {
            if (cows > 0) {
                grade.append(cows).append(cows > 1 ? " cows" : " cow");
            }

            if (cows > 0 && bulls > 0) {
                grade.append(" and ");
            }

            if (bulls > 0) {
                grade.append(bulls).append(bulls > 1 ? " bulls" : " bull");
            }
        } else {
            grade.append("None");
        }

        System.out.println(grade);
    }

    static int countBulls(String guess, String secretCode) {
        int bulls = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                bulls++;
            }
        }
        return bulls;
    }

    static int countCows(String guess, String secretCode) {
        int cows = 0;
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) != secretCode.charAt(i)
                    && secretCode.contains(guess.substring(i, i + 1))) {
                cows++;
            }
        }
        return cows;
    }

    static String generateSecretCode(int length, int symbols) {
        if (symbols < 1 || length < 1 || symbols > 36 || length > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return null;
        }
        if (length > symbols) {
            System.out.println("Error: it's not possible to generate a code with a length of "
                    + length + " with " + symbols + " unique symbols.");
            return null;
        }

        String[] alphaNumerals = alphaNumerals(length, symbols);
        StringBuilder secretCode = new StringBuilder();
        while (secretCode.length() < length) {
            String randomStr = randomPicker(alphaNumerals);
            if (secretCode.indexOf(randomStr) == -1) {
                secretCode.append(randomStr);
            }
        }

        StringBuilder output = new StringBuilder("The secret is prepared: ")
                .append("*".repeat(length))
                .append(" (0-").append(symbols < 10 ? alphaNumerals[symbols - 1] : "9");
        if (symbols > 10) {
            output.append(", ").append(symbols == 11 ? "a" : "a-" + alphaNumerals[symbols - 1]);
        }
        output.append(").");

        System.out.println(output);
        System.out.println("Okay, let's start a game!");
        return secretCode.toString();
    }

    static String randomPicker(String[] alphaNumerals) {
        Random random = new Random();
        return alphaNumerals[random.nextInt(alphaNumerals.length)];
    }

    static String[] alphaNumerals(int length, int symbols) {
        String[] alphaNumerals = new String[symbols];
        for (int i = 0; i < alphaNumerals.length; i++) {
            alphaNumerals[i] = i < 10
                    ? String.valueOf((char) ('0' + i))
                    : String.valueOf((char) ('a' + (i - 10)));
        }
        return alphaNumerals;
    }
}
