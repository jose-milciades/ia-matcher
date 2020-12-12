package com.edi.ia.ner.modelo;

import java.util.ArrayList;
import java.util.Map;

public class ModeloNerVO {
	
	private ArrayList<ParametrosEntidadVO> parametrosEntidades;
	private String modelo;
	private Map<String, ParametrosEntidadVO> map;
	
	
	
	public ArrayList<ParametrosEntidadVO> getParametrosEntidades() {
		return parametrosEntidades;
	}
	public void setParametrosEntidades(ArrayList<ParametrosEntidadVO> parametrosEntidades) {
		this.parametrosEntidades = parametrosEntidades;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public Map<String, ParametrosEntidadVO> getMap() {
		return map;
	}
	public void setMap(Map<String, ParametrosEntidadVO> map) {
		this.map = map;
	}
	
	
	

}
