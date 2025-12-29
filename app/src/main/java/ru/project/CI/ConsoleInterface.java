package ru.project.CI;

import ru.project.Domain.exceptions.BuildingStudentException;
import ru.project.Domain.models.Student;
import ru.project.Domain.models.StudentBuilder;
import ru.project.Lib.Sorting.SortOptions;
import ru.project.Service.IStudentService;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ConsoleInterface {
    private final IStudentService studentService;
    private final Scanner scanner;

    public ConsoleInterface(IStudentService studentService) {
        this.studentService = studentService;

        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        try {
            System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            printAllStudents();
            printMainMenu();
            String userAnswer = scanner.nextLine();
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
                    find();
                    break;
                }
                case "4": {
                    clearCollection();
                    break;
                }
                case "5": {
                    return;
                }
                default: {
                    clearConsole();
                    System.out.println("Пользователь ввел неверную команду");
                }
            }
        }
    }

    private void addToCollection() {
        clearConsole();
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("1. заполнение из файла.");
            System.out.println("2. генерация студентов.");
            System.out.println("3. добавить студента вручную.");
            String userAnswer = scanner.nextLine();
            switch (userAnswer) {
                case "1": {
                    isRunning = addStudentsFromFile();
                    break;
                }
                case "2": {
                    isRunning = generateStudents();
                    break;
                }
                case "3": {
                    isRunning = checkValidationStudent(createStudent());
                    break;
                }
                default: {
                    clearConsole();
                    System.out.println("Пользователь ввел неверную команду");
                }
            }
        }
    }

    private void sort() {
        clearConsole();
        SortOptions.SortAlgs sortAlgs = choosingAlgorithm();
        SortOptions.SortTypes sortType = choosingSortType();
        SortOptions.SortDirections sortDirection = SortOptions.SortDirections.ASC;
        if (sortType == SortOptions.SortTypes.DEFAULT) {
            sortDirection = choosingSortDirection();
        }
        studentService.sortStuds(new SortOptions(sortDirection, sortAlgs, sortType));
    }
    
    private void find() {
        StudentBuilder studentBuilder = createStudent();
        try {
            int count = studentService.searchStud(studentBuilder.build());
            System.out.println("Студентов с подходящим профилем:" + count);
        } catch (BuildingStudentException e) {
            clearConsole();
            System.out.println(e.getMessage());
        }
    }

    private void clearCollection() {
        studentService.clearStuds();
        clearConsole();
    }

    private boolean checkValidationStudent(StudentBuilder studentBuilder) {
        try {
            studentService.addStudent(studentBuilder.build());
            System.out.println("Студент успешно добавлен");
            return false;
        } catch (BuildingStudentException e) {
            clearConsole();
            System.out.println(e.getMessage());
            return true;
        }
    }

    private StudentBuilder createStudent() {
        StudentBuilder studentBuilder = new StudentBuilder();

        clearConsole();
        System.out.println("Введите фамилию студента");
        String surName = scanner.nextLine();
        studentBuilder.setSurname(surName);

        clearConsole();
        System.out.println("Введите имя студента");
        String name = scanner.nextLine();
        studentBuilder.setName(name);

        clearConsole();
        System.out.println("Введите номер группы");
        String groupId = scanner.nextLine();
        studentBuilder.setGroupId(groupId);

        while (true) {
            clearConsole();
            System.out.println("Введите номер зачетки");
            String documentIdString = scanner.nextLine();
            try {
                studentBuilder.setAchSheetNum(Integer.parseInt(documentIdString));
                break;
            } catch (NumberFormatException e) {
                clearConsole();
                System.out.println("Пользователь ввел не число");
            }
        }

        while (true) {
            clearConsole();
            System.out.println("Введите средний балл");
            String averageMarkString = scanner.nextLine();
            try {
                studentBuilder.setAvgRating(Float.parseFloat(averageMarkString));
                break;
            } catch (NumberFormatException e) {
                clearConsole();
                System.out.println("Пользователь ввел не число");
            }
        }
        return studentBuilder;
    }

    private boolean addStudentsFromFile() {
        clearConsole();
        System.out.println("Введите корректный путь к файлу");
        String path = scanner.nextLine();
        if (studentService.fillFromFile(path)) {
            clearConsole();
            System.out.println("Студенты успешно добавлены");
            return false;
        } else {
            clearConsole();
            System.out.println("Пользователь ввел неверный путь к файлу");
            return true;
        }
    }

    private boolean generateStudents() {
        clearConsole();
        System.out.println("Введите количество генерируемых студентов");
        String totalString = scanner.nextLine();
        try {
            int totalInt = Integer.parseInt(totalString);
            clearConsole();
            if (totalInt > 0) {
                studentService.genStuds(totalInt);
                System.out.println("Студенты успешно добавлены");
                return false;
            } else {
                System.out.println("Пользователь ввел некорректное число");
                return true;
            }
        } catch (NumberFormatException e) {
            clearConsole();
            System.out.println("Пользователь ввел не число");
            return true;
        }
    }

    private SortOptions.SortAlgs choosingAlgorithm() {
        boolean isRunning = true;
        SortOptions.SortAlgs sortAlgs = SortOptions.SortAlgs.BUBBLE;
        while (isRunning) {
            System.out.println("Выберите алгоритм сортировки:");
            System.out.println("1. Bubble");
            System.out.println("2. Quick");
            String userAnswer = scanner.nextLine();
            switch (userAnswer) {
                case "1": {
                    isRunning = false;
                    break;
                }
                case "2": {
                    sortAlgs = SortOptions.SortAlgs.QUICK;
                    isRunning = false;
                    break;
                }
                default: {
                    clearConsole();
                    System.out.println("Такого алгоритма нет");
                }
            }
        }
        return sortAlgs;
    }

    private SortOptions.SortTypes choosingSortType() {
        SortOptions.SortTypes sortType = SortOptions.SortTypes.DEFAULT;
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("Выберите тип сортировки:");
            System.out.println("1. по всем полям");
            System.out.println("2. по номеру зачетки");
            String userAnswer = scanner.nextLine();
            switch (userAnswer) {
                case "1": {
                    isRunning = false;
                    break;
                }
                case "2": {
                    sortType = SortOptions.SortTypes.EVEN_ONLY;
                    isRunning = false;
                    break;
                }
                default: {
                    clearConsole();
                    System.out.println("Такого типа сортировки нет");
                    break;
                }
            }
        }
        return sortType;
    }

    private SortOptions.SortDirections choosingSortDirection() {
        boolean isRunning = true;
        SortOptions.SortDirections sortDirection = SortOptions.SortDirections.ASC;
        while (isRunning) {
            System.out.println("Выберите порядок сортировки:");
            System.out.println("1. по возрастанию");
            System.out.println("2. по убыванию");
            String userAnswer2 = scanner.nextLine();
            switch (userAnswer2) {
                case "1": {
                    isRunning = false;
                    break;
                }
                case "2": {
                    sortDirection = SortOptions.SortDirections.DESC;
                    isRunning = false;
                    break;
                }
                default: {
                    clearConsole();
                    System.out.println("Такого порядка сортировки нет");
                }
            }
        }
        return sortDirection;
    }

    private void clearConsole() {
        System.out.println("\n".repeat(50));
    }

    private void printAllStudents() {
        int index = 1;
        for (Student student : studentService.getAllStuds()) {
            System.out.println(index + ". " + student.toString());
            ++index;
        }
    }

    private void printMainMenu() {
        System.out.println("1. заполнение коллекции");
        System.out.println("2. сортировка");
        System.out.println("3. поиск");
        System.out.println("4. очистить коллекцию");
        System.out.println("5. выход");
    }
}
