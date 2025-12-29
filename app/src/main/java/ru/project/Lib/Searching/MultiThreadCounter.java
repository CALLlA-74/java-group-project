package ru.project.Lib.Searching;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadCounter {

    @SuppressWarnings("null")
    public static <T> int count(int numThreads, List<T> list, T element) {
        AtomicInteger counter = new AtomicInteger(0);
        Thread[] threads = new Thread[numThreads];

        int chunkSize = list.size() / numThreads;

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, list.size());

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
        return result;
    }
}
