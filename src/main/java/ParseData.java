import Core.Station;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ParseData {
    public static List<Station> stationList = new LinkedList<>();
    public static Set<Station> stationIndex = new HashSet<>();

    private static String getBuilderJson() {
        return SearchFile.BUILDER_JSON.toString().replaceAll("]\n\\[", "")
                .replaceAll("station_name", "name")
                .replaceAll("depth_meters", "depth");
    }

    private static String getBuilderCsv() {
        return SearchFile.BUILDER_CSV.toString()
                .replaceAll("Название станции,Дата открытия\n", "")
                .replaceAll("Название,Глубина\n", "");

    }

    //Метод для рекурсивного обхода дерева папок
    public static void jsonParser() {
        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(getBuilderJson());
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                Station station = new Station();
                station.setName(jsonObject.get("name").toString());
                setLines(station);
                setConnections(station);
                //Устанавливаем дату открытия
                if (jsonObject.containsKey("date")) {
                    station.setDate(jsonObject.get("date").toString());
                    station.setDepth("");
                }
                //Устанавливаем глубину
                if (jsonObject.containsKey("depth")) {
                    station.setDepth(jsonObject.get("depth").toString());
                    station.setDate("");
                }
                stationList.add(station);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void csvParser() {
        for (String str : getBuilderCsv().split("\n")) {
            if (!str.equals("")) {
                String[] splitText = str.split(",");
                ArrayList<String> columnList = new ArrayList<>();
                Collections.addAll(columnList, splitText);
                Station station = new Station();
                station.setName(columnList.get(0));
                setLines(station);
                setConnections(station);
                //Устанавливаем глубину или дату открытия
                if (columnList.get(1).length() > 5) {
                    station.setDate(columnList.get(1));
                    station.setDepth("");
                } else {
                    station.setDepth(columnList.get(1));
                    station.setDate("");
                }
                stationList.add(station);
            }
        }
    }

    //Устанавливаем номер линии
    private static void setLines(Station station) {
        for (Station st : ParseHtml.stations) {
            String[] strings = st.getName().trim().split("\\s\\s");
            for (String str1 : strings) {
                if (str1.equals(station.getName())) {
                    station.setLine(st.getNumberOfLine());
                }
            }
        }
    }

    //Устанавливаем переходы, если они имеются
    private static void setConnections(Station station) {
        for (Station st : ParseHtml.connection) {
            if (st.getName().trim().equals(station.getName())) {
                station.setHasConnection(true);
            }
        }
    }

    //Метод для записи отсутствующих данных
    private static void stationsAddition() {
        for (int i = 0; i < stationList.size(); i++) {
            for (Station station : stationList) {
                if (station.getName().equals(stationList.get(i).getName()) && station.getDate().isEmpty()) {
                    station.setDate(stationList.get(i).getDate());

                } else if (station.getName().equals(stationList.get(i).getName()) && station.getDepth().isEmpty()) {
                    station.setDepth(stationList.get(i).getDepth());
                    stationIndex.add(station);
                }
            }
        }
    }

    public static void jsonWrite(String path) {
        try {
            JSONObject jsonObject = new JSONObject();
            stationsAddition();
            jsonObject.put("station", stationIndex);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Files.write(Paths.get(path), gson.toJson(jsonObject).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}