package com.edi.ia.ner.modelo;

import java.io.Serializable;



public class EntidadVO implements Serializable{

	private static final long serialVersionUID = 1L;

	public EntidadVO() {
		super();
	}
	
	private String entidad;
	private String codigo;
	private String valor;
	private String codigoServicio;
	private int confianza;
	
	

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
