package com.emflant.accounting.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emflant.accounting.screen.component.EntJButton;
import com.emflant.accounting.screen.component.EntJComboBox;
import com.emflant.accounting.screen.component.EntJTable;
import com.emflant.accounting.screen.component.EntJTextFieldForAmount;
import com.emflant.accounting.screen.component.EntJTextFieldForDate;
import com.emflant.accounting.screen.component.EntJTextFieldForRemarks;
import com.emflant.accounting.service.A01Search;

@Component
public class A01RegisterAccount implements EntScreen {
	
	private static final Logger logger = Logger.getLogger(A01RegisterAccount.class);
	
	@Autowired
	private JFrame mainFrame;
	@Autowired
	private A01Search a01Search;

	private JPanel centerPanel;
	private JPanel southPanel;
	private EntJTextFieldForRemarks tfRemarks;
	private EntJComboBox cbAccountType;
	private JLabel lbAmount;
	private EntJTextFieldForAmount tfAmount;
	private JLabel lbCashAmount;
	private EntJTextFieldForAmount tfCashAmount;
	
	private JLabel lbNewDate;
	private EntJTextFieldForDate tfNewDate;
	
	private EntJButton btnInsert;
	private EntJTable tbAccountList;
	
	private String testwe;
	
	
	public A01RegisterAccount(){
		
		this.tfRemarks = new EntJTextFieldForRemarks();
		this.cbAccountType = new EntJComboBox();
		this.lbAmount = new JLabel("금액");
		this.tfAmount = new EntJTextFieldForAmount(7);
		this.lbCashAmount = new JLabel("현금");
		this.tfCashAmount = new EntJTextFieldForAmount(7);
		this.lbNewDate = new JLabel("신규일자");
		this.tfNewDate = new EntJTextFieldForDate();
		this.btnInsert = new EntJButton("등록");
		
		this.tbAccountList = new EntJTable();
		this.tbAccountList.entAddTableHeader("account_type_nm", "계좌유형", JLabel.CENTER, 50);
		this.tbAccountList.entAddTableHeader("remarks", "적요", JLabel.LEFT, 300);
		this.tbAccountList.entAddTableHeader("format_new_date", "신규일자", JLabel.CENTER, 80);
		this.tbAccountList.entAddTableHeader("account_status_nm", "상태", JLabel.CENTER, 50);
		this.tbAccountList.entAddTableHeader("format_balance", "잔액", JLabel.RIGHT, 100);
		
		this.centerPanel = new JPanel();
		this.centerPanel.setBackground(Color.WHITE);
		this.centerPanel.setLayout(new BorderLayout());
		
		this.southPanel = new JPanel();
		this.southPanel.setBackground(Color.WHITE);
		this.southPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		this.southPanel.add(cbAccountType);
		tfRemarks.addPanel(this.southPanel);
		this.southPanel.add(lbNewDate);
		tfNewDate.addPanel(this.southPanel);
		this.southPanel.add(lbAmount);
		tfAmount.addPanel(this.southPanel);
		
		this.southPanel.add(lbCashAmount);
		tfCashAmount.addPanel(this.southPanel);
		btnInsert.addPanel(this.southPanel);
		
		this.centerPanel.add(BorderLayout.CENTER, new JScrollPane(tbAccountList));
		
	}

	
	public void initScreen()
	{
		this.mainFrame.getContentPane().add(BorderLayout.SOUTH, this.southPanel);
		this.mainFrame.getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		search();
	}
	
	
	public void search(){
		List result = this.a01Search.getAccountsByUserId("emflant");
		
		DefaultTableModel tm = convert(result);
		
		this.tbAccountList.entSetTableModel(tm);
	}
	
	public String display() {
		// TODO Auto-generated method stub
		return "A01RegisterAccount screen.";
	}

	
	
	public DefaultTableModel convert(List list){
		
		DefaultTableModel tmResult = new DefaultTableModel();
		
		if(list.isEmpty()){
			return tmResult;
		}
		
		HashMap hm = (HashMap)list.get(0);
		
		int nColumnCnt = hm.size();
		String[] columns = new String[nColumnCnt];
		
		Iterator<String> iter = hm.keySet().iterator();
		
		int k=0;
		
		while(iter.hasNext()){
			String key = iter.next();
			columns[k] = key;
			k++;
		}

		tmResult.setColumnIdentifiers(columns);
		
		Object[] row = null;
		
	    for(int i=0;i<list.size();i++){
	    	
	    	hm = (HashMap)list.get(i);
	    	
	    	row = new String[nColumnCnt];

	    	for(int j=0;j<nColumnCnt;j++){
	    		
	    		row[j] = ""+hm.get(columns[j]);
	    	}
	    	
	    	tmResult.addRow(row);
	    	
	    }
		
		return tmResult;
	}
}
