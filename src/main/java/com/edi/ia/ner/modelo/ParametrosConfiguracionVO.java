package com.edi.ia.ner.modelo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ner")
public class ParametrosConfiguracionVO {
	
	public static String rutaParametrosEntidad;
	public static ModelosNerVO modelosNerVO;
	

	public  String getRutaParametrosEntidad() {
		return rutaParametrosEntidad;
	}

	public void setRutaParametrosEntidad(String rutaParametrosEntidad) {
		ParametrosConfiguracionVO.rutaParametrosEntidad = rutaParametrosEntidad;
	}
	
}
