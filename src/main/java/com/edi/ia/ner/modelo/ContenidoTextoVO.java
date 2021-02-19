package com.edi.ia.ner.modelo;

import java.util.ArrayList;

public class ContenidoTextoVO {
	
	private String texto;
	private ArrayList<Integer> indices;
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public ArrayList<Integer> getIndices() {
		return indices;
	}
	public void setIndices(ArrayList<Integer> indices) {
		this.indices = indices;
	}
	

}
