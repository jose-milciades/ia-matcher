package com.edi.ia.ner.modelo;

import java.util.ArrayList;

public class CreditoVO {

	String numeroCredito;
	String nombreAcreditado;
	String textoAcreditadoAnexoB;
	String textoConyugeAnexoB;
	String textoPropiedad;
	ArrayList<String> hojasCertificado;
	ArrayList<EntidadVO> entidades;
	
	public ArrayList<EntidadVO> getEntidades() {
		return entidades;
	}
	public void setEntidades(ArrayList<EntidadVO> entidades) {
		this.entidades = entidades;
	}
	
	public ArrayList<String> getHojasCertificado() {
		return hojasCertificado;
	}
	public void setHojasCertificado(ArrayList<String> hojasCertificado) {
		this.hojasCertificado = hojasCertificado;
	}
	
	public String getTextoAcreditadoAnexoB() {
		return textoAcreditadoAnexoB;
	}
	public void setTextoAcreditadoAnexoB(String textoAcreditadoAnexoB) {
		this.textoAcreditadoAnexoB = textoAcreditadoAnexoB;
	}
	public String getTextoConyugeAnexoB() {
		return textoConyugeAnexoB;
	}
	public void setTextoConyugeAnexoB(String textoConyugeAnexoB) {
		this.textoConyugeAnexoB = textoConyugeAnexoB;
	}
	public String getTextoPropiedad() {
		return textoPropiedad;
	}
	public void setTextoPropiedad(String textoPropiedad) {
		this.textoPropiedad = textoPropiedad;
	}
	public String getNumeroCredito() {
		return numeroCredito;
	}
	public void setNumeroCredito(String numeroCredito) {
		this.numeroCredito = numeroCredito;
	}
	public String getNombreAcreditado() {
		return nombreAcreditado;
	}
	public void setNombreAcreditado(String nombreAcreditado) {
		this.nombreAcreditado = nombreAcreditado;
	}
	
	
}
