package ru.project.Lib.Sorting;

import java.util.Comparator;
import java.util.List;

public interface SortAlgorithm<T> {
    void sort(List<T> list, Comparator<T> comparator);
}
