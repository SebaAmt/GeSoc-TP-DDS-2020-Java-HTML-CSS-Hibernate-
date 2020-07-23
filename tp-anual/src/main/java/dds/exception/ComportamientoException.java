package dds.exception;

public class ComportamientoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	  private String message;


	  public ComportamientoException(String message) {
	    super();
	    this.message = message;
	  }

	  public String getMessage() {
	    return message;
	  }
	
}
