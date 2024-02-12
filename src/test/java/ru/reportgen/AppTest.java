package ru.reportgen;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.reportgen.report.CSVReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AppTest {

    static List<TestObject> objects = new ArrayList<>();
    static List<TestObject_NoAnnotations> objects_1 = new ArrayList<>();
    static Map<String, Object> settings = new HashMap<>();

    @BeforeAll
    static void setUp(){
        objects.add(new TestObject(1, "Obj_1", "Obj_1 description"));
        objects.add(new TestObject(2, "Obj_2", "Obj_2 description"));
        objects_1.add(new TestObject_NoAnnotations(3, "Obj_3", "Obj_3 description"));
        objects_1.add(new TestObject_NoAnnotations(4, "Obj_4", "Obj_4 description"));
        settings.put("CSVResultPath", "D:/123.csv");
    }

    @Test
    void semicolonSeparator() {
        settings.put("separator", ';');
        new CSVReport<>(objects, settings).generate();

        String expected = """
                ID;NAME;DESCRIPTION
                1;Obj_1;Obj_1 description
                2;Obj_2;Obj_2 description""";

        String actual = "";

        try(BufferedReader br = new BufferedReader(new FileReader(String.valueOf(settings.get("CSVResultPath"))))) {
            actual = br.lines().collect(Collectors.joining("\n"));
        }
        catch (IOException e) {e.printStackTrace();}

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void numberSeparator() {
        settings.put("separator", '#');
        new CSVReport<>(objects, settings).generate();

        String expected = """
                ID#NAME#DESCRIPTION
                1#Obj_1#Obj_1 description
                2#Obj_2#Obj_2 description""";

        String actual = "";

        try(BufferedReader br = new BufferedReader(new FileReader(String.valueOf(settings.get("CSVResultPath"))))) {
            actual = br.lines().collect(Collectors.joining("\n"));
        }
        catch (IOException e) {e.printStackTrace();}

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void withoutSettings(){
        new CSVReport<>(objects).generate();

        String expected = """
                ID,NAME,DESCRIPTION
                1,Obj_1,Obj_1 description
                2,Obj_2,Obj_2 description""";

        String actual = "";

        try(BufferedReader br = new BufferedReader(new FileReader(CSVReport.DEFAULT_FILE_PATH.toString()))) {
            actual = br.lines().collect(Collectors.joining("\n"));
        }
        catch (IOException e) {e.printStackTrace();}

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void withoutAnnotationsAndSettings(){
        new CSVReport<>(objects_1).generate();

        String expected = """
                DESCRIPTION,ID,NAME
                Obj_3 description,3,Obj_3
                Obj_4 description,4,Obj_4""";

        String actual = "";

        try(BufferedReader br = new BufferedReader(new FileReader(CSVReport.DEFAULT_FILE_PATH.toString()))) {
            actual = br.lines().collect(Collectors.joining("\n"));
        }
        catch (IOException e) {e.printStackTrace();}

        Assertions.assertEquals(expected, actual);
    }
}
