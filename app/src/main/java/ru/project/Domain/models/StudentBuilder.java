package ru.project.Domain.models;

import ru.project.Domain.exceptions.BuildingStudentException;

public class StudentBuilder {
    private String name, 
        surname, 
        groupId;
    private int acheivmentSheetNumber = 0;      // номер зачетной книжки
    private float avgRating = 0f;

    public StudentBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public StudentBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public StudentBuilder setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public StudentBuilder setAchSheetNum(int achSheetNum) {
        this.acheivmentSheetNumber = achSheetNum;
        return this;
    }

    public StudentBuilder setAvgRating(float avgRating) {
        this.avgRating = avgRating;
        return this;
    }

    public Student build() throws BuildingStudentException {
        var verdict = validate();
        if (verdict != null && verdict.length() > 0) 
            throw new BuildingStudentException(verdict);

        return new Student(name, surname, groupId, 
            acheivmentSheetNumber, avgRating);
    }

    public String validate() {
        StringBuilder res = new StringBuilder();
        if (name == null || name.length() == 0) 
            res.append("Name must be not empty!\n");
        if (surname == null || surname.length() == 0) 
            res.append("Surname must be not empty!\n");
        if (groupId == null || groupId.length() == 0) 
            res.append("Group id must be not empty!\n");
        if (acheivmentSheetNumber <= 0)
            res.append("Acheivment sheet number must be more than 0!\n");
        if (avgRating < 0)
            res.append("Average rating must be equal or more than 0!\n");
        
        return res.toString();
    }


}
