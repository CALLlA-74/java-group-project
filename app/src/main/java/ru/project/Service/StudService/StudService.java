package ru.project.Service.StudService;

import java.util.List;

import ru.project.Domain.models.Student;
import ru.project.Lib.Sorting.SortOptions;
import ru.project.Repository.IStudentRepo;
import ru.project.Repository.ListStudRepo.ListStudRepo;
import ru.project.Service.IStudentService;

public class StudService implements IStudentService {
    private IStudentRepo studentRepo;

    public StudService(IStudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public boolean genStuds(int numOfStuds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'genStuds'");
    }

    @Override
    public boolean fillFromFile(String filePath) {
        try {
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean addStudent(Student stud) {
        return studentRepo.store(stud);
    }

    @Override
    public List<Student> getAllStuds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllStuds'");
    }

    @Override
    public void sortStuds(SortOptions options) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sortStuds'");
    }

    @Override
    public int searchStud(Student stud) {
        int result = studentRepo.searchStud(stud);
        logResult(result);
        return result;
    }

    private void logResult(int result) {
    }

    @Override
    public void clearStuds() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clearStuds'");
    }
    
}
