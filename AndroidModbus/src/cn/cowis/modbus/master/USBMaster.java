package cn.cowis.modbus.master;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PipedOutputStream;
import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.util.Log;
import cn.cowis.modbus.exception.TransferException;
import cn.cowis.modbus.master.Master.MontageReceiveMessage;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.util.SerialInputOutputManager;
import com.serotonin.util.queue.ByteQueue;

public class USBMaster implements Serializable,Master {

	private static final long serialVersionUID = 1L;
	
	private UsbSerialDriver usbSerialDriver = null;
	private SerialInputOutputManager serialInputOutputManager = null;
	private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
	private ByteQueue bytes=new ByteQueue();//接收到的数据
	private boolean isOnRunError=false;


	private int allWait=3000;
	
	
	SerialInputOutputManager.Listener listener = new SerialInputOutputManager.Listener() {

		@Override
		public void onNewData(byte[] inputValues) {
			
			USBMaster.this.bytes.push(inputValues);
		}

		@Override
		public void onRunError(Exception arg0) {
			isOnRunError=true;
		}
	};

	public USBMaster(UsbSerialDriver usbSerialDriver) {

		this.usbSerialDriver = usbSerialDriver;
	}

	@Override
	public byte[] readValue(int slaveId, int position, int num)throws Exception {
		byte [] getDatas=null;
		try {
		   serialInputOutputManager.writeAsync(MontageReceiveMessage.montageReader(slaveId, position, num));//发送报文
		

		   testReadBack(num);
		   
			if(isOnRunError){//传输错误
				isOnRunError=false;
				throw new TransferException();
				
			}
			getDatas=MontageReceiveMessage.receiveReader(bytes.popAll());
		}catch (Exception e) {
			
			throw new TransferException();
			
		} 
		
		return getDatas;
	}

	@Override
	public void writeValue(int slaveId, int position, Object obj) throws Exception {
		
		try {
		serialInputOutputManager.writeAsync(MontageReceiveMessage.montageWriter(slaveId, position, obj));//发送报文
		
		
			Thread.sleep(1000);
			if(isOnRunError){//传输错误
				isOnRunError=false;
				throw new TransferException();
			}
		 MontageReceiveMessage.receiveWriter(bytes.popAll());
		}catch (Exception e) {
			
			throw new TransferException();
			
		}
		
	}

	@Override
	public void destroy() {
		if(usbSerialDriver!=null){
		try {
			
			   usbSerialDriver.close();
			   usbSerialDriver=null;
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

	@Override
	public void open() throws IOException {
		
			usbSerialDriver.open();
			serialInputOutputManager = new SerialInputOutputManager(
					usbSerialDriver, listener);
			Log.i("open", "open my driver");
			 mExecutor.submit(serialInputOutputManager);

		

	}
	
	
	@Override
	public byte[] testSlaveId(int slaveId) {
		byte[] params =null;
		try {
			params =readValue(slaveId,1,1);
			return params;
		} catch (Exception e) {
			
			//return false;
		}//读寄存器 地址1,1个寄存器
		return params;
	}

	
	@Override
	public String toString(){
		
		if(usbSerialDriver==null){
			return null;
		}
		
		return usbSerialDriver.toString();
	}

	/**
	 * @param num
	 *        验证是否返回完整报文
	 * @throws IOException 
	 */
	public void testReadBack(int num) throws IOException {
		
		int i=0;
		int total;
		
		while(true){
			
			total=++i*300;
			try {
				Thread.sleep(300);//循环100毫秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if(total>=allWait)return;
			
			if((5+2*num)==bytes.peekAll().length){
				return;
			}
			
			
		}
		
	}
}
