package apresentacao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import processamento.ProcessConferidor;
import auxiliar.AuxiliarConstantes;
import auxiliar.AuxiliarGeral;
import entidade.EntidadeEscala;
import entidade.EntidadeMensagem;
import entidade.EntidadeServidor;

/**
 * 
 * @author Johanes Severo dos Santos
 * Classe que implementa a interface de apresentacao da escala de um servidor.
 */
public class ApresentacaoEscalaServidor extends JPanel implements ActionListener {
	
	private boolean habilitaCamposEscala;
	
	private JLabel lbRotuloMatricula;
	private JLabel lbRotuloNome;
	private JLabel lbRotuloCargo;
	private JLabel lbDiasMes[];
	private JLabel lbDiasSemana[];
	private JLabel lbRotuloCargaHoraria;
	private JLabel lbRotuloCargaHorariaSetor;
	private JLabel lbRotuloPassagemMesAnterior;
	private JLabel lbRotuloPassagemProximoMes, lbPassagemProximoMes;
	private JLabel lbRotuloSemana1, lbSemana1;
	private JLabel lbRotuloSemana2, lbSemana2;
	private JLabel lbRotuloSemana3, lbSemana3; 
	private JLabel lbRotuloSemana4, lbSemana4;
	private JLabel lbRotuloSemana5, lbSemana5; 
	private JLabel lbRotuloSemana6, lbSemana6;
	private JLabel lbRotuloSetor;
	private JLabel lbRotuloErro, lbErro;
	private JLabel lbRotuloMes, lbMes;
	private JLabel lbRotuloAno, lbAno;
	private JLabel lbRotuloQuantidadeRefeicoes, lbQuantidadeRefeicoesDiurno, lbQuantidadeRefeicoesNoturno;
	private JLabel lbRotuloTotalHorasExtras, lbTotalHorasExtras;
	private JLabel lbRotuloCompensacao, lbCompensacao;
	
	private JTextField txMatricula, 
						txNome,  
						txCargo, 
						txCargaHoraria, 
						txCargaHorariaSetor, 
						txPassagemMesAnterior;  
	private JTextField txEscalaContratual[];
	private JTextField txEscalaExtra[];
	private JTextField txObservacao;
	
	private JComboBox cbSetor;
	
	private JButton btValidar;
	private JButton btLimpar;
	
	private EntidadeServidor servidor;
	
	private EntidadeMensagem mensagem;
	
	private int mes;
	private int ano;
	
	private ProcessConferidor processConferidor;
	
	//private PersistenciaDadosSelecionados persistenciaDadosSelecionados;

	public ApresentacaoEscalaServidor(EntidadeServidor servidor, boolean habilitaCamposEscala) {
		
		this.habilitaCamposEscala = habilitaCamposEscala;
		setLayout(new BorderLayout());
		this.servidor = servidor;
		//persistenciaDadosSelecionados = new PersistenciaDadosSelecionados();
		montaLayout(servidor);
		
	}
	
	private void montaLayout(EntidadeServidor servidor) {
		
		try {
			
			mes = servidor.getEscala().getMes();
			ano = servidor.getEscala().getAno();
			
			int ultimoDiaMes = AuxiliarConstantes.getUltimoDiaMes(servidor.getEscala().getMes(), servidor.getEscala().getAno());
			
			JPanel pDadosPessoais = new JPanel(new GridLayout(7, 2));
			JPanel pInformacoes = new JPanel();
			JPanel pDadosEscala = new JPanel(new GridLayout(4, ultimoDiaMes));
			JPanel pFuncionalidades = new JPanel();
			
			lbRotuloMatricula = new JLabel("  MATRICULA: ");
			txMatricula = new JTextField(servidor.getMatricula());
			txMatricula.setForeground(Color.BLUE);
			txMatricula.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloNome = new JLabel("  NOME: ");
			txNome = new JTextField(servidor.getNome());
			txNome.setForeground(Color.BLUE);
			txNome.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloCargo = new JLabel("  CARGO: ");
			txCargo = new JTextField(servidor.getCargo());
			txCargo.setForeground(Color.BLUE);
			txCargo.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloCargaHoraria = new JLabel("  CARGA HORARIA: ");
			txCargaHoraria = new JTextField(Integer.toString(servidor.getCargaHoraria()));
			txCargaHoraria.setForeground(Color.BLUE);
			txCargaHoraria.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloCargaHorariaSetor = new JLabel(" CARGA HORARIA NO SETOR: ");
			txCargaHorariaSetor = new JTextField(Integer.toString(servidor.getCargaHoraria()));
			txCargaHorariaSetor.setForeground(Color.BLUE);
			txCargaHorariaSetor.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			txObservacao = new JTextField();
			txObservacao.setText(servidor.getEscala().getObservacao());
			txObservacao.setEditable(true);
			txObservacao.setColumns(25);
			lbRotuloPassagemMesAnterior = new JLabel("  PASSAGEM DO MES ANTERIOR: ");
			txPassagemMesAnterior = new JTextField(Float.toString(servidor.getEscala().getHorasUltimaSemanaMesAnterior()));
			txPassagemMesAnterior.setForeground(Color.BLUE);
			txPassagemMesAnterior.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbDiasMes = new JLabel[ultimoDiaMes];
			lbDiasSemana = new JLabel[ultimoDiaMes];;
			txEscalaContratual = new JTextField[ultimoDiaMes];
			txEscalaExtra = new JTextField[ultimoDiaMes];
			
			lbRotuloErro = new JLabel("STATUS: ");
			lbErro = new JLabel();
			
			if (!servidor.getEscala().isEscalaLancada()) {
				
				lbErro.setText("ERRO   ");
				lbErro.setForeground(Color.RED);
				lbErro.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 25));
				
			}
			else {
				
				lbErro.setText("OK   ");
				lbErro.setForeground(Color.BLUE);
				lbErro.setFont(new Font( "Monospaced", Font.BOLD + Font.ITALIC, 25));
				
			}
			
			lbRotuloSetor = new JLabel(" | SETOR: ");
			cbSetor = new JComboBox();
			/*String setores[] = persistenciaDadosSelecionados.getSetores();
			
			int selecao = 0;
			for (int i = 0; i < setores.length; ++i) {
				cbSetor.addItem(setores[i]);
				if (servidor.getSetor().equals(setores[i])) {
					selecao = i;
				}
			}
			cbSetor.setSelectedIndex(selecao);*/
			cbSetor.addItem((String) servidor.getSetor());
			cbSetor.setForeground(Color.BLUE);
			cbSetor.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
	
			
			lbRotuloMes = new JLabel(" | MES: ");
			lbMes = new JLabel(servidor.getEscala().getMes() + "");
			lbMes.setForeground(Color.BLUE);
			lbMes.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloAno = new JLabel(" | ANO: ");
			lbAno = new JLabel(servidor.getEscala().getAno() + "");
			lbAno.setForeground(Color.BLUE);
			lbAno.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloQuantidadeRefeicoes= new JLabel(" | QUANTIDADE DE REFEICOES D & N: ");
			lbQuantidadeRefeicoesDiurno = new JLabel(servidor.getEscala().getQuantidadeRefeicoesDiurno() + "");
			lbQuantidadeRefeicoesDiurno.setForeground(Color.BLUE);
			lbQuantidadeRefeicoesDiurno.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbQuantidadeRefeicoesNoturno = new JLabel(servidor.getEscala().getQuantidadeRefeicoesNoturno() + "");
			lbQuantidadeRefeicoesNoturno.setForeground(Color.BLUE);
			lbQuantidadeRefeicoesNoturno.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloTotalHorasExtras = new JLabel(" | TOTAL DE HORAS EXTRAS: ");
			lbTotalHorasExtras = new JLabel(servidor.getEscala().getTotalHorasExtras() + "");
			lbTotalHorasExtras.setForeground(Color.BLUE);
			lbTotalHorasExtras.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloCompensacao = new JLabel(" | BANCO DE HORAS: ");
			lbCompensacao = new JLabel(servidor.getEscala().getBancoHorasDestaEscala()  + "");
			lbCompensacao.setForeground(Color.BLUE);
			lbCompensacao.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloPassagemProximoMes = new JLabel(" | PASSAGEM PARA O PROXIMO MES: ");
			lbPassagemProximoMes = new JLabel(servidor.getEscala().getHorasUltimaSemana() + "");
			lbPassagemProximoMes.setForeground(Color.BLUE);
			txPassagemMesAnterior.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloSemana1 = new JLabel(" | SEMANA 1: ");
			lbSemana1 = new JLabel(servidor.getEscala().getHorasSemana1() + "");
			lbSemana1.setForeground(Color.BLUE);
			lbSemana1.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloSemana2 = new JLabel(" | SEMANA 2: ");
			lbSemana2 = new JLabel(servidor.getEscala().getHorasSemana2() + "");
			lbSemana2.setForeground(Color.BLUE);
			lbSemana2.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloSemana3 = new JLabel(" | SEMANA 3: ");
			lbSemana3 = new JLabel(servidor.getEscala().getHorasSemana3() + "");
			lbSemana3.setForeground(Color.BLUE);
			lbSemana3.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloSemana4 = new JLabel(" | SEMANA 4: ");
			lbSemana4 = new JLabel(servidor.getEscala().getHorasSemana4() + "");
			lbSemana4.setForeground(Color.BLUE);
			lbSemana4.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloSemana5 = new JLabel(" | SEMANA 5: ");
			lbSemana5= new JLabel(servidor.getEscala().getHorasSemana5() + "");
			lbSemana5.setForeground(Color.BLUE);
			lbSemana5.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			lbRotuloSemana6 = new JLabel(" | SEMANA 6: ");
			lbSemana6 = new JLabel(servidor.getEscala().getHorasSemana6() + "");
			lbSemana6.setForeground(Color.BLUE);
			lbSemana6.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 14));
			
			txMatricula.setEditable(false);
			txNome.setEditable(false);  
			txCargo.setEditable(false); 
			txCargaHoraria.setEditable(false); 
			txCargaHorariaSetor.setEditable(false); 
			txPassagemMesAnterior.setEditable(false);
			cbSetor.setEditable(false);
			cbSetor.setEnabled(false);
			
			btValidar = new JButton("Validar");
			btValidar.addActionListener(this);
			btValidar.setEnabled(habilitaCamposEscala);
			
			btLimpar = new JButton("Limpar");
			btLimpar.addActionListener(this);
			btLimpar.setEnabled(habilitaCamposEscala);
			
			int dataAtual[] = AuxiliarConstantes.getDataAtual();
			
			for (int i = 0; i < lbDiasMes.length; ++i) {
				
				lbDiasMes[i] = new JLabel(Integer.toString(i+1));
				lbDiasSemana[i] = new JLabel(AuxiliarConstantes.getDiaSemanaData(i+1, servidor.getEscala().getMes(), servidor.getEscala().getAno()));
				txEscalaContratual[i] = new JTextField();
				
				if (servidor.getEscala() != null)
					txEscalaContratual[i].setText(servidor.getEscala().getEscalaHoraContratual()[i]);
				
				if (i <= dataAtual[0]-1 && servidor.getEscala().getMes() == dataAtual[1] && servidor.getEscala().getAno() == dataAtual[2] && habilitaCamposEscala == false) {
					txEscalaContratual[i].setEditable(false);
				}
			
				txEscalaContratual[i].setEditable(habilitaCamposEscala);
					
				txEscalaExtra[i] = new JTextField();
				
				if (servidor.getEscala() != null)
					txEscalaExtra[i].setText(servidor.getEscala().getEscalaHoraExtra()[i]);
				
				
				txEscalaExtra[i].setEditable(habilitaCamposEscala);
				
				pDadosEscala.add(lbDiasMes[i]);
				
				if (lbDiasSemana[i].getText().equals("Dom")) {
					lbDiasMes[i].setText(" ||| " + lbDiasMes[i].getText());
					pDadosEscala.add(lbDiasMes[i]);
				}
				
				if (lbDiasSemana[i].getText().equals("Sab")) {
					lbDiasMes[i].setText(lbDiasMes[i].getText() + " ||| ");
					pDadosEscala.add(lbDiasMes[i]);
				}
				
				
			}
			for (int i = 0; i < lbDiasMes.length; ++i) {
				
				if (lbDiasSemana[i].getText().equals("Dom")) {
					lbDiasSemana[i].setText(" ||| " + lbDiasSemana[i].getText());
					pDadosEscala.add(lbDiasSemana[i]);
				}
				if (lbDiasSemana[i].getText().equals("Sab")) {
					lbDiasSemana[i].setText(lbDiasSemana[i].getText() + " ||| ");
					pDadosEscala.add(lbDiasSemana[i]);
				}
				else {
					pDadosEscala.add(lbDiasSemana[i]);
				}
			}
			for (int i = 0; i < lbDiasMes.length; ++i) {
				pDadosEscala.add(txEscalaContratual[i]);
			}
			for (int i = 0; i < lbDiasMes.length; ++i) {
				pDadosEscala.add(txEscalaExtra[i]);
			}
			
			pDadosEscala.setBorder(BorderFactory.createLineBorder (Color.black, 1));
			
			txObservacao.setText(servidor.getEscala().getObservacao());
			
			pInformacoes.add(lbRotuloErro);
			pInformacoes.add(lbErro);
			pInformacoes.add(lbRotuloSetor);
			pInformacoes.add(cbSetor);
			pInformacoes.add(lbRotuloMes);
			pInformacoes.add(lbMes);
			pInformacoes.add(lbRotuloAno);
			pInformacoes.add(lbAno);
			pInformacoes.add(lbRotuloQuantidadeRefeicoes);
			pInformacoes.add(lbQuantidadeRefeicoesDiurno);
			pInformacoes.add(lbQuantidadeRefeicoesNoturno);
			pInformacoes.add(lbRotuloTotalHorasExtras);
			pInformacoes.add(lbTotalHorasExtras);
			pInformacoes.add(lbRotuloCompensacao);
			pInformacoes.add(lbCompensacao);
			pInformacoes.add(lbRotuloPassagemProximoMes);
			pInformacoes.add(lbPassagemProximoMes);
			pInformacoes.add(lbRotuloSemana1);
			pInformacoes.add(lbSemana1);
			pInformacoes.add(lbRotuloSemana2);
			pInformacoes.add(lbSemana2);
			pInformacoes.add(lbRotuloSemana3);
			pInformacoes.add(lbSemana3);
			pInformacoes.add(lbRotuloSemana4);
			pInformacoes.add(lbSemana4);
			pInformacoes.add(lbRotuloSemana5);
			pInformacoes.add(lbSemana5);
			pInformacoes.add(lbRotuloSemana6);
			pInformacoes.add(lbSemana6);
			
			pDadosPessoais.add(lbRotuloMatricula);
			pDadosPessoais.add(txMatricula);
			pDadosPessoais.add(lbRotuloNome);
			pDadosPessoais.add(txNome);
			pDadosPessoais.add(lbRotuloCargo);
			pDadosPessoais.add(txCargo);
			pDadosPessoais.add(lbRotuloCargaHoraria);
			pDadosPessoais.add(txCargaHoraria);
			pDadosPessoais.add(lbRotuloCargaHorariaSetor);
			pDadosPessoais.add(txCargaHorariaSetor);
			pDadosPessoais.add(lbRotuloPassagemMesAnterior);
			pDadosPessoais.add(txPassagemMesAnterior);
			pDadosPessoais.add(txObservacao);
			
			pFuncionalidades.add(btValidar);
			pFuncionalidades.add(btLimpar);
			pFuncionalidades.setBorder(BorderFactory.createLineBorder (Color.black, 1));
			
			//pFuncionalidades.add(btAlterar);
			//pFuncionalidades.setBorder(BorderFactory.createLineBorder (Color.black, 1));
			
			add(pInformacoes, BorderLayout.NORTH);
			add(pDadosPessoais, BorderLayout.WEST);
			add(pDadosEscala, BorderLayout.CENTER);
			add(pFuncionalidades, BorderLayout.SOUTH);
			setBorder(BorderFactory.createLineBorder (Color.blue, 2));
		
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public EntidadeServidor getDados() {
		
		EntidadeServidor servidor = new EntidadeServidor();
		
		servidor.setMatricula(txMatricula.getText());
		servidor.setCargaHoraria(Integer.parseInt(txCargaHoraria.getText()));
		servidor.setCargaHorariaSetor(Integer.parseInt(txCargaHorariaSetor.getText()));
		servidor.setNome(txNome.getText());
		servidor.setCargo(txCargo.getText());
		servidor.setSetor((String) cbSetor.getSelectedItem());
		
		EntidadeEscala escala = new EntidadeEscala();
	
		escala.setHorasUltimaSemanaMesAnterior(Float.parseFloat(txPassagemMesAnterior.getText()));
		escala.setHorasUltimaSemana(Float.parseFloat(lbPassagemProximoMes.getText()));
		escala.setBancoHorasDestaEscala(Float.parseFloat(lbCompensacao.getText()));
		escala.setHorasSemana1(Float.parseFloat(lbSemana1.getText()));
		escala.setHorasSemana1(Float.parseFloat(lbSemana2.getText()));
		escala.setHorasSemana1(Float.parseFloat(lbSemana3.getText()));
		escala.setHorasSemana1(Float.parseFloat(lbSemana4.getText()));
		escala.setHorasSemana1(Float.parseFloat(lbSemana5.getText()));
		escala.setHorasSemana1(Float.parseFloat(lbSemana6.getText()));
		escala.setAno(Integer.parseInt(lbAno.getText()));
		escala.setMes(Integer.parseInt(lbMes.getText()));
		escala.setTotalHorasExtras(Integer.parseInt(lbTotalHorasExtras.getText()));
		escala.setQuantidadeRefeicoesDiurno(Integer.parseInt(lbQuantidadeRefeicoesDiurno.getText()));
		escala.setQuantidadeRefeicoesDiurno(Integer.parseInt(lbQuantidadeRefeicoesNoturno.getText()));
		escala.setObservacao(txObservacao.getText());
		
		int ultimoDiaMes = AuxiliarConstantes.getUltimoDiaMes(escala.getMes(), escala.getAno());
		String escalaHoraContratual[] = new String[ultimoDiaMes];
		String escalaHoraExtra[] = new String[ultimoDiaMes];
		
		for (int i = 0; i < ultimoDiaMes; ++i) {
			
			escalaHoraContratual[i] = txEscalaContratual[i].getText();
			escalaHoraExtra[i] = txEscalaContratual[i].getText();
			
		}
		
		escala.setEscalaHoraContratual(escalaHoraContratual);
		escala.setEscalaHoraExtra(escalaHoraExtra);
		
		servidor.setEscala(escala);
		
		return servidor;
		
	}
	
	public EntidadeMensagem validar(float passagemHoras) {
		
		processConferidor = new ProcessConferidor();
		String escalaContratual[] = new String[AuxiliarConstantes.getUltimoDiaMes(servidor.getEscala().getMes(), servidor.getEscala().getAno())];
		String escalaExtra[] = new String[AuxiliarConstantes.getUltimoDiaMes(servidor.getEscala().getMes(), servidor.getEscala().getAno())];
		
		for (int i = 0; i < AuxiliarConstantes.getUltimoDiaMes(servidor.getEscala().getMes(), servidor.getEscala().getAno()); ++i) {
			
			escalaContratual[i] =  AuxiliarGeral.substituiEspacos(txEscalaContratual[i].getText()).trim().toUpperCase();
			escalaExtra[i] = AuxiliarGeral.substituiEspacos(txEscalaExtra[i].getText()).trim().toUpperCase();
			
		}
		
		servidor.getEscala().setEscalaHoraContratual(escalaContratual);
		servidor.getEscala().setEscalaHoraExtra(escalaExtra);
		servidor.getEscala().setObservacao(txObservacao.getText());
		
		mensagem = processConferidor.conferirEscala(servidor, false);
	
		servidor.getEscala().setEscalaLancada(mensagem.isOk());
		removeAll();
		montaLayout(servidor);
		lbErro = new JLabel();
		if (!mensagem.isOk()) {
					
			lbErro.setText("ERRO");
			lbErro.setForeground(Color.RED);
			lbErro.setFont(new Font( "Monospaced", Font.ITALIC + Font.BOLD, 25));
			servidor.getEscala().setEscalaLancada(false);
					
		}
		else {
					
			lbErro.setText("OK");
			lbErro.setForeground(Color.BLUE);
			lbErro.setFont(new Font( "Monospaced", Font.BOLD + Font.ITALIC, 25));
			servidor.getEscala().setEscalaLancada(true);
		}
		updateUI();
		
		return mensagem;
	}
	
	private void limpar() {
		
		for (int i = 0; i < AuxiliarConstantes.getUltimoDiaMes(mes, ano); ++i) {
			
			if (!txEscalaContratual[i].getText().equals("")) {
				txEscalaContratual[i].setText("");
			}
			
			if (!txEscalaExtra[i].getText().equals("")) {
				txEscalaExtra[i].setText("");
			}
		}
		
		txObservacao.setText("");
		validar(0);
		
	}

	public EntidadeMensagem getMensagem() {
		return mensagem;
	}

	public void setMensagem(EntidadeMensagem mensagem) {
		this.mensagem = mensagem;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btValidar) {
			
			String mensagem = validar(servidor.getEscala().getHorasUltimaSemana()).getMensagem().replace('$', '\n');
			JOptionPane.showMessageDialog(null, mensagem, "Relatorio", JOptionPane.INFORMATION_MESSAGE);
			
		}
		if (e.getSource() == btLimpar) {
			
			limpar();
		}
	}

}
