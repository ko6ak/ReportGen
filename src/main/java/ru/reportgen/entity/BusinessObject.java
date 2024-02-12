package ru.reportgen.entity;

import com.opencsv.bean.CsvBindByPosition;

public class BusinessObject {
    @CsvBindByPosition(position = 0)
    private int id;
    @CsvBindByPosition(position = 1)
    private String name;
    @CsvBindByPosition(position = 2)
    private String description;

    public BusinessObject(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
