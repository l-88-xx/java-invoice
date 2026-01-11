package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
   // private Collection<Product> products = new ArrayList<>();

    private Map<Product, Integer> products = new HashMap<>();

    public void addProduct(Product product) {
        this.addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
  /*      if (product == null || quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Invalid product or quantity"); }*/
            this.products.put(product, quantity);
     /*   for (int i = 0; i < quantity; i++)
        {
            this.products.add(product);
        }*/
    }

    public BigDecimal getNetValue() {
        BigDecimal value = BigDecimal.ZERO;

       for(Product product :  this.products.keySet()){
           Integer quantity = this.products.get(product);
           BigDecimal price = product.getPrice();
           price = price.multiply(BigDecimal.valueOf(quantity));
           value = value.add(price);
       }
              /*  for (Product product : this.products){
                    value = value.add(product.getPrice());
                }*/
        return value;
    }

    public BigDecimal getTax() {
        return getGrossValue().subtract(getNetValue());
    }

    public BigDecimal getGrossValue() {

        BigDecimal value = BigDecimal.ZERO;

        for(Product product :  this.products.keySet()){
            Integer quantity = this.products.get(product);
            BigDecimal price = product.getPriceWithTax();
            price = price.multiply(BigDecimal.valueOf(quantity));
            value = value.add(price);
        }
        return value;
    }
}
