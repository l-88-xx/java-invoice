package pl.edu.agh.mwo.invoice.product;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;


public class ProductTest {
    @Test
    public void testProductNameIsCorrect() {
        Product product = new OtherProduct("buty", new BigDecimal("100.0"));
        Assert.assertEquals("buty", product.getName());
    }

    @Test
    public void testProductPriceAndTaxWithDefaultTax() {
        Product product = new OtherProduct("Ogorki", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("0.23"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testProductPriceAndTaxWithDairyProduct() {
        Product product = new DairyProduct("Szarlotka", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("100"), Matchers.comparesEqualTo(product.getPrice()));
        Assert.assertThat(new BigDecimal("0.08"), Matchers.comparesEqualTo(product.getTaxPercent()));
    }

    @Test
    public void testPriceWithTax() {
        Product product = new DairyProduct("Oscypek", new BigDecimal("100.0"));
        Assert.assertThat(new BigDecimal("108"), Matchers.comparesEqualTo(product.getPriceWithTax()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNullName() {
        new OtherProduct(null, new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithEmptyName() {
        new TaxFreeProduct("", new BigDecimal("100.0"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNullPrice() {
        new DairyProduct("Banany", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testProductWithNegativePrice() {
        new TaxFreeProduct("Mandarynki", new BigDecimal("-1.00"));
    }

    @Test
    public void testBottleOfWinePriceWithTaxAndExcise() {
        Product wine = new BottleOfWine("Merlot", new BigDecimal("20.00"));

        // VAT: 20 * 0.23 = 4.60
        // Cena z VAT: 24.60
        // Akcyza: 5.56
        // suma 30.16
        Assert.assertThat(
                new BigDecimal("30.16"),
                Matchers.comparesEqualTo(wine.getPriceWithTax())
        );
    }

    @Test
    public void testFuelCanisterPriceWithExciseOnly() {
        Product fuel = new FuelCanister("Benzyna", new BigDecimal("7.00"));

        // VAT: 0, netto: 7.00
        // Akcyza: 5.56
        // suma 12.56
        Assert.assertThat(
                new BigDecimal("12.56"),
                Matchers.comparesEqualTo(fuel.getPriceWithTax())
        );
    }


}
