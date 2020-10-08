package com.edi.ia.ner.controlador;

import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;

import com.edi.ia.ner.modelo.ParametrosEntidadVO;

public class Cotejar {

	public String reconocerEntidadEntreTexto(ParametrosEntidadVO parametrosEntidadVO, String texto) {
		String valorEntidad = null;
		int inicio = -1;
		int fin = -1;
		Pattern regex;
		Matcher match;
		if (parametrosEntidadVO.getValoresIniciales() != null && parametrosEntidadVO.getValoresFinales() != null) {
			for (int i = 0; i < parametrosEntidadVO.getValoresIniciales().size() && inicio == -1; i++) {
				regex = Pattern.compile("\\b" + Pattern.quote(parametrosEntidadVO.getValoresIniciales().get(i)) + "\\b",
						Pattern.CASE_INSENSITIVE);
				match = regex.matcher(texto);
				if (match.find()) {
					inicio = match.end();
				}
			}

			if (inicio != -1) {
				for (int i = 0; i < parametrosEntidadVO.getValoresFinales().size() && fin == -1; i++) {
					regex = Pattern.compile(
							"\\b" + Pattern.quote(parametrosEntidadVO.getValoresFinales().get(i)) + "\\b",
							Pattern.CASE_INSENSITIVE);
					match = regex.matcher(texto);
					if (match.find()) {
						fin = match.start();
						if (fin > inicio) {
							valorEntidad = texto.substring(inicio, fin).trim();
						}
					}
				}
			}
		}

		return valorEntidad;
	}

	public String reconocerEntidadEntreTextoExpresionRegular(ParametrosEntidadVO parametrosEntidadVO, String texto) {
		String valorEntidad = null;
		int inicio = -1;
		int fin = -1;
		Pattern regex;
		Matcher match;

		regex = Pattern.compile(parametrosEntidadVO.getExpresionRegularValoresIniciales(), Pattern.CASE_INSENSITIVE);
		match = regex.matcher(texto);
		if (match.find()) {
			inicio = match.end();
		}

		if (inicio != -1) {
			regex = Pattern.compile(parametrosEntidadVO.getExpresionRegularValoresFinales(), Pattern.CASE_INSENSITIVE);
			match = regex.matcher(texto);
			if (match.find()) {
				fin = match.start();
				if (fin > inicio) {
					valorEntidad = texto.substring(inicio, fin).trim();
				}
			}
		}

		return valorEntidad;
	}

	public String reconocerEntidadSiguiente(ParametrosEntidadVO parametrosEntidadVO, String texto) {

		String valorEntidad = null;
		int fin = -1;
		int aux = 0;
		Pattern regex;
		Matcher match;

		if (parametrosEntidadVO.getValoresIniciales() != null) {
			for (int i = 0; i < parametrosEntidadVO.getValoresIniciales().size() && fin == -1; i++) {
				regex = Pattern.compile("\\b" + Pattern.quote(parametrosEntidadVO.getValoresIniciales().get(i)) + "\\b",
						Pattern.CASE_INSENSITIVE);
				match = regex.matcher(texto);
				if (match.find()) {
					fin = match.end();
					aux = parametrosEntidadVO.getLongitud() + fin;
					if (aux > texto.length()) {
						aux = texto.length();
					}
					String frase[] = texto.substring(fin, aux).trim().split(" ");
					if (frase.length > parametrosEntidadVO.getPosicion()) {
						valorEntidad = frase[parametrosEntidadVO.getPosicion()];
					}
				}
			}

		}

		return valorEntidad;
	}

	public String reconocerEntidadNumeroDecimal(ParametrosEntidadVO parametrosEntidadVO, String texto) {
		String valorEntidad = null;
		Pattern regex;
		Matcher match;
		int contador = 0;

		regex = Pattern.compile(parametrosEntidadVO.getExpresionRegular());
		match = regex.matcher(texto);
		while (match.find()) {
			if (contador == parametrosEntidadVO.getPosicion()) {
				valorEntidad = texto.substring(match.start(), match.end());
			}
			contador++;
		}
		return valorEntidad;
	}

	public String reconocerEntidadExpresionRegular(ParametrosEntidadVO parametrosEntidadVO, String texto) {

		String valorEntidad = null;
		int fin = -1;
		Pattern regex;
		Matcher match;

		if (parametrosEntidadVO.getValoresIniciales() != null) {
			for (int i = 0; i < parametrosEntidadVO.getValoresIniciales().size() && fin == -1; i++) {
				regex = Pattern.compile("\\b" + Pattern.quote(parametrosEntidadVO.getValoresIniciales().get(i)) + "\\b",
						Pattern.CASE_INSENSITIVE);
				match = regex.matcher(texto);
				if (match.find()) {
					fin = match.end();
					String parrafo = texto.substring(fin, fin + parametrosEntidadVO.getLongitud());
					regex = Pattern.compile(parametrosEntidadVO.getExpresionRegular());
					match = regex.matcher(parrafo);
					if (match.find()) {
						valorEntidad = parrafo.substring(match.start(), match.end());
					}
				}
			}
		}
		return valorEntidad;
	}

	public String reconocerEntidadCreditoPesosAnexoB(ParametrosEntidadVO parametrosEntidadVO, String texto) {

		String valorEntidad = " ";

		Pattern regex;
		Matcher match;

		if (parametrosEntidadVO.getExpresionRegular() != null) {

			regex = Pattern.compile(parametrosEntidadVO.getExpresionRegular());
			match = regex.matcher(texto);
			if (match.find()) {
				valorEntidad = match.group(0).trim() + " ";
			}
		}
		if (parametrosEntidadVO.getExpresionRegularAux() != null) {
			int fin = -1;
			int inicio = -1;
			int contador = 0;
			regex = Pattern.compile(parametrosEntidadVO.getExpresionRegularAux());
			match = regex.matcher(texto);

			while (match.find()) {
				// valorCreditoLetras += match.group(0).trim()+ " ";

				if (fin == -1) {
					inicio = match.start();
				}

				if (fin != -1 && (match.start() - fin) > parametrosEntidadVO.getEspacioEntrePalabras()) {
					if (contador >= parametrosEntidadVO.getAsiertos()) {
						break;
					} else {
						contador = 0;
						inicio = match.start();
					}
				}

				fin = match.end();
				contador++;
			}
			if (contador >= parametrosEntidadVO.getAsiertos()) {
				valorEntidad += texto.substring(inicio, fin);
			}
		}

		if (valorEntidad.equals(" ")) {
			valorEntidad = null;
		}

		return valorEntidad;
	}

	public String reconocerEntidadLista(ParametrosEntidadVO parametrosEntidadVO, String texto) {

		String valorEntidad = null;
		int inicio = -1;
		int fin = -1;
		Pattern regex;
		Matcher match;
		String parrafo;
		String frase[];

		if (parametrosEntidadVO.getValoresIniciales() != null && parametrosEntidadVO.getValoresFinales() != null) {
			for (int i = 0; i < parametrosEntidadVO.getValoresIniciales().size() && inicio == -1; i++) {
				regex = Pattern.compile("\\b" + Pattern.quote(parametrosEntidadVO.getValoresIniciales().get(i)) + "\\b",
						Pattern.CASE_INSENSITIVE);
				match = regex.matcher(texto);
				if (match.find()) {
					inicio = match.end();
				}
			}

			if (inicio != -1) {
				valorEntidad = " ";
				for (int i = 0; i < parametrosEntidadVO.getValoresFinales().size() && fin == -1; i++) {
					regex = Pattern.compile(
							"\\b" + Pattern.quote(parametrosEntidadVO.getValoresFinales().get(i)) + "\\b",
							Pattern.CASE_INSENSITIVE);
					match = regex.matcher(texto);
					if (match.find()) {
						fin = match.end();
						if (fin > inicio) {
							parrafo = texto.substring(inicio, fin).trim();
							regex = Pattern.compile(parametrosEntidadVO.getExpresionRegular());
							match = regex.matcher(parrafo);
							while (match.find()) {
								frase = match.group(0).trim().split(" ");
								if (frase.length > parametrosEntidadVO.getPosicion()) {
									valorEntidad += frase[parametrosEntidadVO.getPosicion()] + ",";
								}
							}
						}
					}
				}
			}
		}
		if (valorEntidad != null) {
			if (valorEntidad.length() >= 1) {
				valorEntidad = valorEntidad.substring(0, valorEntidad.length() - 1);
			}
		}
		return valorEntidad;

	}

	public String reconocerEntidadNumeroClausula(ParametrosEntidadVO parametrosEntidadVO, String texto) {

		Pattern regex;
		int index = -1;
		Matcher match;
		String clausula = null;
		String parrafo = this.reconocerParrafo(parametrosEntidadVO, texto);
		regex = Pattern.compile(parametrosEntidadVO.getExpresionRegular());
		if (parrafo != null) {
			match = regex.matcher(parrafo);
			while (match.find()) {
				if (clausula != "") {
					if ((match.start() - index) == 1) {
						clausula = clausula + " " + match.group(0);
					}
					break;
				}
				clausula = match.group(0).trim();
				index = match.end();
			}
		}
		return clausula;
	}

	public String reconocerEntidadCotejarContenido(ParametrosEntidadVO parametrosEntidadVO, String texto) {
		// String valorEntidad = "";

		String parrafo = this.reconocerParrafo(parametrosEntidadVO, texto);

		return parrafo.trim();
	}

	public String reconocerParrafo(ParametrosEntidadVO parametrosEntidadVO, String texto) {

		String parrafo = null;

		int inicio = -1;
		int fin = -1;
		int keyMapAnterior = -1;
		Pattern regex;
		Matcher match;

		if (parametrosEntidadVO.getValoresContenido() != null) {
			// Buscar indices de palabras claves
			Map<Integer, String> mapIndex = new TreeMap<Integer, String>();
			for (int i = 0; i < parametrosEntidadVO.getValoresContenido().size(); i++) {
				regex = Pattern.compile("\\b" + Pattern.quote(parametrosEntidadVO.getValoresContenido().get(i)) + "\\b",
						Pattern.CASE_INSENSITIVE);
				match = regex.matcher(texto);
				while (match.find()) {
					// agregar indices a un mapa ordenado
					System.out.println("Palabra encontrada: " + match.group(0).trim());
					mapIndex.put(match.start(), match.group(0).trim());
				}
			}
			// Se identifican los indices proximos, se agrupan en listas que tienen palabras
			// claves que estan separadas una longitud determinada
			ArrayList<ArrayList<Integer>> listaIndicesProximos = new ArrayList<ArrayList<Integer>>();
			ArrayList<Integer> indicesProximos = new ArrayList<Integer>();
			// listaIndicesProximos.add(indicesProximos);
			boolean listaAdicionada = false;
			for (int keyMap : mapIndex.keySet()) {
				if (keyMapAnterior != -1) {

					if ((keyMap - keyMapAnterior) <= parametrosEntidadVO.getEspacioEntrePalabras()) {
						indicesProximos.add(keyMap);
						listaAdicionada = false;
					} else {

						listaIndicesProximos.add(indicesProximos);
						indicesProximos = new ArrayList<Integer>();
						indicesProximos.add(keyMap);
						listaAdicionada = true;
					}
				} else {
					indicesProximos.add(keyMap);
				}
				keyMapAnterior = keyMap;
			}
			if (!listaAdicionada) {
				listaIndicesProximos.add(indicesProximos);
			}
			// Se identifica la lista de conicidencias más larga
			ArrayList<Integer> listaIndicesProximosMayor = new ArrayList<Integer>();
			int longitudListaAnterior = -1;
			for (ArrayList<Integer> indicesProximosAux : listaIndicesProximos) {

				System.out.println("indicesProximosAux: " + indicesProximosAux.size());

				for (Integer tmp : indicesProximosAux) {
					System.out.println("Palabra: " + mapIndex.get(tmp));
				}

				if (longitudListaAnterior != -1) {
					if (indicesProximosAux.size() > longitudListaAnterior) {
						listaIndicesProximosMayor = indicesProximosAux;
						longitudListaAnterior = indicesProximosAux.size();
					}

				} else {
					listaIndicesProximosMayor = indicesProximosAux;
					longitudListaAnterior = indicesProximosAux.size();
				}
			}

			if (listaIndicesProximosMayor.size() >= parametrosEntidadVO.getAsiertos()
					&& listaIndicesProximosMayor.size() > 0) {
				// Sacar promedio de posisiones

				int promedioIndices = 0;
				for (Integer indice : listaIndicesProximosMayor) {
					promedioIndices += indice;
				}
				promedioIndices = promedioIndices / listaIndicesProximosMayor.size();

				// obtine indices del parrafo que tiene las palabras clave
				inicio = promedioIndices - parametrosEntidadVO.getLongitud();
				fin = promedioIndices + parametrosEntidadVO.getLongitud();

				if (inicio < 0 || parametrosEntidadVO.getLongitud() == 0) {
					inicio = 0;
				}

				if (fin > texto.length() || parametrosEntidadVO.getLongitud() == 0) {
					fin = texto.length();
				}

				parrafo = texto.substring(inicio, fin).trim();
			}
		}

		return parrafo;

	}

	public int buscarEntidad(String texto, String valorBuscado) {

		int indice = -1;
		if (valorBuscado != null) {
			if (valorBuscado != "" && valorBuscado != " ") {
				
				valorBuscado = valorBuscado.replaceAll("de", "");
				valorBuscado = valorBuscado.replaceAll("DE", "");
				valorBuscado = valorBuscado.replaceAll("la", "");
				valorBuscado = valorBuscado.replaceAll("LA", "");
				valorBuscado = valorBuscado.replaceAll("de", "");
				valorBuscado = valorBuscado.replaceAll("CON", "");
				valorBuscado = valorBuscado.replaceAll("con", "");
				valorBuscado = valorBuscado.replaceAll(" ", "|");
				
				Pattern regex;
				Matcher match;
				regex = Pattern.compile("\\b(?i)(" + valorBuscado + ")\\b", Pattern.CASE_INSENSITIVE);
				match = regex.matcher(texto);
				if (match.find()) {
					indice = match.end();
				}
			}
		}
		return indice;
	}

}
