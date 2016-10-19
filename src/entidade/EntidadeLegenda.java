package entidade;

/**
 * 
 * @author Johanes Severo dos Santos
 * Classe que representa as informa��es de legenda.
 */
public class EntidadeLegenda {
	
	private String legenda;
	private String descricao = "";
	private String area;
	private String inicioManha = "";
	private String fimManha = "";
	private String inicioTarde="";
	private String fimTarde="";
	private String inicioNoite="";
	private String fimNoite="";

	private int valorTotal;
	private int periodo;
	private int valorManha;
	private int valorTarde;
	private int valorNoite;
	
	private boolean refeicao;
	private boolean comecaSete;
	private boolean afastamento;

	public EntidadeLegenda() {}
	
	public EntidadeLegenda(String sigla, int valor, int periodo, boolean refeicao) {
		
		this.legenda = sigla;
		this.valorTotal = valor;
		this.periodo = periodo;
		this.refeicao = refeicao;
		
	}
	
	public EntidadeLegenda(String sigla, int valor, int periodo, boolean refeicao, int valorManha, int valorTarde, int valorNoite) {
		
		this.legenda = sigla;
		this.valorTotal = valor;
		this.periodo = periodo;
		this.refeicao = refeicao;
		this.valorManha = valorManha;
		this.valorTarde = valorTarde;
		this.valorNoite = valorNoite;
		
	}
	
	public EntidadeLegenda(String sigla, int valor, int periodo, boolean refeicao, int valorManha, int valorTarde, int valorNoite, String descricao) {
		
		this.legenda = sigla;
		this.valorTotal = valor;
		this.periodo = periodo;
		this.refeicao = refeicao;
		this.valorManha = valorManha;
		this.valorTarde = valorTarde;
		this.valorNoite = valorNoite;
		this.descricao = descricao;
		
	}
	
	public EntidadeLegenda(String sigla, int valor, int periodo, boolean refeicao, int valorManha, int valorTarde, int valorNoite, String descricao,  String area, boolean comecaSete) {
		
		this.legenda = sigla;
		this.valorTotal = valor;
		this.periodo = periodo;
		this.refeicao = refeicao;
		this.valorManha = valorManha;
		this.valorTarde = valorTarde;
		this.valorNoite = valorNoite;
		this.descricao = descricao;
		this.area = area;
		this.comecaSete = comecaSete;
		
	}
	
	public EntidadeLegenda(String sigla, int valor, int periodo, boolean refeicao, int valorManha, int valorTarde, int valorNoite, String descricao,  String area, boolean comecaSete, boolean afastamento, String inicioManha, String fimManha, String inicioTarde, String fimTarde, String inicioNoite, String fimNoite) {
		
		this.legenda = sigla;
		this.valorTotal = valor;
		this.periodo = periodo;
		this.refeicao = refeicao;
		this.valorManha = valorManha;
		this.valorTarde = valorTarde;
		this.valorNoite = valorNoite;
		this.descricao = descricao;
		this.area = area;
		this.comecaSete = comecaSete;
		this.afastamento = afastamento;
		this.inicioManha = inicioManha;
		this.fimManha = fimManha;
		this.inicioTarde = inicioTarde;
		this.fimTarde = fimTarde;
		this.inicioNoite = inicioNoite;
		this.fimNoite = fimNoite;
		
	}
	
	public int getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(int valor) {
		this.valorTotal = valor;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}
	
	public String getLegenda() {
		return legenda;
	}
	public void setLegenda(String legenda) {
		this.legenda = legenda;
	}
	
	public boolean isRefeicao() {
		return refeicao;
	}

	public void setRefeicao(boolean refeicao) {
		this.refeicao = refeicao;
	}
	public int getValorManha() {
		return valorManha;
	}

	public void setValorManha(int valorManha) {
		this.valorManha = valorManha;
	}

	public int getValorTarde() {
		return valorTarde;
	}

	public void setValorTarde(int valorTarde) {
		this.valorTarde = valorTarde;
	}

	public int getValorNoite() {
		return valorNoite;
	}

	public void setValorNoite(int valorNoite) {
		this.valorNoite = valorNoite;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public boolean isComecaSete() {
		return comecaSete;
	}

	public void setComecaSete(boolean comecaSete) {
		this.comecaSete = comecaSete;
	}
	
	public boolean isAfastamento() {
		return afastamento;
	}

	public void setAfastamento(boolean afastamento) {
		this.afastamento = afastamento;
	}
	
	public String getInicioManha() {
		return inicioManha;
	}

	public void setInicioManha(String inicioManha) {
		this.inicioManha = inicioManha;
	}

	public String getFimManha() {
		return fimManha;
	}

	public void setFimManha(String fimManha) {
		this.fimManha = fimManha;
	}

	public String getInicioTarde() {
		return inicioTarde;
	}

	public void setInicioTarde(String inicioTarde) {
		this.inicioTarde = inicioTarde;
	}

	public String getFimTarde() {
		return fimTarde;
	}

	public void setFimTarde(String fimTarde) {
		this.fimTarde = fimTarde;
	}

	public String getInicioNoite() {
		return inicioNoite;
	}

	public void setInicioNoite(String inicioNoite) {
		this.inicioNoite = inicioNoite;
	}

	public String getFimNoite() {
		return fimNoite;
	}

	public void setFimNoite(String fimNoite) {
		this.fimNoite = fimNoite;
	}


}
