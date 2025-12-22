package ru.project.CI;

import ru.project.Domain.exceptions.BuildingStudentException;
import ru.project.Domain.models.StudentBuilder;
import ru.project.Lib.Sorting.SortOptions;
import ru.project.Service.IStudentService;

import java.util.Scanner;

public class ConsoleInterface {
    private final IStudentService studentService;
    private final Scanner scanner;

    public ConsoleInterface(IStudentService studentService) {
        this.scanner = new Scanner(System.in);
        this.studentService = studentService;
    }

    public void run() {
        while(true) {
            System.out.println(studentService.getAllStuds().toString());
            System.out.println("1. заполнение коллекции\n" + "2. сортировка\n" + "3. поиск\n" + "4. очистить " +
                    "коллекцию\n" + "5. выход");
            String userAnswer = scanner.next();
            switch (userAnswer) {
                case "1": {
                    addToCollection();
                    break;
                }
                case "2": {
                    sort();
                    break;
                }
                case "3": {

                    break;
                }
                case "4": {
                    studentService.clearStuds();
                    System.out.flush();
                    break;
                }
                case "5": {
                    return;
                }
                default: {
                    System.out.flush();
                    System.out.println("Пользователь ввел неверную команду");
                }
            }
        }
    }

    private void addToCollection() {
        System.out.flush();
        while (true) {
            System.out.println("1. заполнение из файла.\n" + "2. генерация студентов.\n" + "3. добавить " +
                    "студента вручную.");
            String userAnswer = scanner.next();
            switch (userAnswer) {
                case "1": {
                    System.out.flush();
                    System.out.println("Введите корректный путь к файлу");
                    String path = scanner.next();
                    if (studentService.fillFromFile(path)) {
                        System.out.flush();
                        System.out.println("Студенты успешно добавлены");
                        return;
                    } else {
                        System.out.flush();
                        System.out.println("Пользователь ввел неверный путь к файлу");
                        break;
                    }
                }
                case "2": {
                    System.out.flush();
                    System.out.println("Введите количество генерируемых студентов");
                    String totalString = scanner.next();
                    try {
                        int totalInt = Integer.parseInt(totalString);
                        System.out.flush();
                        if (totalInt > 0) {
                            studentService.genStuds(totalInt);
                            System.out.println("Студенты успешно добавлены");
                            return;
                        } else {
                            System.out.println("Пользователь ввел некорректное число");
                            break;
                        }
                    } catch (NumberFormatException e) {
                            System.out.flush();
                            System.out.println("Пользователь ввел не число");
                            break;
                    }
                }
                case "3": {
                    StudentBuilder studentBuilder = new StudentBuilder();

                    System.out.flush();
                    System.out.println("Введите фамилию студента");
                    String surName = scanner.next();
                    studentBuilder.setSurname(surName);

                    System.out.flush();
                    System.out.println("Введите имя студента");
                    String name = scanner.next();
                    studentBuilder.setName(name);

                    System.out.flush();
                    System.out.println("Введите номер группы");
                    String groupId = scanner.next();
                    studentBuilder.setGroupId(groupId);

                    while (true) {
                        System.out.println("Введите номер зачетки");
                        String documentIdString = scanner.next();
                        try {
                            studentBuilder.setAchSheetNum(Integer.parseInt(documentIdString));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.flush();
                            System.out.println("Пользователь ввел не число");
                        }
                    }

                    while (true) {
                        System.out.println("Введите средний балл");
                        String averageMarkString = scanner.next();
                        try {
                            studentBuilder.setAvgRating(Float.parseFloat(averageMarkString));
                            break;
                        } catch (NumberFormatException e) {
                            System.out.flush();
                            System.out.println("Пользователь ввел не число");
                        }
                    }

                    try {
                        studentService.addStudent(studentBuilder.build());
                        System.out.println("Студент успешно добавлен");
                        return;
                    } catch (BuildingStudentException e) {
                        System.out.flush();
                        System.out.println(e.getMessage());
                        break;
                    }
                }
                default: {
                    System.out.flush();
                    System.out.println("Пользователь ввел неверную команду");
                }
            }
        }
    }

    private void sort() {
        System.out.flush();
        SortOptions.SortAlgs sortAlgs = null;
        SortOptions.SortTypes sortTypes = null;
        SortOptions.SortDirections sortDirection = SortOptions.SortDirections.DESC;
        boolean process = true;
        while (process) {
            System.out.println("Выберите алгоритм сортировки:\n" +"1. First\n" + "2. Second");
            String userAnswer = scanner.next();
            switch (userAnswer) {
                case "1": {
                    sortAlgs = null;
                    process = false;
                    break;
                }
                case "2": {
                    sortAlgs = null;
                    process = false;
                    break;
                }
                default: {
                    System.out.flush();
                    System.out.println("Такого алгоритма нет");
                }
            }
        }
        process = true;
        while (process) {
            System.out.println("Выберите тип сортировки:\n" +"1. по всем полям\n" + "2. по номеру зачетки");
            String userAnswer = scanner.next();
            switch (userAnswer) {
                case "1": {
                    sortTypes = SortOptions.SortTypes.DEFAULT;
                    while (process) {
                        System.out.println("Выберите порядок сортировки:\n" + "1. по возрастанию\n" + "2. по убыванию");
                        String userAnswer2 = scanner.next();
                        switch (userAnswer2) {
                            case "1": {
                                sortDirection = SortOptions.SortDirections.ASC;
                                process = false;
                                break;
                            }
                            case "2": {
                                sortDirection = SortOptions.SortDirections.DESC;
                                process = false;
                                break;
                            }
                            default: {
                                System.out.flush();
                                System.out.println("Такого порядка сортировки нет");
                            }
                        }
                    }
                }
                case "2": {
                    sortTypes = SortOptions.SortTypes.BY_ONE;
                    process = false;
                    break;
                }
                default: {
                    System.out.flush();
                    System.out.println("Такого типа сортировки нет");
                    break;
                }
            }
        }
        studentService.sortStuds(new SortOptions(sortDirection, sortAlgs));
    }

    private void find() {
        StudentBuilder studentBuilder = new StudentBuilder();

        System.out.flush();
        System.out.println("Введите фамилию студента");
        String surName = scanner.next();
        studentBuilder.setSurname(surName);

        System.out.flush();
        System.out.println("Введите имя студента");
        String name = scanner.next();
        studentBuilder.setName(name);

        System.out.flush();
        System.out.println("Введите номер группы");
        String groupId = scanner.next();
        studentBuilder.setGroupId(groupId);

        while (true) {
            System.out.println("Введите номер зачетки");
            String documentIdString = scanner.next();
            try {
                studentBuilder.setAchSheetNum(Integer.parseInt(documentIdString));
                break;
            } catch (NumberFormatException e) {
                System.out.flush();
                System.out.println("Пользователь ввел не число");
            }
        }

        while (true) {
            System.out.println("Введите средний балл");
            String averageMarkString = scanner.next();
            try {
                studentBuilder.setAvgRating(Float.parseFloat(averageMarkString));
                break;
            } catch (NumberFormatException e) {
                System.out.flush();
                System.out.println("Пользователь ввел не число");
            }
        }

        try {
            int count = studentService.searchStud(studentBuilder.build());
            System.out.println("Студентов с подходящим профелем:" + count);
        } catch (BuildingStudentException e) {
            System.out.flush();
            System.out.println(e.getMessage());
        }
    }

}
