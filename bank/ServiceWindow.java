package com.huida.bank;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceWindow {
	private Type type=Type.common;
	private int id=1;//����
	Integer commonnum=null;//��ͨ�û����
	Integer fastnum=null;//�����û����
	Integer vipnum=null;//vip�û����
	public void setType(Type type){
		this.type=type;
	}
	public void setid(int id){
		this.id=id;
	}
	public void start(){
		ExecutorService pool=Executors.newSingleThreadExecutor();
		pool.execute(new Runnable(){
			public void run(){
				while(true){
				switch(type){
				case common:
					common();
					break;
				case fast:
					fast();
					break;
				case vip:
					vip();
					break;
				} 
				}
			}
		});
	}
	//���崦����ͨ�ͻ��߼�����
	public void common(){
		commonnum=NumberMachine.getnumberMachine().getcommonManager().getNumber();
		if(commonnum!=null){
			System.out.println("��"+id+"��"+type+"����׼��Ϊ"+commonnum+"�ͻ�����");
		long startTime=System.currentTimeMillis();
		int time=Time.MAX_TIME-Time.MIN_TIME+1;
		int randTime=new Random().nextInt(time)+Time.MIN_TIME;//nextInt()0-9֮��������
		try {
			Thread.sleep(randTime*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime=System.currentTimeMillis();
		long serviceTime=endTime-startTime;
		System.out.println("��"+id+"��"+type+"����Ϊ"+commonnum+"�ͻ�����"+(serviceTime/1000)+"��");
		}else{
			System.out.println("û����ͨ�ͻ�,����Ϣһ��");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void fast(){
		fastnum=NumberMachine.getnumberMachine().getfastManager().getNumber();
		if(fastnum!=null){
			System.out.println("��"+id+"��"+type+"����׼��Ϊ"+fastnum+"�ͻ�����");
		long startTime=System.currentTimeMillis();
		try {
			Thread.sleep(Time.MIN_TIME*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime=System.currentTimeMillis();
		long serviceTime=endTime-startTime;
		System.out.println("��"+id+"��"+type+"����Ϊ"+fastnum+"�ͻ�����"+(serviceTime/1000)+"��");
		}else{
			System.out.println("û�п��ٿͻ���Ҫ׼��Ϊ��ͨ�ͻ�����");
			common();
		}		
	}
	public void vip(){
		vipnum=NumberMachine.getnumberMachine().getvipManager().getNumber();
		if(vipnum!=null){
			System.out.println("��"+id+"��"+type+"����׼��Ϊ"+vipnum+"�ͻ�����");
		long startTime=System.currentTimeMillis();
		int time=Time.MAX_TIME-Time.MIN_TIME+1;
		int randTime=new Random().nextInt(time)+1+Time.MIN_TIME;
		try {
			Thread.sleep(randTime*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime=System.currentTimeMillis();
		long serviceTime=endTime-startTime;
		System.out.println("��"+id+"��"+type+"����Ϊ"+vipnum+"�ͻ�����"+(serviceTime/1000)+"��");
	}else{
		System.out.println("û��vip�ͻ���Ҫ׼��Ϊ��ͨ�ͻ�����");
		common();
		}
	}
}
