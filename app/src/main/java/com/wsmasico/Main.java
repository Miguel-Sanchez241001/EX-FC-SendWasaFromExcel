package com.wsmasico;

import java.io.IOException;
import java.util.List;

import com.wsmasico.model.Mensaje;
import com.wsmasico.service.impl.ServiceExtraerDatosExcelImpl;
import com.wsmasico.service.impl.ServiceWhatsapImpl;
import com.wsmasico.ventanas.VnPrincipal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;


public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException, InterruptedException {

        logger.info("Iniciando el programa");


        // Extraer datos del archivo Excel

//        ServiceExtraerDatosExcelImpl serviceExtraerDatosExcel = new ServiceExtraerDatosExcelImpl();
//
//
//        List<Mensaje> mensajes = serviceExtraerDatosExcel.obtenerMensajesXLS("C:\\Users\\Miguel\\Desktop\\dataPuebaEnvioMaximoWhatsap.xlsx");
//
//        logger.info("Mensajes extraídos: " + mensajes.size());
//
//        ServiceWhatsapImpl wasap = new ServiceWhatsapImpl();
//
//        wasap.EnviarWhatsap(mensajes);
        // Ejecutar en el hilo de despacho de eventos para mayor seguridad
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VnPrincipal();
            }
        });
    }
}
