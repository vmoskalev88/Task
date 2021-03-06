import java.io.FileWriter;
import java.util.ArrayList;

public class CompilerJSON {

    /**
     * Генерируем .json файл  из листа объектов в папку нашего проекта
     */
    public static void writeToJSON(ArrayList<IObject> list) {
        // Проверка листа, что в нем есть хотя бы 1 элемент
        if (list.size() < 1) {
            System.out.println("Лист iObjects с элементами пуст!");
            return;
        }

        // Открываем поток, создаем файл куда будем записывать строки
        try (FileWriter writer = new FileWriter("IObject_attributes.json")) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
