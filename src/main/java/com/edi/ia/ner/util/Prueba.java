package com.edi.ia.ner.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Prueba {
	
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String texto = "Adquisición en propiedad de la vivienda cuyo\n" + 
				"domicilio a continuación se indica\n" + 
				"ESTADO CBA\n" + 
				"CE CHE\n" + 
				"REGISTRO\n" + 
				"OFETY\n" + 
				"OSTMTC\n" + 
				"PUNTA SANTA MARIA 10301 SMZ I MZ 48 LT 26\n" + 
				"Domicilio del inmueble vivienda) objeto de Crédito Otorgado\n" + 
				"NIVOU PUNTA NARANJOS ORIENTE IV.CP\n" + 
				"31385 CHIHUAHUA CHIHUAHUA\n" + 
				"zxcv 5156,59631\n" + 
				"Monto fen pesos) del Credito Olorgndo\n" + 
				"CIENTO NOVENTA Y SEIS MIL OCHOCIENTOS\n" + 
				"NOVENTA Y UN PESOS 80/100 M N\n" + 
				"";
		
		Pattern regex = Pattern.compile("(?i)(\\$|S|5)\\s{0,1}\\d{1,4}(?:[\\s,.]\\d{1,5}){1,3}\\s");				
				//"?:[\\s{0,1},.,]{1,3}\\d{1,9}){1,3}");
				
			//"\\s{0,1}(?i)(\\(|DIEZ|VEINT|TREINTA|CUARENTA|CINCUENTA|SESENTA|SETENTA|OCHENTA|NOVENTA|CIEN|UN|DOS|TRES|CUATRO|CINCO|SEIS|SIETE|OCHO|NUEVE|PESOS|DIEZ|ONCE|DOCE|TRECE|CATORCE|QUINCE|QUINIENTOS|SETECIENTOS|NOVECIENTOS|PUNTO|)(?i)(\\)|)\\s{0,1}(?i)(m2|metros cuadtrados|)", Pattern.CASE_INSENSITIVE);
		
		Matcher match = regex.matcher(texto);
		
		
		
		while (match.find()) {
			System.out.println("*"+match.group(0)+"*");
			//System.out.println("*"+texto.substring(match.start(), match.end())+"*");
		}
		
		ArrayList<String> entidades = new ArrayList<String>();
		entidades.add(VariablesGlobales.NOMBRE_ACREDITADO_CLASIFICAR_DATOS);
		entidades.add(VariablesGlobales.NOMBRE_ACREDITADO_CLASIFICAR_DATOS);
		
		System.out.println(entidades.toString());
		
		
		
	}

}
