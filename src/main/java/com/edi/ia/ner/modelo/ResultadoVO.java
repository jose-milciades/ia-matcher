package com.edi.ia.ner.modelo;

import java.util.ArrayList;
import java.util.Map;

public class ResultadoVO {
	
	private Map<Integer, String> mapResultado;
	private ArrayList<String> listaResutado;
	private String grupo;
	private int primerIndice;
	private int ultimoIndice;
	
	
	
	public ResultadoVO() {
		this.listaResutado = new ArrayList<String>();;
	}
	public int getPrimerIndice() {
		return primerIndice;
	}
	public void setPrimerIndice(int primerIndice) {
		this.primerIndice = primerIndice;
	}

	public ArrayList<String> getListaResutado() {
		return listaResutado;
	}
	public void setListaResutado(ArrayList<String> listaResutado) {
		this.listaResutado = listaResutado;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public int getUltimoIndice() {
		return ultimoIndice;
	}
	public void setUltimoIndice(int ultimoIndice) {
		this.ultimoIndice = ultimoIndice;
	}
	public Map<Integer, String> getMapResultado() {
		return mapResultado;
	}
	public void setMapResultado(Map<Integer, String> mapResultado) {
		this.mapResultado = mapResultado;
	}
	
}
