package com.edi.ia.ner.util;

import java.io.IOException;

import java.text.Normalizer;
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

	public String darFormatoNombre(String nombre) {
		nombre = nombre.replaceAll("de", "");
		nombre = nombre.replaceAll("DE", "");
		nombre = nombre.replaceAll("la", "");
		nombre = nombre.replaceAll("LA", "");
		nombre = nombre.replaceAll("de", "");
		nombre = nombre.replaceAll("CON", "");
		nombre = nombre.replaceAll("con", "");
		nombre = nombre.replaceAll("y", "");
		nombre = nombre.replaceAll("Y", "");
		nombre = nombre.replaceAll(" +", " ");

		return nombre;
	}

	public String limpiarAcentos(String texto) {

		String textoSinAcentos = "";

		textoSinAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD);
		textoSinAcentos = textoSinAcentos.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

		return textoSinAcentos;

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

	public String removerValoresAlFinal(ArrayList<String> valoresRemover, String texto) {

		int index = -1;

		if (valoresRemover != null) {
			for (String valorRemover : valoresRemover) {
				index = texto.toLowerCase().lastIndexOf(valorRemover.toLowerCase());
				if (index != -1) {

					if (texto.length() - index == valorRemover.length()) {
						texto = texto.substring(0, index);
					}
				}
			}
		}

		return texto;
	}

	public ArrayList<EntidadVO> asignarCodigoEntidadConyuge(ArrayList<EntidadVO> ListaEntidadVO) {

		List<String> codigosEntidadAcreditado = Arrays.asList(VariablesGlobales.CODIGOS_ENTIDAD_ACREDITADO.split(" "));
		List<String> codigosEntidadConyuge = Arrays.asList(VariablesGlobales.CODIGOS_ENTIDAD_CONYUGE.split(" "));
		// Fijar los codigos de las entiddes del conyuge
		for (EntidadVO entidadVO : ListaEntidadVO) {
			if (codigosEntidadAcreditado.indexOf(entidadVO.getCodigo()) != -1)
				entidadVO.setCodigoConyuge(
						codigosEntidadConyuge.get(codigosEntidadAcreditado.indexOf(entidadVO.getCodigo())));
			if (codigosEntidadConyuge.indexOf(entidadVO.getCodigo()) != -1)
				entidadVO.setCodigoConyuge(
						codigosEntidadAcreditado.get(codigosEntidadConyuge.indexOf(entidadVO.getCodigo())));
		}

		return ListaEntidadVO;
	}

	public Map<String, ArrayList<String>> getValoresPorPrioridad(ArrayList<String> ListaValores) {

		Map<String, ArrayList<String>> map = new TreeMap<String, ArrayList<String>>();
		// Se espera que cada valor de la lista tenga el formato <numero que indica la
		// riorización><;><valor a buscar en el texto>
		for (String valor : ListaValores) {
			ArrayList<String> list = new ArrayList<String>();
			String[] arregloValor = valor.split(";");
			if (arregloValor.length == 2) {
				list.add(arregloValor[1]);
				list = map.putIfAbsent(arregloValor[0], list);

				if (list != null) {
					list.add(arregloValor[1]);
				}

			}

		}
		return map;
	}

}
