package bookcatalogue.model;

/**
 * ISBN-10 and ISBN-13 validation using the official check digit rules.
 * Hyphens and spaces are ignored, and ISBN-10 may end in 'X' (value 10).
 */
public final class Isbn {

    private Isbn() {
    }

    /** Removes hyphens/spaces and upper-cases a trailing 'x'. */
    public static String normalize(String isbn) {
        return isbn == null ? "" : isbn.replaceAll("[\\s-]", "").toUpperCase();
    }

    public static boolean isValid(String isbn) {
        String s = normalize(isbn);
        if (s.length() == 10) {
            return isValidIsbn10(s);
        }
        if (s.length() == 13) {
            return isValidIsbn13(s);
        }
        return false;
    }

    private static boolean isValidIsbn10(String s) {
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            char c = s.charAt(i);
            int digit;
            if (Character.isDigit(c)) {
                digit = c - '0';
            } else if (c == 'X' && i == 9) {
                digit = 10;
            } else {
                return false;
            }
            sum += digit * (10 - i);
        }
        return sum % 11 == 0;
    }

    private static boolean isValidIsbn13(String s) {
        int sum = 0;
        for (int i = 0; i < 13; i++) {
            char c = s.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
            int digit = c - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        return sum % 10 == 0;
    }
}
