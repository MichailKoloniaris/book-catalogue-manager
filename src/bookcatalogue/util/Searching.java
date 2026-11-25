package bookcatalogue.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Sequential and binary search over the first {@code size} elements of an array,
 * matching on a key extracted from each element (e.g. ISBN or publication year).
 */
public final class Searching {

    private Searching() {
    }

    /** Index of the first element whose key equals {@code target}, or -1. O(n). */
    public static <T, K> int sequentialSearch(T[] a, int size, Function<T, K> key, K target) {
        for (int i = 0; i < size; i++) {
            if (key.apply(a[i]).equals(target)) {
                return i;
            }
        }
        return -1;
    }

    /** All elements whose key equals {@code target}, in array order. O(n). */
    public static <T, K> List<T> sequentialSearchAll(T[] a, int size, Function<T, K> key, K target) {
        List<T> found = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            if (key.apply(a[i]).equals(target)) {
                found.add(a[i]);
            }
        }
        return found;
    }

    /**
     * Index of an element whose key equals {@code target}, or -1. O(log n).
     * The array must already be sorted by the same key.
     */
    public static <T, K extends Comparable<? super K>> int binarySearch(
            T[] a, int size, Function<T, K> key, K target) {
        int low = 0;
        int high = size - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            int cmp = key.apply(a[mid]).compareTo(target);
            if (cmp == 0) {
                return mid;
            }
            if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    /**
     * All elements whose key equals {@code target}. Finds one match with binary
     * search, then expands left and right over the neighbouring equal keys.
     * The array must already be sorted by the same key.
     */
    public static <T, K extends Comparable<? super K>> List<T> binarySearchAll(
            T[] a, int size, Function<T, K> key, K target) {
        List<T> found = new ArrayList<>();
        int hit = binarySearch(a, size, key, target);
        if (hit == -1) {
            return found;
        }
        int from = hit;
        while (from > 0 && key.apply(a[from - 1]).equals(target)) {
            from--;
        }
        int to = hit;
        while (to < size - 1 && key.apply(a[to + 1]).equals(target)) {
            to++;
        }
        for (int i = from; i <= to; i++) {
            found.add(a[i]);
        }
        return found;
    }
}
