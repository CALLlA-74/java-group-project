package ru.project.Service;

import ru.project.Config.Config;
import ru.project.Domain.models.Student;
import ru.project.Lib.Sorting.SortOptions;
import ru.project.Repository.ListStudRepo.ListStudRepo;
import ru.project.Service.StudService.StudService;
import ru.project.Repository.IStudentRepo;

import java.util.ArrayList;
import java.util.List;

public class StudentSortingDemo {

    public static void main(String[] args) {

        //Создаем "MockStudent" тестовую коллекцию
        List<Student> mockStudents = new ArrayList<>();
        mockStudents.add(new Student("Ivan", "Ivanov", "B2", 3, 4.5f)); // нечетный
        mockStudents.add(new Student("Petr", "Petrov", "A1", 1, 4.8f)); // нечетный
        mockStudents.add(new Student("Alex", "Sidorov", "A1", 2, 3.9f)); // четный
        mockStudents.add(new Student("Maria", "Ivanova", "B1", 4, 4.2f)); // четный

        //Создаем репозиторий и сервис
        IStudentRepo repo = new ListStudRepo(mockStudents);
        IStudentService service = new StudService(repo);

        System.out.println("=== Исходный список студентов ===");
        printStudents(repo.getAll());

        //QuickSort ASC по всем полям
        SortOptions quickAsc = new SortOptions(
                SortOptions.SortDirections.ASC,
                SortOptions.SortAlgs.QUICK,
                SortOptions.SortTypes.DEFAULT
        );
        service.sortStuds(quickAsc);

        System.out.println("\n=== QuickSort ASC по всем полям ===");
        printStudents(repo.getAll());

        //BubbleSort DESC по всем полям
        SortOptions bubbleDesc = new SortOptions(
                SortOptions.SortDirections.DESC,
                SortOptions.SortAlgs.BUBBLE,
                SortOptions.SortTypes.DEFAULT
        );
        service.sortStuds(bubbleDesc);

        System.out.println("\n=== BubbleSort DESC по всем полям ===");
        printStudents(repo.getAll());

        //QuickSort ASC только четные номера зачетки
        SortOptions evenOnly = new SortOptions(
                SortOptions.SortDirections.ASC,
                SortOptions.SortAlgs.QUICK,
                SortOptions.SortTypes.EVEN_ONLY
        );
        service.sortStuds(evenOnly);

        System.out.println("\n=== QuickSort ASC, только четные номера зачетки ===");
        printStudents(repo.getAll());

        System.out.println("\n=== Логирование в файл: " + Config.logPath + " ===");
    }

    //Вспомогательный метод для вывода студентов
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
