package ru.project.Lib.Sorting;

import ru.project.Domain.models.Student;
import java.util.List;

public interface IStudentRepo {
    List<Student> sort(List<Student> students, SortOptions options);
}