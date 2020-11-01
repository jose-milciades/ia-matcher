package com.edi.ia.ner.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Prueba {
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String texto = "(noventa y sdfsddos cinco) metros cuadrados m2 m²  con las siguientes medidas dossas";
		
		Pattern regex = Pattern.compile("(?i)(\\(|DIEZ|VEINT|TREINTA|CUARENTA|CINCUENTA|SESENTA|SETENTA|OCHENTA|NOVENTA|CIEN|UN|DOS|TRES|CUATRO|CINCO|SEIS|"
				+ "SIETE|OCHO|NUEVE|PESOS|DIEZ|ONCE|DOCE|TRECE|CATORCE|QUINCE|QUINIENTOS|SETECIENTOS|NOVECIENTOS|"
				+ "PUNTO|\\)|metros cuadrados|m2|m²)");				
				//"?:[\\s{0,1},.,]{1,3}\\d{1,9}){1,3}");
				
			//"\\s{0,1}(?i)(\\(|DIEZ|VEINT|TREINTA|CUARENTA|CINCUENTA|SESENTA|SETENTA|OCHENTA|NOVENTA|CIEN|UN|DOS|TRES|CUATRO|CINCO|SEIS|SIETE|OCHO|NUEVE|PESOS|DIEZ|ONCE|DOCE|TRECE|CATORCE|QUINCE|QUINIENTOS|SETECIENTOS|NOVECIENTOS|PUNTO|)(?i)(\\)|)\\s{0,1}(?i)(m2|metros cuadtrados|)", Pattern.CASE_INSENSITIVE);
		
		Matcher match = regex.matcher(texto);
		
		
		
		while (match.find()) {
			System.out.println("*"+match.group(0)+"*");
			//System.out.println("*"+texto.substring(match.start(), match.end())+"*");
		}
		
		
		
		
		
	}

}
