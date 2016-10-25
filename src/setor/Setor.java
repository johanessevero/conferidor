package setor;

public class Setor {
	
	private Integer codSetor;
	private String setor;
	private String email;
	
	public Setor() {}
	
	public Setor(String setor, String email) {
		
		this.setor = setor;
		this.email = email;
		
	}

	public Integer getCodSetor() {
		return codSetor;
	}

	public void setCodSetor(Integer codSetor) {
		this.codSetor = codSetor;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	

}
