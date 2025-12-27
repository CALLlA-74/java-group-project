package ru.project.Service.StudService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;

import ru.project.Domain.models.Student;
import ru.project.Lib.Sorting.SortOptions;
import ru.project.Repository.IStudentRepo;
import ru.project.Service.IStudentService;
import ru.project.Service.StudService.mocks.MockStudRepo;

public class StudServiceTest {

    @Test
    public void sortStudsTest() {

        //Создаем "MockStudent" тестовую коллекцию
        var mockStudents = initStudents();
        
        //Создаем репозиторий и сервис
        IStudentRepo repo = new MockStudRepo(mockStudents);
        IStudentService service = new StudService(repo);

        //QuickSort ASC по всем полям
        SortOptions quickAsc = new SortOptions(
            SortOptions.SortDirections.ASC,
            SortOptions.SortAlgs.QUICK,
            SortOptions.SortTypes.DEFAULT
        );
        service.sortStuds(quickAsc);

        checkStuds(repo.getAll(), quickAsc);

        //BubbleSort DESC по всем полям
        repo = new MockStudRepo(initStudents());
        service = new StudService(repo);

        SortOptions bubbleDesc = new SortOptions(
            SortOptions.SortDirections.DESC,
            SortOptions.SortAlgs.BUBBLE,
            SortOptions.SortTypes.DEFAULT
        );
        service.sortStuds(bubbleDesc);

        System.out.println("\n=== BubbleSort DESC по всем полям ===");
        checkStuds(repo.getAll(), bubbleDesc);

        //QuickSort ASC только четные номера зачетки
        var srcStuds = initStudents();
        mockStudents = srcStuds.subList(0, srcStuds.size());
        repo = new MockStudRepo(mockStudents);
        service = new StudService(repo);

        SortOptions evenOnly = new SortOptions(
            SortOptions.SortDirections.ASC,
            SortOptions.SortAlgs.QUICK,
            SortOptions.SortTypes.EVEN_ONLY
        );
        service.sortStuds(evenOnly);

        checkStudsWithEvenNums(srcStuds, repo.getAll(), evenOnly);
    }

    private static List<Student> initStudents() {
        List<Student> mockStudents = new ArrayList<>();
        mockStudents.add(new Student("Ivan", "Ivanov", "B2", 3, 4.5f)); // нечетный
        mockStudents.add(new Student("Petr", "Petrov", "A1", 1, 4.8f)); // нечетный
        mockStudents.add(new Student("Alex", "Sidorov", "A1", 2, 3.9f)); // четный
        mockStudents.add(new Student("Maria", "Ivanova", "B1", 4, 4.2f)); // четный
        return mockStudents;
    }

    private static Comparator<Student> compByOpts(SortOptions opts) {
        Comparator<Student> comparator = Comparator
                .comparing(Student::getSurname)
                .thenComparing(Student::getName)
                .thenComparing(Student::getGroupId)
                .thenComparing(Student::getAvgRating)
                .thenComparing(Student::getAcheivmentSheetNumber);

        if (opts.getDirection() == SortOptions.SortDirections.DESC) {
            comparator = comparator.reversed();
        }

        return comparator;
    }

    private static void checkStuds(List<Student> students, SortOptions opts) {
        var comp = compByOpts(opts);

        for (int i = 0; i < students.size() - 1; i++) {
            assert(comp.compare(students.get(i), students.get(i + 1)) < 1);
        }
    }

    private static void checkStudsWithEvenNums(
        List<Student> srcStuds,
        List<Student> sortedStuds,
        SortOptions opts
    ) {
        var comp = compByOpts(opts);
        int prevEven = -1;

        for (int i = 0; i < sortedStuds.size(); i++) {
            if (sortedStuds.get(i).getAcheivmentSheetNumber() % 2 == 0) {
                if (prevEven > -1) {
                    assert(comp.compare(sortedStuds.get(prevEven), sortedStuds.get(i)) < 1);
                } 
                prevEven = i;
            } else {
                assert(srcStuds.get(i).equals(sortedStuds.get(i)));
            }
        }
    }
}
