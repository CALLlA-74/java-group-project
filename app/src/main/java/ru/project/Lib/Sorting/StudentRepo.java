package ru.project.Lib.Sorting;

import ru.project.Domain.models.Student;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StudentRepo implements IStudentRepo {

    @Override
    public List<Student> sort(List<Student> students, SortOptions options) {

        SortAlgorithm<Student> algorithm =
                switch (options.getSortAlg()) {
                    case BUBBLE -> new BubbleSort<>();
                    case QUICK -> new QuickSort<>();
                };

        // Comparator через Reflection
        Comparator<Student> comparator = (s1, s2) -> {
            try {
                Class<Student> cls = Student.class;

                // groupId
                var groupField = cls.getDeclaredField("groupId");
                groupField.setAccessible(true);
                String g1 = (String) groupField.get(s1);
                String g2 = (String) groupField.get(s2);

                int res = g1.compareTo(g2);
                if (res != 0) return res;

                // avgRating
                var ratingField = cls.getDeclaredField("avgRating");
                ratingField.setAccessible(true);
                float r1 = ratingField.getFloat(s1);
                float r2 = ratingField.getFloat(s2);

                res = Float.compare(r1, r2);
                if (res != 0) return res;

                // acheivmentSheetNumber
                var sheetField = cls.getDeclaredField("acheivmentSheetNumber");
                sheetField.setAccessible(true);
                int a1 = sheetField.getInt(s1);
                int a2 = sheetField.getInt(s2);

                return Integer.compare(a1, a2);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        if (options.getSortType() == SortOptions.SortDirections.DESC) {
            comparator = comparator.reversed();
        }

        algorithm.sort(students, comparator);
        return students;
    }
}
