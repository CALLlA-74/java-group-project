package ru.project.TestCI;

import ru.project.Domain.models.Student;
import ru.project.Lib.Sorting.SortOptions;
import ru.project.Service.IStudentService;

import java.util.Collections;
import java.util.List;

public class StudentServiceStub implements IStudentService {

    public boolean wasClearCalled = false;
    public String lastFilePath = null;
    public int generatedCount = 0;
    public Student lastAddedStudent = null;
    public SortOptions lastSortOptions = null;
    public Student lastSearchTemplate = null;

    @Override
    public List<Student> getAllStuds() {
        return Collections.emptyList();
    }

    @Override
    public void clearStuds() {
        this.wasClearCalled = true;
    }

    @Override
    public boolean addStudent(Student student) {
        this.lastAddedStudent = student;
        return true;
    }

    @Override
    public boolean fillFromFile(String path) {
        this.lastFilePath = path;
        return true;
    }

    @Override
    public boolean genStuds(int count) {
        this.generatedCount = count;
        return true;
    }

    @Override
    public void sortStuds(SortOptions options) {
        this.lastSortOptions = options;
    }

    @Override
    public int searchStud(Student template) {
        this.lastSearchTemplate = template;
        return 5;
    }
}