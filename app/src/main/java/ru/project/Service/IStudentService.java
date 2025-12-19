package ru.project.Service;

import java.util.List;

import ru.project.Domain.models.Student;
import ru.project.Lib.Sorting.SortOptions;

public interface IStudentService {
    /**
     * Генерирует заданное количество студентов и добавляет их в конец коллекции
     * @param numOfStuds -- количество студентов
     * @return -- true при успешной генерации
     */
    public boolean genStuds(int numOfStuds);

    /**
     * Извлекает информацию о студентах из указанного файла и добавляет их 
     * в конец коллекции
     * @param filePath -- путь к файлу
     * @return -- true при успешной генерации
     */
    public boolean fillFromFile(String filePath);

    /**
     * Добавляет студента в конец коллекции
     * @param stud -- описание полей профиля добавляемого студента
     * @return -- true при успешном добавлении
     */
    public boolean addStudent(Student stud);

    /**
     * @return -- возвращает коллекцию всех студентов
     */
    public List<Student> getAllStuds();

    /**
     * Выполняет сортировку коллекции согласно заданным параметрам
     * @param options -- параметры сортировки
     */
    public void sortStuds(SortOptions options);

    /**
     * @param stud -- "ключ" для поиска
     * @return -- количество студентов, равных по значениям полей "ключу"
     */
    public int searchStud(Student stud);
    
    /**
     * Очищает коллекцию студентов
     */
    public void clearStuds();
}
