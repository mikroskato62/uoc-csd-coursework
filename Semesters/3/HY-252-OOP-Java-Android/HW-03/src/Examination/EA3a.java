package Examination;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

// Domain Model:
class CourseGrade
{
    private String studentId;
    private String courseName;
    private int semester;
    private double grade;

    public CourseGrade(String studentId, String courseName, int semester, double grade)
    {
        this.studentId = studentId;
        this.courseName = courseName;
        this.semester = semester;
        this.grade = grade;
    }

    public String getStudentId() { return studentId; }
    public String getCourseName() { return courseName; }
    public int getSemester() { return semester; }
    public double getGrade() { return grade; }

    @Override
    public String toString()
    {
        return String.format("[%s | Μάθημα: %s | Εξάμηνο: %d | Βαθμός: %.1f]", studentId, courseName, semester, grade);
    }
}

// Functional Factory Interface:
@FunctionalInterface
interface GradeFactory<T extends CourseGrade>
{
    T create(String studentId, String courseName, int semester, double grade);
}

public class EA3a
{
    // Higher-order method accepting a Predicate (Java 8 Functional Programming):
    public static void filterAndPrint(List<CourseGrade> list, Predicate<CourseGrade> predicate, String title)
    {
        System.out.println("\n--- " + title + " ---");
        list.stream()
            .filter(predicate)
            .forEach(System.out::println);
    }

    public static void main(String[] args)
    {
        System.out.println("[HY-252] Εργαστηριακή Εξέταση 3 (Java 8 Streams & Lambdas)\n");

        // 1. Factory with Constructor Method Reference:
        GradeFactory<CourseGrade> factory = CourseGrade::new;
        List<CourseGrade> grades = new ArrayList<>();

        grades.add(factory.create("csd5001", "HY-252", 3, 8.5));
        grades.add(factory.create("csd5002", "HY-240", 3, 6.0));
        grades.add(factory.create("csd5003", "HY-252", 3, 9.5));
        grades.add(factory.create("csd5004", "HY-100", 1, 4.0));
        grades.add(factory.create("csd5005", "HY-252", 3, 7.0));
        grades.add(factory.create("csd5006", "HY-240", 3, 5.0));
        grades.add(factory.create("csd5007", "HY-340", 5, 8.0));
        grades.add(factory.create("csd5008", "HY-252", 3, 10.0));
        grades.add(factory.create("csd5009", "HY-100", 1, 6.5));
        grades.add(factory.create("csd5010", "HY-340", 5, 3.5));

        // 2. Stream filtering using Predicate:
        filterAndPrint(grades, g -> g.getCourseName().equals("HY-252") && g.getGrade() >= 5.0, "Επιτυχόντες στο μάθημα HY-252");

        // 3. Sorting using Stream Comparator (Grade Descending):
        System.out.println("\n--- Ταξινόμηση βαθμών HY-252 (Φθίνουσα) ---");
        grades.stream()
              .filter(g -> g.getCourseName().equals("HY-252"))
              .sorted(Comparator.comparingDouble(CourseGrade::getGrade).reversed())
              .forEach(System.out::println);

        // 4. Stream Summary Statistics:
        DoubleSummaryStatistics stats = grades.stream()
                                              .filter(g -> g.getCourseName().equals("HY-252"))
                                              .mapToDouble(CourseGrade::getGrade)
                                              .summaryStatistics();

        System.out.println("\n--- Στατιστικά HY-252 ---");
        System.out.printf("Ελάχιστος Βαθμός: %.1f%n", stats.getMin());
        System.out.printf("Μέγιστος Βαθμός : %.1f%n", stats.getMax());
        System.out.printf("Μέσος Όρος      : %.2f%n", stats.getAverage());
        System.out.printf("Σύνολο Φοιτητών : %d%n", stats.getCount());

        // 5. Downstream Grouping by Course & Average Grade Calculation:
        System.out.println("\n--- Μέσος Όρος ανά Μάθημα ---");
        Map<String, Double> avgByCourse = grades.stream()
                                                .collect(Collectors.groupingBy(CourseGrade::getCourseName,
                                                         Collectors.averagingDouble(CourseGrade::getGrade)));

        avgByCourse.forEach((course, avg) -> System.out.printf("Μάθημα %s: Μ.Ο. = %.2f%n", course, avg));

        // 6. String joining collector:
        String passingStudents = grades.stream()
                                       .filter(g -> g.getGrade() >= 5.0)
                                       .map(CourseGrade::getStudentId)
                                       .distinct()
                                       .collect(Collectors.joining(", "));

        System.out.println("\nΌλοι οι επιτυχόντες φοιτητές: " + passingStudents);
    }
}
