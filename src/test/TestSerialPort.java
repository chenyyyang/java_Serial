package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import serialException.NoSuchPort;
import serialException.NotASerialPort;
import serialException.PortInUse;
import serialException.SerialPortParameterFailure;
import serialException.TooManyListeners;
import serialPort.SerialTool;

public class TestSerialPort {
	
	private List<String> commList = null;	//保存可用端口号
	private SerialPort serialPort = null;	//保存串口对象
	
	/**
	 * 类的构造方法
	 */
	public TestSerialPort() {
		commList = SerialTool.findPort();;	//保存可用端口号commList
		System.out.println("可用的端口为:"+commList.toString());
		
	}
	public void  open() {
	
		try {
			//获取指定端口名及波特率的串口对象
			serialPort = SerialTool.openPort(commList.get(0), 9600);
			//在该串口对象上添加监听器
			SerialTool.addListener(serialPort, new SerialListener());
			
			
		} catch (SerialPortParameterFailure e) {
			
			e.printStackTrace();
		} catch (NotASerialPort e) {
			
			e.printStackTrace();
		} catch (NoSuchPort e) {
		
			e.printStackTrace();
		} catch (PortInUse e) {
			
			e.printStackTrace();
		} catch (TooManyListeners e) {
			
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TestSerialPort testSerialPort=new TestSerialPort();
		testSerialPort.open();
	}
	
	
	/**
	 * 以内部类形式创建一个串口监听类
	 * @author zhong
	 *
	 */

	private class SerialListener implements SerialPortEventListener {
		
		/**
		 * 处理监控到的串口事件
		 */
	    public void serialEvent(SerialPortEvent serialPortEvent) {
	    	
	        switch (serialPortEvent.getEventType()) {

	            case SerialPortEvent.BI: // 10 通讯中断
	            	System.out.println("与串口设备通讯中断");
	            	break;

	            case SerialPortEvent.OE: // 7 溢位（溢出）错误

	            case SerialPortEvent.FE: // 9 帧错误

	            case SerialPortEvent.PE: // 8 奇偶校验错误

	            case SerialPortEvent.CD: // 6 载波检测

	            case SerialPortEvent.CTS: // 3 清除待发送数据

	            case SerialPortEvent.DSR: // 4 待发送数据准备好了

	            case SerialPortEvent.RI: // 5 振铃指示

	            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
	            	break;
	            
	            case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
	            	
	            	System.out.println("found data");
					byte[] data = null;
					
					try {
						if (serialPort == null) {
							System.out.println("串口对象为空！监听失败！");
						}
						else {
							data = SerialTool.readFromPort(serialPort);	//读取数据，存入字节数组
							//System.out.println(new String(data));
							
							//自定义解析过程
							if (data == null || data.length < 1) {	//检查数据是否读取正确	
								System.out.println( "读取数据过程中未获取到有效数据！请检查设备或程序！");
								System.exit(0);
							}
							else {
								String dataOriginal = new String(data);	//将字节数组数据转换位为保存了原始数据的字符串
								String dataValid = "";	//有效数据（用来保存原始数据字符串去除最开头*号以后的字符串）
								String[] elements = null;	//用来保存按空格拆分原始字符串后得到的字符串数组	
								System.out.println("收到了数据:"+dataOriginal);
								//把数据放进数据库
								String now=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
								Db Dbtool =new Db();
								Dbtool.insertTem(now, dataOriginal);
								
							}
							
						}						
						
					} catch (Exception e) {
						e.printStackTrace();
						System.exit(0);	//发生读取错误时显示错误信息后退出系统
					}	
		            
					break;

	        }

	    }

	}

}




