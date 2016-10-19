package apresentacao;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * 
 * @author Johanes Severo dos Santos
 * Classe que modela o fechamento de um janela (JFrame)
 */
public class ApresentacaoFechamentoJanela extends WindowAdapter {

	private JFrame janela;
	
	public ApresentacaoFechamentoJanela(JFrame janela) {
		
		this.janela = janela;
		
	}
	
	public void windowClosing(WindowEvent e) {
		
		janela.setVisible(false);
		janela.dispose();
		
	}

	
}
