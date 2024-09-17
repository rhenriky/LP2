package ifmt.cba.persistencia;

public class PersistenciaException extends Exception {

	private static final long serialVersionUID = 1L;

	public PersistenciaException(String string, Exception ex) {
		super("Erro ocorrido na manipulacao do banco de dados");
	}

	public PersistenciaException(String msg) {
		super(msg);
	}
}
