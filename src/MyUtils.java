import java.util.Comparator;

public class MyUtils {
    public static Book[] seqSearch(Book[] arr, int target,int bookArraySize) {
        Book[] booksFound = new Book[arr.length];
        int counter = 0;
        for (int i = 0; i < bookArraySize; i++) {
            if (arr[i].getYear() == target) {
                booksFound[counter] = arr[i];
                counter++;
            }
        }
        return booksFound;
    }

    public static int seqSearch(Book[] arr, String target,int bookArraySize) {
        for (int i = 0; i <= bookArraySize; i++) {
            if (arr[i].getISBN().equals(target)) {
                return i;
            }
        }
        return -1;
    }


    public static Book[] binSearch(Book[] arr, int target, int bookArraySize) {
        Book[] booksFound = new Book[arr.length];
        int high = bookArraySize;
        int low = 0;
        int mid = -1;
        while (low <= high) {
            mid = low + (high - low) / 2;
            if (arr[mid].getYear() == target){
                break;
            }
            if (arr[mid].getYear() < target) {
                low = mid + 1;
            }
            else
                high = mid - 1;
        }

        if (mid == -1) {
            return booksFound;
        }

        int found = 1;
        booksFound[0] = arr[mid];
        for (int i = mid + 1; i <= bookArraySize; i++) {
            if (arr[i].getYear() != target)
                break;
            booksFound[found] = arr[i];
            found++;
        }

        for (int i = mid - 1; i >= 0; i--) {
            if (arr[i].getYear() != target)
                break;
            booksFound[found] = arr[i];
            found++;
        }

        return booksFound;
    }
    public static int binSearch(Book[] arr, String target, int bookArraySize)
    {
        int low = 0, high = bookArraySize;

        while (low <= high) {

            int mid = low + (high - low) / 2;

            int res = target.compareTo(arr[mid].getISBN());

            if (res == 0)
                return mid;

            if (res > 0)
                low = mid + 1;

            else
                high = mid - 1;
        }

        return -1;
    }
    public static void valueSearch(Book[] arr, double from, double to) {
        boolean found = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                break;
            }
            if (arr[i].getPrice() >= from && arr[i].getPrice() <= to) {
                System.out.println(arr[i].toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Δεν βρέθηκε βιβλείο");
        }
    }

    public static void bubbleSort(Book[] arr, int bookArraySize, Comparator<Book> comparator) {
        int i, j;
        Book temp;
        boolean flag;
        for (i = 1; i < bookArraySize; i++) {
            flag = true;
            for (j = 0; j < bookArraySize - i; j++)
                if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = false;
                }
            if (flag) return;
        }
    }

    public static void insertSort (Book[] arr, int bookArraySize, Comparator<Book> comparator) {
        for (int i=1; i < bookArraySize; i++) {
            Book current = arr[i];
            int position=i;
            // shift larger values to the right
            while (position>0 && comparator.compare(arr[position-1],current) > 0) {
                arr[position] = arr[position-1];
                position--;
            }
            arr[position] = current;
        }
    } // method sort

    public static void selectionSort(Book[] arr,int bookArraySize, Comparator<Book> comparator) {
        int min;
        Book temp;
        for (int i = 0; i < bookArraySize; i++) {
            min = i;
            for (int k = i + 1; k < bookArraySize; k++) {
                if (comparator.compare(arr[k], arr[min]) < 0) {
                    min = k;
                }
            }
            temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
    }

    public static void quickSort(Book[] arr, int low, int high, Comparator<Book> comparator) {
        if (low < high) {
            int pi = partition(arr, low, high,comparator);


            quickSort(arr, low, pi - 1,comparator);
            quickSort(arr, pi + 1, high,comparator);
        }
    }


    public static int partition(Book[] arr, int low, int high, Comparator<Book> comparator) {
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (comparator.compare(arr[j], arr[high]) < 0) {
                i++;


                Book temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }


        Book temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;


        return i + 1;
    }

    // mergeSort ------------------------------------------------------

    public static void mergeSort (Book[] arr,int bookArraySize, Comparator<Book> comparator) {
        mSort (arr, 0, bookArraySize - 1,comparator);
    }
    public static void mSort (Book[] arr, int f, int l,Comparator<Book> comparator) {
        if (f==l) return;
        int mid=(f+l)/2; // Μεσαία θέση του πίνακα
        mSort (arr, f, mid,comparator); // Αναδρομική κλήση για το 1ο μισό
        mSort (arr, mid+1, l,comparator); // Αναδρομική κλήση για το 2ο μισό
        merge(arr,f,l,mid,comparator);// Συγχώνευση των δύο υπο-πινάκων
    }
    public static void merge (Book[] arr, int f, int l, int mid,Comparator<Book> comparator) {
        int n = l-f+1; // size of the range to be merged

        // temporary array b
        Book [] b = new Book[n];
        int i1 = f, i2 = mid+1;
        int j = 0; // next open position in b
        // merge both halves into a temporary array b
        while (i1 <= mid && i2 <= l) {
            if (comparator.compare(arr[i1], arr[i2]) < 0) {
                b[j] = arr[i1];
                i1++;
            }
            else {
                b[j] = arr[i2];
                i2++;
            }
            j++;
        }
        // Copy the rest elements of the first half of the array
        while (i1 <= mid) {
            b[j] = arr[i1];
            i1++;
            j++;
        }
        // Copy the rest elements of the second half of the array
        while (i2<=l) {
            b[j] = arr[i2];
            i2++;
            j++;
        }
        // copy back from the temporary array
        for (j=0; j<n; j++)
            arr[f+j] = b[j];
    } // method merge
}
