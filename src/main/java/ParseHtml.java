import Core.Line;
import Core.Station;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParseHtml {
    static List<Line> lines = new ArrayList<>();
    static List<Station> stations = new ArrayList<>();
    static List<Station> connection = new ArrayList<>();

    public static final String HTML_FILE = parseHtml("src/main/resources/code.html");
    public static final Document DOCUMENT = Jsoup.parse(HTML_FILE);

    private static String parseHtml(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            List<String> doc = Files.readAllLines(Paths.get(path));
            doc.forEach(line -> stringBuilder.append(line));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static void parseLinesAndStation() {
        Elements elements1 = DOCUMENT.getElementsByClass("js-metro-line");
        elements1.forEach(element1 -> lines.add(new Line(element1.attr("data-line"), element1.text().replaceAll(" линия", ""))));
        Elements elements2 = DOCUMENT.getElementsByClass("js-metro-stations t-metrostation-list-table");
        elements2.forEach(element2 -> stations.add(new Station(element2.attr("data-line"), element2.text().replaceAll("[0-9.]", ""))));
        Elements elements3 = DOCUMENT.getElementsByClass("t-icon-metroln");
        Elements elements4 = elements3.parents();
        elements4.forEach(element -> connection.add(new Station(element.attr("переход на станцию"), element.text().replaceAll("[0-9.]", ""))));
        getConnection();
    }

    public static void jsonWrite(String path) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("lines", lines);

            JSONObject jsonObjectStation = new JSONObject();
            for (Station station : stations) {
                String[] strings = station.getName().trim().split("\\s\\s");
                jsonObjectStation.put(station.getNumberOfLine(), strings);
            }
            jsonObject.put("stations", jsonObjectStation);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            Files.write(Paths.get(path), gson.toJson(jsonObject).getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Station> getConnection() {
        connection.removeIf(station -> station.getName().length() > 35 || station.getName().contains("линия"));
        return connection;
    }

    public static String jsonRead(String path) {
        StringBuilder builder = new StringBuilder();
        try {
            List<String> list = Files.readAllLines(Paths.get(path));
            list.forEach(l -> builder.append(l));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public static void print() {
        String message;
        for (Station station : stations) {
            String[] strings = station.getName().split("\\s\\s");
            message = "Линия " + station.getNumberOfLine() + ":\r\n Количество станции на линии: " + strings.length;
            System.out.println(message);
        }
    }
}