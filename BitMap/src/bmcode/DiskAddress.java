package bmcode;

public class DiskAddress {
	public int diskNum;//磁盘块号
	public int cylinderNum;//柱面号
	public int trackNum;//磁道号
	public int phyRecordNum;//物理记录号
	
	public DiskAddress(){
		
	}
	
	public void setDiskNum(int diskNum) {
		this.diskNum=diskNum;
	}
	
	public int getDiskNum() {
		return diskNum;
		
	}
	
	public void setdisk(int bit,int bitnum) {
		this.cylinderNum=bit;
		this.trackNum=bitnum/4;
		this.phyRecordNum=bitnum%4;
	}
	
	public int getCylinderNum() {
		return cylinderNum;
		
	}
	
	public int getTrackNum() {
		return trackNum;
	}
	
	public int getPhyRecordNum() {
		return phyRecordNum;
		
	}
	
}
