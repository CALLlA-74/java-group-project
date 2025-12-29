package ru.project.Lib.Searching;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import ru.project.Config.Config;

public class MultiThreadCounterTest {
    @Test public void countTest() {
        int numOfVals = 2500;
        List<Integer> numbers = new ArrayList<>(numOfVals);
        var rd = new Random(System.nanoTime());

        for (int i = 0; i < numOfVals; i++) {
            numbers.add(Integer.valueOf(rd.nextInt(1, 100)));
        }

        int scrhNum = rd.nextInt(1, 100);
        int countOf = 0;
        for (int i = 10; i < numOfVals; i+=2) {
            numbers.set(i, Integer.valueOf(scrhNum));
        }

        for (var v : numbers) {
            if (v.equals(Integer.valueOf(scrhNum))) countOf++;
        }

        assert(
            MultiThreadCounter.count(
                Config.threadCount, 
                numbers, 
                Integer.valueOf(scrhNum)
            ) == countOf);
    }
}
