package apresentacao;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import setor.Setor;
import setor.SetorRN;
import auxiliar.AuxiliarConstantes;
import auxiliar.DAOException;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class ApresentacaoCadastroSetor extends JFrame implements ActionListener {

	private JLabel lbSetor, lbEmail, lbEmailJanela;
	private JTextField txSetor, txEmail;
	
	private JButton btInserir;
	
	private SetorRN setorRN;
	
	private String stringSetor;
	
	private Setor setor;
	
	private boolean jaExiste = false;
	
	public ApresentacaoCadastroSetor(String stringSetor, JLabel lbEmailJanela) {
		
		super(AuxiliarConstantes.TITULO);
		
		this.setorRN = new SetorRN();
		this.stringSetor= stringSetor;
		this.lbEmailJanela = lbEmailJanela;
		
		FormLayout formLayout = new FormLayout(
				 "2dlu, 50px, 2dlu, 200px",
				 "2dlu, " +
				 "pref, "+
				 "2dlu, " +
				 "pref, " +
				 "2dlu, " +
				 "pref "
				 );

		setLayout(formLayout);
		
		CellConstraints celulas = new CellConstraints();
		
		lbSetor = new JLabel("Setor: ");
		lbSetor.setFont(new Font("Arial", Font.BOLD, 15));
		txSetor = new JTextField(30);
		txSetor.setText(stringSetor);
		txSetor.setEditable(false);
		
		lbEmail = new JLabel("Email: ");
		lbEmail.setFont(new Font("Arial", Font.BOLD, 15));
		txEmail = new JTextField(30);
	
		btInserir = new JButton("Cadastrar");
		btInserir.setFont(new Font("Arial", Font.BOLD, 15));
		btInserir.addActionListener(this);
		
		add(lbSetor, celulas.xy(2, 2));
		add(txSetor, celulas.xy(4, 2));
		add(lbEmail, celulas.xy(2, 4));
		add(txEmail, celulas.xy(4, 4));
		add(btInserir, celulas.xyw(2, 6, 3));
		
		try {
			Setor setorBusca = setorRN.buscar(stringSetor.trim());
			
			if (setorBusca != null) {
				jaExiste = true;
				txSetor.setText(setorBusca.getSetor());
				txEmail.setText(setorBusca.getEmail());
			}
			
			setSize(400, 200);
			addWindowListener(new ApresentacaoFechamentoJanela(this));
			setLocation(0, 0);
			setResizable(false);
			setVisible(true);
			
		} catch (DAOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

		}
		
	}
	
	public void getDados() {
		
		if (setor == null) {
			setor = new Setor();
		}
		
		setor.setSetor(txSetor.getText().trim());
		setor.setEmail(txEmail.getText());
		
	}

	public void actionPerformed(ActionEvent e) {
	
		try {
			
			getDados();
			
			if (txEmail.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "O email nao pode estar em branco!", "", JOptionPane.ERROR_MESSAGE);

			}
			else
			if (jaExiste = true) {
				setorRN.atualizar(setor);
				JOptionPane.showMessageDialog(null, "Email atualizado com sucesso!", "", JOptionPane.INFORMATION_MESSAGE);
				lbEmailJanela.setText(txEmail.getText());
				lbEmailJanela.updateUI();
			}
			else {
				setorRN.inserir(setor);
				JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!", "", JOptionPane.INFORMATION_MESSAGE);
			}
		} 
		catch (DAOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);

		}
	}
}
