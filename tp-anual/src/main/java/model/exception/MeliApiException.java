package model.exception;

public class MeliApiException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String message;


  public MeliApiException(String message) {
    super();
    this.message = message;
  }

  public String getMessage() {
    return message;
  }


}