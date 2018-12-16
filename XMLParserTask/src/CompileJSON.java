import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CompileJSON {

    /**
     * Генерируем .json файл  из листа объектов в папку нашего проекта
     */
    public void writeToJSON(ArrayList<IObject> list) throws IOException {
        // Проверка листа, что в нем есть хотя бы 1 элемент
        if (list.size() < 1) {
            System.out.println("Лист iObjects с элементами пуст!");
            return;
        }

        // Открываем поток, создаем файл куда будем записывать строки
        FileWriter writer = new FileWriter("IObject_attributes.json");

        try {
            // Ниже идет пацанская генерация, согласно JSON формату
            writer.write("{\"response\":[");

            for (int i = 0; i < list.size(); i++) {
                String name = list.get(i).getName();
                String uid = list.get(i).getUid();

                if (i == list.size() - 1) {
                    writer.write("{\"uid\":\"" + uid + "\",\"name\":\"" + name + "\"}");

                } else {
                    writer.write("{\"uid\":\"" + uid + "\",\"name\":\"" + name + "\"},");
                }
            }

            writer.write("]}");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
    }
}
