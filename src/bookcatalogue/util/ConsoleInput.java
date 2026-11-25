package bookcatalogue.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

/**
 * Reads typed values from standard input, re-prompting until the input is valid.
 * A single shared reader is used so that buffered input is never lost between calls.
 */
public final class ConsoleInput {

    /** Thrown when standard input is closed (end of file). */
    public static class EndOfInputException extends RuntimeException {
        public EndOfInputException() {
            super("End of input");
        }
    }

    private static final BufferedReader IN =
            new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

    private ConsoleInput() {
    }

    public static String readLine(String prompt) {
        System.out.print(prompt);
        try {
            String line = IN.readLine();
            if (line == null) {
                throw new EndOfInputException();
            }
            return line.trim();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static String readNonEmpty(String prompt) {
        while (true) {
            String s = readLine(prompt);
            if (!s.isEmpty()) {
                return s;
            }
            System.out.println("  Το πεδίο δεν μπορεί να είναι κενό.");
        }
    }

    public static int readInt(String prompt) {
        while (true) {
            String s = readLine(prompt);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("  Δώστε έναν ακέραιο αριθμό.");
            }
        }
    }

    public static int readInt(String prompt, int min, int max) {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println("  Δώστε τιμή από " + min + " έως " + max + ".");
        }
    }

    public static short readShort(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value >= Short.MIN_VALUE && value <= Short.MAX_VALUE) {
                return (short) value;
            }
            System.out.println("  Η τιμή είναι εκτός ορίων.");
        }
    }

    public static double readDouble(String prompt) {
        while (true) {
            String s = readLine(prompt).replace(',', '.');
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("  Δώστε έναν αριθμό (π.χ. 24.90).");
            }
        }
    }
}
