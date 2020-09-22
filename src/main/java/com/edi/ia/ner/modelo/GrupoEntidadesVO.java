package com.edi.ia.ner.modelo;

import java.util.ArrayList;

public class GrupoEntidadesVO {
	
	private String texto;
	private int pagina;
	private String idDocumentoProcesado;
	ArrayList<GrupoEntidadVO> grupoEntidades;
	
	
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public int getPagina() {
		return pagina;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	public String getIdDocumentoProcesado() {
		return idDocumentoProcesado;
	}
	public void setIdDocumentoProcesado(String idDocumentoProcesado) {
		this.idDocumentoProcesado = idDocumentoProcesado;
	}
	public ArrayList<GrupoEntidadVO> getGrupoEntidades() {
		return grupoEntidades;
	}
	public void setGrupoEntidades(ArrayList<GrupoEntidadVO> grupoEntidades) {
		this.grupoEntidades = grupoEntidades;
	}
}
