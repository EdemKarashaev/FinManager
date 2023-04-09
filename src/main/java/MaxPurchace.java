
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;


public class MaxPurchace {
    JSONObject maxCat = new JSONObject();

    public JSONObject calcMax(JSONObject readJson, ArrayList list) throws IOException, ParseException {

        try {
            Object obj = new JSONParser().parse(new FileReader("data.json"));
            JSONObject jo = (JSONObject) obj;
            String value = (String) readJson.get("title");
            String catG = null;
            for (int p = 0; p < list.size(); p++) {
                if (value.equals(list.get(p))) {
                    catG = (String) list.get(p + 1);
                    break;
                } else {
                    catG = "другое";
                }
            }

            //найти входящий title в файле data.json и сложить значение при нахождении
            Set<String> keys = jo.keySet();
            for (String key : keys) {
                if (key.equals(catG)) {
                    JSONObject val = (JSONObject) jo.get(key);
                    long cost = (Long) readJson.get("cost");
                    Long sum = (Long) val.get("sum");
                    Long newSum = sum + cost;
                    val.put("sum", newSum);
                }
            }

            FileWriter fileWriter = new FileWriter("data.json");
            fileWriter.write(jo.toJSONString());
            fileWriter.close();
            System.out.println("Json обновлен");

            //найти максимальную категорию по сумме значений и отправить ее на сервер
            long maxCategory = 0;

            for (String key : keys) {
                JSONObject categoryObject = (JSONObject) jo.get(key);
                //пройтись по внутренним ключам
                for (Object inner : categoryObject.keySet()) {
                    Long sum = (Long) categoryObject.get("sum");
                    if (sum > maxCategory) {
                        maxCategory=sum;
                        maxCat.put("MaxCategory", categoryObject);
                    } else if (maxCategory==0){
                        maxCat.put("MaxCategory", "Категории пусты");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxCat;
    }
}
