package ru.project.Service.StudService;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;

import ru.project.Lib.Sorting.*;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import ru.project.Config.Config;
import ru.project.Domain.exceptions.BuildingStudentException;
import ru.project.Domain.models.Student;
import ru.project.Domain.models.StudentBuilder;
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
        if (studentRepo == null) {
            throw new IllegalStateException("StudentRepo is not initialized");
        }

        List<Student> students = studentRepo.getAll();
        if (students == null || students.isEmpty()) {
            return;
        }

        sortInternal(students, options);
        writeToLog(students);
    }


    private void sortInternal(List<Student> students, SortOptions options) {

        //Выбор алгоритма
        SortAlgorithm<Student> algorithm = switch (options.getSortAlg()) {
            case BUBBLE -> new BubbleSort<>();
            case QUICK -> new QuickSort<>();
        };

        //Компаратор по ТЗ
        Comparator<Student> comparator = Comparator
                .comparing(Student::getSurname)
                .thenComparing(Student::getName)
                .thenComparing(Student::getGroupId)
                .thenComparing(Student::getAvgRating)
                .thenComparing(Student::getAcheivmentSheetNumber);

        if (options.getDirection() == SortOptions.SortDirections.DESC) {
            comparator = comparator.reversed();
        }

        //DEFAULT / EVEN_ONLY
        if (options.getSortType() == SortOptions.SortTypes.DEFAULT) {
            algorithm.sort(students, comparator);
        } else {
            sortEvenOnly(students, algorithm);
        }

    }

    private void sortEvenOnly(
            List<Student> students,
            SortAlgorithm<Student> algorithm
    ) {
        List<Student> evenStudents = new ArrayList<>();
        List<Integer> evenIndexes = new ArrayList<>();

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            if (s.getAcheivmentSheetNumber() % 2 == 0) {
                evenStudents.add(s);
                evenIndexes.add(i);
            }
        }

        Comparator<Student> evenComparator =
                Comparator.comparingInt(Student::getAcheivmentSheetNumber);

        algorithm.sort(evenStudents, evenComparator);

        for (int i = 0; i < evenIndexes.size(); i++) {
            students.set(evenIndexes.get(i), evenStudents.get(i));
        }
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

    private void writeToLog(List<Student> students) {
        try (FileWriter fw = new FileWriter(Config.logPath, true)) {
            for (Student s : students) {
                fw.write(s.toString());
                fw.write(System.lineSeparator());
            }
            fw.write("-----");
            fw.write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}