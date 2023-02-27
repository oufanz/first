package DAY2y27dCollecion_test;

public class Card {
    private String size;
    private String color;
    private int index;

    public Card(String size, String color, int index) {
        this.size = size;
        this.color = color;
        this.index = index;
    }

    @Override
    public String toString() {
        return "Card{" +
                "size='" + size + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
