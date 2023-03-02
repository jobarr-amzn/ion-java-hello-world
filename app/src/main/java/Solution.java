
import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.system.IonBinaryWriterBuilder;
import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.system.IonTextWriterBuilder;
import com.fasterxml.jackson.dataformat.ion.IonFactory;
import com.fasterxml.jackson.dataformat.ion.IonObjectMapper;
import com.fasterxml.jackson.dataformat.ion.IonParser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class Solution {


    static List<App.Record> getRecordsJackson(Supplier<InputStream> supplier) throws Exception {
        IonObjectMapper mapper = new IonObjectMapper();

        try (InputStream in = supplier.get();
             IonReader reader = IonReaderBuilder.standard().build(in);
             IonParser parser = new IonFactory().createParser(reader))
        {
            return mapper.readValues(parser, App.Record.class).readAll();
        }
    }

    static List<App.Record> getRecordsReader(Supplier<InputStream> supplier) throws Exception {

        try (InputStream in = supplier.get();
             IonReader reader = IonReaderBuilder.standard().build(in))
        {
            List<App.Record> records = new ArrayList<>();
            while (reader.next() != null) {
                expectType(reader, IonType.STRUCT);
                App.Record r = new App.Record();
                reader.stepIn();
                while (reader.next() != null) {
                    switch (reader.getFieldName()) {
                        case "label": r.label = reader.stringValue();  break;
                        case "value": r.value = reader.decimalValue(); break;
                        case  "rank": r.rank  = reader.intValue();     break;
                        case "order": r.order = reader.intValue();     break;
                        default: illegalState("Found unexpected field name '%s'", reader.getFieldName());
                    }
                }
                reader.stepOut();
                records.add(r);
            }
            return records;
        }
    }

    private static void expectType(IonReader reader, IonType expected) {
        IonType actual = reader.getType();
        if (actual != expected) {
            illegalState("Expected to find a %s found %s instead", expected, actual);
        }
    }

    private static void illegalState(String fmt, Object... args) {
        throw new IllegalStateException(String.format(fmt, args));
    }

    static List<App.Record> regenerate() throws Exception {
        List<App.Record> records = readRaw("hello_world.txt");
        records.sort(Comparator.comparing(r -> r.value, Comparator.naturalOrder()));

        writeAsIon("out.ion", records, false);
        writeAsIon("out.10n", records, true);

        return records;
    }

    static List<App.Record> readRaw(String filename) throws IOException {
        List<App.Record> records = new ArrayList<>();
        Map<String, Integer> counts = new HashMap<>();

        List<String> lines = Files.readAllLines(Paths.get(filename));
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < line.length(); j++) {
                String fragment = line.substring(j, j + 1);
                double count = counts.compute(fragment, (k, v) -> v == null ? 1 : v + 1);
                double categoryValue = records.size() / (double) counts.keySet().size();

                App.Record r = new App.Record();
                r.label = fragment;
                r.rank = i;
                r.order = j;
                r.value = BigDecimal.valueOf(categoryValue / count).setScale(2, RoundingMode.HALF_UP);

                records.add(r);
            }
        }

        return records;
    }

    static void writeAsIon(String fileName, List<App.Record> records, boolean useBinary) throws Exception {
        IonObjectMapper mapper = new IonObjectMapper();
        mapper.setCreateBinaryWriters(useBinary);

        try (OutputStream out = new FileOutputStream(fileName);
             IonWriter writer = getIonWriter(useBinary, out))
        {
            for (App.Record r : records) {
                mapper.writeValue(writer, r);
            }
        }
    }

    static IonWriter getIonWriter(boolean useBinary, OutputStream out) {
        return useBinary ? IonBinaryWriterBuilder.standard().build(out)
                : IonTextWriterBuilder.standard().build(out);
    }
}
