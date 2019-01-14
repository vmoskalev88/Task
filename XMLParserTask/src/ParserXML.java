import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.nio.file.Path;

public class ParserXML {
    /**
     * Метод для обработки XML файла.
     * <p>
     * Выбираем атрибуты UID и Name у элемента IObject, дочки элемента EnumEnum и сохраняем их в новый объекст IObject,
     * после чего заносим обеъкт в лист
     *
     * @param currentFile - полный путь к XML файлу
     */
    public static void parseXML(Path currentFile) throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();

        try {
            Document document = builder.parse(new File(currentFile.toString()));

            // Ищем элементы по тегу
            NodeList iObjectElements = document.getDocumentElement().getElementsByTagName("IObject");

            for (int i = 0; i < iObjectElements.getLength(); i++) {
                Node iObject = iObjectElements.item(i);

                // Проверяем, что родителем найденнего элемента является EnumENum,
                // создаем объект и записываем его в лист
                if (iObject.getParentNode().getNodeName().equals("EnumEnum")) {
                    NamedNodeMap attributes = iObject.getAttributes();

                    Solution.getIObjects().add(new IObject(attributes.getNamedItem("UID").getNodeValue(),
                            attributes.getNamedItem("Name").getNodeValue()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
