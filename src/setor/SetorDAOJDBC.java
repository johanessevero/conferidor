package setor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import auxiliar.DAOException;

public class SetorDAOJDBC implements SetorDAO {

	private Connection conexao;
	
	public SetorDAOJDBC(Connection conexao) {
		this.conexao = conexao;
	}

	public void inserir(Setor setor) throws DAOException {
	
		try {
			
			PreparedStatement declaracao = conexao.prepareStatement("insert into tb_setor (descricao, email) values (?, ?)");
			
			declaracao.setString(1, setor.getSetor());
			declaracao.setString(2, setor.getEmail());
			
			declaracao.execute();
		
		} 
		catch (SQLException ex) {
		
			throw new DAOException("Nao e possivel inserir os dados do setor");
			
		}
		
	}

	public Setor buscar(String nomeSetor) throws DAOException {
		
		Setor setor = null;
		
		try {
			
			PreparedStatement declaracao = conexao.prepareStatement("select * from tb_setor where descricao='"+nomeSetor+"'");
			
			ResultSet resultado = declaracao.executeQuery();
			
			while (resultado.next()) {
				
				setor = new Setor();
				
				setor.setSetor(resultado.getString("descricao"));
				setor.setEmail(resultado.getString("email"));
				
			}
			
		} 
		catch (SQLException ex) {
		
			throw new DAOException("Nao e possivel buscar o setor.");
			
		}
		
		return setor;
	}


	public void atualizar(Setor setor) throws DAOException {
		
	try {
			
			PreparedStatement declaracao = conexao.prepareStatement("update tb_setor set email = ? where descricao='"+setor.getSetor().trim()+"'");
			
			declaracao.setString(1, setor.getEmail());
			
			declaracao.execute();
		
		} 
		catch (SQLException ex) {
		
			throw new DAOException("Nao e possivel atualizar o email do setor");
			
		}
		
	}

}
