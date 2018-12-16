import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;

public class Solution {

    private static ArrayList<IObject> iObjects = new ArrayList<>(); // Лист для элеметов типа IObject

    public static void main(String[] args) {
        String schemaFolder = System.getenv("SPF_SCHEMA");   // Считываем путь к каталогу из системной переменной
        File storage;

        try {
            storage = new File(schemaFolder);
            fileProcessing(storage);                                // Запускаем поиск документов
            writeToJSON();

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
    public static void fileProcessing(File folder) throws IOException, SAXException, ParserConfigurationException {
        File[] files = folder.listFiles();

        for (File file : files) {
            if (file.isDirectory()) fileProcessing(file);           // Если найденный элемент каталог, то ищем в нем файлы

            else {                                                  // иначе парсим файл
                String currentFile = file.getAbsolutePath();

                parseXML(currentFile);                              // Запускаем парсес XML файла
            }
        }
    }

    /**
     * Метод для обработки XML файла.
     * <p>
     * Выбираем атрибуты UID и Name у элемента IObject, дочки элемента EnumEnum и сохраняем их в новый объекст IObject,
     * после чего заносим обеъкт в лист
     *
     * @param currentFile - полный путь к XML файлу
     */
    public static void parseXML(String currentFile) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new File(currentFile));

        // Ищем элементы по тегу
        NodeList iObjectElements = document.getDocumentElement().getElementsByTagName("IObject");

        for (int i = 0; i < iObjectElements.getLength(); i++) {
            Node iObject = iObjectElements.item(i);

            // Проверяем, что родителем найденнего элемента является EnumENum,
            // создаем объект и записываем его в лист
            if (iObject.getParentNode().getNodeName().equals("EnumEnum")) {
                NamedNodeMap attributes = iObject.getAttributes();

                iObjects.add(new IObject(attributes.getNamedItem("UID").getNodeValue(), attributes.getNamedItem("Name").getNodeValue()));
            }
        }
    }

    /**
     * Генерируем .json файл  из листа объектов в папку нашего проекта
     */
    public static void writeToJSON() throws IOException {
        // Проверка листа, что в нем есть хотя бы 1 элемент
        if (iObjects.size() < 1) {
            System.out.println("Лист iObjects с элементами пуст!");
            return;
        }

        // Создаем файл куда будем записывать строки
        FileWriter writer = new FileWriter("IObject_attributes.json");

        // Ниже идет пацанская генерация, согласно JSON формату
        writer.write("{\"response\":[");

        for (int i = 0; i < iObjects.size(); i++) {
            String name = iObjects.get(i).getName();
            String uid = iObjects.get(i).getUid();

            if (i == iObjects.size() - 1) {
                writer.write("{\"uid\":\"" + uid + "\",\"name\":\"" + name + "\"}");

            } else {
                writer.write("{\"uid\":\"" + uid + "\",\"name\":\"" + name + "\"},");
            }
        }

        writer.write("]}");
        writer.close();
    }
}
