import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class SearchFile {
    private static final String PATH = "data";

    private static List<String> jsonList = new ArrayList<>();
    private static List<String> csvList = new ArrayList<>();

    static final StringBuilder BUILDER_JSON = new StringBuilder();
    static final StringBuilder BUILDER_CSV = new StringBuilder();

    //Метод рекурсивного обхода дерево папок
    public static void searchFile() throws IOException {
        Path root = Paths.get(PATH);
        Files.walkFileTree(root, new FileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileName = file.getFileName().toString();
                if (fileName.endsWith(".json")) {
                    jsonList = Files.readAllLines(file);
                    jsonList.forEach(lst -> BUILDER_JSON.append(lst + "\n"));
                }
                if (fileName.endsWith(".csv")) {
                    csvList = Files.readAllLines(file);
                    csvList.forEach(lst -> BUILDER_CSV.append(lst + "\n"));
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
