// [!] UoC - CSD - HY252 Java - Exercise 3 (Bonus) - RIP: csd5748 ...

/* ------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

// All imports:
import java.util.function.Predicate;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.*;
import java.util.stream.*;
import java.util.Comparator;
import java.util.stream.Collectors;

/* ------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

// 1.1:
interface SmartDevice
{
    public void turnOn();
    public void turnOff();
    public default void reset() { System.out.println("SmartDevice is resetting ..."); }
    static boolean isDeviceOnline(String status) { return "ONLINE".equals(status); }

}
class SmartBulb1 implements SmartDevice
{
    public void turnOn() { System.out.println("Bulb is ON."); }
    public void turnOff() { System.out.println("Bulb is OFF."); }
}

// 1.2:
interface NetworkComponent
{
    default public void connect() {}
    public default void reset() { System.out.println("NetworkComponent is resetting ..."); }
}
class SmartBulb2 implements SmartDevice, NetworkComponent
{
    public void turnOn() { System.out.println("Bulb is ON."); }
    public void turnOff() { System.out.println("Bulb is OFF."); }
    public void reset() { NetworkComponent.super.reset(); }
}

// 1.3:
public void main13(String[] args)
{
    SmartBulb1 bulb1 = new SmartBulb1();
    SmartBulb2 bulb2 = new SmartBulb2();
    // bulb1.isDeviceOnline("ONLINE");
    // bulb2.isDeviceOnline("OFFLINE");
    System.out.println("[1.3] CheckA: " + SmartDevice.isDeviceOnline("ONLINE") +
                       " & CheckB: " + SmartDevice.isDeviceOnline("OFFLINE") + ".");
    // Note: static methods inside interfaces are not inherited by instances; reference to the class is required ...
}

// 1.4:
interface LongPredicate { public boolean test(int v); }
public class ExerciseAmbiguity
{
    private static void method(Predicate<Long> p) { System.out.print("Predicate."); }
    private static void method(LongPredicate p) { System.out.print("IntPredicate."); }
    public static void main(String args[])
    {
        // Note: variable x is not defined; both overloaded methods execute - a cast is needed ...
        method((int x) -> true);
        method((LongPredicate) (x) -> true);
        method((Predicate<Long>) (x) -> true);
    }
}

/* ------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

// 2.1:
class A21
{
    public static void main(String[] aa)
    {
        JFrame frame = new JFrame();
        JButton button1 = new JButton("Click me!");

        button1.addActionListener(e ->
        {
            System.out.println("Hello Java 8!");
            new Thread(() ->
            {
                System.out.println("Τρέχω παράλληλα.");
                main(null);
            }).start();
        });

        frame.add(button1);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }
}

// 2.2:
class a23_ekfonisi
{
    public static void main(String[] args)
    {
        ArrayList<Double> items = new ArrayList<>();
        for (int i = 0; i < 30; i++) items.add(i * i / 3.3);
        /* Start */
        DoubleSummaryStatistics stats = items.stream()
                                             .mapToDouble(Double::doubleValue)
                                             .summaryStatistics();
        double product = items.stream().reduce(1.0, (a, b) -> a * b);
        System.out.println("Minimum = " + stats.getMin() + "\nMaximum = " + stats.getMax() +
                           "\nAverage = " + stats.getAverage() + "\nProduct = " + product);
        /* End */
    }}

// 2.3:
class a24
{
    // (a)
    public static void calculate(List<Integer> list, Predicate<Integer> pred)
    {
        for (Integer num : list) if (pred.test(num)) System.out.print(num + " ");
        System.out.print("\n");
    }
    // (a)

    public static void main(String[] a)
    {
        final int K = 20;
        ArrayList<Integer> items = new ArrayList<>();
        System.out.println("Initial list: ");
        Random rand = new Random();
        for (int i = 0; i < K; i++)
        {
            items.add(rand.nextInt(100) + 1);
            System.out.print(items.get(i) + " ");
        }
        System.out.print("\n");

        // (β)
        int val1 = 15;
        System.out.println("Numbers bigger than " + val1 + ":");
        calculate(items, x -> x > val1);
        System.out.println("Numbers smaller than " + val1 + ":");
        calculate(items, x -> x < val1);
        System.out.println("Numbers equal to 2 or between 10 and 20: ");
        calculate(items, x -> (x >= 10 && x <= 20) || x == 2);
        // (β)
    }
}

/* ------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

// 3.0:
// Note: this class is "static" to avoid errors ...
static class Movie implements Comparable
{
    private String title;
    private String type;
    private String director;
    private int year;
    private double rating;

    public Movie(String title, String type, String director, int year, double rating)
    { this.title = title; this.type = type; this.director = director; this.year = year; this.rating = rating; }

    public int compareTo(Object carObj)
    {
        Movie movie = (Movie) carObj;
        return Double.compare(movie.rating, this.rating);
    }

    interface MovieFactory<P extends Movie>
    { P create(String title, String type, String director, int year, double rating); }

    // (α)
    public String getDirector() { return director; }
    public int getYear() { return year; }
    public double getRating() { return rating; }
    // (α)

    public String toString()
    { return "[Title: " + title + " Type: " + type + " Director: " + director + " Year: " + year + " Rating: " + rating + "]"; }
}
public class a3
{
    public static void main(String[] args)
    {
        // Please note: nothing changed except (a) and (b) here (ex. 3) - below is an error from the original code ...
        Movie.MovieFactory<Movie> mv = Movie::new;
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(mv.create("2001: A Space Odyssey", "Science Fiction", "Stanley Kubrick", 1968, 8.3));
        movies.add(mv.create("The Godfather", "Drama", "Francis Coppola", 1972, 9.2));
        movies.add(mv.create("Mulholland Drive", "Drama", "David Lynch", 2001, 7.9));
        movies.add(mv.create("Dune", "Science Fiction", "Denis Villeneuve", 2021, 8.2));
        movies.add(mv.create("Blade Runner 2049", "Science Fiction", "Denis Villeneuve", 2017, 8.0));
        movies.add(mv.create("The Shining", "Horror", "Stanley Kubrick", 1980, 8.4));
        movies.add(mv.create("Apocalypse Now", "Drama", "Francis Coppola", 1968, 8.4));
        movies.add(mv.create("Ready Player One", "Science Fiction", "Steven Spielberg", 2018, 7.4));
        movies.add(mv.create("Minority Report", "Action", "Steven Spielberg", 2002, 7.6));

        // (β)
        // 3.1:
        System.out.println("\n3.1 Movies Directed by Stanley Kubrick");
        movies.stream()
              .filter(m -> m.getDirector().equals("Stanley Kubrick"))
              .forEach(System.out::println);
        // 3.2:
        System.out.println("\n3.2 Movies Directed by Francis Coppola or Denis Villeneuve");
        movies.stream()
              .filter(m -> m.getDirector().equals("Francis Coppola") || m.getDirector().equals("Denis Villeneuve"))
              .forEach(System.out::println);
        // 3.3:
        System.out.println("\n3.3 Movies Directed by Francis Coppola or Denis Villeneuve sorted by rating");
        movies.stream()
              .filter(m -> m.getDirector().equals("Francis Coppola") || m.getDirector().equals("Denis Villeneuve"))
              .sorted()
              .forEach(System.out::println);
        // 3.4:
        System.out.println("\n3.4 Movies Directed by Francis Coppola or Denis Villeneuve sorted by year");
        movies.stream()
              .filter(m -> m.getDirector().equals("Francis Coppola") || m.getDirector().equals("Denis Villeneuve"))
              .sorted(Comparator.comparingInt(Movie::getYear))
              .forEach(System.out::println);
        // 3.5:
        System.out.println("\n3.5 Movies Directed by Francis Coppola or Denis Villeneuve sorted by year descending");
        movies.stream()
              .filter(m -> m.getDirector().equals("Francis Coppola") || m.getDirector().equals("Denis Villeneuve"))
              .sorted(Comparator.comparingInt(Movie::getYear).reversed())
              .forEach(System.out::println);
        // 3.6:
        System.out.println("\n3.6 The best rated movie");
        movies.stream()
              .max(Comparator.comparingDouble(Movie::getRating))
              .ifPresent(System.out::println);
        // 3.7:
        System.out.println("\n3.7 The best rated movie of Steven Spielberg");
        movies.stream()
              .filter(m -> m.getDirector().equals("Steven Spielberg"))
              .max(Comparator.comparingDouble(Movie::getRating))
              .ifPresent(System.out::println);
        // 3.8:
        System.out.println("\n3.8: The three movies with the best rating");
        movies.stream()
              .sorted(Comparator.comparingDouble(Movie::getRating).reversed())
              .limit(3)
              .forEach(System.out::println);
        // 3.9:
        System.out.println("\n3.9: Number of movies by year");
        Map<Integer, Long> countByYear = movies.stream().collect(Collectors.groupingBy(Movie::getYear, Collectors.counting()));
        System.out.println(countByYear);
        // 3.10:
        System.out.println("\n3.10: Average rating of movies by year");
        Map<Integer, Double> avgByYear = movies.stream().collect(Collectors.groupingBy(Movie::getYear, Collectors.averagingDouble(Movie::getRating)));
        System.out.println(avgByYear);
        // (β)
    }
}

/* ------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

// 4.0:
// Note: this class is "static" to avoid errors ...
static class Laptop
{
    private String brand;
    private String model;
    private String cpu;
    private int ram;
    private double price;

    public Laptop(String brand, String model, String cpu, int ram, double price)
    { this.brand = brand; this.model = model; this.cpu = cpu; this.ram = ram; this.price = price; }

    interface LaptopFactory<P extends Laptop>
    { P create(String brand, String model, String cpu, int ram, double price); }

    // (α)
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getRam() { return ram; }
    public double getPrice() { return price; }
    // (α)

    public String toString()
    { return String.format("[%s %s, CPU: %s, RAM: %dGB, Price: %.2f€]", brand, model, cpu, ram, price); }
}
public class a4
{
    public static void main(String[] args)
    {
        Laptop.LaptopFactory<Laptop> factory = Laptop::new;
        ArrayList<Laptop> laptops = new ArrayList<>();
        laptops.add(factory.create("Apple", "MacBook Air M2", "M2", 8, 1199.0));
        laptops.add(factory.create("Dell", "XPS 13", "i7-1250U", 16, 1450.0));
        laptops.add(factory.create("Lenovo", "ThinkPad X1 Carbon", "i7-1260P", 16, 1899.0));
        laptops.add(factory.create("Asus", "ZenBook 14", "Ryzen 7 5800H", 16, 999.0));
        laptops.add(factory.create("HP", "Pavilion 15", "i5-1135G7", 8, 650.0));
        laptops.add(factory.create("Apple", "MacBook Pro 16", "M2 Max", 32, 3200.0));
        laptops.add(factory.create("Dell", "Inspiron 15", "i3-1115G4", 8, 450.0));
        laptops.add(factory.create("Razer", "Blade 15", "i9-12900H", 32, 3600.0));
        laptops.add(factory.create("Lenovo", "IdeaPad 3", "Ryzen 5 5625U", 8, 520.0));
        System.out.println();

        // (β)
        // 4.1:
        System.out.println("4.1 Laptops with >= 16GB RAM AND Price <= 1500€ ");
        laptops.stream()
               .filter(l -> l.getRam() >= 16 && l.getPrice() <= 1500)
               .forEach(System.out::println);
        // 4.2:
        System.out.println("\n4.2 Unique Brands ");
        laptops.stream()
               .map(Laptop::getBrand)
               .distinct()
               .forEach(System.out::println);
        // 4.3:
        System.out.println("\n4.3 Sorted by Price (Ascending) ");
        laptops.stream()
               .sorted(Comparator.comparingDouble(Laptop::getPrice))
               .forEach(System.out::println);
        // 4.4:
        System.out.println("\n4.4 Is there a cheap laptop (<400€)? ");
        boolean isCheap = laptops.stream().anyMatch(l -> l.getPrice() < 400);
        System.out.println("Answer: " + isCheap);
        // 4.5:
        System.out.println("\n4.5 All models as one String ");
        String allModels = laptops.stream()
                                  .map(Laptop::getModel)
                                  .collect(Collectors.joining(", "));
        System.out.println(allModels);
    }
}

/* ------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

// (Java 8 - Compact source file - Main method) Testing:
public static void main(String[] args)
{
    System.out.println("""
                       [!] Hi! In this compact source files named "Ask3.java", \
                       
                       csd5748 (RIP) tried to solve every question regarding the \
                       
                       bonus exercise 3 of HY252 (Uoc CSD), using InteliJ Java 8 :)
                       """);
}

/* ------------------------------------------------------------------------------------------------------------------------------------------------------------------ */

// [!] UoC - CSD - HY252 Java - Exercise 3 (Bonus) - RIP: csd5748 ...