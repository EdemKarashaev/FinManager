import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxPurchaceTest {

    @Test
    public void testCalcMax() throws IOException, ParseException, IOException {

        // Создаем временный файл data.json
        File tempFile = File.createTempFile("test", ".json");
        tempFile.deleteOnExit();
        FileWriter writer = new FileWriter(tempFile);
        writer.write("{\"еда\":{\"sum\":0},\"одежда\":{\"sum\":0}}");
        writer.close();

        // Создаем объект MaxPurchace
        MaxPurchace maxPurchace = new MaxPurchace();

        // Создаем JSON-объект для передачи в метод calcMax()
        JSONObject readJson = new JSONObject();
        readJson.put("title", "еда");
        readJson.put("cost", 50);

        // Создаем список категорий для поиска
        ArrayList<String> categories = new ArrayList<>();
        categories.add("еда");
        categories.add("Категория A");
        categories.add("одежда");
        categories.add("Категория B");

        // Вызываем метод calcMax на созданных данных
        JSONObject result = maxPurchace.calcMax(readJson, categories);

        // Проверяем, что максимальная категория по сумме значений была правильно рассчитана
        JSONObject expected = new JSONObject();
        JSONObject maxCategory = new JSONObject();
        maxCategory.put("sum", 50);
        expected.put("MaxCategory", maxCategory);
        assertEquals(expected, result);
    }
}