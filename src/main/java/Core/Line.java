package Core;

import java.util.Objects;

public class Line {
    private String name;
    private String number;

    public Line(String number, String name) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Objects.equals(number, line.number) &&
                Objects.equals(name, line.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, name);
    }
}