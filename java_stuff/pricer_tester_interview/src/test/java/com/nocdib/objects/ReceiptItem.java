package com.nocdib.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ReceiptItem {

    private String product;
    private int amount;
    private int price;
    private WebElement sell;

    public ReceiptItem(WebElement item) {
        product = item.findElement(By.xpath("th[1]")).getText().trim();
        amount = Integer.parseInt(item.findElement(By.xpath("th[2]")).getText().trim());
        price = Integer.parseInt(item.findElement(By.xpath("th[3]")).getText().trim());
        sell = item.findElement(By.xpath("th[4]/button"));
    }

    public String getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
    }

    public WebElement getSell() {
        return sell;
    }

    public void sell(){
        this.getSell().click();
    }

    public String toString() {
        return new StringBuilder(product).append(" - ").append(amount).append(" - ").append(price).toString();
    }
}
