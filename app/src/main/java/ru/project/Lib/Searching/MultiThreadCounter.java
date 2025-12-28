package ru.project.Lib.Searching;

import ru.project.Config.Config;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadCounter {

    public static <T> int count(int numThreads, List<T> list, T element) {
        AtomicInteger counter = new AtomicInteger(0);
        Thread[] threads = new Thread[numThreads];

        int chunkSize = list.size() / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1)
                    ? list.size()
                    : start + chunkSize;

            threads[i] = new Thread(() -> {
                for (int j = start; j < end; j++) {
                    if (list.get(j).equals(element)) {
                        counter.incrementAndGet();
                    }
                }
            });

            threads[i].start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        int result = counter.get();

        System.out.println(
                "Количество вхождений элемента: " + result
        );

        //логирование в файл
        writeToLog(element, result, numThreads);

        return result;
    }

    private static <T> void writeToLog(T element, int count, int numThreads) {
        try (FileWriter fw = new FileWriter(Config.logPath, true)) {
            fw.write("Поиск элемента: " + element.toString());
            fw.write(System.lineSeparator());
            fw.write("Количество вхождений: " + count);
            fw.write(System.lineSeparator());
            fw.write("Потоков использовано: " + numThreads);
            fw.write(System.lineSeparator());
            fw.write("-----");
            fw.write(System.lineSeparator());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
