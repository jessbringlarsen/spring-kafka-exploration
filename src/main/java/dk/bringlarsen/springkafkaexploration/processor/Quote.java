package dk.bringlarsen.springkafkaexploration.processor;

public final class Quote {
    private final String id;
    private final Integer price;

    public Quote(String id, Integer price) {
        this.id = id;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public Integer getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id='" + id + '\'' +
                ", price=" + price +
                '}';
    }
}