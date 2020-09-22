package com.edi.ia.ner.controlador;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import com.edi.ia.ner.modelo.DocumentoVO;
import com.edi.ia.ner.modelo.EntidadVO;
import com.edi.ia.ner.modelo.GrupoEntidadVO;
import com.edi.ia.ner.modelo.GrupoEntidadesVO;
import com.edi.ia.ner.modelo.ModelosNerVO;
import com.edi.ia.ner.modelo.ParametrosConfiguracionVO;
import com.edi.ia.ner.modelo.ParametrosEntidadVO;
import com.edi.ia.ner.util.Archivo;
import com.edi.ia.ner.util.Utilidad;
import com.google.gson.JsonSyntaxException;

@Service
public class Controlador implements ControladorServicio {
	private Utilidad utilidad = new Utilidad();
	private Float TASA_INTERES_ORDINARIO = null;
	private Float TASA_INTERES_MORATORIO = null;

	@Override
	public GrupoEntidadesVO reconocerParrafo(GrupoEntidadesVO grupoEntidad) {
		grupoEntidad.setTexto(utilidad.darFormatoTexto(grupoEntidad.getTexto()));

		ArrayList<String> entidadesPreviamenteProcesadas = new ArrayList<String>();

		ModelosNerVO modelosNerVO;

		try {
			modelosNerVO = utilidad.obtenerModelosNer();

			// Obtener los parametros de las entidades por modelo
			Map<String, ParametrosEntidadVO> mapParametrosEntidadVO = utilidad.obtenerParametrosEntidad(modelosNerVO,
					"ner-grupo-entidades");
			for (GrupoEntidadVO grupoEntidadVO : grupoEntidad.getGrupoEntidades()) {
				// obtiene los parametros de la entidad a busrcar
				ParametrosEntidadVO parametrosEntidadVO = mapParametrosEntidadVO.get(grupoEntidadVO.getGrupoEntidad());
				grupoEntidadVO.setValor(null);

				if (parametrosEntidadVO != null
						&& entidadesPreviamenteProcesadas.contains(grupoEntidadVO.getGrupoEntidad()) == false) {
					// Buscar Valor de la entidad
					grupoEntidadVO.setValor(this.buscarValorEntidad(parametrosEntidadVO, grupoEntidad.getTexto()));
					grupoEntidadVO.setCodigoServicio(parametrosEntidadVO.getCodigoServicio());
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
		return grupoEntidad;

	}

	@Override
	public DocumentoVO reconocerEntidades(DocumentoVO documento) {

		TASA_INTERES_ORDINARIO = null;
		TASA_INTERES_MORATORIO = null;

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
					entidadVO.setValor(null);
					// Validar que los parametros no sean nulos y que la entidad no se haya
					// procesado anteriormente
					if (parametrosEntidadVO != null
							&& entidadesPreviamenteProcesadas.contains(entidadVO.getEntidad()) == false) {
						// Buscar Valor de la entidad
						entidadVO.setValor(this.buscarValorEntidad(parametrosEntidadVO, documento.getTexto()));
						// Realizar reglas particulares por entidad
						this.aplicarReglasParticularesEntidad(entidadVO, modeloVO.getEntidades());
					}

					entidadesPreviamenteProcesadas.add(entidadVO.getEntidad());

					if (entidadVO.getValor() != null) {
						entidadVO.setConfianza(95);
					}
				}
			});

		} catch (JsonSyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		documento.setTexto(null);
		return documento;
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
		} else if (parametrosEntidadVO.getTipoReconocimiento().equals("reconocerEntidadCotejarContenido")) {
			valorEntidad = cotejar.reconocerEntidadCotejarContenido(parametrosEntidadVO, texto);
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
		}

		if (valorEntidad != null) {
			valorEntidad = valorEntidad.trim();
		}

		// Float f = Float.parseFloat("5.2");

		return valorEntidad;
	}

	@Override
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
		System.out.println("TipoDemanda: " + documento.getTipoDemanda());
		documento.setTexto(null);
		return documento;
	}

	public void aplicarReglasParticularesEntidad(EntidadVO entidadVO, ArrayList<EntidadVO> listaEntidadesVO) {

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
				}
			}
		}

	}
}
