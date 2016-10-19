package setor;

import auxiliar.DAOException;

public interface SetorDAO {
	
	public void inserir(Setor setor) throws DAOException;
	public Setor buscar(String setor) throws DAOException;
	public void atualizar(Setor setor) throws DAOException;

}
