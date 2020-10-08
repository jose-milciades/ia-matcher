package com.edi.ia.ner.servicios;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edi.ia.ner.modelo.CreditoVO;
import com.edi.ia.ner.modelo.DocumentoVO;
import com.edi.ia.ner.modelo.GrupoEntidadesVO;
import com.edi.ia.ner.controlador.ControladorServicio;

@RestController
@RequestMapping("robotLectura")
public class ServiciosNer {
	
	@Autowired
	ControladorServicio controlador;

	@PostMapping(value = "/reconocerEntidades")
	public DocumentoVO reconocerEntidades(@RequestBody DocumentoVO documento) {
		long startTime = System.currentTimeMillis();
		DocumentoVO documentoRespuesta = controlador.reconocerEntidades(documento);
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("Servicio:__reconocerEntidades"+"  num_pagina:__" + documentoRespuesta.getPagina() + "  Tiempo respuesta:__" + estimatedTime + "  Id_documento:__" + documentoRespuesta.getIdDocumentoProcesado());
		return documentoRespuesta;
	}

	@PostMapping(value = "/clasificarCertificadoAdeudo")
	public CreditoVO clasificarDatosCredito(@RequestBody CreditoVO creditoVO) {

		return controlador.clasificarDatosCredito(creditoVO);

	}
	
	@PostMapping(value = "/reconocerParrafo")
	public GrupoEntidadesVO reconocerParrafo(@RequestBody GrupoEntidadesVO grupoEntidad) {
		long startTime = System.currentTimeMillis();
		GrupoEntidadesVO grupoEntidadesVO = controlador.reconocerParrafo(grupoEntidad);
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println("Servicio:__reconocerParrafo"+"  num_pagina:__" + grupoEntidad.getPagina() + "  Tiempo respuesta:__" + estimatedTime + "  Id_documento:__" + grupoEntidad.getIdDocumentoProcesado());
		
		return grupoEntidadesVO;

	}

}
