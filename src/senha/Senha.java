package senha;

public class Senha {
	
	private String senha;
	private int mes;
	private int ano;
	
	public Senha(String senha, int mes, int ano) {
		
		this.senha = senha;
		this.ano = ano;
		this.mes = mes;
		
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}
	public int getAno() {
		return ano;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getMes() {
		return mes;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getSenha() {
		return senha;
	}
	
	

}
