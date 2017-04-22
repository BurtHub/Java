package com.huida.bank;

import java.util.*;

public class NumberManager {
	private ArrayList<Integer> queueNubmer=new ArrayList<Integer>();
	private int  lastNumber=1;//��¼����������˼�����
	
	public synchronized int generaterNubmer(){
		queueNubmer.add(lastNumber++);
		return lastNumber-1;
	}
	public synchronized Integer getNumber(){
		if(queueNubmer.size()>0){
			Integer num=queueNubmer.remove(0);
			return num;
			}
		else
			return null;
	}
}
