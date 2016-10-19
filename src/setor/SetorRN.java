package setor;

import auxiliar.DAOException;
import auxiliar.DAOFactory;

public class SetorRN {
	
	public void inserir(Setor setor) throws DAOException {
		
		SetorDAO setorDAO = DAOFactory.getSetorDAO();
		setorDAO.inserir(setor);
	}
	
	public Setor buscar(String nomeSetor) throws DAOException {
		
		SetorDAO setorDAO = DAOFactory.getSetorDAO();
		
		return setorDAO.buscar(nomeSetor);
		
	}
	
	public void atualizar(Setor setor) throws DAOException {
		
		SetorDAO setorDAO = DAOFactory.getSetorDAO();
		setorDAO.atualizar(setor);
	}

}
