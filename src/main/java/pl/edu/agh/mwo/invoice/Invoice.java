package pl.edu.agh.mwo.invoice;

import pl.edu.agh.mwo.invoice.product.Product;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class Invoice {

    private final Map<Product, Integer> products = new LinkedHashMap<>();

    public void addProduct(Product product) {
        this.addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid product or quantity");
        }

        if (this.products.containsKey(product)) {
            Integer currentQuantity = this.products.get(product);

            this.products.put(product, currentQuantity + quantity);
        } else {
            this.products.put(product, quantity);
        }
    }

    public BigDecimal getNetValue() {
        BigDecimal value = BigDecimal.ZERO;

        for (Product product : this.products.keySet()) {
            Integer quantity = this.products.get(product);
            BigDecimal price = product.getPrice();
            price = price.multiply(BigDecimal.valueOf(quantity));
            value = value.add(price);
        }
        return value;
    }

    public BigDecimal getTax() {

        BigDecimal value = BigDecimal.ZERO;

        for (Product product : this.products.keySet()) {
            Integer quantity = this.products.get(product);
            BigDecimal tax = product.getPrice()
                    .multiply(product.getTaxPercent())
                    .multiply(BigDecimal.valueOf(quantity));

            value = value.add(tax);
        }
        return value;
    }

    public BigDecimal getGrossValue() {

        BigDecimal value = BigDecimal.ZERO;

        for (Product product : this.products.keySet()) {
            Integer quantity = this.products.get(product);
            BigDecimal price = product.getPriceWithTax();
            price = price.multiply(BigDecimal.valueOf(quantity));
            value = value.add(price);
        }
        return value;
    }

    private static int NUMBER = 0;
    private final int number;

    public Invoice() {
        this.number = ++NUMBER;
    }

    public int getNumber() {
        return this.number;
    }

    public String print() {
        String result = "Faktura nr " + this.number + "\n";

        for (Product product : this.products.keySet()) {
            Integer quantity = this.products.get(product);

            result += product.getName()
                    + " "
                    + quantity
                    + " "
                    + product.getPrice()
                    + "\n";
        }
        result += "Liczba pozycji: " + this.products.size();

        return result;
    }
}
