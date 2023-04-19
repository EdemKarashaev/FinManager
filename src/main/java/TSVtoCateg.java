import java.io.*;
import java.util.ArrayList;

public class TSVtoCateg {
   private ArrayList<String> forJson = new ArrayList<>();
    private ArrayList<String> fields = new ArrayList<>();

    public ArrayList<String> convert(File file) throws IOException {
         BufferedReader reader = new BufferedReader(new FileReader(file));
         String line;
         ArrayList<String[]> rows = new ArrayList<>();
         String[] row;
        while ((line = reader.readLine()) != null) {
            row = line.split("\\n");
            rows.add(row);
        }
        for (String[] elem : rows) {
            String[] bn = elem[0].split("\\t");
            fields.add(bn[0]);
            fields.add(bn[1]);
        }
        //заполнение аррэйлиста данными из tsv, вычленяем категории
        forJson.add(fields.get(1));
        for (int i = 1; i <= fields.size(); i += 2) {
            String category = fields.get(i);
            if (!forJson.contains(category)) {
                forJson.add(category);
            }
        }
        // ArrayList для отправки обратно
        return forJson;
    }

    public ArrayList<String> getFields() {
        return fields;
    }
}

