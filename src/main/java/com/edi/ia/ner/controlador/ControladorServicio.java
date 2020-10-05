package com.edi.ia.ner.controlador;

import java.util.ArrayList;

import com.edi.ia.ner.modelo.AcreditadoVO;
import com.edi.ia.ner.modelo.DocumentoVO;
import com.edi.ia.ner.modelo.EntidadVO;
import com.edi.ia.ner.modelo.GrupoEntidadesVO;

public interface ControladorServicio {

	DocumentoVO reconocerEntidades(DocumentoVO documento);

	DocumentoVO reconocerTipoDemanda(DocumentoVO documento);
	
	GrupoEntidadesVO reconocerParrafo(GrupoEntidadesVO grupoEntidad);
	
	ArrayList<EntidadVO> clasificarCertificadoAdeudo (AcreditadoVO AcreditadoVO, ArrayList<EntidadVO> ListaEntidadesVO);

}