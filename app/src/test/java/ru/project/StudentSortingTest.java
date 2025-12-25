package ru.project;

import org.junit.jupiter.api.Test;
import ru.project.Domain.models.Student;
import ru.project.Lib.Sorting.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentSortingTest {

    // ===== Вспомогательный метод для создания тестовых студентов =====
    private List<Student> createTestStudents() {
        List<Student> list = new ArrayList<>();
        list.add(new Student("Ivan", "Ivanov", "B2", 3, 4.5f));
        list.add(new Student("Petr", "Petrov", "A1", 1, 4.8f));
        list.add(new Student("Alex", "Sidorov", "A1", 2, 3.9f));
        return list;
    }

    // ===== Вспомогательные методы для чтения полей через Reflection =====
    private String getGroupId(Student s) {
        try {
            var f = Student.class.getDeclaredField("groupId");
            f.setAccessible(true);
            return (String) f.get(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private float getAvgRating(Student s) {
        try {
            var f = Student.class.getDeclaredField("avgRating");
            f.setAccessible(true);
            return f.getFloat(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getSheetNumber(Student s) {
        try {
            var f = Student.class.getDeclaredField("acheivmentSheetNumber");
            f.setAccessible(true);
            return f.getInt(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ===== QuickSort ASC по всем полям =====
    @Test
    void quickSortAsc_shouldSortByAllFields() {
        List<Student> students = createTestStudents();

        SortOptions options = new SortOptions(
                SortOptions.SortDirections.ASC,
                SortOptions.SortAlgs.QUICK
        );

        IStudentService service = new StudentService(new StudentRepo());
        List<Student> sorted = service.sortAndLog(students, options);

        // Ожидаемый порядок:
        // A1 3.9 2
        // A1 4.8 1
        // B2 4.5 3
        assertEquals("A1", getGroupId(sorted.get(0)));
        assertEquals(3.9f, getAvgRating(sorted.get(0)), 0.01);
        assertEquals(2, getSheetNumber(sorted.get(0)));

        assertEquals("A1", getGroupId(sorted.get(1)));
        assertEquals(4.8f, getAvgRating(sorted.get(1)), 0.01);
        assertEquals(1, getSheetNumber(sorted.get(1)));

        assertEquals("B2", getGroupId(sorted.get(2)));
    }

    // ===== BubbleSort DESC по всем полям =====
    @Test
    void bubbleSortDesc_shouldSortByAllFields() {
        List<Student> students = createTestStudents();

        SortOptions options = new SortOptions(
                SortOptions.SortDirections.DESC,
                SortOptions.SortAlgs.BUBBLE
        );

        IStudentService service = new StudentService(new StudentRepo());
        List<Student> sorted = service.sortAndLog(students, options);

        // Ожидаемый порядок:
        // B2 4.5 3
        // A1 4.8 1
        // A1 3.9 2
        assertEquals("B2", getGroupId(sorted.get(0)));
        assertEquals(4.5f, getAvgRating(sorted.get(0)), 0.01);
        assertEquals(3, getSheetNumber(sorted.get(0)));

        assertEquals("A1", getGroupId(sorted.get(1)));
        assertEquals(4.8f, getAvgRating(sorted.get(1)), 0.01);
        assertEquals(1, getSheetNumber(sorted.get(1)));

        assertEquals("A1", getGroupId(sorted.get(2)));
        assertEquals(3.9f, getAvgRating(sorted.get(2)), 0.01);
        assertEquals(2, getSheetNumber(sorted.get(2)));
    }

    // ===== Проверка создания лог-файла =====
    @Test
    void logFile_shouldBeCreated() {
        File logFile = new File(Config.LOG_PATH);

        // Метод sortAndLog должен отработать без ошибок
        IStudentService service = new StudentService(new StudentRepo());
        service.sortAndLog(createTestStudents(),
                new SortOptions(SortOptions.SortDirections.ASC, SortOptions.SortAlgs.BUBBLE));

        assertTrue(logFile.exists(), "Лог-файл должен существовать");
        assertTrue(logFile.length() > 0, "Лог-файл не должен быть пустым");
    }
}
