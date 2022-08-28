import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ParseHtml.parseLinesAndStation();
        ParseHtml.jsonWrite("src/main/resources/map.json");
        ParseHtml.jsonRead("src/main/resources/map.json");
        SearchFile.searchFile();
        ParseData.jsonParser();
        ParseData.csvParser();
        ParseData.jsonWrite("src/main/resources/stations.json");
        ParseHtml.print();
    }
}