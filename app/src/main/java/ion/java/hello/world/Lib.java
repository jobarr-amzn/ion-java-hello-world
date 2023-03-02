package ion.java.hello.world;

import com.amazon.ion.IonReader;
import com.amazon.ion.IonType;
import com.amazon.ion.IonWriter;
import com.amazon.ion.system.IonBinaryWriterBuilder;
import com.amazon.ion.system.IonReaderBuilder;
import com.amazon.ion.system.IonTextWriterBuilder;
import com.fasterxml.jackson.dataformat.ion.IonFactory;
import com.fasterxml.jackson.dataformat.ion.IonObjectMapper;
import com.fasterxml.jackson.dataformat.ion.IonParser;
import ion.java.hello.world.App.Record;

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
import java.util.stream.Collectors;

public class Lib {
    public static void processRecords(List<Record> records) {
        if (records != null) {
            records.stream()
                    .collect(Collectors.groupingBy((Record r) -> r.rank))
                    .values()
                    .forEach(l -> {
                        l.stream()
                                .sorted(Comparator.comparing(r -> r.order))
                                .forEach(r -> System.out.print(r.label));
                        System.out.println();
                    });
        } else {
            System.err.println("Looks like there weren't any records loaded.");
            System.exit(1);
        }
    }

}
