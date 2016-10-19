package apresentacao;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import mac.MAC;
import mac.MACRN;
import processamento.ProcessCriptografia;
import auxiliar.AuxiliarConstantes;
import auxiliar.DAOException;
import auxiliar.RNException;
import auxiliar.UtilException;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ApresentacaoAutenticacao extends JFrame implements ActionListener {
	
	private JPasswordField pwSenha;
	private JLabel lbDescricao;
	private JLabel lbSenha;
	private JButton btSenha;
	
	private int data[];
	
	private String senhaCorreta;
	
	private ProcessCriptografia criptografia;
	
	private MACRN macRN;
	private MAC mac;
	
	private boolean eComputador = false;
	
	public ApresentacaoAutenticacao() {
		
		this.setTitle("..: Autenticacao :..");
		
		macRN = new MACRN();
		criptografia = new ProcessCriptografia();
		
		try {
			mac = macRN.buscar();
			if (mac == null) {
			
				macRN.inserir();
				eComputador = true;
			
			}
			else {
			
				String macGravada = mac.getParte1().trim() +"-"+mac.getParte2().trim()+"-"+mac.getParte3().trim()+"-"+mac.getParte4().trim() +"-"+mac.getParte5().trim() +"-"+mac.getParte6().trim();
				String macComputador = AuxiliarConstantes.getEnderecoMac().trim();
				String parte1 = "";
				String parte2 = "" ;
				String parte3 = "";
				String parte4 = "";
				String parte5 = "";
				String parte6 = "";
				for(int i = 0; i < macComputador.length(); i++) {
					
					if (i < 2) {					
						parte1 += macComputador.charAt(i);
					}
					else
					if (i  > 2 && i < 5) {
						parte2 += macComputador.charAt(i);
					}
					else
					if (i  > 5 && i < 8) {
						parte3 += macComputador.charAt(i);
					}
					else
					if (i  > 8 && i < 11) {
						parte4 += macComputador.charAt(i);
					}
					else
					if (i  > 11 && i < 14) {
						parte5 += macComputador.charAt(i);
					}
					else
					if (i  > 14 && i < 17) {
						parte6 += macComputador.charAt(i);
					}
						
				}
				
				parte1 = criptografia.criptografar(parte1);
				parte2 = criptografia.criptografar(parte2);
				parte3 = criptografia.criptografar(parte3);
				parte4 = criptografia.criptografar(parte4);
				parte5 = criptografia.criptografar(parte5);
				parte6 = criptografia.criptografar(parte6);
				
				macComputador = parte1.trim() +"-"+parte2.trim()+"-"+parte3.trim()+"-"+parte4.trim() +"-"+parte5.trim() +"-"+parte6.trim();

				if (macGravada.equals(macComputador.trim())) {
					eComputador = true;
				}
				
				System.out.println(macComputador);
				System.out.println(macGravada);
			
			}
			
		//try {
		    //eComputador = true;
			if (eComputador) {
						
				/*criptografia = new ProcessCriptografia();
						
				data = AuxiliarConstantes.getHorarioDeBrasiliaOnline();
						
				for (int i = 0; i < AuxiliarConstantes.SENHAS.length; ++i) {
							
					if (AuxiliarConstantes.SENHAS[i].getMes() == data[1] & AuxiliarConstantes.SENHAS[i].getAno() == data[2]) {
						senhaCorreta = AuxiliarConstantes.SENHAS[i].getSenha();
					}
							
				}
				
				lbDescricao = new JLabel("Digite uma senha valida para o mes: " + AuxiliarConstantes.MESES[data[1]-1] + " /" + data[2]);
				lbSenha = new JLabel("Senha: ");
				pwSenha = new JPasswordField(11);
				btSenha = new JButton("OK");
				btSenha.addActionListener(this);
						
				FormLayout formLayout = new FormLayout(
						 "2dlu, 50px, 2dlu, 600px",
						 "2dlu, " +
						 "pref, "+
						 "2dlu, " +
						 "pref, " +
						 "2dlu, " +
						 "pref, " +
						 "2dlu, " +
						 "pref "
						 );

					setLayout(formLayout);
				
					CellConstraints celulas = new CellConstraints();
				
					add(lbDescricao, celulas.xyw(2, 2, 3));
					add(lbSenha, celulas.xy(2, 4));
					add(pwSenha, celulas.xy(4, 4));
					add(btSenha, celulas.xyw(2, 6, 2));
					
					setSize(700, 150);
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					setLocation(0, 0);
					setResizable(false);
					setVisible(true);*/
				
					try {
						ApresentacaoConferencia ac = new ApresentacaoConferencia();
					} catch (Exception ex) {
					
						JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);

					}
						
				}	
				else {
					lbDescricao = new JLabel("");
					lbDescricao.setText("ESTE SOFTWARE NAO ESTA NO LOCAL ONDE FOI INSTALADO INICIALMENTE!");
					lbSenha = new JLabel("Senha: ");
					pwSenha = new JPasswordField(11);
					pwSenha.setEditable(false);
					btSenha = new JButton("OK");
					btSenha.addActionListener(this);
					btSenha.setEnabled(false);
						
					FormLayout formLayout = new FormLayout(
							 "2dlu, 50px, 2dlu, 600px",
							 "2dlu, " +
							 "pref, "+
							 "2dlu, " +
							 "pref, " +
							 "2dlu, " +
							 "pref, " +
							 "2dlu, " +
							 "pref "
							 );

					setLayout(formLayout);
					
					CellConstraints celulas = new CellConstraints();
					
					add(lbDescricao, celulas.xyw(2, 2, 3));
					add(lbSenha, celulas.xy(2, 4));
					add(pwSenha, celulas.xy(4, 4));
					add(btSenha, celulas.xyw(2, 6, 2));
						
					setSize(700, 150);
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					setLocation(0, 0);
					setResizable(false);
					setVisible(true);
			}
		}
		catch(RNException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);

		}
		catch(UtilException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);

		}
		catch(DAOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);

		}
		/*catch(IOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);

		}*/
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		
		if (e.getSource() == btSenha) {
			

			String senha = criptografia.criptografar(new String(pwSenha.getPassword()).trim().toString());
			System.out.println(senha + " " + senhaCorreta);
			
				if (!senha.equalsIgnoreCase(senhaCorreta)) {
					
					pwSenha.setText("");
					lbDescricao.setText("SENHA INCORRETA! ENVIE UM EMAIL PARA johanessevero@gmail.com E OBTENHA A SENHA PARA O MÊS DE: " + AuxiliarConstantes.MESES[data[1]-1] + " /" + data[2]);
					pwSenha.updateUI();
					lbDescricao.updateUI();
				}
				else {
					this.setVisible(false);
					this.dispose();
					
					try {
						ApresentacaoConferencia ac = new ApresentacaoConferencia();
					} catch (Exception ex) {
						
						JOptionPane.showMessageDialog(null, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);

					}
					
				}
		}
	}
}
