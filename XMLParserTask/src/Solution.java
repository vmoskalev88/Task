import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

public class Solution {

    public static ArrayList<IObject> iObjects = new ArrayList<>();  // Лист для элеметов типа IObject

    public static void main(String[] args) {
        String schemaFolder = System.getenv("SPF_SCHEMA");   // Считываем путь к каталогу из системной переменной
        File storage;

        try {
            storage = new File(schemaFolder);
            fileProcessing(storage);                                // Запускаем поиск документов

            new CompileJSON().writeToJSON(iObjects);

        } catch (NullPointerException e) {
            System.out.println("Переменная не содержит путь к каталогу файлов");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Поиск документов в определенной папке
     *
     * @param folder - путь к коренвому каталогу
     */
    private static void fileProcessing(File folder) throws ParserConfigurationException {
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isDirectory())
                fileProcessing(file);                               // Если найденный элемент каталог, то ищем в нем файлы

            else {                                                  // иначе парсим файл
                String currentFile = file.getAbsolutePath();

                new ParseXML().parseXML(currentFile);
            }
        }
    }
}
