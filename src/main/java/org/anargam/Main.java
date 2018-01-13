package org.anargam;

import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

public class Main {
    private static final String REQUEST_MESSAGE = "Найти анаграммы для слова:";

    private static Scanner scanner;


    public static void main(String[] args) {
        Anagram anagram = new Anagram("/words.txt");

        scanner = new Scanner(System.in);
        String inputLine = askForNextWord();

        while (!Arrays.asList("exit", "quit", "q").contains(inputLine)) {
            Collection<String> words = anagram.searchFor(inputLine);
            if (words != null) {
                int index = 0;
                for (String word : words) {
                    System.out.println(String.format("%d. %s", ++index, word));
                }
            }

            inputLine = askForNextWord();
        }
    }

    private static String askForNextWord() {
        System.out.println(REQUEST_MESSAGE);
        return scanner.nextLine();
    }
}
