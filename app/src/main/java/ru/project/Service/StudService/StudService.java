package ru.project.Service.StudService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
        var data = new byte[0];
        try {
            data = Files.readAllBytes(Paths.get(filePath));
        } catch (IOException e) {
            return false;
        }
        if (data.length == 0) return false;

        Gson gson = new Gson();
        var listType = new TypeToken<List<Student>>(){}.getType();
        var ans = true;

        try {
            List<Student> studs = gson.fromJson(new String(data), listType);
            
            for (var s : studs) 
                ans &= studentRepo.store(s);
        } catch (JsonSyntaxException e) {
            return false;
        }

        return ans;
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
