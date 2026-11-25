package bookcatalogue;

import bookcatalogue.util.Sorting;

import java.util.Comparator;

/** The sorting algorithms the user can choose from. */
public enum SortAlgorithm {
    BUBBLE("Bubble sort") {
        @Override
        <T> void sort(T[] a, int size, Comparator<? super T> cmp) {
            Sorting.bubbleSort(a, size, cmp);
        }
    },
    INSERTION("Ταξινόμηση με εισαγωγή (insertion sort)") {
        @Override
        <T> void sort(T[] a, int size, Comparator<? super T> cmp) {
            Sorting.insertionSort(a, size, cmp);
        }
    },
    SELECTION("Ταξινόμηση με επιλογή (selection sort)") {
        @Override
        <T> void sort(T[] a, int size, Comparator<? super T> cmp) {
            Sorting.selectionSort(a, size, cmp);
        }
    },
    QUICK("Γρήγορη ταξινόμηση (quicksort)") {
        @Override
        <T> void sort(T[] a, int size, Comparator<? super T> cmp) {
            Sorting.quickSort(a, size, cmp);
        }
    },
    MERGE("Ταξινόμηση με συγχώνευση (merge sort)") {
        @Override
        <T> void sort(T[] a, int size, Comparator<? super T> cmp) {
            Sorting.mergeSort(a, size, cmp);
        }
    };

    private final String label;

    SortAlgorithm(String label) {
        this.label = label;
    }

    public String label() {
        return label;
    }

    abstract <T> void sort(T[] a, int size, Comparator<? super T> cmp);
}
