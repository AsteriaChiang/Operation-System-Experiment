package ffcode;

import java.util.ArrayList;
import java.util.Scanner;

public class FirstFit {
	public static int memorySize = 128;// 内存大小
	public static ArrayList<Partition> partitions = new ArrayList<Partition>();
	public static ArrayList<Job> jobs = new ArrayList<Job>();

	// public static Memory memory =new Memory(memorySize);

	public FirstFit() {

	}

	// 为作业分配内存
	public static void allocateMemory(Job newjob) {

		for (Partition onepartition : partitions) {
			// 遍历空闲分区表找到大小合适的空闲分区
			if (onepartition.getPCBSize() >= newjob.getJobSize()) {
				// 完成作业信息并插入作业队列中
				newjob.setJobAddress(onepartition.getPCBAddress());
				jobs.add(newjob);
				// 若作业大小小于原空闲分区大小，则空闲分区块地址和长度改变
				if (onepartition.getPCBSize() != newjob.getJobSize()) {
					onepartition.setPCBAddress(onepartition.getPCBAddress() + newjob.getJobSize());
					onepartition.setPCBSize(onepartition.getPCBSize() - newjob.getJobSize());
				}
				// 若大小相等，则删去此空闲分区
				else {
					partitions.remove(onepartition.getPCBNumber());
					// 改变被删去空闲分区后的空闲分区分区号
					for (int i = onepartition.getPCBNumber(); i < partitions.size(); i++) {
						partitions.get(i + 1).setPCBNumber(i);
					}
				}
				return;
			}
		}
		System.out.print("----无合适的分区");
	}

	// 释放作业占有的内存
	public static void releaseMemory(Job deletejob) {
		// 将deletejob从jobs队列删去
		jobs.remove(deletejob);
		// 生成新的空闲分区delepatition
		Partition delepatition = new Partition();
		delepatition.setPCBAddress(deletejob.getJobAddress());
		delepatition.setPCBSize(deletejob.getJobSize());
		int indexnum = 0;
		// 找到新的空闲分区在表中的存储位置
		for (Partition onepartition : partitions) {
			if (onepartition.getPCBAddress() > delepatition.getPCBAddress()) {
				indexnum = onepartition.getPCBNumber();
				// partitions.add(delepatition.getPCBNumber(), delepatition);
				break;
			}
		}
		// 若delepatition要安在尾端
		if (indexnum == 0) {
			partitions.add(delepatition);
			delepatition.setPCBNumber(partitions.size());
		}
		// 若delepatition要安在首端
		if (indexnum == 1) {
			// 仅判断后
			if ((delepatition.getPCBAddress() + delepatition.getPCBSize() + 1) == partitions.get(indexnum - 1)
					.getPCBAddress()) {
				partitions.get(indexnum - 1).setPCBAddress(delepatition.getPCBAddress());
				partitions.get(indexnum - 1)
						.setPCBSize(delepatition.getPCBSize() + partitions.get(indexnum - 1).getPCBSize());
			}
			//将空闲分区插入
			else {
				delepatition.setPCBNumber(indexnum);
				partitions.add(indexnum-1, delepatition);
				for(int i=indexnum;i<partitions.size();i++) {
					partitions.get(i).setPCBNumber(i+1);
				}
			}
		}

		// 判断delepatition前后是否有空闲分区
		if (indexnum - 1 < partitions.size()&&indexnum>1) {
			// 后
			if ((delepatition.getPCBAddress() + delepatition.getPCBSize() + 1) == partitions.get(indexnum - 1)
					.getPCBAddress()) {
				partitions.get(indexnum - 1).setPCBAddress(delepatition.getPCBAddress());
				partitions.get(indexnum - 1)
						.setPCBSize(delepatition.getPCBSize() + partitions.get(indexnum - 1).getPCBSize());
			}
			// 前

			else if ((delepatition.getPCBAddress() - partitions.get(indexnum - 2).getPCBSize() - 1) == partitions
					.get(indexnum - 1).getPCBAddress()) {
				partitions.get(indexnum - 2)
						.setPCBSize(delepatition.getPCBSize() + partitions.get(indexnum - 2).getPCBSize());
			}
			// 前后
			else if ((delepatition.getPCBAddress() + delepatition.getPCBSize() + 1) == partitions.get(indexnum - 1)
					.getPCBAddress()
					&& (delepatition.getPCBAddress() - partitions.get(indexnum - 2).getPCBSize() - 1) == partitions
							.get(indexnum - 2).getPCBAddress()) {

				partitions.get(indexnum - 2).setPCBSize(delepatition.getPCBSize()
						+ partitions.get(indexnum - 2).getPCBSize() + partitions.get(indexnum - 1).getPCBSize());
				partitions.remove(indexnum - 1);
				// 改变空闲分区编号
				for (int j = indexnum - 1; j < partitions.size(); j++) {
					partitions.get(j).setPCBNumber(j + 1);
				}
			}
			else {
				delepatition.setPCBNumber(indexnum);
				partitions.add(indexnum-1, delepatition);
				for(int i=indexnum;i<partitions.size();i++) {
					partitions.get(i).setPCBNumber(i+1);
				}
			}
		}

	}

	// 显示空闲分区表
	public static void showPartition() {
		System.out.println("\n" + "——————展示空闲分区表——————");
		System.out.println("分区号" + "\t" + "起始地址" + "\t" + " 长度");
		for (Partition partition : partitions) {
			System.out.println(
					partition.getPCBNumber() + "\t" + partition.getPCBAddress() + "\t" + partition.getPCBSize());

		}

	}
	// 显示已分配作业
	public static void showJob() {
		System.out.println("\n" + "——————展示已分配内存的作业情况——————");
		System.out.println("作业编号" + "\t" + "存储地址" + "\t" + "长度");

		for (Job job : jobs) {
			System.out.println(job.getJobNumber() + "\t" + job.getJobAddress() + "\t" + job.getJobSize());

		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int firstfit = 1;
		int jobnum = 1;
		Scanner scanner = new Scanner(System.in);

		// 初始化空闲分区表
		Partition partition = new Partition();
		partition.setPCBAddress(1);
		partition.setPCBSize(memorySize);
		partition.setPCBNumber(1);
		partitions.add(partition);
		while (firstfit == 1) {
			int control = 1;
			System.out.println("若申请作业空间请输入1，释放作业空间输入0.");
			control = scanner.nextInt();
			// 释放作业空间
			if (control == 0) {
				showJob();
				System.out.println("请输入要释放的作业编号:");
				int delenum = scanner.nextInt();
				Job job = new Job(0);
				for (Job onejob : jobs) {
					if (onejob.getJobNumber() == delenum) {
						job = onejob;
					}
				}
				releaseMemory(job);
			}
			// 申请作业空间
			else if (control == 1) {
				System.out.println("请输入要申请的作业大小:");
				int jobsize = scanner.nextInt();
				Job newjob = new Job(jobnum);
				newjob.setJobSize(jobsize);
				allocateMemory(newjob);
				jobnum++;
			}
			System.out.println("若继续运行输入1，停止运行输入0.");
			firstfit = scanner.nextInt();
		}
		scanner.close();
		showPartition();
		showJob();

	}

}
