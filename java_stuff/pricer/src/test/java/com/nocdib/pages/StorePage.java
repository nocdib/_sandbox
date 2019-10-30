package com.nocdib.pages;

import com.nocdib.objects.ReceiptItem;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorePage {
    private WebDriver driver;

    @FindBy(id="money")
    WebElement money;

    @FindBy(id="message")
    WebElement message;

    @FindBy(tagName = "select")
    //@FindBys({ @FindBy(tagName = "select"), @FindBy(className = "form-control") })
    WebElement product;

    @FindBy(id = "buyAmount")
    WebElement amount;

    @FindBy(className = "btn-primary")
    private WebElement buyButton;

    @FindBy(id = "productList")
    private WebElement priceTable;

    @FindBy(id = "bought")
    private WebElement receipt;

    @FindBy(id = "totalVAT")
    WebElement totalVat;

    @FindBy(id = "totalPrice")
    WebElement totalPrice;

    /*
    @FindBys(@FindBy(css=”div[class=’yt-lockup-tile yt-lockup-video’]”)))
    private List<WebElement> videoElements;
    */

    public StorePage (WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebElement getMoney() {
        return money;
    }

    public WebElement getMessage() {
        return message;
    }

    public Select getProduct() {
        return new Select(product);
    }

    public WebElement getAmount() {
        return amount;
    }

    public WebElement getBuyButton() {
        return buyButton;
    }

    public WebElement getPriceTable() {
        return priceTable;
    }

    public WebElement getReceipt() {
        return receipt;
    }

    public WebElement getTotalVat() {
        return totalVat;
    }

    public WebElement getTotalPrice() {
        return totalPrice;
    }

    public Map<String, Integer> getPrices(){
        HashMap<String, Integer> prices = new HashMap<>();
        // get each <tr>
        List<WebElement> rows = this.getPriceTable().findElements(By.tagName("tr"));
        // add each product/price
        rows.forEach((row) -> {
            String product = row.findElement(By.id("prod-name")).getText().trim();
            int price = Integer.parseInt(row.findElement(By.id("prod-price")).getText().trim());
            prices.put(product, price);
        });
        return prices;
    }

    public List<ReceiptItem> getItemizedReceipt(){
        List<ReceiptItem> items = new ArrayList<>();
        // get each <tr>
        List<WebElement> rows = this.getReceipt().findElements(By.tagName("tr"));
        // add each ReceiptItem to the list
        rows.forEach((row) -> {
            items.add(new ReceiptItem(row));
        });
        return items;
    }

}
