package ru.project.Repository;

import ru.project.Domain.models.Student;
import ru.project.Lib.Sorting.SortOptions;
import ru.project.Lib.Sorting.SortAlgorithm;
import ru.project.Lib.Sorting.BubbleSort;
import ru.project.Lib.Sorting.QuickSort;

import java.util.Comparator;
import java.util.List;
import java.util.Collections;

public class StudentRepo implements IStudentRepo {

    @Override
    public List<Student> sort(List<Student> students, SortOptions options) {

        SortAlgorithm<Student> algorithm =
                switch (options.getAlgorithm()) {
                    case BUBBLE -> new BubbleSort<>();
                    case QUICK -> new QuickSort<>();
                };

        Comparator<Student> comparator =
                Comparator.comparing(Student::getGroupId)
                        .thenComparing(Student::getAvgRating)
                        .thenComparing(Student::getAcheivmentSheetNumber);

        if (options.getDirection() == SortOptions.SortDirections.DESC) {
            comparator = comparator.reversed();
        }

        algorithm.sort(students, comparator);
        return students;
    }
}