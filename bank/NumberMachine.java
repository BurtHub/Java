package com.huida.bank;

public class NumberMachine {
	//��ͨ�ͻ����������
	private NumberManager commonManager=new NumberManager();
	//���ٿͻ����������
	private NumberManager fastManager=new NumberManager();
	//VIP�ͻ����������
	private NumberManager vipManager=new NumberManager();
	private NumberMachine(){}
	private static NumberMachine numberMachine=new NumberMachine();
	public static NumberMachine getnumberMachine(){
		return numberMachine;
	}
	public NumberManager getcommonManager(){
		return commonManager;
	}
	public NumberManager getfastManager(){
		return fastManager;
	}
	public NumberManager getvipManager(){
		return vipManager;
	}
}
