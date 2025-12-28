package ru.project.Service.StudService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import ru.project.Domain.models.Student;
import ru.project.Lib.Sorting.SortOptions;
import ru.project.Repository.IStudentRepo;
import ru.project.Service.IStudentService;
import ru.project.Service.StudService.mocks.MockStudRepo;

public class StudServiceTest {
    @SuppressWarnings("null")
    @Test public void genStudsTest() {
        IStudentRepo repo = Mockito.mock(IStudentRepo.class);
        Mockito.when(repo.store(Mockito.any(Student.class))).thenReturn(true);

        IStudentService service = new StudService(repo);
        assert(service.genStuds(5));


        repo = Mockito.mock(IStudentRepo.class);
        Mockito.when(repo.store(Mockito.any(Student.class))).thenReturn(false);
        
        service = new StudService(repo);
        assert(service.genStuds(5) == false);
    }

    @Test
    public void sortStudsTest() {
        IStudentService service = initService();

        //QuickSort ASC по всем полям
        SortOptions quickAsc = new SortOptions(
            SortOptions.SortDirections.ASC,
            SortOptions.SortAlgs.QUICK,
            SortOptions.SortTypes.DEFAULT
        );
        service.sortStuds(quickAsc);

        checkStuds(service.getAllStuds(), quickAsc);

        //BubbleSort DESC по всем полям
        service = initService();

        SortOptions bubbleDesc = new SortOptions(
            SortOptions.SortDirections.DESC,
            SortOptions.SortAlgs.BUBBLE,
            SortOptions.SortTypes.DEFAULT
        );
        service.sortStuds(bubbleDesc);

        System.out.println("\n=== BubbleSort DESC по всем полям ===");
        checkStuds(service.getAllStuds(), bubbleDesc);

        //QuickSort ASC только четные номера зачетки
        service = initService();
        var studs = service.getAllStuds();
        var srcStuds = studs.subList(0, studs.size());

        SortOptions evenOnly = new SortOptions(
            SortOptions.SortDirections.ASC,
            SortOptions.SortAlgs.QUICK,
            SortOptions.SortTypes.EVEN_ONLY
        );
        service.sortStuds(evenOnly);

        checkStudsWithEvenNums(srcStuds, service.getAllStuds(), evenOnly);
    }

    private static IStudentService initService() {
        /*List<Student> mockStudents = new ArrayList<>();
        mockStudents.add(new Student("Ivan", "Ivanov", "B2", 3, 4.5f)); // нечетный
        mockStudents.add(new Student("Petr", "Petrov", "A1", 1, 4.8f)); // нечетный
        mockStudents.add(new Student("Alex", "Sidorov", "A1", 2, 3.9f)); // четный
        mockStudents.add(new Student("Maria", "Ivanova", "B1", 4, 4.2f)); // четный */

        IStudentService service = new StudService(new MockStudRepo(new ArrayList<>()));
        assert(service.genStuds(1000));

        return service;
    }

    private static Comparator<Student> compByOpts(SortOptions opts) {
        Comparator<Student> comparator = Comparator
                .comparing(Student::getSurname)
                .thenComparing(Student::getName)
                .thenComparing(Student::getGroupId)
                .thenComparing(Student::getAvgRating)
                .thenComparing(Student::getAcheivmentSheetNumber);

        if (opts.getDirection() == SortOptions.SortDirections.DESC 
            && opts.getSortType() == SortOptions.SortTypes.DEFAULT) {
            
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
