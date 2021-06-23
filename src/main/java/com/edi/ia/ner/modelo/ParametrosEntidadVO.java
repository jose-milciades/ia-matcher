package com.edi.ia.ner.modelo;

import java.util.ArrayList;

public class ParametrosEntidadVO {

	
	private String entidadRelacionadas;
	private String tipoReconocimiento;
	private int posicion;
	private int posicionMatch;
	private int espacioEntrePalabras;
	private int longitud;
	private int longitudGrupoColindancia;
	private int asiertos;
	private int cantidadMinimaCaracteres;
	private String expresionRegular;
	private String expresionRegularAux;
	private String expresionRegularValoresIniciales;
	private String expresionRegularAdicionalValoresIniciales;
	private String expresionRegularValoresFinales;
	private String etiqueta;
	private String codigoServicio;
	private String agregarTextoInicial;
	private String agregarTextoFinal;
	private ArrayList<String> entidades;
	private ArrayList<String> valoresIniciales;
	private ArrayList<String> valoresFinales;
	private ArrayList<String> valoresContenido;
	private ArrayList<String> valoresOmitir;
	private ArrayList<String> valoresRemoverAlFinal;
	private boolean mantenerSaltoLinea;
	
	
	
	public boolean isMantenerSaltoLinea() {
		return mantenerSaltoLinea;
	}

	public void setMantenerSaltoLinea(boolean mantenerSaltoLinea) {
		this.mantenerSaltoLinea = mantenerSaltoLinea;
	}

	public int getLongitudGrupoColindancia() {
		return longitudGrupoColindancia;
	}

	public void setLongitudGrupoColindancia(int longitudGrupoColindancia) {
		this.longitudGrupoColindancia = longitudGrupoColindancia;
	}

	public ArrayList<String> getValoresRemoverAlFinal() {
		return valoresRemoverAlFinal;
	}

	public void setValoresRemoverAlFinal(ArrayList<String> valoresRemoverAlFinal) {
		this.valoresRemoverAlFinal = valoresRemoverAlFinal;
	}

	public ArrayList<String> getValoresOmitir() {
		return valoresOmitir;
	}

	public void setValoresOmitir(ArrayList<String> valoresOmitir) {
		this.valoresOmitir = valoresOmitir;
	}

	public int getCantidadMinimaCaracteres() {
		return cantidadMinimaCaracteres;
	}

	public void setCantidadMinimaCaracteres(int cantidadMinimaCaracteres) {
		this.cantidadMinimaCaracteres = cantidadMinimaCaracteres;
	}

	public String getExpresionRegularValoresIniciales() {
		return expresionRegularValoresIniciales;
	}

	public void setExpresionRegularAdicionalValoresIniciales(String expresionRegularAdicionalValoresIniciales) {
		this.expresionRegularAdicionalValoresIniciales = expresionRegularAdicionalValoresIniciales;
	}
	
	public String getExpresionRegularAdicionalValoresIniciales() {
		return expresionRegularAdicionalValoresIniciales;
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


	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
	
	public int getPosicionMatch() {
		return posicionMatch;
	}

	public void setPosicionMatch(int posicionMatch) {
		this.posicionMatch = posicionMatch;
	}

	public ArrayList<String> getEntidades() {
		return entidades;
	}

	public void setEntidades(ArrayList<String> entidades) {
		this.entidades = entidades;
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

	public String getEntidadRelacionadas() {
		return entidadRelacionadas;
	}

	public void setEntidadRelacionadas(String entidadRelacionadas) {
		this.entidadRelacionadas = entidadRelacionadas;
	}

	public String getAgregarTextoInicial() {
		return agregarTextoInicial;
	}

	public void setAgregarTextoInicial(String agregarTextoInicial) {
		this.agregarTextoInicial = agregarTextoInicial;
	}

	public String getAgregarTextoFinal() {
		return agregarTextoFinal;
	}

	public void setAgregarTextoFinal(String agregarTextoFinal) {
		this.agregarTextoFinal = agregarTextoFinal;
	}
	
	
}
