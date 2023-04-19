import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Test;

public class TSVtoCategTest {
    @Test
    public void testConvert() throws IOException {
        // Создаем временный файл TSV
        File tempFile = File.createTempFile("test", ".tsv");
        tempFile.deleteOnExit();
        FileWriter writer = new FileWriter(tempFile);
        writer.write("Category\tValue\n");
        writer.write("A\t1\n");
        writer.write("B\t2\n");
        writer.write("A\t2\n");
        writer.write("C\t3\n");
        writer.close();

        // Вызываем метод convert на созданном файле
        TSVtoCateg converter = new TSVtoCateg();
        ArrayList<String> result = converter.convert(tempFile);

        // Проверяем, что возвращенный ArrayList содержит правильные категории
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("Value");
        expected.add("1");
        expected.add("2");
        expected.add("3");
        assertEquals(expected, result);
    }
}