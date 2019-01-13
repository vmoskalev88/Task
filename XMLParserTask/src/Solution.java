import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

public class Solution {

    private static ArrayList<IObject> iObjects = new ArrayList<>();  // Лист для элеметов типа IObject

    public static void addToIObjectsList(IObject object) {           // Метод для добавления объекта в лист iObjects
        iObjects.add(object);
    }

    public static void main(String[] args) {
        final String schemaFolder = System.getenv("SPF_SCHEMA");   // Считываем путь к каталогу из системной переменной
        File storage;

        try {
            storage = new File(schemaFolder);
            processFile(storage);                                // Запускаем поиск документов

            CompilerJSON.writeToJSON(iObjects);

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
    private static void processFile(File folder) throws ParserConfigurationException {
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isDirectory())
                processFile(file);                               // Если найденный элемент каталог, то ищем в нем файлы

            else if (file.getName().toLowerCase().endsWith(".xml")) {              // иначе парсим xml файл
                String currentFile = file.getAbsolutePath();

                ParserXML.parseXML(currentFile);
            }
        }
    }
}

//    комментарии разработчика:
//     +  0. Имена классов должны быть существительными, имена методов - глаголами
//     +  1. Публичные изменяемые поля - нарушение принципа инкапсуляции (в данном случае это ещё и непотокобезопасно).
//           Это про лист iObjects?
//        2. Вместо ловли NPE в блоке catch лучше в явном виде проверить переменную на !=null
//        3. Обход дерева файлов удобнее и производительнее делать с использованием NIO.2 API
//           (см. https://docs.oracle.com/javase/tutorial/essential/io/walk.html),
//           а работать с I/O - используя try-with-resources
//           (https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html)
//     +? 4. Если в папке будет файл с расширением .XML, данные из него не попадут в выходной JSON
//     +  5. Если класс не имеет полей, то может все его методы стоит сделать статическими
//           (чтобы для их вызова не приходилось создавать инстанс этого класса)?
