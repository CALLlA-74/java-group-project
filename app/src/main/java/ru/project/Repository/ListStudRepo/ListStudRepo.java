package ru.project.Repository.ListStudRepo;

import java.util.List;
import java.util.stream.Stream;

import ru.project.Domain.models.Student;
import ru.project.Repository.IStudentRepo;

public class ListStudRepo implements IStudentRepo {
   private List<Student> students;

   public ListStudRepo(List<Student> students) {
        this.students = students;
    }

    @Override
    public boolean store(Student s) {
        /*students = new LightList<>(
            (Student[])Stream
            .concat(students.stream(), Stream.of(s))
            .toArray()
        );*/

        Stream.of(s).forEach(stud -> students.add(stud));
        return true;
    }

    @Override
    public List<Student> getAll() {
        return students;
    }

    @Override
    public int searchStud(Student stud) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'searchStud'");
    }

    @Override
    public void removeAll() {
        students.clear();
    }
    
}
