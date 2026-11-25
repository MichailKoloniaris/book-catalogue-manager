package bookcatalogue.util;

import java.util.Comparator;

/**
 * Classic comparison sorts, implemented by hand for the first {@code size}
 * elements of an array. All methods sort in place and are generic over the
 * element type, so the same code sorts books by author, ISBN, year or price.
 */
public final class Sorting {

    private Sorting() {
    }

    /** Bubble sort with early exit when a pass makes no swaps. O(n^2), stable. */
    public static <T> void bubbleSort(T[] a, int size, Comparator<? super T> cmp) {
        for (int pass = 1; pass < size; pass++) {
            boolean swapped = false;
            for (int j = 0; j < size - pass; j++) {
                if (cmp.compare(a[j], a[j + 1]) > 0) {
                    swap(a, j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) {
                return;
            }
        }
    }

    /** Insertion sort. O(n^2), stable, fast on nearly sorted input. */
    public static <T> void insertionSort(T[] a, int size, Comparator<? super T> cmp) {
        for (int i = 1; i < size; i++) {
            T current = a[i];
            int pos = i;
            while (pos > 0 && cmp.compare(a[pos - 1], current) > 0) {
                a[pos] = a[pos - 1];
                pos--;
            }
            a[pos] = current;
        }
    }

    /** Selection sort. O(n^2), not stable, minimal number of swaps. */
    public static <T> void selectionSort(T[] a, int size, Comparator<? super T> cmp) {
        for (int i = 0; i < size - 1; i++) {
            int min = i;
            for (int k = i + 1; k < size; k++) {
                if (cmp.compare(a[k], a[min]) < 0) {
                    min = k;
                }
            }
            swap(a, i, min);
        }
    }

    /** Quicksort with Lomuto partitioning. O(n log n) on average, not stable. */
    public static <T> void quickSort(T[] a, int size, Comparator<? super T> cmp) {
        quickSort(a, 0, size - 1, cmp);
    }

    private static <T> void quickSort(T[] a, int low, int high, Comparator<? super T> cmp) {
        if (low < high) {
            int p = partition(a, low, high, cmp);
            quickSort(a, low, p - 1, cmp);
            quickSort(a, p + 1, high, cmp);
        }
    }

    private static <T> int partition(T[] a, int low, int high, Comparator<? super T> cmp) {
        T pivot = a[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (cmp.compare(a[j], pivot) < 0) {
                swap(a, ++i, j);
            }
        }
        swap(a, i + 1, high);
        return i + 1;
    }

    /** Top-down merge sort. O(n log n), stable, uses O(n) extra space. */
    public static <T> void mergeSort(T[] a, int size, Comparator<? super T> cmp) {
        if (size > 1) {
            mergeSort(a, 0, size - 1, cmp);
        }
    }

    private static <T> void mergeSort(T[] a, int first, int last, Comparator<? super T> cmp) {
        if (first >= last) {
            return;
        }
        int mid = (first + last) >>> 1;
        mergeSort(a, first, mid, cmp);
        mergeSort(a, mid + 1, last, cmp);
        merge(a, first, mid, last, cmp);
    }

    @SuppressWarnings("unchecked")
    private static <T> void merge(T[] a, int first, int mid, int last, Comparator<? super T> cmp) {
        Object[] buffer = new Object[last - first + 1];
        int left = first;
        int right = mid + 1;
        int k = 0;
        while (left <= mid && right <= last) {
            // "<=" keeps equal elements in their original order (stability).
            buffer[k++] = cmp.compare(a[left], a[right]) <= 0 ? a[left++] : a[right++];
        }
        while (left <= mid) {
            buffer[k++] = a[left++];
        }
        while (right <= last) {
            buffer[k++] = a[right++];
        }
        for (k = 0; k < buffer.length; k++) {
            a[first + k] = (T) buffer[k];
        }
    }

    private static <T> void swap(T[] a, int i, int j) {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}
