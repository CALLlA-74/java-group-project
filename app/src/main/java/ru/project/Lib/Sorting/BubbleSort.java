package ru.project.Lib.Sorting;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BubbleSort<T> implements SortAlgorithm<T> {

    @Override
    public void sort(List<T> list, Comparator<T> comparator) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    T tmp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tmp);
                }
            }
        }
    }
}