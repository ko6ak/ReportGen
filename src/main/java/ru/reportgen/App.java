package ru.reportgen;

import ru.reportgen.entity.BusinessObject;
import ru.reportgen.report.CSVReport;
import ru.reportgen.report.Report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main( String[] args ) {

        List<BusinessObject> objects = new ArrayList<>();
        objects.add(new BusinessObject(1, "Obj_1", "Obj_1 description"));
        objects.add(new BusinessObject(2, "Obj_2", "Obj_2 description"));

        Map<String, Object> settings = new HashMap<>();
        settings.put("separator", ';');
        settings.put("CSVResultPath", "D:/123.csv");

        Report csv = new CSVReport<>(objects, settings);
        csv.generate();
    }
}
