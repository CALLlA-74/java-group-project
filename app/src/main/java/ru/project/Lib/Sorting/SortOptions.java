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
        DEFAULT,    // обычная сортировка
        EVEN_ONLY   // доп. задание №1
    }

    private SortDirections direction;
    private SortAlgs sortAlg;
    private SortTypes sortType;

    public SortOptions(SortDirections direction,
                       SortAlgs sortAlg,
                       SortTypes sortType) {
        this.direction = direction;
        this.sortAlg = sortAlg;
        this.sortType = sortType;
    }

    public SortDirections getDirection() {
        return direction;
    }

    public SortAlgs getSortAlg() {
        return sortAlg;
    }

    public SortTypes getSortType() {
        return sortType;
    }
}
