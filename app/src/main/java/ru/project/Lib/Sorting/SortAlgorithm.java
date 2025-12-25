package ru.project.Lib.Sorting;

import java.util.Comparator;
import java.util.List;

// package-private интерфейс для алгоритмов сортировки
interface SortAlgorithm<T> {
    void sort(List<T> list, Comparator<T> comparator);
}