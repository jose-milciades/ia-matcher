package com.edi.ia.ner.modelo;

import java.util.ArrayList;

public class ParametrosEntidadVO {
	
	private String entidad;
	private String tipoReconocimiento;
	private int posicion;
	private int espacioEntrePalabras;
	private int longitud;
	private int asiertos;
	private String expresionRegular;
	private String expresionRegularAux;
	private String expresionRegularValoresIniciales;
	private String expresionRegularValoresFinales;
	private String etiqueta;
	private String codigoServicio;
	
	
	
	public String getExpresionRegularValoresIniciales() {
		return expresionRegularValoresIniciales;
	}
	public void setExpresionRegularValoresIniciales(String expresionRegularValoresIniciales) {
		this.expresionRegularValoresIniciales = expresionRegularValoresIniciales;
	}
	public String getExpresionRegularValoresFinales() {
		return expresionRegularValoresFinales;
	}
	public void setExpresionRegularValoresFinales(String expresionRegularValoresFinales) {
		this.expresionRegularValoresFinales = expresionRegularValoresFinales;
	}
	public String getCodigoServicio() {
		return codigoServicio;
	}
	public void setCodigoServicio(String codigoServicio) {
		this.codigoServicio = codigoServicio;
	}
	private ArrayList<String> valoresIniciales;
	private ArrayList<String> valoresFinales;
	private ArrayList<String> valoresContenido;
	
	public String getExpresionRegularAux() {
		return expresionRegularAux;
	}
	public void setExpresionRegularAux(String expresionRegularAux) {
		this.expresionRegularAux = expresionRegularAux;
	}
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	public int getEspacioEntrePalabras() {
		return espacioEntrePalabras;
	}
	public void setEspacioEntrePalabras(int espacioEntrePalabras) {
		this.espacioEntrePalabras = espacioEntrePalabras;
	}
	public int getAsiertos() {
		return asiertos;
	}
	public void setAsiertos(int asiertos) {
		this.asiertos = asiertos;
	}
	public ArrayList<String> getValoresContenido() {
		return valoresContenido;
	}
	public void setValoresContenido(ArrayList<String> valoresContenido) {
		this.valoresContenido = valoresContenido;
	}
	public String getEntidad() {
		return entidad;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	public String getTipoReconocimiento() {
		return tipoReconocimiento;
	}
	public void setTipoReconocimiento(String tipoReconocimiento) {
		this.tipoReconocimiento = tipoReconocimiento;
	}
	public ArrayList<String> getValoresIniciales() {
		return valoresIniciales;
	}
	public void setValoresIniciales(ArrayList<String> valoresIniciales) {
		this.valoresIniciales = valoresIniciales;
	}
	public ArrayList<String> getValoresFinales() {
		return valoresFinales;
	}
	public void setValoresFinales(ArrayList<String> valoresFinales) {
		this.valoresFinales = valoresFinales;
	}
	public int getLongitud() {
		return longitud;
	}
	public void setLongitud(int longitud) {
		this.longitud = longitud;
	}
	public String getExpresionRegular() {
		return expresionRegular;
	}
	public void setExpresionRegular(String expresionRegular) {
		this.expresionRegular = expresionRegular;
	}
	
	
}
