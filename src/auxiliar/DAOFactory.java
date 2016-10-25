package auxiliar;

import mac.MACDAO;
import mac.MACDAOJDBC;
import setor.SetorDAO;
import setor.SetorDAOJDBC;

/**
 * Classe Factory para criação de DAO's
 * @author john
 *
 */
public class DAOFactory {
	
	public static MACDAO getMACDAO() throws DAOException {
		
		MACDAO macDAO;
		macDAO = new MACDAOJDBC(JDBCUtil.getInstance().getConexao());
		
		return macDAO;
	}
	
	public static SetorDAO getSetorDAO() throws DAOException {
		
		SetorDAO setorDAO;
		setorDAO = new SetorDAOJDBC(JDBCUtil.getInstance().getConexao());
		
		return setorDAO;
	}
	
}
