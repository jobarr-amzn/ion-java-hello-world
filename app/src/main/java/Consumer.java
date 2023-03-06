import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Consumer {
    public static void processRecords(List<App.Record> records) {
        if (records == null || records.isEmpty()) {
            System.err.println("Looks like there weren't any records loaded.");
        } else {
            records.stream()
                    .collect(Collectors.groupingBy(r -> r.rank))
                    .values()
                    .forEach(l -> {
                        l.stream()
                                .sorted(Comparator.comparing(r -> r.order))
                                .forEach(r -> System.out.print(r.label));
                        System.out.println();
                    });
        }
    }
}
