package com.big.web.b2b_big2.finance.h2h.offline;

import java.util.ArrayList;
import java.util.List;

import com.big.web.b2b_big2.finance.h2h.offline.pojo.Billing;
import com.big.web.b2b_big2.finance.h2h.offline.pojo.Reconcile;
import com.big.web.b2b_big2.util.Utils;

public class Action {
	public static void generateBilling(){
		Billing billing = new Billing();
		billing.setAccountNo("123AB");
		
		List<Billing> list = new ArrayList<Billing>(); 
	}
	
	public static void generateReconcile(){
		
		List<Reconcile> list = new ArrayList<Reconcile>();
		
		Reconcile rec = new Reconcile();
		list.add(rec);
		
//		Utils.listToFile(list, file);
	}
	
	public static void main(String[] args) {
		Object obj = "Eric";
		
		System.out.println(obj.toString());
	}
}
