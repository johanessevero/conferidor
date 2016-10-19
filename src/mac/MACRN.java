package mac;

import processamento.ProcessCriptografia;
import auxiliar.AuxiliarConstantes;
import auxiliar.DAOException;
import auxiliar.DAOFactory;
import auxiliar.RNException;
import auxiliar.UtilException;

public class MACRN {
	
	public void inserir() throws DAOException, RNException, UtilException {
		
		ProcessCriptografia criptografia = new ProcessCriptografia();
		MAC mac = new MAC();
		
		String macString = AuxiliarConstantes.getEnderecoMac();
		
		String parte1 = "";
		String parte2 = "" ;
		String parte3 = "";
		String parte4 = "";
		String parte5 = "";
		String parte6 = "";
			
		for(int i = 0; i < macString.length(); i++) {
			
			if (i < 2) {					
				parte1 += macString.charAt(i);
			}
			else
			if (i  > 2 && i < 5) {
				parte2 += macString.charAt(i);
			}
			else
			if (i  > 5 && i < 8) {
				parte3 += macString.charAt(i);
			}
			else
			if (i  > 8 && i < 11) {
				parte4 += macString.charAt(i);
			}
			else
			if (i  > 11 && i < 14) {
				parte5 += macString.charAt(i);
			}
			else
			if (i  > 14 && i < 17) {
				parte6 += macString.charAt(i);
			}
				
		}	
			
		mac.setParte1(criptografia.criptografar(parte1));
		mac.setParte2(criptografia.criptografar(parte2));
		mac.setParte3(criptografia.criptografar(parte3));
		mac.setParte4(criptografia.criptografar(parte4));
		mac.setParte5(criptografia.criptografar(parte5));
		mac.setParte6(criptografia.criptografar(parte6));
		
		MACDAO macDAO = DAOFactory.getMACDAO();
		macDAO.inserir(mac);
		
	}
	
	public MAC buscar() throws DAOException {
		
		MACDAO macDAO = DAOFactory.getMACDAO();
		
		return macDAO.buscar();
		
	}

}
