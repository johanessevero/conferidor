package auxiliar;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {
	
    private Connection conexao;
    private static JDBCUtil singleton;
 
    private JDBCUtil() throws DAOException {
    	String path = System.getProperty("user.dir") +File.separator+"bdsisce"+File.separator+"bdsisce";
        System.out.println(path);
    	try {
			conexao = DriverManager.getConnection("jdbc:hsqldb:file:"+path, "john", "johnjohn");
		} catch (SQLException ex) {
			
			throw new DAOException("Nao foi possivel fazer a conexao com o banco!", ex);
		}
    }
 
    public static JDBCUtil getInstance() throws DAOException {
        if (singleton == null)
            singleton = new JDBCUtil();
        return singleton;
    }
 
    public Connection getConexao(){
        return conexao;
    }
    
    public void closeConexao() throws DAOException {
    	try {
			conexao.close();
		} catch (SQLException ex) {
			throw new DAOException("Não é possível fechar a conexão!", ex);
		}
    }
	

}
