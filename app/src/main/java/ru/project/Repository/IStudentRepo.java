package ru.project.Repository;

import ru.project.Domain.models.Student;
import ru.project.Lib.Sorting.SortOptions;

import java.util.List;

public interface IStudentRepo {
    List<Student> sort(List<Student> students, SortOptions options);
}
