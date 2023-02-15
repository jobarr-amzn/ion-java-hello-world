package ion.java.hello.world;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Lib {
    public static void processRecords(List<App.Record> records) {
        if (records != null) {
            records.stream()
                    .collect(Collectors.groupingBy((App.Record r) -> r.rank))
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
