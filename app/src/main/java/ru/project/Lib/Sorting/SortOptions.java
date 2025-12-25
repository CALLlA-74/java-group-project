package ru.project.Lib.Sorting;

public class SortOptions {

    public enum SortDirections {
        ASC, DESC
    }

    public enum SortAlgs {
        BUBBLE,
        QUICK
    }

    public enum SortTypes {
        DEFAULT,
        BY_ONE
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
