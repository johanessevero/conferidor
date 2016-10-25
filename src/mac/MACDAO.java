package mac;

import auxiliar.DAOException;

public interface MACDAO {
	
	public void inserir(MAC mac) throws DAOException;
	public MAC buscar() throws DAOException;

}
