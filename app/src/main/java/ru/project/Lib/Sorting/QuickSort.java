package ru.project.Lib.Sorting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class QuickSort<T> implements SortAlgorithm<T> {

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        quickSort(list, 0, list.size() - 1, comparator);
    }

    private void quickSort(List<T> list, int low, int high, Comparator<T> comp) {
        if (low < high) {
            int p = partition(list, low, high, comp);
            quickSort(list, low, p - 1, comp);
            quickSort(list, p + 1, high, comp);
        }
    }

    private int partition(List<T> list, int low, int high, Comparator<T> comp) {
        T pivot = list.get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comp.compare(list.get(j), pivot) <= 0) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, high);
        return i + 1;
    }
}
