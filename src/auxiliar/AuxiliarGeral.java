package auxiliar;

import java.math.BigDecimal;

/**
 * 
 * @author Johanes Severo dos Santos
 * Classe que contém funções relacionadas a formatação de strings.
 */
public class AuxiliarGeral {

	public static String substituiEspacos(String string) {
		
		String novaString = "";
		
		for (int i = 0; i < string.length(); ++i) {
			
			if (string.charAt(i) != ' ')
				novaString += string.charAt(i);
		
		}
		
		return novaString;
		
	}
	
	public static String substituiUnderline(String string) {
		
		String novaString = "";
		
		for (int i = 0; i < string.length(); ++i) {
			
			if (string.charAt(i) != ' ')
				novaString += string.charAt(i);
			else
				novaString += "_";
		}
		
		return novaString;
		
	}
	
	public static final BigDecimal arredonda2Casas(double valor) {
		
	    BigDecimal arredondado = new BigDecimal(valor);
	    arredondado = arredondado.setScale(2, BigDecimal.ROUND_HALF_UP);
	    
	    return arredondado;
	}
	
	public static void main(String args[]) {
		
		System.out.println(AuxiliarGeral.arredonda2Casas(0.895));
		
	}

}
