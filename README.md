# Book Catalogue Manager

![Build](https://github.com/MichailKoloniaris/book-catalogue-manager/actions/workflows/build.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17%2B-orange)

A console application for a small bookstore: keep a catalogue of books, search and sort it
with hand-written algorithms, and record the day's sales.

Written in Java with no external libraries, as an optional assignment for the
**Object-Oriented Programming** course (2nd semester) at the International Hellenic University,
Department of Information and Electronic Engineering. The user interface is in Greek.

## Features

- **Catalogue of three book types**: scientific books (with scientific area), dictionaries
  (with number of entries) and reading books, each with copies in stock.
- **Input validation**: ISBN-10 and ISBN-13 check digit validation (hyphens allowed, `X` for
  ISBN-10), year 1500 to 2200, price 0 to 1000 €, duplicate ISBNs rejected. Invalid input is
  asked for again instead of crashing the program.
- **Sorting** by author, ISBN, year or price, with a choice of five algorithms:
  bubble, insertion, selection, quicksort and merge sort.
- **Searching** by ISBN or year. The catalogue remembers which field it is sorted by and
  switches from sequential to binary search automatically when it can.
- **Price range filter** and full catalogue listing.
- **Daily sales**: sell copies (stock is reduced and cannot go below zero), total value per
  book type and overall, best-seller of the day, and a sales report.

## Example

```text
$ java -jar book-catalogue-manager.jar --demo
Φορτώθηκαν 6 βιβλία επίδειξης.

=== Book Catalogue Manager ===
1. Εισαγωγή νέου βιβλίου
2. Επεξεργασία καταχωρημένων βιβλίων
3. Πωλήσεις
4. Έξοδος
Επιλογή (1-4): 2
...
Ταξινόμηση κατά "Έτος κυκλοφορίας" με Ταξινόμηση με συγχώνευση (merge sort):
  [Ανάγνωσης] "1984" - George Orwell | ISBN 9780451524935 | Signet Classics, 328 σελ., 1961 | 8.50€ | 5 αντίτυπα
  [Επιστημονικό] "The C Programming Language" - Brian W. Kernighan | ISBN 9780131103627 | Prentice Hall, 272 σελ., 1988 | 39.00€ | 3 αντίτυπα | Περιοχή: Προγραμματισμός
  [Λεξικό] "Λεξικό της Κοινής Νεοελληνικής" - Ινστιτούτο Νεοελληνικών Σπουδών | ISBN 9789600316421 | ΙΝΣ, 1520 σελ., 1998 | 55.00€ | 2 αντίτυπα | Λήμματα: 45000
  ...
Αναζήτηση με: 1. ISBN  2. Έτος κυκλοφορίας  3. Επιστροφή
Επιλογή (1-3): 2
Έτος: 1961
(δυαδική αναζήτηση)
  [Ανάγνωσης] "1984" - George Orwell | ISBN 9780451524935 | ...
...
ISBN            Είδος          Τίτλος                         Αντίτυπα
----------------------------------------------------------------------
9780134685991   Επιστημονικό   Effective Java                 2
9780451524935   Ανάγνωσης      1984                           3

Αξία πωλήσεων (Επιστημονικό): 91.00€
Αξία πωλήσεων (Λεξικό): 0.00€
Αξία πωλήσεων (Ανάγνωσης): 25.50€
Συνολική αξία πωλήσεων ημέρας: 116.50€
```

## Getting started

Requires a JDK, version 17 or newer.

```sh
git clone https://github.com/MichailKoloniaris/book-catalogue-manager.git
cd book-catalogue-manager

./build.sh          # Windows: build.bat
java -jar book-catalogue-manager.jar --demo
```

`--demo` starts with six sample books so every menu can be tried right away. Leave it out to
start with an empty catalogue.

To run the scripted session that the CI uses:

```sh
java -jar book-catalogue-manager.jar --demo < demo/demo-session.txt
```

In **IntelliJ IDEA**, open the folder, mark `src` as the sources root and run
`bookcatalogue.Main`.

> On Windows, if Greek characters show up as `?`, switch the terminal to UTF-8 first with
> `chcp 65001` and run `java -Dstdout.encoding=UTF-8 -jar book-catalogue-manager.jar`.

## Design

```text
src/bookcatalogue
├── Main.java              Console menus
├── Catalogue.java         Fixed-size array of books, tracks the current sort order
├── SortField.java         Author / ISBN / year / price, each with its Comparator
├── SortAlgorithm.java     The five algorithms the user can choose from
├── SampleData.java        Books loaded by --demo
├── model
│   ├── Book.java          Abstract base class with validation
│   ├── ScienceBook.java   ┐
│   ├── Dictionary.java    ├ Concrete book types
│   ├── ReadingBook.java   ┘
│   ├── BookType.java      Enum of categories
│   ├── Sellable.java      Interface for items kept in stock
│   ├── Isbn.java          ISBN-10 / ISBN-13 check digit validation
│   └── InvalidIsbnException.java
├── sales
│   └── DaySales.java      Copies sold per book, totals, best-seller
└── util
    ├── Sorting.java       Generic sorting algorithms
    ├── Searching.java     Generic sequential and binary search
    └── ConsoleInput.java  Validated reading from standard input
```

OOP concepts used: an abstract base class with inheritance and polymorphism (`Book` and its
three subclasses), an interface (`Sellable`), a custom exception (`InvalidIsbnException`),
enums with behaviour (`SortField`, `SortAlgorithm`) and encapsulation with validating setters.

The algorithms are generic (`<T>` with a `Comparator<? super T>`), so the same code sorts
books by any field.

| Algorithm      | Average      | Worst        | Stable |
|----------------|--------------|--------------|--------|
| Bubble sort    | O(n²)        | O(n²)        | yes    |
| Insertion sort | O(n²)        | O(n²)        | yes    |
| Selection sort | O(n²)        | O(n²)        | no     |
| Quicksort      | O(n log n)   | O(n²)        | no     |
| Merge sort     | O(n log n)   | O(n log n)   | yes    |
| Binary search  | O(log n)     | O(log n)     | n/a    |

## History

The first commit is the project exactly as it was submitted in May 2025. The second commit
refactors it into packages and fixes the bugs found while preparing it for GitHub, among them:

- Sales were stored by array position, so sorting the catalogue after a sale credited the
  sale to the wrong book. Sales are now stored per book.
- Selling a reading book crashed with a `ClassCastException`, because only two of the three
  types had stock. All books now have copies in stock.
- The stock check was reversed and sold copies were never removed from stock.
- ISBN search went one position past the end of the array, and a sale used binary search
  even when the catalogue was not sorted by ISBN.
- Binary search by year returned a wrong book when no book matched.
- An invalid menu choice, year or price could leave an empty slot or crash the program.
- Every input call created a new reader on `System.in`, losing input when it was piped in.
