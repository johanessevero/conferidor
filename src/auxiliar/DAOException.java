package auxiliar;

/**
 * Classe de exceção padrão para a camada de persistencia
 * @author john
 *
 */
public class DAOException extends Exception {

	public DAOException() {
		// TODO Auto-generated constructor stub
	}

	public DAOException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DAOException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public DAOException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}