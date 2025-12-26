package ru.project.Service.StudService;

import java.util.List;

import ru.project.Domain.exceptions.BuildingStudentException;
import ru.project.Domain.models.Student;
import ru.project.Domain.models.StudentBuilder;
import ru.project.Lib.Sorting.SortOptions;
import ru.project.Repository.IStudentRepo;
import ru.project.Service.IStudentService;
import ru.project.Service.StringGenerator;

public class StudService implements IStudentService {
    private IStudentRepo studentRepo;
    private StringGenerator generator;

    public StudService(IStudentRepo studentRepo) {
        this.studentRepo = studentRepo;
        generator = new StringGenerator();
    }

    @Override
    public boolean genStuds(int numOfStuds) {
        boolean ans = true;
        for (int i = 0; i < numOfStuds; i++) {
            try {
                ans &= studentRepo.store(
                    new StudentBuilder()
                    .setName(generator.genFirstName())
                    .setSurname(generator.genLastName())
                    .setGroupId(generator.genGroupId())
                    .setAchSheetNum(generator.genSheetNum())
                    .setAvgRating(generator.genRating())
                    .build()
                );
            } catch (BuildingStudentException e) {
                ans = false;
            }
        }

        return ans;
    }

    @Override
    public boolean fillFromFile(String filePath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fillFromFile'");
    }

    @Override
    public boolean addStudent(Student stud) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addStudent'");
    }

    @Override
    public List<Student> getAllStuds() {
        return studentRepo.getAll();
    }

    @Override
    public void sortStuds(SortOptions options) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sortStuds'");
    }

    @Override
    public int searchStud(Student stud) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchStud'");
    }

    @Override
    public void clearStuds() {
        studentRepo.removeAll();
    }
    
}
