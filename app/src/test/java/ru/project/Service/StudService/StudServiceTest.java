package ru.project.Service.StudService;

import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.google.gson.Gson;

import ru.project.Domain.models.Student;
import ru.project.Repository.IStudentRepo;
import ru.project.Service.IStudentService;

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

    private static void initFile(List<Student> studs) {
        var gson = new Gson();
        try (FileWriter writer = new FileWriter("developer.json")) {
            gson.toJson(studs, writer);
        } catch (IOException e) {
            assertTrue(e.getStackTrace().toString(), false);
        }
    }

    @Test public void fillFromFileTest() {
        
    }
}
