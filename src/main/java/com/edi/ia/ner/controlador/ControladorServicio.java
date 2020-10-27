package com.edi.ia.ner.controlador;


import com.edi.ia.ner.modelo.CreditoVO;
import com.edi.ia.ner.modelo.DocumentoVO;
import com.edi.ia.ner.modelo.GrupoEntidadesVO;

public interface ControladorServicio {

	DocumentoVO reconocerEntidades(DocumentoVO documento);

	DocumentoVO reconocerTipoDemanda(DocumentoVO documento);
	
	GrupoEntidadesVO reconocerParrafo(GrupoEntidadesVO grupoEntidad);
	
	CreditoVO clasificarDatosCredito(CreditoVO creditoVO);
}