package apresentacao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.mail.MessagingException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import processamento.ProcessConferidor;
import processamento.ProcessEmail;
import processamento.ProcessGeracaoArquivoDados;
import processamento.ProcessLeitorDadosPlanilha;
import setor.Setor;
import setor.SetorRN;
import auxiliar.AuxiliarConstantes;
import auxiliar.DAOException;
import auxiliar.RNException;
import auxiliar.RelatorioException;
import entidade.EntidadeMensagem;
import entidade.EntidadeServidor;

/**
 * 
 * @author Johanes Severo dos Santos
 * Classe que modela a apresentacao da interface para a conferencia da planilha.
 */
public class ApresentacaoConferencia extends JFrame implements ActionListener {

	private JTextField txArquivo;
	
	private JTextArea taRelatorio;
	
	private JLabel lbHorasExtras, lbEmail;
	
	private JButton btRelatorioErrosServidores[];
	private JButton btConferir;
	private JButton btEscolherArquivo;
	private JButton btVisualizarEscala;
	private JButton btConferirTudo;
	private JButton btElaborarEscala;
	
	private JComboBox cbMeses;
	private JComboBox cbAno;
	private JComboBox cbSetor;
	private JComboBox cbFormato;
	
	private JFileChooser fcEscolherArquivo;
	
	private JPanel pFuncionalidades;
	private JPanel pTextoRelatorio;
	private JPanel pRelatoriosServidores = new JPanel();
	private JPanel pBancoDados;
	
	private JSplitPane spNomesRelatorio;

	private EntidadeServidor servidores[];
	
	private ProcessConferidor conferidor;
	private ProcessLeitorDadosPlanilha leitor;
	private ProcessGeracaoArquivoDados geracaoArquivos;
	private ProcessEmail email;
	
	private String nomePlanilhaLeitura;
	private String setor;
	
	private int quantidadeHorasExtras;
	
	private JMenuBar jmPrincipal;
	private JMenu jmRelatorios;
	private JMenuItem jmErros;
	private JMenuItem jmTesouraria;
	private JMenuItem jmExtra;
	private JMenuItem jmRascunho;
	private JMenuItem jmQuantitativoPeriodo;
	private JMenuItem jmEscaladosNoDia;
	private JMenu jmEnviar;
	
	private JMenu jmCadastro;
	private JMenuItem jmEmail;
	private JMenuItem jmEnviarRelatorioErros;
	
	private SetorRN setorRN;
	
	public ApresentacaoConferencia() throws DAOException {
		
		super(AuxiliarConstantes.TITULO);
		setLayout(new BorderLayout());
		
		conferidor = new ProcessConferidor();
		leitor = new ProcessLeitorDadosPlanilha();
		geracaoArquivos = new ProcessGeracaoArquivoDados();
		email = new ProcessEmail();
		setorRN = new SetorRN();
		
		nomePlanilhaLeitura = "";
		
		jmPrincipal = new JMenuBar();
		jmRelatorios = new JMenu("   Relatorios   ");
		jmCadastro = new JMenu(" Cadastro ");
		jmErros = new JMenuItem(" Relatorio de erros ");
		jmTesouraria = new JMenuItem(" Relatorio da tesouraria ");
		jmRascunho = new JMenuItem(" Rascunho de escala ");
		jmExtra = new JMenuItem(" Processo de horas extras ");
		//jmExtra.setEnabled(false);
		jmQuantitativoPeriodo = new JMenuItem(" Quantitativo por periodo ");
		jmEmail = new JMenuItem(" Cadastrar email do setor");
		jmEscaladosNoDia = new JMenuItem(" Relatorio escalados no dia ");
		jmEnviar = new JMenu("  Enviar  ");
		jmEnviarRelatorioErros = new JMenuItem("  Enviar relatorio de erros para o email do setor  ");
		jmEnviarRelatorioErros.addActionListener(this);
		
		jmPrincipal.add(jmRelatorios);
		//jmPrincipal.add(jmCadastro);
		//jmCadastro.add(jmEmail);
		jmRelatorios.add(jmErros);
		jmErros.addActionListener(this);
		jmRelatorios.add(jmTesouraria);
		jmTesouraria.addActionListener(this);
		jmRelatorios.add(jmExtra);
		jmExtra.addActionListener(this);
		jmRelatorios.add(jmRascunho);
		jmRascunho.addActionListener(this);
		jmRelatorios.add(jmQuantitativoPeriodo);
		jmQuantitativoPeriodo.addActionListener(this);
		jmRelatorios.add(jmEscaladosNoDia);
		jmEscaladosNoDia.addActionListener(this);
		jmEmail.addActionListener(this);
		jmEnviar.add(jmEnviarRelatorioErros);
		//jmPrincipal.add(jmEnviar);
		setJMenuBar(jmPrincipal);
		
		pFuncionalidades = new JPanel();
		fcEscolherArquivo = new JFileChooser();
		fcEscolherArquivo.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		txArquivo = new JTextField();
		txArquivo.setColumns(25);
		txArquivo.setEditable(false);
		btEscolherArquivo = new JButton("Escolher arquivo...");
		btEscolherArquivo.addActionListener(this);
		btConferir = new JButton("Conferir");
		btConferir.addActionListener(this);
		btVisualizarEscala = new JButton("Visualizar");
		btVisualizarEscala.setEnabled(false);
		btVisualizarEscala.addActionListener(this);
		btConferirTudo = new JButton("Conferir tudo");
		btConferirTudo.addActionListener(this);
		btElaborarEscala = new JButton("Elaborar escala prox mes");
		btElaborarEscala.addActionListener(this);
		btElaborarEscala.setEnabled(false);
		
		cbMeses = new JComboBox(AuxiliarConstantes.MESES);
		cbAno = new JComboBox(AuxiliarConstantes.getAnos());
		cbSetor = new JComboBox();
		cbSetor.setEnabled(false);
		String formatos[] = {"XML", "XLS"};
		cbFormato = new JComboBox(formatos);
		//cbFormato = new JComboBox(formato);
		
		lbHorasExtras = new JLabel("");
		lbEmail = new JLabel("");
		
		pFuncionalidades.add(txArquivo);
		pFuncionalidades.add(btEscolherArquivo);
		pFuncionalidades.add(cbMeses);
		pFuncionalidades.add(cbAno);
		pFuncionalidades.add(cbFormato);
		pFuncionalidades.add(btConferir);
		pFuncionalidades.add(btConferirTudo);
		
		pTextoRelatorio = new JPanel();
		taRelatorio = new JTextArea();
		taRelatorio.setColumns(100);
		taRelatorio.setRows(50);
		taRelatorio.setAutoscrolls(true);
		taRelatorio.setEditable(false);
		pTextoRelatorio.add(taRelatorio);
		
		pRelatoriosServidores = new JPanel();
		
		pBancoDados = new JPanel();
		pBancoDados.add(btVisualizarEscala);
		pBancoDados.add(btElaborarEscala);
		pBancoDados.add(lbHorasExtras);
		pBancoDados.add(lbEmail);
		
		spNomesRelatorio = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, new JScrollPane(pRelatoriosServidores), new JScrollPane(pTextoRelatorio));
		spNomesRelatorio.setDividerLocation((800/2)+100);
		spNomesRelatorio.setOneTouchExpandable(true);
		
		add(spNomesRelatorio, BorderLayout.CENTER);
		add(pFuncionalidades, BorderLayout.NORTH);
		add(pBancoDados, BorderLayout.SOUTH);
		
		geracaoArquivos = new ProcessGeracaoArquivoDados();
	
		addWindowListener(new ApresentacaoFechamentoJanela(this));
		setLocation(0, 0);
		setResizable(true);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);
		
	}
	
	private void mostraBotoesNomes(boolean arquivo) throws NumberFormatException, RNException {
		
		if (arquivo) {
				
			if (cbFormato.getSelectedIndex() == 0)
				servidores = leitor.getDadosPlanilhaEscalaXML(nomePlanilhaLeitura, cbMeses.getSelectedIndex() + 1, Integer.parseInt((String) cbAno.getSelectedItem()));
				
			else 
				servidores = leitor.getDadosPlanilhaEscala(nomePlanilhaLeitura, cbMeses.getSelectedIndex() + 1, Integer.parseInt((String) cbAno.getSelectedItem()));
				
				conferidor.conferirEscala(servidores, true);
			}
			else {
				conferidor.conferirEscala(servidores, true);
			}
		
			setor = servidores[0].getSetor();
			
			try {
				Setor setorBusca = setorRN.buscar(setor.trim());
				
				if (setorBusca != null) {
					lbEmail.setText(setorBusca.getEmail());
					lbEmail.updateUI();
				}
				
			} catch (DAOException ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

			}
			
			quantidadeHorasExtras = 0;
			lbHorasExtras.setText("");
			taRelatorio.setText("");
			
			pRelatoriosServidores.removeAll();
			pRelatoriosServidores.setLayout((new GridLayout(servidores.length + 1, 1)));
				
			btRelatorioErrosServidores = new JButton[servidores.length];
		
			for (int i = 0; i < servidores.length; ++i) {
				
				EntidadeMensagem relatorio = null;
				
				relatorio = conferidor.getRelatorioErro(servidores[i].getMatricula());
					
				btRelatorioErrosServidores[i] = new JButton();
				btRelatorioErrosServidores[i].setSize(10, 20);
				btRelatorioErrosServidores[i].addActionListener(this);
				pRelatoriosServidores.add(btRelatorioErrosServidores[i]);
				
				float falta = conferidor.calculaFaltaUltimaSemana(servidores[i].getEscala().getHorasUltimaSemana(), servidores[i].getCargaHoraria());
				
				if (!relatorio.isOk()) {
					
					btRelatorioErrosServidores[i].setText("ERRO: " + servidores[i].getMatricula() + " " + servidores[i].getNome() + " " + AuxiliarConstantes.getDiaSemanaData(AuxiliarConstantes.getUltimoDiaMes(servidores[i].getEscala().getMes(), servidores[i].getEscala().getAno()), servidores[i].getEscala().getMes(), servidores[i].getEscala().getAno()) + " " + falta + " THE: " + servidores[i].getEscala().getTotalHorasExtras());
					btRelatorioErrosServidores[i].setForeground(Color.RED);
					btRelatorioErrosServidores[i].setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14 ));
					
				}
				else {
					
					btRelatorioErrosServidores[i].setText("OK: " + servidores[i].getMatricula() + " " + servidores[i].getNome() + " " + AuxiliarConstantes.getDiaSemanaData(AuxiliarConstantes.getUltimoDiaMes(servidores[i].getEscala().getMes(), servidores[i].getEscala().getAno()), servidores[i].getEscala().getMes(), servidores[i].getEscala().getAno()) + " " + falta + " THE: " + servidores[i].getEscala().getTotalHorasExtras());
					btRelatorioErrosServidores[i].setForeground(Color.BLUE);
					btRelatorioErrosServidores[i].setFont(new Font( "Monospaced", Font.BOLD, 14 ));
					
				}
					
				quantidadeHorasExtras += servidores[i].getEscala().getTotalHorasExtras(); 
				
			}
				
			lbHorasExtras.setText("Total de horas extras: " + quantidadeHorasExtras);
			lbHorasExtras.setFont(new Font( "Monospaced", Font.BOLD, 14 ));
			
			pFuncionalidades.updateUI();
			pRelatoriosServidores.updateUI();
		
		btVisualizarEscala.setEnabled(true);
		btElaborarEscala.setEnabled(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		try {
			
			if (e.getSource() == btElaborarEscala) {
				

				if (servidores != null)
					new ApresentacaoEscolhaArea(servidores);
				else 
					JOptionPane.showMessageDialog(null, "Nao ha uma conferencia feita!", "Erro", JOptionPane.ERROR_MESSAGE);
				
			}
			else
			if (e.getSource() == jmEnviarRelatorioErros) {
				

				if (servidores != null) {
					String nomeArq = geracaoArquivos.geraRelatorioErros(servidores, conferidor, servidores[0].getSetor());
					try {
						if (!lbEmail.getText().isEmpty()) {
							email.enviarEmailArquivo("Relatorio de erros", "Segue o relatorio de erros.", lbEmail.getText().trim(), nomeArq.trim());
							JOptionPane.showMessageDialog(null, "Relatorio enviado com sucesso para: " + lbEmail.getText() + "!", "Erro", JOptionPane.INFORMATION_MESSAGE);

						}
						else
							JOptionPane.showMessageDialog(null, "Este setor não tem um email cadastrado!", "Erro", JOptionPane.ERROR_MESSAGE);

					} catch (MessagingException e1) {
						JOptionPane.showMessageDialog(null, "Erro ao enviar o email!", "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
				else 
					JOptionPane.showMessageDialog(null, "Nao ha uma conferencia feita!", "Erro", JOptionPane.ERROR_MESSAGE);
				
			}
			else
			if (e.getSource() == jmEmail) {
				

				if (servidores != null)
					new ApresentacaoCadastroSetor(setor.trim(), lbEmail);
				else 
					JOptionPane.showMessageDialog(null, "Nao ha uma conferencia feita!", "Erro", JOptionPane.ERROR_MESSAGE);
				
			}
			else
			if (e.getSource() == jmExtra) {
				
				if (servidores != null)
					geracaoArquivos.geraProcessoHorasExtras(servidores, cbMeses.getSelectedIndex()+1, Integer.parseInt((String) cbAno.getSelectedItem()), leitor.getValoresHoraExtra());	
				else 
					JOptionPane.showMessageDialog(null, "Nao ha uma conferencia feita!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			else
			if (e.getSource() == jmEscaladosNoDia) {
					
				if (servidores != null)
					new ApresentacaoEscolhaDia(geracaoArquivos, servidores[0].getSetor(), servidores);	
				else 
					JOptionPane.showMessageDialog(null, "Nao ha uma conferencia feita!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			else
			if (e.getSource() == jmTesouraria) {
				if (servidores != null)
					geracaoArquivos.geraRelatorioQuantidadeRefeicoes(servidores, servidores[0].getSetor());
				else 
					JOptionPane.showMessageDialog(null, "Nao ha uma conferencia feita!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			else
			if (e.getSource() == jmQuantitativoPeriodo) {
				if (servidores != null)
					geracaoArquivos.geraQuantitativoPorPeriodo(servidores, servidores[0].getSetor(), servidores[0].getEscala().getMes(), servidores[0].getEscala().getAno());
				else 
					JOptionPane.showMessageDialog(null, "Nao ha uma conferencia feita!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			else
			if (e.getSource() == jmRascunho) {
					
				if (servidores != null)
					geracaoArquivos.geraRascunho(servidores, servidores[0].getSetor(), servidores[0].getEscala().getMes(), servidores[0].getEscala().getAno());
				else 
					JOptionPane.showMessageDialog(null, "Nao ha conferencia feita!!", "Erro", JOptionPane.ERROR_MESSAGE);	
			}
			else
			if (e.getSource() == jmErros) {
						
				if (servidores != null)
					geracaoArquivos.geraRelatorioErros(servidores, conferidor, servidores[0].getSetor());
				else 
					JOptionPane.showMessageDialog(null, "Nao ha conferencia feita!!", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			else
			if (e.getSource() == btConferir) {
						
				if (nomePlanilhaLeitura.equals(""))
					JOptionPane.showMessageDialog(null, "Escolha um arquivo para coferencia.", "Erro", JOptionPane.ERROR_MESSAGE);
				else {
					mostraBotoesNomes(true);
				}				
			}
			else 
			if (e.getSource() == btVisualizarEscala) {
							
					ApresentacaoEscalaServidores aes = new ApresentacaoEscalaServidores(servidores, true, cbMeses.getSelectedIndex() + 1, new File(nomePlanilhaLeitura).getPath());
					JPanel pJanelaPrincipal = (JPanel) aes.getContentPane();
					pJanelaPrincipal.updateUI();
			}
			
			
			if (e.getSource() == btEscolherArquivo) {
					
				int opcao = fcEscolherArquivo.showOpenDialog(this);
				if (opcao == JFileChooser.APPROVE_OPTION) {
					File arquivo = fcEscolherArquivo.getSelectedFile();
					nomePlanilhaLeitura = arquivo.getAbsolutePath();
					txArquivo.setText(arquivo.getAbsolutePath());
				}
					
			}
			else
			if (e.getSource() == btConferirTudo) {

				File arq = new File(txArquivo.getText());
				if (arq.isDirectory()) {
					conferidor.conferirTudo(leitor, txArquivo.getText().trim(), cbFormato.getSelectedIndex(), cbMeses.getSelectedIndex() + 1, Integer.parseInt((String) cbAno.getSelectedItem()));
					JOptionPane.showMessageDialog(null, "Escalas conferidas com sucesso!", "Erro", JOptionPane.INFORMATION_MESSAGE);

				}
				else
					JOptionPane.showMessageDialog(null, "Escolha um diretorio para conferencia!", "Erro", JOptionPane.ERROR_MESSAGE);

			}
			else {
				if (servidores != null) {
					taRelatorio.setText("");		
					String mensagem = "";
					for (int i = 0; i < servidores.length; ++i) {
							
						if (e.getSource() == btRelatorioErrosServidores[i]) {
							
							EntidadeMensagem entidadeMensagem = conferidor.getRelatorioErro(servidores[i].getMatricula());
								
							//taRelatorio.setText(conferidor.getRelatorioErro(servidores[i].getMatricula()).getMensagem())
						    int posChr = 0;
						    while (posChr <  entidadeMensagem.getMensagem().length()) {
						    String parteMensagem = "";
						   	aqui: for (int k = posChr; k < entidadeMensagem.getMensagem().length(); ++k) {
							    			
						    	if (entidadeMensagem.getMensagem().charAt(k) != '$')
						    		parteMensagem += entidadeMensagem.getMensagem().charAt(k);
							    				
						    		if (entidadeMensagem.getMensagem().charAt(k) == '$') {
								
						    			taRelatorio.append(parteMensagem + "\n");
						    			mensagem += parteMensagem;
						    			posChr = k+1;
							    		break aqui;
							    	}				
							    }
							}
						}
					}
				}
			}
		}
		catch(RNException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);	}
		catch(RelatorioException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);	
		}
	}
}
