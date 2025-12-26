package ru.project;

import ru.project.Domain.models.Student;
import ru.project.Lib.Sorting.SortOptions;
import ru.project.Service.Config;
import ru.project.Service.IStudentService;
import ru.project.Service.StudentService;
import ru.project.Repository.StudentRepo;

import java.util.ArrayList;
import java.util.List;

public class StudentSortingDemo {

    public static void main(String[] args) {

        // ===== Создаем тестовых студентов =====
        List<Student> students = new ArrayList<>();
        students.add(new Student("Ivan", "Ivanov", "B2", 3, 4.5f));
        students.add(new Student("Petr", "Petrov", "A1", 1, 4.8f));
        students.add(new Student("Alex", "Sidorov", "A1", 2, 3.9f));

        System.out.println("=== Исходный список студентов ===");
        printStudents(students);

        // ===== QuickSort ASC =====
        SortOptions quickAsc = new SortOptions(
                SortOptions.SortDirections.ASC,
                SortOptions.SortAlgs.QUICK
        );

        IStudentService service = new StudentService(new StudentRepo());
        List<Student> sortedQuick = service.sortAndLog(students, quickAsc);

        System.out.println("\n=== QuickSort ASC по всем полям ===");
        printStudents(sortedQuick);

        // ===== BubbleSort DESC =====
        SortOptions bubbleDesc = new SortOptions(
                SortOptions.SortDirections.DESC,
                SortOptions.SortAlgs.BUBBLE
        );

        List<Student> sortedBubble = service.sortAndLog(students, bubbleDesc);

        System.out.println("\n=== BubbleSort DESC по всем полям ===");
        printStudents(sortedBubble);

        System.out.println("\n=== Логирование в файл: " + Config.LOG_PATH + " ===");
    }

    // ===== Вспомогательный метод для вывода студентов =====
    private static void printStudents(List<Student> students) {
        for (Student s : students) {
            System.out.println(
                    "Группа: " + s.getGroupId() +
                            ", Средний балл: " + s.getAvgRating() +
                            ", Зачетка: " + s.getAcheivmentSheetNumber()
            );
        }
    }
}
