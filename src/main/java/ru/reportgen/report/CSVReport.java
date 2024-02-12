package ru.reportgen.report;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CSVReport<T> implements Report {
    public static final Path DEFAULT_FILE_PATH = Paths.get(System.getProperty("user.home") + "\\" + "result.csv");

    private final Map<String, Object> settings;
    private final List<T> list;
    private char separator = CSVWriter.DEFAULT_SEPARATOR;
    private Path CSVResultPath = DEFAULT_FILE_PATH;

    public CSVReport(List<T> list) {
        this.list = list;
        settings = new HashMap<>();
    }

    public CSVReport(List<T> list, Map<String, Object> settings) {
        this.list = list;
        this.settings = settings;
    }

    @Override
    public void generate() {
        if (list.isEmpty() || list.contains(null)) {
            System.out.println("Empty list or list contain null.");
            return;
        }

        parseSettings();

        try(Writer writer = Files.newBufferedWriter(CSVResultPath)) {

            String header = Arrays.stream(list.get(0).getClass().getDeclaredFields())
                    .filter(field -> field.isAnnotationPresent(CsvBindByPosition.class))
                    .sorted(Comparator.comparingInt(field -> field.getAnnotation(CsvBindByPosition.class).position()))
                    .map(field -> field.getName().toUpperCase())
                    .collect(Collectors.joining(String.valueOf(separator)));

            if (!header.isEmpty()) writer.write(header + "\n");

            StatefulBeanToCsv<T> csvWriter = new StatefulBeanToCsvBuilder<T>(writer)
                    .withSeparator(separator)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withLineEnd(CSVWriter.DEFAULT_LINE_END)
                    .build();

            csvWriter.write(list);

        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }

    private void parseSettings() {
        if (settings != null) {
            if (settings.containsKey("separator")) separator = (char) settings.get("separator");
            if (settings.containsKey("CSVResultPath")) CSVResultPath = Paths.get((String) settings.get("CSVResultPath"));
        }
    }
}
