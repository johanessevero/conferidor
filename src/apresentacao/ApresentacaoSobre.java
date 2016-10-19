package apresentacao;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ApresentacaoSobre extends JFrame {
	
	private JTextArea txaSobre;
	
	public ApresentacaoSobre() {

		setLayout(new BorderLayout());
		
		txaSobre = new JTextArea();
		txaSobre.append("Este software foi desenvolvido por:\n");
		txaSobre.append("Johanes Severo dos Santos. Contato:\n");
		txaSobre.append("Email: johanessevero@gmail.com\n");
		
		add(new JScrollPane(txaSobre), BorderLayout.CENTER);
		
		setSize(300, 300);
		addWindowListener(new ApresentacaoFechamentoJanela(this));
		setLocation(0, 0);
		setResizable(false);
		setVisible(true);
		
	}
	
	public static void main(String args[]) {
		
		new ApresentacaoSobre();
		
	}

}
