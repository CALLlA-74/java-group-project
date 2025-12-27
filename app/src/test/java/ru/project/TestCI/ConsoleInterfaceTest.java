package ru.project.TestCI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.project.CI.ConsoleInterface;
import ru.project.Lib.Sorting.SortOptions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class ConsoleInterfaceTest {

    private InputStream originalSystemIn;
    private StudentServiceStub serviceStub;
    private ConsoleInterface consoleInterface;

    @Before
    public void setUp() {
        originalSystemIn = System.in;
        serviceStub = new StudentServiceStub();
    }

    @After
    public void tearDown() {
        System.setIn(originalSystemIn);
    }

    private void provideInput(String data) {
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        consoleInterface = new ConsoleInterface(serviceStub);
    }

    private Object getPrivateField(Object target, String fieldName) {
        try {

            Field field = target.getClass().getDeclaredField(fieldName);

            field.setAccessible(true);

            return field.get(target);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось прочитать поле " + fieldName, e);
        }
    }

    @Test
    public void testClearCollection() {
        provideInput("4\n5\n");
        consoleInterface.run();
        assertTrue("Метод очистки должен был быть вызван", serviceStub.wasClearCalled);
    }

    @Test
    public void testFillFromFile() {
        provideInput("1\n1\ntest.txt\n5\n");
        consoleInterface.run();
        assertEquals("test.txt", serviceStub.lastFilePath);
    }

    @Test
    public void testGenerateStudents() {
        provideInput("1\n2\n100\n5\n");
        consoleInterface.run();
        assertEquals(100, serviceStub.generatedCount);
    }

    @Test
    public void testAddStudentSuccess() {

        String input = "1\n3\nИванов\nИван\nG1\n123\n4.5\n5\n";
        provideInput(input);

        consoleInterface.run();

        assertNotNull("Студент должен был добавиться", serviceStub.lastAddedStudent);

        assertEquals("Иванов", getPrivateField(serviceStub.lastAddedStudent, "surname"));
        assertEquals("Иван", getPrivateField(serviceStub.lastAddedStudent, "name"));

        float rating = (float) getPrivateField(serviceStub.lastAddedStudent, "avgRating");
        assertEquals(4.5f, rating, 0.0001f);
    }

    @Test
    public void testSortWithDirection() {
        provideInput("2\n1\n1\n2\n5\n");
        consoleInterface.run();

        assertNotNull(serviceStub.lastSortOptions);

        Object direction = getPrivateField(serviceStub.lastSortOptions, "direction");
        assertEquals(SortOptions.SortDirections.DESC, direction);
    }

    @Test
    public void testSearchStudent() {
        String input = "3\nПетров\nПетр\nG2\n999\n5.0\n5\n";
        provideInput(input);
        consoleInterface.run();

        assertNotNull(serviceStub.lastSearchTemplate);
        assertEquals("Петров", getPrivateField(serviceStub.lastSearchTemplate, "surname"));
    }
}