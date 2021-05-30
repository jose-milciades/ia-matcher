package com.edi.ia.ner.controlador;

import java.util.regex.Pattern;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;

import com.edi.ia.ner.modelo.ContenidoTextoVO;
import com.edi.ia.ner.modelo.ParametrosEntidadVO;
import com.edi.ia.ner.modelo.ResultadoVO;
import com.edi.ia.ner.util.Utilidad;
import com.edi.ia.ner.util.VariablesGlobales;

public class Cotejar {

	Utilidad utilidad = new Utilidad();

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
				texto = utilidad.recortarTexto(texto, inicio, parametrosEntidadVO.getLongitud());
				inicio = 0;
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

	public String reconocerEntidadFechaOmisos(ParametrosEntidadVO parametrosEntidadVO,
			ArrayList<String> hojasCertificado) {
		String valorEntidad = null;
		String textoAux = null;
		ArrayList<String> moviminetos = new ArrayList<String>();
		

		Pattern regex;
		Matcher match;
		for (String textoHoja : hojasCertificado) {
			if (moviminetos.isEmpty()) {
				regex = Pattern.compile(parametrosEntidadVO.getExpresionRegularValoresIniciales(),
						Pattern.CASE_INSENSITIVE);
				match = regex.matcher(textoHoja);
				if (match.find()) {
					regex = Pattern.compile(parametrosEntidadVO.getExpresionRegular(), Pattern.CASE_INSENSITIVE);
					textoAux = textoHoja.substring(match.end(), textoHoja.length() - 1).trim();
					match = regex.matcher(textoAux);
					while (match.find()) {
						moviminetos.add(textoAux.substring(match.start(), match.end()));
					}
				}
			}
			else {
				regex = Pattern.compile(parametrosEntidadVO.getExpresionRegularValoresFinales(),
						Pattern.CASE_INSENSITIVE);
				match = regex.matcher(textoHoja);
				if (match.find()) {
					regex = Pattern.compile(parametrosEntidadVO.getExpresionRegular(), Pattern.CASE_INSENSITIVE);
					textoAux = textoHoja.substring(0, match.start())+" ";
					match = regex.matcher(textoAux);
					while (match.find()) {
						moviminetos.add(textoAux.substring(match.start(), match.end()));
						
					}
					break;

				} else {
					regex = Pattern.compile(parametrosEntidadVO.getExpresionRegular(), Pattern.CASE_INSENSITIVE);
					match = regex.matcher(textoHoja.trim());
					while (match.find()) {
						moviminetos.add(textoHoja.substring(match.start(), match.end()));

					}
				}
			}
		}
		for (String mov : moviminetos) {
			System.out.println(mov);
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
					int fincadena = fin + parametrosEntidadVO.getLongitud();
					if (texto.length() < fin + parametrosEntidadVO.getLongitud()) {
						fincadena = texto.length();
					}
					String parrafo = texto.substring(fin, fincadena);
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

				if (fin == -1) {
					inicio = match.start();
				}
				System.out.println("Credito en texto 1: " + match.group(0).trim());
				if (fin != -1 && (match.start() - fin) > parametrosEntidadVO.getEspacioEntrePalabras()) {
					if (contador >= parametrosEntidadVO.getAsiertos()) {
						System.out.println("Credito en texto 2: " + match.group(0).trim());
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

	public String reconocerEntidadSuperficie(ParametrosEntidadVO parametrosEntidadVO, String texto) {

		String valorEntidad = " ";
		int fin = -1;
		int inicio = -1;

		Pattern regex;
		Matcher match;

		if (parametrosEntidadVO.getValoresIniciales() != null) {
			for (int i = 0; i < parametrosEntidadVO.getValoresIniciales().size() && inicio == -1; i++) {
				regex = Pattern.compile(parametrosEntidadVO.getValoresIniciales().get(i), Pattern.CASE_INSENSITIVE);
				match = regex.matcher(texto);
				if (match.find()) {
					inicio = match.end();
				}
			}
		}

		if (inicio != -1) {

			int finTexto = inicio + parametrosEntidadVO.getLongitud();
			if (texto.length() < finTexto) {
				finTexto = texto.length();
			}
			String parrafo = texto.substring(inicio, finTexto);

			if (parametrosEntidadVO.getExpresionRegular() != null) {

				regex = Pattern.compile(parametrosEntidadVO.getExpresionRegular());
				match = regex.matcher(parrafo);
				if (match.find()) {
					valorEntidad = match.group(0).trim() + " ";
					parrafo = parrafo.substring(match.end(), parrafo.length());
				}
			}
			if (parametrosEntidadVO.getExpresionRegularAux() != null) {
				fin = -1;
				inicio = -1;
				int contador = 0;
				regex = Pattern.compile(parametrosEntidadVO.getExpresionRegularAux());
				match = regex.matcher(parrafo);

				while (match.find()) {

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
				if (contador >= parametrosEntidadVO.getAsiertos() && inicio != -1 && fin != -1) {
					valorEntidad += parrafo.substring(inicio, fin);
				}
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

	public ContenidoTextoVO reconocerEntidadCotejarContenido(ParametrosEntidadVO parametrosEntidadVO, String texto) {

		String parrafo = null;
		int inicio = -1;
		int fin = -1;
		int keyMapAnterior = -1;
		Pattern regex;
		Matcher match;
		ArrayList<Integer> listaIndicesProximosMayor = new ArrayList<Integer>();
		if (parametrosEntidadVO.getValoresContenido() != null) {
			// Buscar indices de palabras claves
			Map<Integer, String> mapIndex = new TreeMap<Integer, String>();
			for (int i = 0; i < parametrosEntidadVO.getValoresContenido().size(); i++) {
				regex = Pattern.compile("\\b" + Pattern.quote(parametrosEntidadVO.getValoresContenido().get(i)) + "\\b",
						Pattern.CASE_INSENSITIVE);
				match = regex.matcher(texto);
				while (match.find()) {
					// agregar indices a un mapa ordenado
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

			int longitudListaAnterior = -1;

			for (ArrayList<Integer> indicesProximosAux : listaIndicesProximos) {

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

				// Esto es una rutina de prueba se debe eliminar
				///// 7
				String palabraTxt = "";
				for (Integer tmp : listaIndicesProximosMayor) {
					palabraTxt += " " + mapIndex.get(tmp);
				}
				System.out.println("Palabras encontradas para las entidades " + parametrosEntidadVO.getEntidades().toString() + ": "
						+ listaIndicesProximosMayor.size() + " (" + palabraTxt + ")");
				///////

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
			} else {
				listaIndicesProximosMayor.clear();
			}
		}

		ContenidoTextoVO contenidoTextoVO = new ContenidoTextoVO();
		contenidoTextoVO.setIndices(listaIndicesProximosMayor);
		contenidoTextoVO.setTexto(parrafo);

		return contenidoTextoVO;
	}

	public String reconocerParrafo(ParametrosEntidadVO parametrosEntidadVO, String texto) {

		ContenidoTextoVO contenidoTextoVO = this.reconocerEntidadCotejarContenido(parametrosEntidadVO, texto);
		return contenidoTextoVO.getTexto();

	}

	public int buscarNombre(String texto, String nombre) {

		int indice = -1;
		if (nombre != null) {
			if (nombre != "" && nombre != " ") {

				texto = utilidad.limpiarAcentos(texto);
				nombre = utilidad.limpiarAcentos(nombre);

				nombre = utilidad.darFormatoNombre(nombre);
				ArrayList<String> valoresContenido = new ArrayList<String>();
				valoresContenido.addAll(Arrays.asList(nombre.split(" ")));
				ParametrosEntidadVO parametrosEntidadVO = new ParametrosEntidadVO();
				ArrayList<String> entidades = new ArrayList<String>();
				entidades.add(VariablesGlobales.NOMBRE_ACREDITADO_CLASIFICAR_DATOS);
				parametrosEntidadVO.setEntidades(entidades);
				parametrosEntidadVO.setValoresContenido(valoresContenido);
				parametrosEntidadVO.setAsiertos(2);
				parametrosEntidadVO.setEspacioEntrePalabras(20);
				parametrosEntidadVO.setLongitud(0);

				ArrayList<Integer> indices = this.reconocerEntidadCotejarContenido(parametrosEntidadVO, texto)
						.getIndices();

				if (indices != null && indices.size() > 0) {
					indice = indices.get(indices.size() - 1);
				}
			}
		}
		return indice;
	}

	public ResultadoVO reconocerEntidadMedidasColindancias(ParametrosEntidadVO parametrosEntidadVO, String texto) {

		ArrayList<ResultadoVO> listaIndicesResultadosVO = new ArrayList<ResultadoVO>();
		boolean colindanciasAgrupadas = false;

		// Mapa con lista priorizada de los valores o expresiones regulares que se deben
		// indentificar en el
		// texto
		Map<String, ArrayList<String>> valoresPorPrioridad = utilidad
				.getValoresPorPrioridad(parametrosEntidadVO.getValoresContenido());

		ResultadoVO indicesResultadoOmitir = this.getIndices(parametrosEntidadVO.getValoresOmitir(), texto);

		// Recorrer expreciones regulares por prioridad
		for (Map.Entry<String, ArrayList<String>> valores : valoresPorPrioridad.entrySet()) {

			// Obtiene los resultados en mapas ordenados por Prioridad
			ResultadoVO indicesResultadosVO = this.getIndices(valores.getValue(), texto);
			indicesResultadosVO.setGrupo(valores.getKey());

			listaIndicesResultadosVO.add(indicesResultadosVO);
			///////// Prueba
			this.omitirValores(listaIndicesResultadosVO, indicesResultadoOmitir);
			this.setUtimoIndice(listaIndicesResultadosVO);
			if (indicesResultadosVO.getMapResultado().size() > 3 && indicesResultadosVO.getGrupo().equals("1")) {
				// if (indicesResultadosVO.getMapResultado().size() % 2 != 0) {

				// Comentado 2 de enro de 2020
				// this.setIndiceFinalDeUltimaColindacia(indicesResultadosVO,
				// parametrosEntidadVO, texto);
				// }
				colindanciasAgrupadas = true;
			}
		}
		// Quitar indices de valores a omitir

		this.omitirValores(listaIndicesResultadosVO, indicesResultadoOmitir);

		// Quitar los indices que sen mayores al mayor indice del siguiente grupo
		if (colindanciasAgrupadas == false) {
			this.omitirIndiceMayorSigueinteGrupo(listaIndicesResultadosVO, "1");
		}

		// Obtener las colindancias agrupadas en la lista del objeto ResultadoVO
		ResultadoVO resultadoFinal = this.unirMedidasColindancias(listaIndicesResultadosVO, parametrosEntidadVO, texto);

		// Identificar indice Final y obtener la ultima colindancia
		// if (colindanciasAgrupadas == false) {
		if (resultadoFinal.getUltimoIndice() >= 0) {
			this.setUltimaMedidaColindancia(resultadoFinal, parametrosEntidadVO, texto);
		}
		// }

		return resultadoFinal;
	}

	public void setUltimaMedidaColindancia(ResultadoVO resultadoFinal, ParametrosEntidadVO parametrosEntidadVO,
			String texto) {

		int indiceAnterior = -1;
		boolean existeCaracterDeTermino = false;
		// Identificar indice Final y obtener la ultima colindancia
		ResultadoVO resultadoIndicesFinales = this.getIndices(parametrosEntidadVO.getValoresFinales(), texto);

		for (Map.Entry<Integer, String> indice : resultadoIndicesFinales.getMapResultado().entrySet()) {

			if (indice.getKey() > resultadoFinal.getUltimoIndice()) {
				if (indice.getKey() - indiceAnterior > parametrosEntidadVO.getLongitud()
						&& resultadoFinal.getUltimoIndice() - indice.getKey() > 15) {

					resultadoFinal.getListaResutado()
							.add(texto.substring(resultadoFinal.getUltimoIndice(), indice.getKey()));
					existeCaracterDeTermino = true;

					break;
				}
			}
			indiceAnterior = indice.getKey();
		}
		if (!existeCaracterDeTermino) {

			if (texto.indexOf(" ", resultadoFinal.getUltimoIndice() + 100) > resultadoFinal.getUltimoIndice()) {
				resultadoFinal.getListaResutado().add(texto.substring(resultadoFinal.getUltimoIndice(),
						texto.indexOf(" ", resultadoFinal.getUltimoIndice() + 100)));

			} else {
				resultadoFinal.getListaResutado()
						.add(texto.substring(resultadoFinal.getUltimoIndice(), texto.length()));

			}
		}
	}

	public void setIndiceFinalDeUltimaColindacia(ResultadoVO rsultadoVO, ParametrosEntidadVO parametrosEntidadVO,
			String texto) {

		int indiceAnterior = -1;
		Integer indiceFinal = -1;
		boolean existeCaracterDeTermino = false;
		int longitud = parametrosEntidadVO.getLongitud();
		if (rsultadoVO.getGrupo().equals("1")) {
			longitud = parametrosEntidadVO.getLongitudGrupoColindancia();
		}
		// Identificar indice Final y obtener la ultima colindancia
		ResultadoVO resultadoIndicesFinales = this.getIndices(parametrosEntidadVO.getValoresFinales(), texto);

		for (Map.Entry<Integer, String> indice : resultadoIndicesFinales.getMapResultado().entrySet()) {

			if (indice.getKey() > rsultadoVO.getUltimoIndice()) {
				if (indice.getKey() - indiceAnterior > longitud) {
					indiceFinal = indice.getKey();
					existeCaracterDeTermino = true;
					break;
				}
			}
			indiceAnterior = indice.getKey();
		}
		if (!existeCaracterDeTermino) {

			if (texto.indexOf(" ", rsultadoVO.getUltimoIndice() + 100) > rsultadoVO.getUltimoIndice()) {
				indiceFinal = texto.indexOf(" ", rsultadoVO.getUltimoIndice() + 100);

			} else {
				indiceFinal = texto.length();

			}
		}

		if (indiceFinal != -1) {
			rsultadoVO.getMapResultado().put(indiceFinal, " ");
			rsultadoVO.setUltimoIndice(indiceFinal);
		}
	}

	public void omitirValores(ArrayList<ResultadoVO> listaIndicesResultadosVO, ResultadoVO indicesResultadoOmitir) {

		for (ResultadoVO resultadoVO : listaIndicesResultadosVO) {
			ArrayList<Integer> indicesOmitir = new ArrayList<Integer>();
			for (Map.Entry<Integer, String> valores : resultadoVO.getMapResultado().entrySet()) {
				for (Map.Entry<Integer, String> valoresOmitir : indicesResultadoOmitir.getMapResultado().entrySet()) {

					if (valoresOmitir.getKey() > valores.getKey() - 15
							&& valoresOmitir.getKey() < valores.getKey() + 15) {
						indicesOmitir.add(valores.getKey());
					}

				}
			}
			for (Integer i : indicesOmitir) {
				resultadoVO.getMapResultado().remove(i);
			}
			if (resultadoVO.getMapResultado().size() == 0) {
				resultadoVO.setUltimoIndice(-1);
			}
		}
	}

	public void omitirIndiceMayorSigueinteGrupo(ArrayList<ResultadoVO> listaIndicesResultadosVO, String grupo) {
		int i = 0;
		ArrayList<Integer> indicesOmitir = new ArrayList<Integer>();
		Integer ultimoIndiceSiguiente = -1;
		for (ResultadoVO resultadoVO : listaIndicesResultadosVO) {
			if (resultadoVO.getGrupo().equals(grupo)) {
				if (listaIndicesResultadosVO.size() >= i + 1) {
					ResultadoVO siquieteResultadoVO = listaIndicesResultadosVO.get(i + 1);
					for (Map.Entry<Integer, String> seguientesValores : siquieteResultadoVO.getMapResultado()
							.entrySet()) {
						ultimoIndiceSiguiente = seguientesValores.getKey();
					}
					for (Map.Entry<Integer, String> valores : resultadoVO.getMapResultado().entrySet()) {

						if (valores.getKey() > ultimoIndiceSiguiente) {
							indicesOmitir.add(valores.getKey());
						} else
							resultadoVO.setUltimoIndice(valores.getKey());
					}

				}

				for (Integer indice : indicesOmitir) {
					resultadoVO.getMapResultado().remove(indice);
				}
				break;
			}

			i++;
		}

	}

	public void setUtimoIndice(ArrayList<ResultadoVO> listaIndicesResultadosVO) {
		for (ResultadoVO resultadoVO : listaIndicesResultadosVO) {
			for (Map.Entry<Integer, String> valores : resultadoVO.getMapResultado().entrySet()) {
				resultadoVO.setUltimoIndice(valores.getKey());
			}
		}

	}

	public ResultadoVO unirMedidasColindancias(ArrayList<ResultadoVO> listaIndicesResultadosVO,
			ParametrosEntidadVO parametrosEntidadVO, String texto) {

		ArrayList<String> listaResutadoTotal = new ArrayList<String>();
		ResultadoVO resultadoTotal = new ResultadoVO();
		resultadoTotal.setUltimoIndice(-1);
		resultadoTotal.setListaResutado(listaResutadoTotal);

		int indiceAnterior = -1;

		for (ResultadoVO resultadoVO : listaIndicesResultadosVO) {
			// fijar longitud (de de desacarte) de grupo de colindancias o de colidancias
			// individuales
			int longitud = parametrosEntidadVO.getLongitud();
			if (resultadoVO.getGrupo().equals("1")) {
				longitud = parametrosEntidadVO.getLongitudGrupoColindancia();
			}
			// comentado el 2 de enro de 2020 indiceAnterior = -1;

			for (Map.Entry<Integer, String> valores : resultadoVO.getMapResultado().entrySet()) {
				{

					// Validar que el indice actual sea mayor que el indice del grupo anterior, para
					// que no se traslapen los valores
					if (valores.getKey() > resultadoTotal.getUltimoIndice()) {

						if (resultadoVO.getMapResultado().size() > 1) {
							if (indiceAnterior != -1) {
								// Validar que se cumpla una separación minima entre los indices
								if (valores.getKey() - indiceAnterior > longitud) {
									// Fijar resultados encontrados en el texto
									// System.out.println("valores.getKey(): " + valores.getKey() + " Texto: "
									// + texto.substring(indiceAnterior, valores.getKey()));
									listaResutadoTotal.add(texto.substring(indiceAnterior, valores.getKey()));
									indiceAnterior = valores.getKey();

									// Fija el ultimo indice de los resulatados del grupo
									resultadoVO.setUltimoIndice(valores.getKey());

								}
							} else {
								indiceAnterior = valores.getKey();

							}
						}
					}
				}
			}
			// Fija el ultimo indice al resultado total, necesario para completar el ultimo
			// dato
			if (resultadoTotal.getUltimoIndice() < resultadoVO.getUltimoIndice()) {
				resultadoTotal.setUltimoIndice(resultadoVO.getUltimoIndice());
				// System.out.println("resultadoTotal.setUltimoIndice: " +
				// resultadoVO.getUltimoIndice());
			}
		}
		return resultadoTotal;
	}

	public ResultadoVO getIndices(ArrayList<String> listaValores, String texto) {

		Map<Integer, String> mapResultado = new TreeMap<Integer, String>();
		Pattern regex;
		Matcher match;
		ResultadoVO resultadoVO = new ResultadoVO();
		resultadoVO.setMapResultado(mapResultado);
		for (String valor : listaValores) {
			regex = Pattern.compile(valor);
			match = regex.matcher(texto);
			while (match.find()) {
				mapResultado.put(match.start(), match.group(0));
				resultadoVO.setUltimoIndice(match.start());

			}
		}
		return resultadoVO;
	}

}
