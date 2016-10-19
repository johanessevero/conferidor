package processamento;

import java.util.ArrayList;
import java.util.Random;

public class ProcessLicenca {
	
	private char letras[] = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'w', 'y', 'z'};
	private byte numeros[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
	private final int quantidadeDigitos = 8;
	
	public String geraSenha() {
		
		String senha = "";
		
		for (int i = 0; i < quantidadeDigitos; ++i) {

			if (i % 2 == 0) {
				Random rLetra = new Random();
				int iLetra = rLetra.nextInt(letras.length);
				senha += "" + letras[iLetra];
			}
			else {
				Random rNumero = new Random();
				int iNumero = rNumero.nextInt(numeros.length);
				senha += "" + numeros[iNumero];
				
			}
		}
		
		return senha;
	}
	
	public void geraConstanteSenhas() {
		
		ProcessLicenca l = new ProcessLicenca();
		ProcessCriptografia c = new ProcessCriptografia();
		ArrayList<String> senhas = new ArrayList<String>();
		int mes = 1;
		int ano = 2012;
		
		for (int i = 0; i < (38*12); ++i) {
			senhas.add(l.geraSenha());
			mes += 1;
			if (mes == 13) {
				mes = 1;
				ano += 1;
			}
		}
		
		mes = 1;
		ano = 2012;
		System.out.println();
		System.out.println();
		System.out.println();
		for (int i = 0; i < (38*12); ++i) {
			System.out.println(senhas.get(i) + " " +  mes + " "  + ano);
			mes += 1;
			if (mes == 13) {
				mes = 1;
				ano += 1;
			}
		}
		
		mes = 1;
		ano = 2012;
		
		for (int i = 0; i < (38*12); ++i) {
			System.out.println("new Senha(\"" + c.criptografar(senhas.get(i)) + "\", " + mes + ", "  + ano +  "),");
			mes += 1;
			if (mes == 13) {
				mes = 1;
				ano += 1;
			}
		}
		
		System.out.println("}");
		
	}
	
	public static void main(String args[]) {
		
		ProcessLicenca pl = new ProcessLicenca();
		pl.geraConstanteSenhas();
	}
}
