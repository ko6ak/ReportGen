package ru.reportgen;

import com.opencsv.bean.CsvBindByPosition;

public class TestObject {
    @CsvBindByPosition(position = 0)
    private int id;
    @CsvBindByPosition(position = 1)
    private String name;
    @CsvBindByPosition(position = 2)
    private String description;

    public TestObject(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
