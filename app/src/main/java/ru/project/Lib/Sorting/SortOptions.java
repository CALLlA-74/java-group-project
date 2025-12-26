package ru.project.Lib.Sorting;

public class SortOptions {

    public enum SortDirections {
        ASC, DESC
    }

    public enum SortAlgs {
        BUBBLE, QUICK
    }

    private final SortDirections direction;
    private final SortAlgs algorithm;

    public SortOptions(SortDirections direction, SortAlgs algorithm) {
        this.direction = direction;
        this.algorithm = algorithm;
    }

    public SortDirections getDirection() {
        return direction;
    }

    public SortAlgs getAlgorithm() {
        return algorithm;
    }
}