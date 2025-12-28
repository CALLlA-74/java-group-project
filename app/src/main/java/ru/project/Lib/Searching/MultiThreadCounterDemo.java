package ru.project.Lib.Searching;

import ru.project.Lib.Searching.MultiThreadCounter;
import ru.project.Config.Config;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//Супер черновой тест уровня и так сойдет
public class MultiThreadCounterDemo {

    public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(2);
        numbers.add(4);
        numbers.add(2);
        numbers.add(5);
        numbers.add(2);

        int elementToFind = 2;
        int numThreads = 3;

        int result = MultiThreadCounter.count(
                numThreads,
                numbers,
                elementToFind
        );

        System.out.println(
                "Элемент " + elementToFind +
                        " найден " + result + " раз(а)"
        );

        writeToLog(numbers, elementToFind, result, numThreads);
    }

    private static void writeToLog(
            List<Integer> list,
            int element,
            int count,
            int threads
    ) {
        try (FileWriter fw = new FileWriter(Config.logPath, true)) {
            fw.write("=== MultiThreadCounter ===");
            fw.write(System.lineSeparator());
            fw.write("Коллекция: " + list);
            fw.write(System.lineSeparator());
            fw.write("Искомый элемент: " + element);
            fw.write(System.lineSeparator());
            fw.write("Потоков: " + threads);
            fw.write(System.lineSeparator());
            fw.write("Количество вхождений: " + count);
            fw.write(System.lineSeparator());
            fw.write("-----");
            fw.write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
