package com.edi.ia.ner.controlador;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.edi.ia.ner.modelo.CreditoVO;
import com.edi.ia.ner.modelo.DocumentoVO;
import com.edi.ia.ner.modelo.EntidadVO;
import com.edi.ia.ner.modelo.GrupoEntidadVO;
import com.edi.ia.ner.modelo.GrupoEntidadesVO;
import com.edi.ia.ner.modelo.ModeloVO;
import com.edi.ia.ner.modelo.ModelosNerVO;
import com.edi.ia.ner.modelo.ParametrosConfiguracionVO;
import com.edi.ia.ner.modelo.ParametrosEntidadVO;
import com.edi.ia.ner.modelo.ResultadoVO;
import com.edi.ia.ner.util.Archivo;
import com.edi.ia.ner.util.Utilidad;
import com.edi.ia.ner.util.VariablesGlobales;
import com.google.gson.JsonSyntaxException;

@Service
public class Controlador implements ControladorServicio {
	private Utilidad utilidad = new Utilidad();
	private Float TASA_INTERES_ORDINARIO = null;
	private Float TASA_INTERES_MORATORIO = null;

	@Override
	public CreditoVO clasificarDatosCredito(CreditoVO creditoVO) {

		String nombreAcreditado = null;
		String nombreConyuge = null;
		String numeroCreditoAcreditado = null;
		String numeroCreditoConyuge = null;
		boolean datosCertificadoValidos = false;

		// Obtener datos del certificado de adeudo
		for (EntidadVO entidadVO : creditoVO.getEntidades()) {
			if (entidadVO.getCodigo().equals(VariablesGlobales.CODIGO_NOMBRE_ACREDITADO)) {
				nombreAcreditado = entidadVO.getValor();
				if (nombreAcreditado == null || nombreAcreditado == "" || nombreAcreditado == " ")
					datosCertificadoValidos = false;
				else
					datosCertificadoValidos = true;
			}

			if (entidadVO.getCodigo().equals(VariablesGlobales.CODIGO_NOMBRE_CONYUGE)) {
				nombreConyuge = entidadVO.getValor();
				if (nombreConyuge == null || nombreConyuge == "" || nombreConyuge == " ")
					datosCertificadoValidos = false;
				else
					datosCertificadoValidos = true;

			}

			if (entidadVO.getCodigo().equals(VariablesGlobales.CODIGO_NUMERO_CREDITO_ACREDITADO))
				numeroCreditoAcreditado = entidadVO.getValor();

			if (entidadVO.getCodigo().equals(VariablesGlobales.CODIGO_NUMERO_CREDITO_CONYUGE))
				numeroCreditoConyuge = entidadVO.getValor();
		}

		creditoVO.setEntidades(utilidad.asignarCodigoEntidadConyuge(creditoVO.getEntidades()));

		Cotejar cotejar = new Cotejar();

		// Dar formato a los textos de entrada
		creditoVO.setTextoPropiedad(utilidad.darFormatoTexto(creditoVO.getTextoPropiedad().toUpperCase()));
		creditoVO.setTextoAcreditadoAnexoB(utilidad.darFormatoTexto(creditoVO.getTextoAcreditadoAnexoB().toUpperCase()));
		creditoVO.setTextoConyugeAnexoB(utilidad.darFormatoTexto(creditoVO.getTextoConyugeAnexoB().toUpperCase()));

		try {
			ModelosNerVO modelosNerVO = utilidad.obtenerModelosNer();
			// Identificar si la escritura es con Anexos
			boolean escrituraConAnexoB = false;
			if (this.buscarValorEntidad(modelosNerVO, "ner-grupo-entidades", "ANEXO_B",
					creditoVO.getTextoAcreditadoAnexoB()) != null) {
				escrituraConAnexoB = true;
			}

			if (datosCertificadoValidos) {

				// Identificar orden del certificado
				int indiceNombreAcreditado = cotejar.buscarNombre(creditoVO.getTextoPropiedad(), nombreAcreditado);
				int indiceNombreConyuge = cotejar.buscarNombre(creditoVO.getTextoPropiedad(), nombreConyuge);

				if (indiceNombreAcreditado != -1 && indiceNombreConyuge != -1) {
					// Al cumplirse esta condcion debe intercambiar el orden de las entidades del
					// certificado entre el acreditado y el conyuge
					if (indiceNombreAcreditado > indiceNombreConyuge) {
						this.intercambiarDatos(creditoVO.getEntidades(), VariablesGlobales.ORIGEN_ENTIDAD_CERTIFICADO);
						// Al cumplir la condición anterior se deben intercanbiar los datos de
						// referencia
						String tmp = nombreAcreditado;
						nombreAcreditado = nombreConyuge;
						nombreConyuge = tmp;
						tmp = numeroCreditoAcreditado;
						numeroCreditoAcreditado = numeroCreditoConyuge;
						numeroCreditoConyuge = tmp;
					}
				}
				// Intercambiar anexo b

				// Confirmar si la escritura tiene Anexo_B
				if (escrituraConAnexoB) {

					// Intercambiar anexo_B con respecto a los datos del certificado
					indiceNombreConyuge = cotejar.buscarNombre(creditoVO.getTextoAcreditadoAnexoB(), nombreConyuge);
					indiceNombreAcreditado = cotejar.buscarNombre(creditoVO.getTextoConyugeAnexoB(), nombreAcreditado);
					boolean intercambiar = false;
					if (indiceNombreConyuge > 0 || indiceNombreAcreditado > 0) {
						intercambiar = true;
					}

					if (intercambiar) {
						this.intercambiarDatos(creditoVO.getEntidades(), VariablesGlobales.ORIGEN_ENTIDAD_ANEXO_B);
					}
				}
			}
			// Obtener datos del acreditado del anexo B
			if (escrituraConAnexoB) {
				String nombreAcreditadoAnexoB = this.buscarValorEntidad(modelosNerVO, "ner-escritura",
						"ACREDITADO_ANEXO_B", creditoVO.getTextoAcreditadoAnexoB());
				String nombreConyugeAnexoB = this.buscarValorEntidad(modelosNerVO, "ner-escritura",
						"ACREDITADO_ANEXO_B", creditoVO.getTextoConyugeAnexoB());

				int indiceNombreAcreditado = cotejar.buscarNombre(creditoVO.getTextoPropiedad(),
						nombreAcreditadoAnexoB);
				int indiceNombreConyuge = cotejar.buscarNombre(creditoVO.getTextoPropiedad(), nombreConyugeAnexoB);

				if (indiceNombreAcreditado != -1 && indiceNombreConyuge != -1) {
					// Al cumplirse esta condcion debe intercambiar el orden de las entidades del
					// certificado entre el acreditado y el conyuge
					if (indiceNombreAcreditado > indiceNombreConyuge) {
						this.intercambiarDatos(creditoVO.getEntidades(), VariablesGlobales.ORIGEN_ENTIDAD_ANEXO_B);
					}
				}
			}
		} catch (JsonSyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		creditoVO.setTextoAcreditadoAnexoB("");
		creditoVO.setTextoConyugeAnexoB("");
		creditoVO.setTextoPropiedad("");
		return creditoVO;
	}

	@Override
	public GrupoEntidadesVO reconocerParrafo(GrupoEntidadesVO grupoEntidad) {

		grupoEntidad.setTexto(utilidad.darFormatoTexto(grupoEntidad.getTexto()));
		grupoEntidad.setTextoPaginaActual(utilidad.darFormatoTexto(grupoEntidad.getTextoPaginaActual()));

		ArrayList<String> entidadesPreviamenteProcesadas = new ArrayList<String>();

		ModelosNerVO modelosNerVO;

		try {
			modelosNerVO = utilidad.obtenerModelosNer();

			// Obtener los parametros de las entidades por modelo
			Map<String, ParametrosEntidadVO> mapParametrosEntidadVO = utilidad.obtenerParametrosEntidad(modelosNerVO,
					"ner-grupo-entidades");
			for (GrupoEntidadVO grupoEntidadVO : grupoEntidad.getGrupoEntidades()) {

				String texto = grupoEntidad.getTexto();
				if (grupoEntidadVO.isEnviaTextoSiguienteHoja()) {
					texto = grupoEntidad.getTextoPaginaActual();
				}

				// obtiene los parametros de la entidad a busrcar
				ParametrosEntidadVO parametrosEntidadVO = mapParametrosEntidadVO.get(grupoEntidadVO.getGrupoEntidad());
				grupoEntidadVO.setValor(null);

				if (parametrosEntidadVO != null
						&& entidadesPreviamenteProcesadas.contains(grupoEntidadVO.getGrupoEntidad()) == false) {
					// validar si el texto tine la cantidad minima de caractes para hacer la
					// busqueda de la entidad, cero indica hacer la busqueda sin importar la
					// longitud del tecto
					if (parametrosEntidadVO.getCantidadMinimaCaracteres() == 0
							|| texto.length() >= parametrosEntidadVO.getCantidadMinimaCaracteres()) {
						
						// Buscar Valor de la entidad
						grupoEntidadVO.setValor(this.buscarValorEntidad(parametrosEntidadVO, texto));
						grupoEntidadVO.setCodigoServicio(parametrosEntidadVO.getCodigoServicio());
					}
				}
				entidadesPreviamenteProcesadas.add(grupoEntidadVO.getGrupoEntidad());
				if (grupoEntidadVO.getValor() != null) {

				}
			}
		} catch (JsonSyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		grupoEntidad.setTexto(null);
		grupoEntidad.setTextoPaginaActual(null);
		return grupoEntidad;

	}

	@Override
	public DocumentoVO reconocerEntidades(DocumentoVO documento) {

		TASA_INTERES_ORDINARIO = null;
		TASA_INTERES_MORATORIO = null;
		Cotejar cotejar = new Cotejar();

		documento.setTexto(utilidad.darFormatoTexto(documento.getTexto()));
		try {
			// Obtener nodelos con parametros de configuración
			ModelosNerVO modelosNerVO = utilidad.obtenerModelosNer();

			// Recorren los modelos
			documento.getModelos().stream().forEach(modeloVO -> {

				ArrayList<String> entidadesPreviamenteProcesadas = new ArrayList<String>();


				// Obtener los parametros de las entidades por modelo
				Map<String, ParametrosEntidadVO> mapParametrosEntidadVO = utilidad
						.obtenerParametrosEntidad(modelosNerVO, modeloVO.getModelo());

				// Recorrer entidades
				for (EntidadVO entidadVO : modeloVO.getEntidades()) {
					// obtiene los parametros de la entidad a busrcar
					ParametrosEntidadVO parametrosEntidadVO = mapParametrosEntidadVO.get(entidadVO.getEntidad());

					// Validar que los parametros no sean nulos y que la entidad no se haya
					// procesado anteriormente, para que las entidades repetidas se procesen en una
					// siguiente hoja
					if (parametrosEntidadVO != null
							&& entidadesPreviamenteProcesadas.contains(entidadVO.getEntidad()) == false) {

						// Buscar valor de entidades relacionadas con colindacias
						if (parametrosEntidadVO.getTipoReconocimiento().equals("reconocerEntidadMedidasColindancias")) {
							ResultadoVO resultadoVO = cotejar.reconocerEntidadMedidasColindancias(parametrosEntidadVO,
									documento.getTexto());
							this.completarEntidadesRelacionadas(resultadoVO, parametrosEntidadVO,
									modeloVO.getEntidades());
						}

						else {
							// Buscar valor de entidades individuales
							entidadVO.setValor(this.buscarValorEntidad(parametrosEntidadVO, documento.getTexto()));
							this.aplicarReglasParticularesEntidad(entidadVO, modeloVO.getEntidades(),
									parametrosEntidadVO);
						}
					}

					entidadesPreviamenteProcesadas.add(entidadVO.getEntidad());

					if (entidadVO.getValor() != " ") {
						entidadVO.setConfianza(95);
					}
				}
				// Validar si reconocio al menos la entidad credito en pesos del anexo_b para
				// retornar valores de los contrario limpia los valores.
				List<String> codigosEntidadAcreditado = Arrays
						.asList(VariablesGlobales.CODIGOS_ENTIDAD_ACREDITADO.split(" "));
				if (this.validarMinimoEntidadesReconocidasAnexoB(modeloVO, codigosEntidadAcreditado,
						VariablesGlobales.CODIGO_CREDITO_PESOS_ACREDITADO)) {
					this.limpiarDatosConyugeAnexoB(modeloVO);
				}
				List<String> codigosEntidadConyuge = Arrays
						.asList(VariablesGlobales.CODIGOS_ENTIDAD_CONYUGE.split(" "));
				this.validarMinimoEntidadesReconocidasAnexoB(modeloVO, codigosEntidadConyuge,
						VariablesGlobales.CODIGO_CREDITO_PESOS_CONYUGE);

			});

		} catch (JsonSyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		documento.setTexto(null);
		return documento;
	}

	

	private boolean validarMinimoEntidadesReconocidasAnexoB(ModeloVO modeloVO, List<String> codigosEntidad,
			String CodigoCreditoPesos) {

		boolean limpiarEntidades = false;

		for (EntidadVO entidadVO : modeloVO.getEntidades()) {
			if (entidadVO.getValor() != null) {
				if (entidadVO.getCodigo().equals(CodigoCreditoPesos)
						&& (entidadVO.getValor().equals(" ") || entidadVO.getValor().equals(""))) {
					limpiarEntidades = true;
					break;
				}
			}
		}

		if (limpiarEntidades) {
			for (EntidadVO entidadVO : modeloVO.getEntidades()) {
				if (codigosEntidad.contains(entidadVO.getCodigo())) {
					entidadVO.setValor(null);
				}
			}

		}
		return limpiarEntidades;
	}

	public void limpiarDatosConyugeAnexoB(ModeloVO modeloVO) {
		List<String> codigosEntidadConyuge = Arrays.asList(VariablesGlobales.CODIGOS_ENTIDAD_CONYUGE.split(" "));
		for (EntidadVO entidadVO : modeloVO.getEntidades()) {
			if (codigosEntidadConyuge.contains(entidadVO.getCodigo())) {
				entidadVO.setValor(null);
			}
		}
	}

	private String buscarValorEntidad(ParametrosEntidadVO parametrosEntidadVO, String texto) {
		Cotejar cotejar = new Cotejar();
		String valorEntidad = null;
		if (parametrosEntidadVO.getTipoReconocimiento().equals("reconocerEntidadEntreTexto")) {
			valorEntidad = cotejar.reconocerEntidadEntreTexto(parametrosEntidadVO, texto);
		} else if (parametrosEntidadVO.getTipoReconocimiento().equals("reconocerEntidadExpresionRegular")) {
			valorEntidad = cotejar.reconocerEntidadExpresionRegular(parametrosEntidadVO, texto);
		} else if (parametrosEntidadVO.getTipoReconocimiento().equals("reconocerEntidadSiguiente")) {
			valorEntidad = cotejar.reconocerEntidadSiguiente(parametrosEntidadVO, texto);
		} else if (parametrosEntidadVO.getTipoReconocimiento().equals("reconocerEntidadLista")) {
			valorEntidad = cotejar.reconocerEntidadLista(parametrosEntidadVO, texto);
			// } else if
			// (parametrosEntidadVO.getTipoReconocimiento().equals("reconocerEntidadCotejarContenido"))
			// {
			// valorEntidad = cotejar.reconocerEntidadCotejarContenido(parametrosEntidadVO,
			// texto);
		} else if (parametrosEntidadVO.getTipoReconocimiento().equals("reconocerEntidadNumeroClausula")) {
			valorEntidad = cotejar.reconocerEntidadNumeroClausula(parametrosEntidadVO, texto);
		} else if (parametrosEntidadVO.getTipoReconocimiento().equals("reconocerParrafo")) {
			valorEntidad = cotejar.reconocerParrafo(parametrosEntidadVO, texto);
		} else if (parametrosEntidadVO.getTipoReconocimiento().equals("reconocerEntidadCreditoPesosAnexoB")) {
			valorEntidad = cotejar.reconocerEntidadCreditoPesosAnexoB(parametrosEntidadVO, texto);
		} else if (parametrosEntidadVO.getTipoReconocimiento().equals("reconocerEntidadNumeroDecimal")) {
			valorEntidad = cotejar.reconocerEntidadNumeroDecimal(parametrosEntidadVO, texto);
		} else if (parametrosEntidadVO.getTipoReconocimiento().equals("reconocerEntidadEntreTextoExpresionRegular")) {
			valorEntidad = cotejar.reconocerEntidadEntreTextoExpresionRegular(parametrosEntidadVO, texto);
		} else if (parametrosEntidadVO.getTipoReconocimiento().equals("reconocerEntidadSuperficie")) {
			valorEntidad = cotejar.reconocerEntidadSuperficie(parametrosEntidadVO, texto);
		}

		if (valorEntidad != null) {
			valorEntidad = valorEntidad.trim();
			valorEntidad = valorEntidad.toUpperCase();
			
		}

		// Float f = Float.parseFloat("5.2");

		return valorEntidad;
	}

	private String buscarValorEntidad(ModelosNerVO modelosNerVO, String modelo, String entidad, String texto) {

		Map<String, ParametrosEntidadVO> mapParametrosEntidadVO = utilidad.obtenerParametrosEntidad(modelosNerVO,
				modelo);
		ParametrosEntidadVO parametrosEntidadVO = mapParametrosEntidadVO.get(entidad);

		return this.buscarValorEntidad(parametrosEntidadVO, texto);
	}

	public DocumentoVO reconocerTipoDemanda(DocumentoVO documento) {
		Archivo archivo = new Archivo();
		Cotejar cotejar = new Cotejar();
		if (documento.getTipoDemanda().equals("td03")) {
			documento.setTipoDemanda("");
			try {
				Map<String, ParametrosEntidadVO> mapParametrosEntidadVO = new TreeMap<String, ParametrosEntidadVO>();
				ModelosNerVO modelosNerVO = archivo
						.leerParametrosEntidades(ParametrosConfiguracionVO.rutaParametrosEntidad);

				int index = -1;
				for (int i = 0; i < modelosNerVO.getModelos().size() && index == -1; i++) {

					if (modelosNerVO.getModelos().get(i).getModelo().equals("reconocer_demanda")) {
						mapParametrosEntidadVO = modelosNerVO.getModelos().get(i).getMap();
						index = 1;
						ParametrosEntidadVO parametrosEntidadVO = mapParametrosEntidadVO.get("TIPO_DEMANDA");
						if (cotejar.reconocerParrafo(parametrosEntidadVO, documento.getTexto()) != "") {
							documento.setTipoDemanda("td02");
						}
					}
				}
			} catch (JsonSyntaxException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		documento.setTexto(null);
		return documento;
	}

	public void aplicarReglasParticularesEntidad(EntidadVO entidadVO, ArrayList<EntidadVO> listaEntidadesVO,
			ParametrosEntidadVO parametrosEntidadVO) {

		// Regla sumar TASA_INTERES_MORATORIO
		if (entidadVO.getEntidad().equals("TASA_INTERES_ORDINARIO") && entidadVO.getValor() != null) {
			TASA_INTERES_ORDINARIO = Float.parseFloat(utilidad.darFormatoFloat(entidadVO.getValor()));
		}
		if (entidadVO.getEntidad().equals("TASA_INTERES_MORATORIO") && entidadVO.getValor() != null) {
			TASA_INTERES_MORATORIO = Float.parseFloat(utilidad.darFormatoFloat(entidadVO.getValor()));
		}
		if (TASA_INTERES_ORDINARIO != null & TASA_INTERES_MORATORIO != null) {
			for (EntidadVO entidadVOAux : listaEntidadesVO) {
				if (entidadVOAux.getEntidad().equals("TASA_INTERES_MORATORIO")) {
					entidadVOAux.setValor(new BigDecimal((TASA_INTERES_ORDINARIO + TASA_INTERES_MORATORIO)).setScale(1,
							RoundingMode.HALF_EVEN) + "%");
					break;
				}
			}
		}

		// Se fija valor por defecto para indicar que la entidad se encuentra en el
		// documento pero no se logro procesar
		if (entidadVO.getValor() == null) {
			entidadVO.setValor(" ");
		} else {
			if (entidadVO.getValor() != " ") {
				entidadVO.setValor(entidadVO.getValor().trim());
				entidadVO.setValor(utilidad.removerValoresAlFinal(parametrosEntidadVO.getValoresRemoverAlFinal(),
						entidadVO.getValor()));
			}

		}

	}

	public void completarEntidadesRelacionadas(ResultadoVO resultadoVO, ParametrosEntidadVO parametrosEntidadVO,
			ArrayList<EntidadVO> listaEntidadesVO) {

		ArrayList<String> listaentidadesRelacionadas = new ArrayList<String>();
		listaentidadesRelacionadas.addAll(Arrays.asList(parametrosEntidadVO.getEntidadRelacionadas().split(",")));
		int i = 0;
		for (String valor : resultadoVO.getListaResutado()) {
			valor = valor.trim().toUpperCase();
			if (listaentidadesRelacionadas.size() >= i) {
				for (EntidadVO entidadVO : listaEntidadesVO) {
				 	if (entidadVO.getEntidad().equals(listaentidadesRelacionadas.get(i))) {
						entidadVO.setValor(valor);
					}

					this.aplicarReglasParticularesEntidad(entidadVO, listaEntidadesVO, parametrosEntidadVO);
				}
			}
			i++;
		}
	}

	public void intercambiarDatos(ArrayList<EntidadVO> listaEntidadesVO, String origenEntidad) {
		for (EntidadVO entidadVO : listaEntidadesVO) {
			if (entidadVO.getOrigenEntidad().equals(origenEntidad)) {
				entidadVO.setCodigo(entidadVO.getCodigoConyuge());
			}
		}

	}

}
