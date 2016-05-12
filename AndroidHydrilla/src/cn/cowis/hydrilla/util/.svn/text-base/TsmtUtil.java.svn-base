package cn.cowis.hydrilla.util;

public class TsmtUtil {

	/**
	 * @param stopParity
	 * 
	 * @return
	 */
	public static int getStopPosition(int stopParity) {

		if (stopParity == 0 | stopParity == 2 || stopParity == 4) {
			return 0;
		} else {
			return 1;
		}

	}

	public static int getParityPosition(int stopParity) {

		if (stopParity == 0 || stopParity == 1) {
			return 0;
		} else if (stopParity == 4 || stopParity == 5) {
			return 1;
		} else if (stopParity == 2 || stopParity == 3) {

			return 2;
		}
		return 0;

	}

	public static int getBaudPosition(int baud) {

		if(baud==2){
			return 0;
		}else if(baud==3){
			
			return 1;
		}else if(baud==6){
			return 2;
		}else if(baud==7){
			return 3;
		}else if(baud==8){
			return 4;
		}
		return 0;

	}
	
	
	/**
	 * @param value
	 * @return
	 */
	public static byte getBaud(String value) {
		if ("115200".equals(value)) {
			return 0;

		} else if ("57600".equals(value)) {
			return 1;

		} else if ("38400".equals(value)) {
			return 2;

		} else if ("28800".equals(value)) {
			return 3;

		} else if ("19200".endsWith(value)) {
			return 4;

		} else if ("14400".equals(value)) {
			return 5;

		} else if ("9600".equals(value)) {
			return 6;

		} else if ("4800".equals(value)) {
			return 7;

		} else if ("2400".equals(value)) {
			return 8;

		} else if ("1200".equals(value)) {
			return 9;
		}
		return -1;
	}

	/**
	 * @param parity
	 * @param stopbit
	 * @return
	 */
	public static byte getParityStopbit(String parity, String stopbit) {
		if ("NONE".equals(parity) && "1".equals(stopbit)) {
			return 0;
		} else if ("NONE".equals(parity) && "2".equals(stopbit)) {
			return 1;
		} else if ("EVEN".equals(parity) && "1".equals(stopbit)) {
			return 2;
		} else if ("EVEN".equals(parity) && "2".equals(stopbit)) {
			return 3;
		} else if ("ODD".equals(parity) && "1".equals(stopbit)) {
			return 4;
		} else if ("ODD".equals(parity) && "2".equals(stopbit)) {
			return 5;
		}
		return -1;
	}
	

}
