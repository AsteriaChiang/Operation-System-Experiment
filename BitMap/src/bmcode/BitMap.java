package bmcode;

import java.util.ArrayList;
import java.util.Scanner;

public class BitMap {
	public static int cylinder = 8;
	public static int track = 2;
	public static int phyRecord = 4;
	public static ArrayList<DiskAddress> disks = new ArrayList<DiskAddress>();

	//初始化位示图
	public static void initeBitMap(int bitmap[][]) {
		for (int i = 0; i < cylinder; i++) {
			for (int j = 0; j < track * phyRecord; j++) {
				bitmap[i][j] = 0;
			}
		}
	}

	// 申请磁盘空间
	public static void allocBit(int bitmap[][]) {
		for (int i = 0; i < cylinder; i++) {
			for (int j = 0; j < track * phyRecord; j++) {
				if (bitmap[i][j] == 0) {
					bitmap[i][j] = 1;
					// 新建磁盘块
					DiskAddress newdisk = new DiskAddress();
					newdisk.setdisk(i, j);
					newdisk.setDiskNum(disks.size() + 1);
					disks.add(newdisk);
					// 把分配到的磁盘空间的物理地址显示或打印出来
					System.out.println("编号:" + newdisk.getDiskNum() + "\t" + 
							"柱面号 " + newdisk.getCylinderNum() + "\t"
							+ "磁道号:" + newdisk.getTrackNum() + "\t" 
							+ "物理记录号:" + newdisk.getPhyRecordNum());
					return;
				}
			}
		}
		System.out.println("------无空闲磁盘空间");
	}

	// 归还磁盘空间
	public static void recovBit(int bitmap[][], int recovnum) {
		// 修改位示图
		DiskAddress recovdisk = disks.get(recovnum - 1);
		bitmap[recovdisk.getCylinderNum()][recovdisk.getTrackNum() * 4 
		                                   + recovdisk.getPhyRecordNum()] = 0;

		// 将归还的磁盘从磁盘队列中移走
		disks.remove(recovdisk);
		for (int i = recovnum - 1; i < disks.size(); i++) {
			disks.get(i).setDiskNum(i + 1);
		}

		// 把归还块对应于位示图的字节号和位数显示或打印出来
		System.out.println("字节号:" + recovdisk.getCylinderNum() + "\t" 
		+ "位数:" + recovdisk.getTrackNum() * 4
				+ recovdisk.getPhyRecordNum());
	}

	// 展示已分配的磁盘空间
	public static void showDiskArray() {
		System.out.println("------展示磁盘空间");
		System.out.println("编号" + "\t" + "柱面号" + "\t" + "磁道号" + "\t" + "物理记录号");
		for (DiskAddress disk : disks) {
			System.out.println(disk.getDiskNum() + "\t" + disk.getCylinderNum() 
			+ "\t" + disk.getTrackNum() + "\t" + disk.getPhyRecordNum());

		}
	}

	// 展示位示图
	public static void showBitMap(int bitmap[][]) {
		System.out.println("------展示位示图");
		for (int i = 0; i < cylinder; i++) {
			for (int j = 0; j < track * phyRecord; j++) {
				System.out.print(bitmap[i][j] + "\t");
			}
			System.out.print("\n");
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		int control = 1;// 判断符号

		int bitmap[][] = new int[cylinder][track * phyRecord];
		initeBitMap(bitmap);
		showBitMap(bitmap);
		while (control == 1) {
			System.out.println("——————申请磁盘空间输入1，回收磁盘空间输入0");
			int decide = scanner.nextInt();// 判断符号
			if (decide == 1) {
				allocBit(bitmap);
			} else {
				showDiskArray();
				System.out.println("——————输入想释放的磁盘号");
				int recovnum = scanner.nextInt();// 判断符号
				recovBit(bitmap, recovnum);
			}
			System.out.println("——————继续输入1，停止输入0");
			control=scanner.nextInt();
		}
		showBitMap(bitmap);
		scanner.close();
	}

}
