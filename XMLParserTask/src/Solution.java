import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Solution {

    private static ArrayList<IObject> iObjects = new ArrayList<>();  // Лист для элеметов типа IObject

    public static ArrayList<IObject> getIObjects() {
        return iObjects;
    }

    public static void main(String[] args) {
        final String schemaFolder = System.getenv("SPF_SCHEMA");   // Считываем путь к каталогу из системной переменной

        if (schemaFolder != null) {                                      // Если переменная окружения существует
            File storage;

            try {
                storage = new File(schemaFolder);
                Path storagePath = storage.toPath();

                Searcher searcher = new Searcher();
                Files.walkFileTree(storagePath, searcher);               // Запускаем поиск xml файлов по каталогу

                CompilerJSON.writeToJSON(iObjects);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else System.err.println("Переменная не содержит путь к каталогу файлов");
    }
}
