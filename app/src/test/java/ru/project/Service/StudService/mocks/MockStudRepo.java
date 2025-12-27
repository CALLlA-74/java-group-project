package ru.project.Service.StudService.mocks;

import java.util.List;

import ru.project.Domain.models.Student;
import ru.project.Repository.IStudentRepo;

public class MockStudRepo implements IStudentRepo {
    private List<Student> studs;

    public MockStudRepo(List<Student> studs) {
        this.studs = studs;
    }

    @Override
    public boolean store(Student s) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'store'");
    }

    @Override
    public List<Student> getAll() {
        return studs;
    }

    @Override
    public int searchStud(Student stud) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchStud'");
    }

    @Override
    public void removeAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }
    
}
