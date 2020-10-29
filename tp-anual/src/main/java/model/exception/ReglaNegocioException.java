package model.exception;

public class ReglaNegocioException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	  private String message;


	  public ReglaNegocioException(String message) {
	    super();
	    this.message = message;
	  }

	  public String getMessage() {
	    return message;
	  }
	
}
