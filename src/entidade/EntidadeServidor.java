package entidade;

/**
 * 
 * @author Johanes Severo dos Santos
 * Classe que representa as informacoes de Servidor.
 */
public class EntidadeServidor {
	
	private String matricula;
	private String nome;
	private String setor;
	private String cargo;
	private int cargaHoraria;
	private int cargaHorariaSetor;
	private EntidadeEscala escala;

	public EntidadeServidor() {}
	
	public EntidadeServidor(String matricula, String nome, String setor, String cargo, int cargaHoraria, int cargaHorariaSetor) {
		
		this.matricula = matricula;
		this.nome = nome;
		this.setor = setor;
		this.cargo = cargo;
		this.cargaHoraria = cargaHoraria;
		this.cargaHorariaSetor = cargaHorariaSetor;
		
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSetor() {
		return setor;
	}
	public void setSetor(String setor) {
		this.setor = setor;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public int getCargaHoraria() {
		return cargaHoraria;
	}
	public void setCargaHoraria(int cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}
	
	public EntidadeEscala getEscala() {
		return escala;
	}

	public void setEscala(EntidadeEscala escala) {
		this.escala = escala;
	}
	public int getCargaHorariaSetor() {
		return cargaHorariaSetor;
	}

	public void setCargaHorariaSetor(int cargaHorariaSetor) {
		this.cargaHorariaSetor = cargaHorariaSetor;
	}
	
}
