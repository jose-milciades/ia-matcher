package com.edi.ia.ner.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Prueba {
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String texto = "POR SU COSTADO DERECHO OESTE, LINDA CON LOTE DE CANDELARIA ESTHER BARRANCOS RIVERA VIUDA DE ALONZO, MIDE VEINTITRÉS METROS, CERRANDO EL PERÍMETRO.- Dicho bien";
		
		
		Pattern regex = Pattern.compile("(?i)( I\\.| II\\.| III\\.|\\-| a\\)| b\\)| c\\)|\\-| I\\)| II\\)| III\\))", Pattern.CASE_INSENSITIVE);
		
		Matcher match = regex.matcher(texto);
		
		
		
		while (match.find()) {
			System.out.println("*"+match.group(0)+"*");
		}
		
		System.out.println(texto.substring(4, texto.length()));
		System.out.println(texto.indexOf(" ", 4+10));
		
		
		
	}

}
