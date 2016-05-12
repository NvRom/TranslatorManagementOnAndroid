package cn.cowis.modbus.exception;

public class TransferException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TransferException(){
	    super("数据传输错误");
	}
}
