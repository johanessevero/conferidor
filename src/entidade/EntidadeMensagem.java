package entidade;

/**
 * 
 * @author Johanes Severo dos Santos
 * Classe que representa as informações de mensagem de erro de uma validação de escala.
 */
public class EntidadeMensagem {
	
	private String mensagem;
	private boolean ok;
	
	public EntidadeMensagem(String mensagem, boolean ok) {
		
		this.mensagem = mensagem;
		this.ok = ok;
		
	}
	
	public String getMensagem() {
		
		return mensagem;
		
	}
	public void setMensagem(String mensagem) {
		
		this.mensagem = mensagem;
		
	}
	public boolean isOk() {
		
		return ok;
		
	}
	public void setEscalaOk(boolean ok) {
		
		this.ok = ok;
		
	}

}
