package Core;

import java.util.Objects;

public class Station {
    private String name;
    private String numberOfLine;

    private String line;
    private String date;
    private String depth;
    private boolean hasConnection;

    public Station(String numberOfLine, String name) {
        this.numberOfLine = numberOfLine;
        this.name = name;
    }

    public Station() {
    }

    public Station(String name, String line, String date, String depth, boolean hasConnection) {
        this.name = name;
        this.line = line;
        this.date = date;
        this.depth = depth;
        this.hasConnection = hasConnection;
    }

    public String getName() {
        return name;
    }

    public String getNumberOfLine() {
        return numberOfLine;
    }

    public String getLine() {
        return line;
    }

    public String getDate() {
        return date;
    }

    public String getDepth() {
        return depth;
    }

    public boolean isHasConnection() {
        return hasConnection;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void setHasConnection(boolean hasConnection) {
        this.hasConnection = hasConnection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return hasConnection == station.hasConnection && Objects.equals(name, station.name) && Objects.equals(numberOfLine, station.numberOfLine) && Objects.equals(line, station.line) && Objects.equals(date, station.date) && Objects.equals(depth, station.depth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfLine, name);
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\" : \"" + name + "\",\n" +
                "\"numberOfLine\" : \"" + numberOfLine + "\",\n" +
                "\"date\" : \"" + date + "\",\n" +
                "\"depth\" : \"" + depth + "\",\n" +
                "\"hasConnection\" : \"" + hasConnection +
                "},";
    }

}