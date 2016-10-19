package mac;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import auxiliar.DAOException;

public class MACDAOJDBC implements MACDAO {
	
	private Connection conexao;
	
	public MACDAOJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void inserir(MAC mac) throws DAOException {

		try {
			
			PreparedStatement declaracao = conexao.prepareStatement("insert into tb_mac (parte_1, parte_2, parte_3, parte_4, parte_5, parte_6) values (?, ?, ?, ?, ?, ?)");
			
			declaracao.setString(1, mac.getParte1());
			declaracao.setString(2, mac.getParte2());
			declaracao.setString(3, mac.getParte3());
			declaracao.setString(4, mac.getParte4());
			declaracao.setString(5, mac.getParte5());
			declaracao.setString(6, mac.getParte6());
			
			declaracao.execute();
		
		} 
		catch (SQLException ex) {
		
			throw new DAOException("Nao e possivel inserir o MAC");
			
		}
		
	}

	@Override
	public MAC buscar() throws DAOException {
		
		MAC mac = null;
		
		try {
			
			PreparedStatement declaracao = conexao.prepareStatement("select * from tb_mac");
			
			ResultSet resultado = declaracao.executeQuery();
			
			while (resultado.next()) {
				
				mac = new MAC();
				
				mac.setParte1(resultado.getString("parte_1"));
				mac.setParte2(resultado.getString("parte_2"));
				mac.setParte3(resultado.getString("parte_3"));
				mac.setParte4(resultado.getString("parte_4"));
				mac.setParte5(resultado.getString("parte_5"));
				mac.setParte6(resultado.getString("parte_6"));
				
			}
			
		} 
		catch (SQLException ex) {
		
			throw new DAOException("Nao e possivel buscar o MAC.");
			
		}
		
		return mac;
	}
	

}
