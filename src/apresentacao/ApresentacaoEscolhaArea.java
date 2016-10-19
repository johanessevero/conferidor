package apresentacao;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import processamento.ProcessElaboradorEscala;
import auxiliar.AuxiliarConstantes;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import entidade.EntidadeServidor;

public class ApresentacaoEscolhaArea extends JFrame implements ActionListener {
	
	private JLabel lbAreas;
	
	private JComboBox cbAreas;
	
	private JButton btElaborar;
	
	private String area = "";
	
	private EntidadeServidor servidores[];
	
	private ProcessElaboradorEscala elaborador;
	
	public ApresentacaoEscolhaArea(EntidadeServidor servidores[]) {
		
		super(AuxiliarConstantes.TITULO);
		this.servidores = servidores;
		
		elaborador = new ProcessElaboradorEscala();
		
		FormLayout formLayout = new FormLayout(
				 "2dlu, 150px, 2dlu, 250px",
				 "2dlu, " +
				 "pref, "+
				 "2dlu, " +
				 "pref "
				 );

		setLayout(formLayout);
		
		CellConstraints celulas = new CellConstraints();
		
		lbAreas = new JLabel("Escolha a sua Area: ");
		lbAreas.setFont(new Font("Arial", Font.BOLD, 15));
		cbAreas = new JComboBox(AuxiliarConstantes.AREAS);
		cbAreas.setFont(new Font("Arial", Font.BOLD, 15));
		btElaborar = new JButton("Elaborar escala");
		btElaborar.setFont(new Font("Arial", Font.BOLD, 15));
		btElaborar.addActionListener(this);
		
		add(lbAreas, celulas.xy(2, 2));
		add(cbAreas, celulas.xy(4, 2));
		add(btElaborar, celulas.xyw(2, 4, 3));
		
		setSize(550, 100);
		addWindowListener(new ApresentacaoFechamentoJanela(this));
		setLocation(0, 0);
		setResizable(false);
		setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
	
		if (e.getSource() == btElaborar) {
			
			area = (String) cbAreas.getSelectedItem();
			EntidadeServidor servidoresProximoMes[] = elaborador.elaborarEscalaServidoresProximoMes(servidores, area);
			
			new ApresentacaoEscalaServidores(servidoresProximoMes, true, servidoresProximoMes[0].getEscala().getMes(), "");
			
		}
	}
	
	public static void main(String args[]) {
		new ApresentacaoEscolhaArea(null);
	}
}