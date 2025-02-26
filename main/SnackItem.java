package main;

public class SnackItem {
    public String name;
    public int index;
    public String filePath;
    public double price;

    public SnackItem(int index, String name, String filePath, double price) {
        this.index = index;
        this.name = name;
        this.filePath = filePath;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " (Index: " + index + ") - " + price + "CHF";
    }
}
