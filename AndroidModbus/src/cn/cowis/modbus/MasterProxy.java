package cn.cowis.modbus;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import cn.cowis.modbus.master.Master;
import cn.cowis.modbus.tool.ConvertData;


/**
 * 代理类：{@link ModbusMaster}的代理，接口形式相同，但加入了同步控制，限制对 {@link ModbusMaster} 的并发调用。
 * 
 * @author shuang
 * 
 */
public class MasterProxy implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private  Master master;
	private static  MasterProxy masterProxy=null;
	byte[] params ;
	



	public MasterProxy(Master master) {
		this.master=master;
		
	}
	
	public static MasterProxy getInstance(Master master){
		
		if(masterProxy!=null){
			masterProxy.destroy();
			masterProxy=null;
		}
		masterProxy= new MasterProxy(master);
		
		return masterProxy;
		
	}
	

	
	public byte[] syncGetValue(int slaveId, int position,int num) throws Exception {
			
		System.out.println(this.toString());
	
		synchronized (this) {
			return master.readValue(slaveId, position, num);
		}
	}

	/**
	 * 用同步块封装master的setValue方法，<b>避免并发调用</b>
	 * 
	 * @param slaveId
	 * @param range
	 * @param offset
	 * @param dataType
	 * @param value
	 * @throws Exception 
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException
	 */
	public void syncSetValue(int slaveId,  int position, 
			Object value) throws Exception {
		
		
		
		synchronized (this) {
			
			master.writeValue(slaveId, position, value);
			
		}
	}

	/**
	 * 自动搜索 slaveId 从机地址
	 * @param min
	 * @param max
	 * @return
	 */
	public List<Integer> scanForSlaveNodes(int min, int max) {
		List<Integer> result = new ArrayList<Integer>();
		
		System.out.println(min+"==="+max);
		synchronized (master) {
			for (int i = min; i <= max; i++){
				params =master.testSlaveId(i);
				if (params!=null){
					result.add(Integer.valueOf(i));
					
				}
			}

		}
		return result;

	}
	
	public short getParams(){
		return ConvertData.toShort(params)[0];
	}
	
	/**
	 * 销毁master
	 */
	public void destroy() {
		master.destroy();
	}
	
	/**
	 * 打开 master
	 * @throws Exception 
	 */
	public void open() throws Exception{
		
		
		master.open();
		
	}


//	public boolean isCutSend() {
//		return cutSend;
//	}
	
	
}
