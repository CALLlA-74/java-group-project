package ru.project.Service.StudService;

import org.junit.Test;
import org.mockito.Mockito;

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
}
