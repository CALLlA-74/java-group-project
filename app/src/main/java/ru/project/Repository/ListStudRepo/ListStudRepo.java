package ru.project.Repository.ListStudRepo;

import java.util.List;

import ru.project.Config.Config;
import ru.project.Domain.models.Student;
import ru.project.Lib.Searching.MultiThreadCounter;
import ru.project.Repository.IStudentRepo;

public class ListStudRepo implements IStudentRepo {
    private List<Student> students;

    public ListStudRepo(List<Student> students) {
        this.students = students;
    }

    @Override
    public boolean store(Student s) {

        throw new UnsupportedOperationException("Unimplemented method 'store'");
    }

    @Override
    public List<Student> getAll() {

        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public int searchStud(Student student) {
        return MultiThreadCounter.count(Config.threadCount, students, student);
    }

    @Override
    public void removeAll() {

        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

}
