package ru.project.Domain.models;

public class Student {
    private String name,
            surname,
            groupId;
    private int acheivmentSheetNumber;
    private float avgRating;

    public Student(String name, String surname, String groupId,
                   int acheivmentSheetNumber, float avgRating) {

        this.name = name;
        this.surname = surname;
        this.groupId = groupId;
        this.acheivmentSheetNumber = acheivmentSheetNumber;
        this.avgRating = avgRating;
    }

    public boolean equals(Object o) {
        if (o instanceof Student) {
            Student s = (Student) o;
            return s.name.equals(name) &&
                    s.surname.equals(surname) &&
                    s.groupId.equals(groupId) &&
                    s.acheivmentSheetNumber == acheivmentSheetNumber &&
                    Math.abs(s.avgRating - avgRating) < 0.01;
        } else return false;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGroupId() {
        return groupId;
    }

    public int getAcheivmentSheetNumber() {
        return acheivmentSheetNumber;
    }

    public float getAvgRating() {
        return avgRating;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(surname).append(" ").append(name).append(System.lineSeparator());
        sb.append("   группа: ").append(groupId).append(System.lineSeparator());
        sb.append("   номер зачетки: ").append(acheivmentSheetNumber).append(System.lineSeparator());
        sb.append("   средний балл: ")
                .append(Math.round(avgRating * 100.0) / 100.0);

        return sb.toString();
    }
}
