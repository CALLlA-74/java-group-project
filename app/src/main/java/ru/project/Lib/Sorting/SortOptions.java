package ru.project.Lib.Sorting;

public class SortOptions {
    public enum SortDirections {
        ASC, DESC
    }

    public enum SortAlgs {

    }

    public enum SortTypes {
        DEFAULT,    // сортировка по всем полям
        BY_ONE      // по одному полю
    }

    private SortDirections orderBy;
    private SortAlgs sortAlg;

    public SortOptions(SortDirections orderBy, SortAlgs sortAlg) {
        this.orderBy = orderBy;
        this.sortAlg = sortAlg;
    }

    public SortAlgs getSortAlg() {
        return sortAlg;
    }

    public SortDirections getSortType() {
        return orderBy;
    }
}
