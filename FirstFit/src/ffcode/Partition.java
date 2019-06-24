package ffcode;

public class Partition {

	public int pcbNumber;//分区号
	public int pcbSize;//分区大小
	public int pcbAddress;//长度起始地址
	
	public Partition() {
		
	}
	
	public void setPCBNumber(int pcbNumber) {
		this.pcbNumber=pcbNumber;
	}
	
	public int getPCBNumber() {
		return pcbNumber;
		
	}
	
	public void setPCBAddress(int pcbAddress) {
		this.pcbAddress=pcbAddress;
	}
	
	public int getPCBAddress() {
		return pcbAddress;
		
	}
	
	public void setPCBSize(int pcbSize) {
		this.pcbSize=pcbSize;
	}
	
	public int getPCBSize() {
		return pcbSize;
		
	}
	
}
