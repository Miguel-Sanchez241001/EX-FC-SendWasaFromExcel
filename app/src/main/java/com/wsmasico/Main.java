package com.wsmasico;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.wsmasico.Utils.Constantes;

 
 
public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException, InterruptedException {

        logger.info("Este es un mensaje de información");

        File excelFile = new File("C:\\Users\\Miguel\\Desktop\\dataPuebaEnvioMaximoWhatsap.xlsx");
        FileInputStream fis = new FileInputStream(excelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        List<String> numeros = new ArrayList<>();
        for (Row row : sheet) {
            Cell cell = row.getCell(0); // Suponiendo que los números están en la primera columna
            numeros.add(cell.getStringCellValue());
        }
        workbook.close();

        logger.info(numeros.toString());



 // Configura y descarga automáticamente el ChromeDriver correspondiente
 //WebDriverManager.chromedriver().driverVersion("123.0.6312.122").setup();
 System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\Miguel\\Desktop\\wsMasivo\\app\\chromedriver.exe");
         // Inicializamos ls opciones de chrome
         ChromeOptions optionsGoo = new ChromeOptions();
         // Permitimos la propiedad no-sandbox para evitar problemas en linux
         optionsGoo.addArguments("--no-sandbox");
         // Deshabilitamos las notificaciones
         optionsGoo.addArguments("--disable-notifications");
         // Guardamos la sesion en la carpeta chromeWA
         optionsGoo.addArguments("--user-data-dir=C:\\Users\\Miguel\\Desktop\\wsMasivo\\app\\data");
 
 // Crea una instancia de ChromeDriver   
 WebDriver driver = new ChromeDriver(optionsGoo);

 // Abre una página, por ejemplo, Google
 driver.get("https://www.google.com");

 // Aquí puedes añadir más acciones para interactuar con la página

 // Cierra el navegador





        
        
  

        for (String numero : numeros) {

            String urlWhatsapp = "https://web.whatsapp.com/send/?phone=" + numero.trim();
            driver.get(urlWhatsapp);
            // Declaramos el tiempo de espera

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3000L));
 
            // Esperamos que cargue
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[1]/p")));
            // Escribimos el mensaje
            driver.findElement(By.xpath(Constantes.RUTA_BOX_MSJ)).sendKeys("MENSAJE TEST");
            // Esperamos 1 segundo
            Thread.sleep(1000);
            // Precionamos enter
            driver.findElement(By.xpath(Constantes.RUTA_BOX_BTNENVIAR)).sendKeys(Keys.ENTER);
            Thread.sleep(2000);
        }
        driver.quit();
    }

    
}