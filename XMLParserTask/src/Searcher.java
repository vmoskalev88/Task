import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

/**
 * Класс-поисковик, осуществляющий поиск xml файлов в каталоге
 */
public class Searcher extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

        String fileName = String.valueOf(file.getFileName());
        if (fileName.toLowerCase().endsWith(".xml")) {

            System.out.println(file.getFileName().toAbsolutePath());     // Выводим в консоль путь к подошедшему файлу

            try {
                ParserXML.parseXML(file.toAbsolutePath());               // Запускаем xml парсер
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        System.err.println(exc);
        return CONTINUE;
    }
}
