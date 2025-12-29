package ru.project.Service.MultiThreadCounter;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ru.project.Domain.models.Student;
import ru.project.Lib.Searching.MultiThreadCounter;
import ru.project.Service.IStudentService;
import ru.project.Service.StudService.StudService;
import ru.project.Service.StudService.mocks.MockStudRepo;

public class MultiThreadCounterTest {

    @Test
    public void countStudentsTest() {
        //Инициализация сервиса с MockRepo
        IStudentService service =
                new StudService(new MockStudRepo(new ArrayList<>()));

        int total = 100;
        assert service.genStuds(total);

        List<Student> students = service.getAllStuds();
        assert students.size() == total;

        Student target = students.get(0);

        int expected = 0;
        for (Student s : students) {
            if (s.equals(target)) {
                expected++;
            }
        }

        //Многопоточный подсчёт
        int threads = 4;
        int actual = MultiThreadCounter.count(threads, students, target);

        //Проверка
        assert expected == actual;

        System.out.println(
                "MultiThreadCounter OK: expected=" + expected +
                        ", actual=" + actual
        );
    }
}