package com.edi.ia.ner.modelo;

public class GrupoEntidadVO {
	
	private String grupoEntidad;
	private String valor;
	private String codigoServicio;
	private boolean enviaTextoSiguienteHoja;
	private boolean siempreEnviarEntidades;
	
	
	
	public boolean isSiempreEnviarEntidades() {
		return siempreEnviarEntidades;
	}
	public void setSiempreEnviarEntidades(boolean siempreEnviarEntidades) {
		this.siempreEnviarEntidades = siempreEnviarEntidades;
	}
	public boolean isEnviaTextoSiguienteHoja() {
		return enviaTextoSiguienteHoja;
	}
	public void setEnviaTextoSiguienteHoja(boolean enviaTextoSiguienteHoja) {
		this.enviaTextoSiguienteHoja = enviaTextoSiguienteHoja;
	}
	public String getGrupoEntidad() {
		return grupoEntidad;
	}
	public void setGrupoEntidad(String grupoEntidad) {
		this.grupoEntidad = grupoEntidad;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public String getCodigoServicio() {
		return codigoServicio;
	}
	public void setCodigoServicio(String codigoServicio) {
		this.codigoServicio = codigoServicio;
	}
}
