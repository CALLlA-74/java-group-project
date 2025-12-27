package ru.project;

import java.util.List;

import ru.project.CI.ConsoleInterface;
import ru.project.Domain.models.Student;
import ru.project.Lib.List.LightList;
import ru.project.Repository.IStudentRepo;
import ru.project.Repository.ListStudRepo.ListStudRepo;
import ru.project.Service.IStudentService;
import ru.project.Service.StudService.StudService;

public class App {
    public static void main(String[] args) {
        new App().build();
    }

    private void build() {
        IStudentRepo repo = new ListStudRepo((List<Student>)new LightList<Student>());
        IStudentService service = new StudService(repo);
        var consoleInterface = new ConsoleInterface(service);
        consoleInterface.run();
    }
}
