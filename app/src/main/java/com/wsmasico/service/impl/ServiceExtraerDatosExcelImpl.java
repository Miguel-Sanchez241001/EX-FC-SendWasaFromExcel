package com.wsmasico.service.impl;

import com.wsmasico.Main;
import com.wsmasico.Utils.Constantes;
import com.wsmasico.model.Mensaje;
import com.wsmasico.service.inter.ServicioExtraerDatosExcel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceExtraerDatosExcelImpl implements ServicioExtraerDatosExcel {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final Logger logger = LogManager.getLogger(ServiceExtraerDatosExcelImpl.class);

    @Override
    public List<Mensaje> obtenerMensajesXLS(String path) {
        List<Mensaje> mensajeList = new ArrayList<Mensaje>();

        try (FileInputStream inputStream = new FileInputStream(path);
             Workbook workbook = new XSSFWorkbook(inputStream); ) {
            Sheet sheet = workbook.getSheetAt(0);

            List<String> atributos = getAttributeNames(Mensaje.class);
            Method[] metodos = new Method[atributos.size()];
            // Obtener métodos de los atributos
            for (int j = 0; j < atributos.size(); j++) {
                try {
                     metodos[j] = Mensaje.class.getMethod("set" + capitalize(atributos.get(j)),String.class);
                } catch (NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                }
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue; // Saltar la primera fila si es el encabezado
                }
                Mensaje msj = new Mensaje();

                for (int i = 0; i < 5; i++) {
                    Cell cell = row.getCell(i); // Suponiendo que los números están en la primera columna
                     metodos[i].invoke(msj, obtenerValorString(cell));
                }

                msj.setMensaje(Constantes.ENTREVISTA_PRESENCIAL
                                .replace("[NOMBRE]",msj.getNombreCompleto())
                                .replace("[PUESTO]",msj.getPuesto())
                                .replace("[FECHA]",msj.getFecha())
                                .replace("[LUGAR]",msj.getLugar())
                                );
                logger.info(msj);
                mensajeList.add(msj);
            }



        }  catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


        return mensajeList;
    }

    private String capitalize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    public static List<String> getAttributeNames(Class<?> clazz) {
        List<String> attributeNames = new ArrayList<String>();
        for (Field field : clazz.getDeclaredFields()) {
            attributeNames.add(field.getName());
        }
        return attributeNames;
    }

    public  String obtenerValorString(Cell celda) {

        if (celda == null) {
            return "";
        }

        CellType tipoCelda = celda.getCellType();
        switch (tipoCelda) {
            case STRING:
                return celda.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(celda)) {
                    Date fecha = celda.getDateCellValue();
                    return dateFormat.format(fecha);
                } else {
                    celda.setCellType(CellType.STRING);
                    return  celda.getStringCellValue();
                }
            case BOOLEAN:
                return String.valueOf(celda.getBooleanCellValue());
            case FORMULA:
                return celda.getCellFormula();
            case BLANK:
                return "";
            default:
                return "Tipo de celda no soportado";
        }
    }
}
