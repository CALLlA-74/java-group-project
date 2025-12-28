package ru.project.Service.StudService;

import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.gson.Gson;

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

    private static void initFile(List<Student> studs, String filePath) {
        var gson = new Gson();
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(studs, writer);
        } catch (IOException e) {
            assertTrue(e.getStackTrace().toString(), false);
        }
    }

    private static List<Student> iniStudents(int numOfStuds) {
        var service = new StudService(new MockStudRepo(new ArrayList<Student>(numOfStuds)));
        assert(service.genStuds(numOfStuds));
        assert(service.getAllStuds().size() == numOfStuds);
        return service.getAllStuds();
    }

    @Test public void fillFromFileTest() {
        String filePath = "./input.json";
        int numOfStuds = 2000;
        var srcStuds = iniStudents(numOfStuds);
        initFile(srcStuds, filePath);

        var service = new StudService(new MockStudRepo(new ArrayList<Student>(numOfStuds)));
        assert(service.fillFromFile(filePath));
        assert(service.getAllStuds().size() == srcStuds.size());
        var studs = service.getAllStuds();
        for (int i = srcStuds.size() - 1; i >= 0; i--) {
            assert(srcStuds.get(i).equals(studs.get(i)));
        }
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
        IStudentService service = new StudService(new MockStudRepo(new ArrayList<>()));
        assert(service.genStuds(10));

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
