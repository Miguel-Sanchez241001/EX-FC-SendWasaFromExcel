package com.wsmasico.service.inter;

import com.wsmasico.model.Mensaje;

import java.util.List;

public interface ServicioExtraerDatosExcel {

    public List<Mensaje> obtenerMensajesXLS(String path);
}
