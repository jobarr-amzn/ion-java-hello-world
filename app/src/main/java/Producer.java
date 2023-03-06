import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Producer {
    static InputStream binaryIonRecordStream() throws FileNotFoundException {
        return new FileInputStream("data/out.10n");
    }

    static InputStream textIonRecordStream() throws FileNotFoundException {
        return new FileInputStream("data/out.ion");
    }
}
