import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.exec.util.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.json.Json;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static WebDriver driver;
    public static final String PATH_DRIVER = "Your chrome driver path"; //example:C:\Users\your_pc\Downloads\chromedriver_win32\chromedriver.exe
    public static final String XPATH_NAME_LANG = "/html/body/section/section/main/devsite-content/article/div[2]/div/table/tbody/tr/td";
    public static final String URL = "https://developers.google.com/admin-sdk/directory/v1/languages";

    public static void main(String[] args) {
        //setup
        System.setProperty("webdriver.chrome.driver",PATH_DRIVER);
        ChromeOptions options = new ChromeOptions ();
        options.setHeadless(true);
        driver = new ChromeDriver();
        driver.get(URL);
        //to-do
        List<WebElement>  names = driver.findElements(By.xpath(XPATH_NAME_LANG));
        Lang lang = new Lang();
        List<Lang> langs = new ArrayList<>();
        for(WebElement name: names){
            if(lang.getName()==null) {
                lang.setName(name.getText());
                continue;
            }else{
                lang.setCode(name.getText());
            }
            langs.add(lang);
            lang = new Lang();
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(langs);
        try {
            FileWriter writer = new FileWriter("json.txt", true);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        driver.close();
    }
}
