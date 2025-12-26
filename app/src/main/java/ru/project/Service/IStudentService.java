package ru.project.Service;

import ru.project.Domain.models.Student;
import ru.project.Lib.Sorting.SortOptions;

import java.util.List;

public interface IStudentService {
    List<Student> sortAndLog(List<Student> students, SortOptions options);
}