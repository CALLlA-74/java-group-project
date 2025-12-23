package ru.project.Lib.Sorting;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SortOptions {

    public enum SortDirections {
        ASC, DESC
    }

    public enum SortAlgs {
        BUBBLE,
        QUICK
    }

    public enum SortTypes {
        DEFAULT,    // сортировка по всем полям
        BY_ONE      // по одному полю
    }

    private SortDirections orderBy;
    private SortAlgs sortAlg;

    public SortOptions(SortDirections orderBy, SortAlgs sortAlg) {
        this.orderBy = orderBy;
        this.sortAlg = sortAlg;
    }

    public SortAlgs getSortAlg() {
        return sortAlg;
    }

    public SortDirections getSortType() {
        return orderBy;
    }
}


class Student {
    private int groupNumber;
    private double avgScore;
    private long recordBook;

    public Student(int groupNumber, double avgScore, long recordBook) {
        this.groupNumber = groupNumber;
        this.avgScore = avgScore;
        this.recordBook = recordBook;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public long getRecordBook() {
        return recordBook;
    }

    @Override
    public String toString() {
        return "Student{" +
                "group=" + groupNumber +
                ", avgScore=" + avgScore +
                ", recordBook=" + recordBook +
                '}';
    }
}


interface SortAlgorithm<T> {
    void sort(List<T> list, Comparator<T> comparator);
}

// Сортировка пузырьком
class BubbleSort<T> implements SortAlgorithm<T> {

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    T tmp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tmp);
                }
            }
        }
    }
}

// Quicksort
class QuickSort<T> implements SortAlgorithm<T> {

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        quickSort(list, 0, list.size() - 1, comparator);
    }

    private void quickSort(List<T> list, int low, int high, Comparator<T> comp) {
        if (low < high) {
            int p = partition(list, low, high, comp);
            quickSort(list, low, p - 1, comp);
            quickSort(list, p + 1, high, comp);
        }
    }

    private int partition(List<T> list, int low, int high, Comparator<T> comp) {
        T pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comp.compare(list.get(j), pivot) <= 0) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }
}


interface IStudentRepo {
    List<Student> sort(List<Student> students, SortOptions options);
}

class StudentRepo implements IStudentRepo {

    @Override
    public List<Student> sort(List<Student> students, SortOptions options) {

        SortAlgorithm<Student> algorithm =
                switch (options.getSortAlg()) {
                    case BUBBLE -> new BubbleSort<>();
                    case QUICK -> new QuickSort<>();
                };

        Comparator<Student> comparator =
                Comparator.comparing(Student::getGroupNumber)
                        .thenComparing(Student::getAvgScore)
                        .thenComparing(Student::getRecordBook);

        if (options.getSortType() == SortOptions.SortDirections.DESC) {
            comparator = comparator.reversed();
        }

        algorithm.sort(students, comparator);
        return students;
    }
}

// Сервис и логирование
interface IStudentService {
    List<Student> sortAndLog(List<Student> students, SortOptions options);
}

class Config {
    public static final String LOG_PATH = "students.log";
}

class StudentService implements IStudentService {

    private final IStudentRepo repo;

    public StudentService(IStudentRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<Student> sortAndLog(List<Student> students, SortOptions options) {
        List<Student> sorted = repo.sort(students, options);
        writeToLog(sorted);
        return sorted;
    }

    private void writeToLog(List<Student> students) {
        try (FileWriter fw = new FileWriter(Config.LOG_PATH, true)) {
            for (Student s : students) {
                fw.write(s.toString() + System.lineSeparator());
            }
            fw.write("-----" + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

//проверка сортировки
class Demo {
    public static void main(String[] args) {

        List<Student> students = new ArrayList<>(List.of(
                new Student(2, 4.5, 1003),
                new Student(1, 3.8, 1001),
                new Student(3, 4.9, 1002),
                new Student(1, 4.9, 1004)
        ));

        SortOptions options = new SortOptions(
                SortOptions.SortDirections.ASC, // Порядок сорта, 2 режима
                SortOptions.SortAlgs.QUICK  // Тип сорта, 2 режима
        );

        IStudentService service =
                new StudentService(new StudentRepo());

        service.sortAndLog(students, options);

        students.forEach(System.out::println);
    }
}
