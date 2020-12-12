package com.edi.ia.ner.modelo;

import java.io.Serializable;
import java.util.ArrayList;


public class DocumentoVO implements Serializable{
	

	private static final long serialVersionUID = 1L;

	
	public DocumentoVO() {
		super();
	}
	
	private String texto;
	private int pagina;
	private String idDocumentoProcesado;
	private String tipoDemanda;
	private ArrayList <ModeloVO> modelos;
	
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
	public ArrayList <ModeloVO> getModelos() {
		return modelos;
	}
	public void setModelos(ArrayList <ModeloVO> modelos) {
		this.modelos = modelos;
	}
	public String getTipoDemanda() {
		return tipoDemanda;
	}
	public void setTipoDemanda(String tipoDemanda) {
		this.tipoDemanda = tipoDemanda;
	}
	

}
