package ru.project.Lib.Sorting;

import ru.project.Domain.models.Student;
import java.util.List;

public interface IStudentService {
    List<Student> sortAndLog(List<Student> students, SortOptions options);
}