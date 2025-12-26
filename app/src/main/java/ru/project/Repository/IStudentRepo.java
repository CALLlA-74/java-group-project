package ru.project.Repository;

import java.util.List;

import ru.project.Domain.models.Student;

public interface IStudentRepo {

    /**
     * Сохраняет профиль студента в репозитории
     * @param s -- сохраняемый профиль студента
     * @return true -- при успешном добавлении
     */
    public boolean store(Student s);

    /**
     * @return возвращает коллекцию всех студентов
     */
    public List<Student> getAll();

    /**
     *
     * @param stud
     * @return
     */
    public int searchStud(Student stud);

    /**
     * Очищает содержимое репозитория
     */
    public void removeAll();
}