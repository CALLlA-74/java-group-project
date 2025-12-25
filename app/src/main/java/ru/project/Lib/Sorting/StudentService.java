package ru.project.Lib.Sorting;

import ru.project.Domain.models.Student;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class StudentService implements IStudentService {

    private final IStudentRepo repo;

    public StudentService(IStudentRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<Student> sortAndLog(List<Student> students, SortOptions options) {
        List<Student> sorted = repo.sort(students, options);
        writeToLog(sorted);
        return sorted;
    }

    private void writeToLog(List<Student> students) {
        try (FileWriter fw = new FileWriter(Config.LOG_PATH, true)) {
            for (Student s : students) {
                fw.write(s.toString() + System.lineSeparator());
            }
            fw.write("-----" + System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
