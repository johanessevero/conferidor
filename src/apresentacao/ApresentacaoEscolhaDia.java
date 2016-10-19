package apresentacao;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import processamento.ProcessGeracaoArquivoDados;
import auxiliar.AuxiliarConstantes;
import auxiliar.RelatorioException;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import entidade.EntidadeServidor;

public class ApresentacaoEscolhaDia extends JFrame implements ActionListener {
	
	private JLabel lbDia;
	
	private JComboBox cbDias;
	
	private JButton btGerar;
	
	private ProcessGeracaoArquivoDados geracaoArquivos;
	
	private String local;
	
	private EntidadeServidor servidores[];
	
	public ApresentacaoEscolhaDia(ProcessGeracaoArquivoDados geracaoArquivos, String local, EntidadeServidor servidores[]) {
		
		super(AuxiliarConstantes.TITULO);
		
		this.geracaoArquivos = geracaoArquivos;
		this.local = local;
		this.servidores = servidores;
		
		FormLayout formLayout = new FormLayout(
				 "2dlu, 150px, 2dlu, 50px",
				 "2dlu, " +
				 "pref, "+
				 "2dlu, " +
				 "pref "
				 );

		setLayout(formLayout);
		
		CellConstraints celulas = new CellConstraints();
		
		lbDia = new JLabel("Dia: ");
		lbDia.setFont(new Font("Arial", Font.BOLD, 15));
		cbDias = new JComboBox(AuxiliarConstantes.DIAS_MES);
		cbDias.setFont(new Font("Arial", Font.BOLD, 15));
		btGerar = new JButton("Gerar relatorio");
		btGerar.setFont(new Font("Arial", Font.BOLD, 15));
		btGerar.addActionListener(this);
		
		add(lbDia, celulas.xy(2, 2));
		add(cbDias, celulas.xy(4, 2));
		add(btGerar, celulas.xyw(2, 4, 3));
		
		setSize(300, 100);
		addWindowListener(new ApresentacaoFechamentoJanela(this));
		setLocation(0, 0);
		setResizable(false);
		setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
	
		try {
			if (e.getSource() == btGerar) {
				geracaoArquivos.geraEscaladosNoDia(servidores, local, Integer.parseInt((String) cbDias.getSelectedItem()));
			}
		} 
		catch (RelatorioException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

		}
	}
}
