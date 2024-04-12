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
 import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
 

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {

        logger.info("Este es un mensaje de información");

        // leyendo archivo excel 
            File excelFile = new File("C:\\Users\\Miguel\\Desktop\\dataPuebaEnvioMaximoWhatsap.xlsx");
            FileInputStream fis = new FileInputStream(excelFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0); 

            List<String> numeros = new ArrayList<>();
            for (Row row : sheet) {
                Cell cell = row.getCell(0); // Suponiendo que los números están en la primera columna
                numeros.add(cell.getStringCellValue());
            }


                //Leemos el chromedriver que esta en la misma direccion del programa
                System.setProperty("webdriver.chrome.driver", "C:\\Users\\Miguel\\Downloads\\chromedriver-win64\\chromedriver.exe");
                //Inicializamos ls opciones de chrome
                ChromeOptions optionsGoo = new ChromeOptions();
                //Permitimos la propiedad no-sandbox para evitar problemas en linux
                optionsGoo.addArguments("--no-sandbox");
                //Deshabilitamos las notificaciones
                optionsGoo.addArguments("--disable-notifications");
                //Guardamos la sesion en la carpeta chromeWA
                optionsGoo.addArguments("--user-data-dir=C:\\Users\\Miguel\\Downloads\\chromedriver-win64\\data");
                //Instanciamos un nuevo chromedriver
                ChromeDriver  driver = new ChromeDriver(optionsGoo);
            // Abriendo ws 
            // System.setProperty("webdriver.chrome.driver", "C:\\Users\\Miguel\\Downloads\\chromedriver-win64\\chromedriver.exe");
            // WebDriver driver = new ChromeDriver();
            // driver.get("https://web.whatsapp.com");

       // enviando mensjae de prueba 

       for (String numero : numeros) {

    String urlWhatsapp = "https://web.whatsapp.com/send/?phone=" + numero.trim()+"&text&type=phone_number&app_absent=0";
    driver.get(urlWhatsapp);
       //Declaramos el tiempo de espera
 
       WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000L));
     //Abrimos whatsapp web con el numero de telefono a enviar
    // WebElement mensajeInput = driver.findElement(By.xpath("//div[@contenteditable='true']"));
    // mensajeInput.sendKeys("Estoy probando envio masivo de ws");
    
    // WebElement enviarBtn = driver.findElement(By.xpath("//span[@data-icon='send']"));
    // enviarBtn.click();

    //Esperamos que cargue
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[1]/p")));
                    //Escribimos el mensaje
                    driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[1]/p")).sendKeys("Holaaaaa soy un botsito");
                    //Esperamos 1 segundo
                    pausa(1000);
                    //Precionamos enter
                    driver.findElement(By.xpath("//*[@id=\"main\"]/footer/div[1]/div/span[2]/div/div[2]/div[1]/div/div[1]/p")).sendKeys(Keys.ENTER);
                    pausa(1000);
}

        System.out.println("Hello world!");
    }

    private static void pausa(int sleeptime) {
        try {
            Thread.sleep(sleeptime);
        } catch (InterruptedException ex) {
        }
    }
}