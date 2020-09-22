package com.edi.ia.ner.util;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.edi.ia.ner.modelo.ModelosNerVO;
import com.edi.ia.ner.modelo.ParametrosConfiguracionVO;
import com.edi.ia.ner.modelo.ParametrosEntidadVO;
import com.google.gson.JsonSyntaxException;

public class Utilidad {
	
	public static String stackTraceToString(Throwable e) {
	    StringBuilder sb = new StringBuilder();
	    sb.append(e.toString());
        sb.append("\n");
	    for (StackTraceElement element : e.getStackTrace()) {
	        sb.append("       "+element.toString());
	        sb.append("\n");
	    }
	    return sb.toString();
	}
	
	public String  darFormatoTexto(String texto) {
		texto = texto.replaceAll("\\n", " ");
		texto = texto.replaceAll(" +", " ");
		return texto;
	}
	
	public ModelosNerVO  obtenerModelosNer() throws JsonSyntaxException, IOException {
		Archivo archivo = new Archivo();
		ModelosNerVO modelosNerVO = archivo
				.leerParametrosEntidades(ParametrosConfiguracionVO.rutaParametrosEntidad);
		return modelosNerVO;
	}
	
	public Map<String, ParametrosEntidadVO>  obtenerParametrosEntidad(ModelosNerVO modelosNerVO, String modelo) {
		Map<String, ParametrosEntidadVO> mapParametrosEntidadVO = new TreeMap<String, ParametrosEntidadVO>();
		for (int i = 0; i < modelosNerVO.getModelos().size(); i++) {
			if (modelosNerVO.getModelos().get(i).getModelo().equals(modelo)) {
				mapParametrosEntidadVO = modelosNerVO.getModelos().get(i).getMap();
				
				break;
			}
		}
		
		return mapParametrosEntidadVO;
	}
	
	public String  darFormatoFloat(String texto) {
		texto = texto.replaceAll("%", "");
		texto = texto.trim();
		texto = texto.replaceAll(" ", ".");
		texto = texto.replaceAll(",", ".");
		
		return texto;
	}

}
