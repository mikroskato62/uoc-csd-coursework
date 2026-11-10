// [!] HY252 - csd5748 !

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


class LibraryCatalog
{
    private String name;
    private String city;
    ArrayList<Book> books;

    public String getName() {return this.name;}

    public void setName(String newName) {this.name = newName;}

    public String getCity() {return this.city;}

    public void setCity(String newCity) {this.city = newCity;}

    public LibraryCatalog(String name, String city)
    {
        setName(name);
        setCity(city);
        this.books = new ArrayList<>();
    }

    public void addBook(Book b) throws IllegalArgumentException
    {
        for (Book book : this.books) { if (book.getTitle().equals(b.getTitle())) { return; } }
        this.books.add(b);
    }

    @Override
    public String toString()
    {
        float avg = 0;
        for (Book b : this.books) { avg += 2026 - b.getPublicationYear(); }
        avg = avg / this.books.toArray().length;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getName()).append("(").append(this.getCity()).append(") \n");
        for (Book b : this.books) { stringBuilder.append("      *").append(b.toString()).append("\n"); }
        stringBuilder.append("\n      " + "Number of books: ").append(this.books.toArray().length);
        stringBuilder.append("\n      " + "Average Age: ").append(avg).append(" years");
        return stringBuilder.toString();
    }
}

class Book implements Comparable
{
    private String title;
    private int publicationYear;

    public String getTitle() { return this.title; }
    public void setTitle(String newTitle) { this.title = newTitle; }
    public int getPublicationYear() { return this.publicationYear; }
    public void setPublicationYear(int newPublicationYear) { this.publicationYear = newPublicationYear; }

    public Book(String title, int publicationYear) { setTitle(title); setPublicationYear(publicationYear); }

    @Override
    public String toString() { return (this.getTitle() + " (written before " + (2026 - this.getPublicationYear()) + " years)"); }

    // @Override
    public boolean equals(Book a, Book b) { return (a.title.equals(b.title) && a.publicationYear == b.publicationYear); }

    @Override public int compareTo(Object o) { return 0; }
}

class TextBook extends Book
{
    private final int numberOfCopies;

    public TextBook(String title, int publicationYear, int numberOfCopies)
    { super(title, publicationYear); this.numberOfCopies = numberOfCopies; }

    @Override
    public String toString()
    { return (super.toString() + " [" + this.numberOfCopies + " copies]"); }
}

public class Tester
{
    public static void main(String... args)
    {
        LibraryCatalog lib1 = new LibraryCatalog("Bibliothiki PK", "Hrakleio");
        LibraryCatalog lib2 = new LibraryCatalog("Bibliothiki ITE", "Hrakleio");

        lib1.addBook(new TextBook("Java 88", 2025, 20));
        lib2.addBook(new Book("Surely You're Joking, Mr. Feynman!", 1985));
        lib2.addBook(new Book("Dummy", 2026));

        System.out.println(lib1.toString());
        System.out.println();
        System.out.println(lib2.toString());

        try
        {
            Set set = new HashSet<>();
            set.add(lib2.books.get(1));
            set.addAll(lib1.books);
            set.add(lib2.books.get(0));
            System.out.println(Arrays.toString(set.toArray()));
            // set.stream().sorted();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}

// [!] The end ...