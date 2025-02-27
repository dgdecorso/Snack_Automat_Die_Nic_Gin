package main;

public class SnackItem {
    public String name;
    public int index;
    public String filePath;
    public double price;
    public int stock;
    public SnackItem(int index, String name, String filePath, double price, int stock) {
        this.index = index;
        this.name = name;
        this.filePath = filePath;
        this.price = price;
        this.stock = stock;
    }

    @Override
    public String toString() {
        return name + " (Index: " + index + ") - " + price + "CHF";
    }
}
