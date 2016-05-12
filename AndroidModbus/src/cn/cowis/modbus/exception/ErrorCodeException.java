package cn.cowis.modbus.exception;

public class ErrorCodeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ErrorCodeException(int code){
		super(String.valueOf(code));
	}
}
