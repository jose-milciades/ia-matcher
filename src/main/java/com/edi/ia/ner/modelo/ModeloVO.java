package com.edi.ia.ner.modelo;

import java.io.Serializable;
import java.util.ArrayList;


public class ModeloVO implements Serializable{

	private static final long serialVersionUID = 1L;


	public ModeloVO() {
		super();
	}
	
	
	private String modelo;
	private ArrayList<EntidadVO> entidades;
	
	
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public ArrayList<EntidadVO> getEntidades() {
		return entidades;
	}
	public void setEntidades(ArrayList<EntidadVO> entidades) {
		this.entidades = entidades;
	}
	

}
