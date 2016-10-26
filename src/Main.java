import java.awt.EventQueue;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import apresentacao.ApresentacaoAutenticacao;
import apresentacao.ApresentacaoConferencia;
import auxiliar.DAOException;
import auxiliar.JDBCUtil;

/**
 * 
 * @author Johanes Severo dos Santos Classe com a funcao Main chamada pelo
 *         interpretador.
 */
public class Main {

	public static void main(String[] args) throws Exception {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
					JDBCUtil.getInstance();
					new ApresentacaoConferencia();
					// new ApresentacaoAutenticacao();
				} catch (UnsupportedLookAndFeelException ex) {
					JOptionPane.showMessageDialog(null,
							"Erro ao iniciar o apicativo!", "Erro",
							JOptionPane.ERROR_MESSAGE);

				} catch (ClassNotFoundException e) {
					JOptionPane.showMessageDialog(null,
							"Erro ao iniciar o apicativo!", "Erro",
							JOptionPane.ERROR_MESSAGE);

				} catch (InstantiationException e) {
					JOptionPane.showMessageDialog(null,
							"Erro ao iniciar o apicativo!", "Erro",
							JOptionPane.ERROR_MESSAGE);

				} catch (IllegalAccessException e) {
					JOptionPane.showMessageDialog(null,
							"Erro ao iniciar o apicativo!", "Erro",
							JOptionPane.ERROR_MESSAGE);

				} catch (DAOException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(),
							"Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

	}
}
