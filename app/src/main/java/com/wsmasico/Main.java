package com.wsmasico;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
 

public class Main {
    public static void main(String[] args) throws IOException {

    
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



            // Abriendo ws 
            System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
            WebDriver driver = new ChromeDriver();
            driver.get("https://web.whatsapp.com");

       // enviando mensjae de prueba 

       for (String numero : numeros) {

    String urlWhatsapp = "https://web.whatsapp.com/send?phone=" + numero.trim();
    driver.get(urlWhatsapp);
    
    WebElement mensajeInput = driver.findElement(By.xpath("//div[@contenteditable='true']"));
    mensajeInput.sendKeys("Estoy probando envio masivo de ws");
    
    WebElement enviarBtn = driver.findElement(By.xpath("//span[@data-icon='send']"));
    enviarBtn.click();
}

        System.out.println("Hello world!");
    }
}