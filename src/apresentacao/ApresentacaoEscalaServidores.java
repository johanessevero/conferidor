package apresentacao;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import processamento.ProcessGeracaoArquivoDados;
import auxiliar.AuxiliarConstantes;
import auxiliar.RelatorioException;
import entidade.EntidadeServidor;

/**
 * 
 * @author Johanes Severo dos Santos
 * Classe que implementa a interface de edicao e visualizacao das escalas dos servidores.
 */
public class ApresentacaoEscalaServidores extends JFrame implements ActionListener {

	private boolean habilitaCamposEscala;
	
	private int mes;
	
	private String localArquivo;
	
	private JPanel pDadosEscalaSetor;
	private JPanel pEscalaServidores;
	
	private EntidadeServidor servidores[];
	
	private JButton btGerarRascunho;
	private JButton btGerarQuantitativo;
	
	private ProcessGeracaoArquivoDados geracaoArquivoDados;
	
	private boolean contemErro;
	
	public ApresentacaoEscalaServidores() {}
	
	public ApresentacaoEscalaServidores(EntidadeServidor servidores[], boolean habilitaCamposEscala, int mes, String localArquivo) {
		
		super(AuxiliarConstantes.TITULO);
		
		this.contemErro = false;
		
		geracaoArquivoDados = new ProcessGeracaoArquivoDados();
		
		this.habilitaCamposEscala = habilitaCamposEscala;
		this.mes = mes;
		this.localArquivo = localArquivo;
		pDadosEscalaSetor = new JPanel();
		btGerarRascunho = new JButton("Gerar rascunho");
		btGerarRascunho.addActionListener(this);
		btGerarQuantitativo = new JButton("Gerar quantitativo");
		btGerarQuantitativo.addActionListener(this);
		pDadosEscalaSetor.add(btGerarRascunho);
		pDadosEscalaSetor.add(btGerarQuantitativo);
		pEscalaServidores = new JPanel(new GridLayout(servidores.length, 1));
		setLayout(new BorderLayout());
		add(pDadosEscalaSetor, BorderLayout.NORTH);
		mostrarEscalasServidores(servidores);
		//mostrarFuncionalidades();
		
		addWindowListener(new ApresentacaoFechamentoJanela(this));
		setLocation(0, 0);
		setResizable(true);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
	
	}
	
	private void mostrarFuncionalidades() {
		
		pDadosEscalaSetor.removeAll();
		add(pDadosEscalaSetor, BorderLayout.NORTH);
		pDadosEscalaSetor.updateUI();
		
	}
	
	private void mostrarEscalasServidores(EntidadeServidor servidores[]) {
		
		try {
			pEscalaServidores.removeAll();
			this.servidores = servidores;
			
			for (int i = 0; i < servidores.length; ++i) {
			
				pEscalaServidores.add(new ApresentacaoEscalaServidor(servidores[i], habilitaCamposEscala));
				
				if (!servidores[i].getEscala().isEscalaLancada() & contemErro == false)
					contemErro = true;
			}
			
			add(new JScrollPane(pEscalaServidores), BorderLayout.CENTER);
			pEscalaServidores.updateUI();
			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			ex.printStackTrace();
		} 
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		try {
			if (e.getSource() == btGerarRascunho) {
		
				geracaoArquivoDados.geraRascunho(servidores, localArquivo, servidores[0].getEscala().getMes(), servidores[0].getEscala().getAno());
				
			}
			else
			if (e.getSource() == btGerarQuantitativo) {
			
				geracaoArquivoDados.geraQuantitativoPorPeriodo(servidores, localArquivo, servidores[0].getEscala().getMes(), servidores[0].getEscala().getAno());
			}
			
		}
		catch(RelatorioException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
	}
}
