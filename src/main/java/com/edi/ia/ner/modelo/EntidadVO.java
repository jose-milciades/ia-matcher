package com.edi.ia.ner.modelo;

import java.io.Serializable;



public class EntidadVO implements Serializable{

	private static final long serialVersionUID = 1L;

	public EntidadVO() {
		super();
	}
	
	private String entidad;
	private String codigo;
	private String codigoConyuge;
	private String valor;
	private String codigoServicio;
	private int confianza;
	private Integer numeroPagina;
	private String grupo;
	private String origenEntidad;
	private Integer idDocumentoProcesado;
	private EntidadVO entidadAnterior;
	

	public Integer getNumeroPagina() {
		return numeroPagina;
	}

	public void setNumeroPagina(Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public EntidadVO getEntidadAnterior() {
		return entidadAnterior;
	}

	public void setEntidadAnterior(EntidadVO entidadAnterior) {
		this.entidadAnterior = entidadAnterior;
	}

	public Integer getIdDocumentoProcesado() {
		return idDocumentoProcesado;
	}

	public void setIdDocumentoProcesado(Integer idDocumentoProcesado) {
		this.idDocumentoProcesado = idDocumentoProcesado;
	}

	public String getCodigoConyuge() {
		return codigoConyuge;
	}

	public void setCodigoConyuge(String codigoConyuge) {
		this.codigoConyuge = codigoConyuge;
	}

	public String getOrigenEntidad() {
		return origenEntidad;
	}

	public void setOrigenEntidad(String origenEntidad) {
		this.origenEntidad = origenEntidad;
	}

	public String getCodigoServicio() {
		return codigoServicio;
	}

	public void setCodigoServicio(String codigoServicio) {
		this.codigoServicio = codigoServicio;
	}

	public int getConfianza() {
		return confianza;
	}

	public void setConfianza(int confianza) {
		this.confianza = confianza;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	
}
