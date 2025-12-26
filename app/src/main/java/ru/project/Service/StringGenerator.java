package ru.project.Service;

import java.util.Locale;
import java.util.Random;

import com.github.javafaker.Faker;

public class StringGenerator {
    private Faker faker;
    private Random rd;

    private static final String[] deps = {
        "КЭ", "ИУ", "Э", "МТ", "ИБМ", "СМ", "МТ", "Л", "ФН", "РЛ", "РК", "БМТ"
    };

    public StringGenerator() {
        faker = new Faker(new Locale("ru_RU"));
        rd = new Random(System.nanoTime());
    }

    public String genFirstName() {
        return faker.name().firstName();
    }

    public String genLastName() {
        return faker.name().lastName();
    }

    public String genGroupId() {
        int idx = rd.nextInt(0, deps.length);
        int num = rd.nextInt(11, 99);
        return new StringBuilder()
            .append(deps[idx])
            .append(Integer.toString(num))
            .toString();
    }

    public int genSheetNum() {
        return rd.nextInt(111111, 999999);
    }

    public float genRating() {
        return rd.nextFloat(2f, 5f);
    }
}
