package com.edi.ia.ner.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.edi.ia.ner.modelo.EntidadVO;
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
			sb.append("       " + element.toString());
		}
		return sb.toString();
	}

	public String darFormatoTexto(String texto) {
		texto = texto.replaceAll("\\n", " ");
		texto = texto.replaceAll(" +", " ");
		texto = texto.replaceAll("- -", "-");
		texto = texto.replaceAll("-+", "-");

		return texto;
	}

	public ModelosNerVO obtenerModelosNer() throws JsonSyntaxException, IOException {
		Archivo archivo = new Archivo();
		ModelosNerVO modelosNerVO = archivo.leerParametrosEntidades(ParametrosConfiguracionVO.rutaParametrosEntidad);
		return modelosNerVO;
	}

	public Map<String, ParametrosEntidadVO> obtenerParametrosEntidad(ModelosNerVO modelosNerVO, String modelo) {
		Map<String, ParametrosEntidadVO> mapParametrosEntidadVO = new TreeMap<String, ParametrosEntidadVO>();
		for (int i = 0; i < modelosNerVO.getModelos().size(); i++) {
			if (modelosNerVO.getModelos().get(i).getModelo().equals(modelo)) {
				mapParametrosEntidadVO = modelosNerVO.getModelos().get(i).getMap();

				break;
			}
		}

		return mapParametrosEntidadVO;
	}

	public String darFormatoFloat(String texto) {
		texto = texto.replaceAll("%", "");
		texto = texto.trim();
		texto = texto.replaceAll(" ", ".");
		texto = texto.replaceAll(",", ".");

		return texto;
	}

	public ArrayList<EntidadVO> asignarCodigoEntidadConyuge(ArrayList<EntidadVO> ListaEntidadVO) {

		List<String> codigosEntidadAcreditado = Arrays.asList(VariablesGlobales.CODIGOS_ENTIDAD_ACREDITADO.split(" "));
		List<String> codigosEntidadConyuge = Arrays.asList(VariablesGlobales.CODIGOS_ENTIDAD_CONYUGE.split(" "));
		// Fijar los codigos de las entiddes del conyuge
		for (EntidadVO entidadVO : ListaEntidadVO) {
			if (codigosEntidadAcreditado.indexOf(entidadVO.getCodigo()) != -1)
				entidadVO.setCodigoConyuge(codigosEntidadConyuge.get(codigosEntidadAcreditado.indexOf(entidadVO.getCodigo())));
			if (codigosEntidadConyuge.indexOf(entidadVO.getCodigo()) != -1)
				entidadVO.setCodigoConyuge(codigosEntidadAcreditado.get(codigosEntidadConyuge.indexOf(entidadVO.getCodigo())));
		}

		return ListaEntidadVO;
	}

}
