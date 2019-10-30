package com.nocdib.tests;

import com.nocdib.objects.ReceiptItem;
import com.nocdib.pages.StorePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.testng.Assert;
import org.testng.annotations.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class StoreTest {

    private static WebDriver driver;
    private final String url = "https://hoff.app/store/";
    StorePage storePage;
    Map<String, Integer> prices;

    @BeforeMethod
    private void testInit(){
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get(url);
        storePage = new StorePage(driver);
        prices = storePage.getPrices();
    }

    @AfterMethod
    private void testEnd(){
        driver.close();
    }

    /* Utility function that returns a random product */
    private String getRandomProduct(){
        Object[] products = prices.keySet().toArray();
        Object product = products[new Random().nextInt(products.length)];
        return product.toString();
    }

    @Test
    /* Try buying an item with a negative quantity */
    private void negativeProductAmountTest(){
        String expectedMessage = "Enter a amount higher than 0.";
        int moneyToSpend = Integer.parseInt(storePage.getMoney().getText());
        String quantity = "-1";
        storePage.getProduct().selectByVisibleText(getRandomProduct());
        storePage.getAmount().sendKeys(quantity);
        storePage.getBuyButton().click();
        // Verify that the money amount is unchanged and that a warning message appears
        Assert.assertEquals(storePage.getMessage().getText(), expectedMessage);
        Assert.assertEquals(Integer.parseInt(storePage.getMoney().getText()), moneyToSpend);
    }

    @Test
    /* Try buying an item with a zero quantity */
    private void zeroProductAmountTest(){
        String expectedMessage = "You can't buy 0 amount";
        int moneyToSpend = Integer.parseInt(storePage.getMoney().getText());
        String quantity = "0";
        storePage.getProduct().selectByVisibleText(getRandomProduct());
        storePage.getAmount().sendKeys(quantity);
        storePage.getBuyButton().click();
        // Verify that the money amount is unchanged and that a warning message appears
        Assert.assertEquals(storePage.getMessage().getText(), expectedMessage);
        Assert.assertEquals(Integer.parseInt(storePage.getMoney().getText()), moneyToSpend);
    }

    @Test
    /* Try buying an item with a non-numeric quantity */
    private void nonNumericProductAmountTest(){
        String expectedMessage = "Please enter a correct number";
        int moneyToSpend = Integer.parseInt(storePage.getMoney().getText());
        String quantity = "not_a_number";
        storePage.getProduct().selectByVisibleText(getRandomProduct());
        storePage.getAmount().sendKeys(quantity);
        storePage.getBuyButton().click();
        // Verify that the money amount is unchanged and that a warning message appears
        Assert.assertEquals(storePage.getMessage().getText(), expectedMessage);
        Assert.assertEquals(Integer.parseInt(storePage.getMoney().getText()), moneyToSpend);
    }

    @Test
    /* Try buying an item in a decimal amount value */
    private void decimalProductAmountTest(){
        String expectedMessage = "Please enter a correct number";
        int moneyToSpend = Integer.parseInt(storePage.getMoney().getText());
        String quantity = "0.5";
        storePage.getProduct().selectByVisibleText(getRandomProduct());
        storePage.getAmount().sendKeys(quantity);
        storePage.getBuyButton().click();
        // Verify that the money amount is unchanged and that a warning message appears
        Assert.assertEquals(storePage.getMessage().getText(), expectedMessage);
        Assert.assertEquals(Integer.parseInt(storePage.getMoney().getText()), moneyToSpend);
    }

    @Test
    /* Try buying without selecting an item */
    private void noProductSelectedTest(){
        String expectedMessage = "Please select a product!";
        int moneyToSpend = Integer.parseInt(storePage.getMoney().getText());
        String quantity = "1";
        storePage.getAmount().sendKeys(quantity);
        storePage.getBuyButton().click();
        // Verify that the money amount is unchanged and that a warning message appears
        Assert.assertEquals(storePage.getMessage().getText(), expectedMessage);
        Assert.assertEquals(Integer.parseInt(storePage.getMoney().getText()), moneyToSpend);
    }

    @Test
    /* Buy an item, check the money/message, sell it, then check again */
    private void buySellMoneyMessageTest(){
        String errorMessage = "";
        for (Object product : prices.keySet().toArray()) {
            BigDecimal moneyToSpend = new BigDecimal(Integer.parseInt(storePage.getMoney().getText()));
            int totalPrice = prices.get(product);
            String quantity = "1";
            // Buy the item
            storePage.getProduct().selectByVisibleText(product.toString());
            storePage.getAmount().sendKeys(quantity);
            storePage.getBuyButton().click();
            storePage.getAmount().clear();
            // Check transaction message and money
            String expectedMessage = String.format("You bought %s x %s for a total of %d", quantity, product, Integer.parseInt(quantity)* totalPrice);
            BigDecimal remainingMoney = new BigDecimal(Integer.parseInt(storePage.getMoney().getText()));
            try{
                Assert.assertEquals(
                        moneyToSpend.subtract(remainingMoney),
                        (new BigDecimal(totalPrice)),
                        String.format("\nBuy - Money Error (%s): For (%s) %s", product, quantity, product)
                );
            }catch(AssertionError e){
                errorMessage += e.getMessage();
            }
            try{
                Assert.assertEquals(
                        storePage.getMessage().getText(),
                        expectedMessage,
                        "\nBuy - Message Error: ");
            }catch(AssertionError e){
                errorMessage += e.getMessage();
            }
            // sell the item
            storePage.getItemizedReceipt().get(0).sell();
            // Check transaction message and money
            expectedMessage = String.format("You sold %s x %s for a total of %d", quantity, product, Integer.parseInt(quantity)* totalPrice);
            remainingMoney = new BigDecimal(Integer.parseInt(storePage.getMoney().getText()));
            try{
                Assert.assertEquals(
                        remainingMoney,
                        moneyToSpend,
                        String.format("\nSell - Money Error (%s): ", product)
                );
            }catch(AssertionError e){
                errorMessage += e.getMessage();
            }
            try{
                Assert.assertEquals(
                        storePage.getMessage().getText(),
                        expectedMessage,
                        "\nSell - Message Error: ");
            }catch(AssertionError e){
                errorMessage += e.getMessage();
            }
        };
        // Throw an error if there is an error string
        if(!errorMessage.equals("")){
            throw new AssertionError(errorMessage);
        }
    }

    @Test
    /* Check the VAT after buying and selling each item */
    private void buySellReceiptVatTest() {
        String errorMessage = "";
        for (Object product : prices.keySet().toArray()) {
            String quantity = "1";
            // Buy the item
            storePage.getProduct().selectByVisibleText(product.toString());
            storePage.getAmount().sendKeys(quantity);
            storePage.getBuyButton().click();
            storePage.getAmount().clear();
            // Check VAT
            try{
                Assert.assertEquals(
                        storePage.getTotalVat().getText(),
                        0.25 * prices.get(product).intValue() * Integer.parseInt(quantity),
                        String.format("\nBuy - Total VAT on receipt is not as expected for %s: ", product.toString())
                );
            }catch(AssertionError e){
                errorMessage += e.getMessage();
            }

            // sell the item
            storePage.getItemizedReceipt().get(0).sell();
            // check the VAT
            try{
                Assert.assertEquals(
                        storePage.getTotalVat().getText(),
                        0,
                        String.format("\nSell - Total VAT on receipt is not as expected for %s: ", product.toString())
                );
            }catch(AssertionError e){
                errorMessage += e.getMessage();
            }
        };

        if(!errorMessage.equals("")){
            throw new AssertionError(errorMessage);
        }
    }

    @Test
    /* Verify the total on the receipt after buying/selling */
    private void buySellReceiptTotalTest() {
        String errorMessage = "";
        for (Object product : prices.keySet().toArray()) {
            int receiptTotal = Integer.parseInt(storePage.getTotalPrice().getText());
            int productPrice = prices.get(product);
            String quantity = "1";
            // Buy the item
            storePage.getProduct().selectByVisibleText(product.toString());
            storePage.getAmount().sendKeys(quantity);
            storePage.getBuyButton().click();
            storePage.getAmount().clear();
            // Check receipt total
            try{
                Assert.assertEquals(
                        Integer.parseInt(storePage.getTotalPrice().getText()),
                        productPrice * Integer.parseInt(quantity) + receiptTotal,
                        String.format("\nBuy - Total on receipt is not as expected for %s: ", product.toString())
                );
            }catch(AssertionError e){
                errorMessage += e.getMessage();
            }

            // sell the item
            storePage.getItemizedReceipt().get(0).sell();
            // Check receipt total
            try{
                Assert.assertEquals(
                        Integer.parseInt(storePage.getTotalPrice().getText()),
                        receiptTotal,
                        String.format("\nSell - Total on receipt is not as expected for %s: ", product.toString())
                );
            }catch(AssertionError e){
                errorMessage += e.getMessage();
            }
        };

        if(!errorMessage.equals("")){
            throw new AssertionError(errorMessage);
        }
    }

    @Test
    /* Verify the receipt entry (amount, price) of every purchased item */
    private void receiptItemTest() {
        String errorMessage = "";
        for (Object product : prices.keySet().toArray()) {
            int productPrice = prices.get(product);
            String quantity = "1";
            // Buy the item
            storePage.getProduct().selectByVisibleText(product.toString());
            storePage.getAmount().sendKeys(quantity);
            storePage.getBuyButton().click();
            storePage.getAmount().clear();
            // Check receipt entry
            try{
                Assert.assertEquals(
                        storePage.getItemizedReceipt().get(0).getAmount(),
                        Integer.parseInt(quantity),
                        String.format("\nBuy - Amount on the receipt is not as expected for %s: ", product.toString())
                );
            }catch(AssertionError e){
                errorMessage += e.getMessage();
            }
            try{
                Assert.assertEquals(
                        storePage.getItemizedReceipt().get(0).getPrice(),
                        productPrice,
                        String.format("\nBuy - Price on the receipt is not as expected for %s: ", product.toString())
                );
            }catch(AssertionError e){
                errorMessage += e.getMessage();
            }

            // sell the item
            storePage.getItemizedReceipt().get(0).sell();
        };

        if(!errorMessage.equals("")){
            throw new AssertionError(errorMessage);
        }
    }

    @Test
    /* Test the lower limit of what it takes to overdraw the money */
    private void insufficientFundsTest() {
        String errorMessage = "";
        String expectedMessage = "Insufficient funds!";
        for (Object product : prices.keySet().toArray()) {
            int productPrice = prices.get(product);
            int moneyToSpend = Integer.parseInt(storePage.getMoney().getText());
            String quantity = Integer.toString((moneyToSpend/productPrice) + 1);
            // Buy the item
            storePage.getProduct().selectByVisibleText(product.toString());
            storePage.getAmount().sendKeys(quantity);
            storePage.getBuyButton().click();
            storePage.getAmount().clear();
            // Check receipt entry
            try{
                Assert.assertEquals(storePage.getMessage().getText(), expectedMessage);
            }catch(AssertionError e){
                errorMessage += e.getMessage();
            }

        };
        if(!errorMessage.equals("")){
            throw new AssertionError(errorMessage);
        }
    }

    @Test
    /* Buy the maximum amount allowed by the available funds */
    private void maximumAmountTest() {
        String errorMessage = "";
        for (Object product : prices.keySet().toArray()) {
            int totalPrice = prices.get(product);
            int productPrice = prices.get(product);
            int moneyToSpend = Integer.parseInt(storePage.getMoney().getText());
            String quantity = Integer.toString(moneyToSpend/productPrice);
            // Buy the item
            storePage.getProduct().selectByVisibleText(product.toString());
            storePage.getAmount().sendKeys(quantity);
            storePage.getBuyButton().click();
            storePage.getAmount().clear();
            // Check message
            String expectedMessage = String.format("\nYou bought %s x %s for a total of %d", quantity, product, Integer.parseInt(quantity)* totalPrice);
            try{
                Assert.assertEquals(
                        storePage.getMessage().getText(),
                        expectedMessage,
                        "\n"
                );
            }catch(AssertionError e){
                errorMessage += e.getMessage();
            }

            // sell the item
            if(storePage.getItemizedReceipt().size() > 0) {
                storePage.getItemizedReceipt().get(0).sell();
            }

            expectedMessage = String.format("\nYou sold %s x %s for a total of %d", quantity, product, Integer.parseInt(quantity)* totalPrice);
            try{
                Assert.assertEquals(
                        storePage.getMessage().getText(),
                        expectedMessage,
                        "\n"
                );
            }catch(AssertionError e){
                errorMessage += e.getMessage();
            }
        };

        if(!errorMessage.equals("")){
            throw new AssertionError(errorMessage);
        }
    }



}
