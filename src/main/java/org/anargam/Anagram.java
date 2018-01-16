package org.anargam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Anagram {
    private Map<WordHash, List<String>> dictionary;

    public Anagram(String filepath) {
        dictionary = new HashMap<>();
        try {
            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    Anagram.class.getResourceAsStream(filepath), "Cp866"));

            String line = reader.readLine();
            while (line != null) {
                WordHash hash = new WordHash(line);

                if (hash.valid()) {
                    List<String> words = dictionary.get(hash);
                    if (words == null) {
                        words = new ArrayList<>();
                        dictionary.put(hash, words);
                    }

                    words.add(line);
                }

                line = reader.readLine();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Collection<String> searchFor(final String searchingWord) {
        WordHash hash = new WordHash(searchingWord);
        return hash.valid() ? dictionary.get(hash) : null;
    }

    private static class WordHash {
        private static final char FIRST_CHAR = 'а';
        private static final char[] REPLACING_PAIR = {'ё', 'е'};
        private static final int ALPHABET_SIZE = 33;

        private byte[] frequencies = new byte[ALPHABET_SIZE];
        private boolean isValid;


        WordHash(String word) {
            char[] sequence = word
                    .trim()
                    .toLowerCase()
                    .replace(REPLACING_PAIR[0], REPLACING_PAIR[1])
                    .toCharArray();

            for (char character : sequence) {
                int index = character - FIRST_CHAR;
                if (index < 0 || index >= ALPHABET_SIZE) {
                    isValid = false;
                    return;
                }

                frequencies[index]++;
            }

            isValid = true;
        }

        boolean valid() {
            return isValid;
        }


        @Override
        public int hashCode() {
            return Arrays.hashCode(frequencies);
        }

        @Override
        public boolean equals(Object obj) {
            return obj.getClass() == WordHash.class && Arrays.equals(frequencies, ((WordHash) obj).frequencies);
        }
    }
}
