package product;

public class Product {
    String name;
    String category;
    short count;
    double price;

    public Product(String name, String category, short count, double price) {
        this.name = name;
        this.category = category;
        this.count = count;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public short getCount() {
        return count;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
