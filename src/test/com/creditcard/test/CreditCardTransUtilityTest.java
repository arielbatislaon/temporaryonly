package com.creditcard.test;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.creditcard.*;
import com.creditcard.util.CreditCardTransUtil;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreditCardTransUtilityTest {
	List<String> transactionInputList = null;
	Date dateInput1 = null ;
	Date dateInput2 = null ;
	Date dateInput3 = null ;
	Date dateInput4 = null ;
	Date dateInput5 = null ;
	Date dateInput6 = null ;
	double priceTreshHold1 =0.0;
	double priceTreshHold2 =0.0;
	double priceTreshHold3 =0.0;
	
	@Before
	public void setUp() throws Exception {
		
		//Begin: Setup transaction list test data for each test
        	transactionInputList = new ArrayList<String>();
        	transactionInputList.add("cc1,2015-10-05T00:01:12,50.00");
        	transactionInputList.add("cc1,2015-10-05T00:01:13,50.00");
        	transactionInputList.add("cc6,2015-10-05T00:01:14,50.00");
		
        	transactionInputList.add("cc1,2015-10-06T00:01:14,50.00");
        	transactionInputList.add("cc1,2015-10-06T00:01:15,50.00");
        	transactionInputList.add("cc5,2015-10-06T00:01:13,50.00");
		
        	transactionInputList.add("cc2,2015-10-07T00:01:12,50.00");
        	transactionInputList.add("cc2,2015-10-07T00:01:13,50.00");
        	transactionInputList.add("cc2,2015-10-07T00:02:14,50.00");
        	transactionInputList.add("cc2,2015-10-07T00:03:10,50.00");
        	transactionInputList.add("cc4,2015-10-07T00:01:15,50.00");
		
        	transactionInputList.add("cc3,2015-10-08T00:01:14,50.00");
        	transactionInputList.add("cc3,2015-10-08T00:01:15,50.00");
        	transactionInputList.add("cc5,2015-10-08T00:01:12,50.00");
		
        	transactionInputList.add("cc1,2015-10-09T00:01:15,50.00");
        	transactionInputList.add("cc3,2015-10-09T00:01:12,50.00");
		
        	transactionInputList.add("cc4,2015-10-10T00:01:13,50.00");
        	transactionInputList.add("cc4,2015-10-10T00:01:14,50.00");
        	transactionInputList.add("cc5,2015-10-10T00:01:15,50.00");
        	transactionInputList.add("cc5,2015-10-10T00:03:15,50.00");
        	transactionInputList.add("cc5,2015-10-10T00:04:15,50.00");
        	transactionInputList.add("cc5,2015-10-10T00:05:15,50.00");
		
	        //price treshhold test data
        	priceTreshHold1 =40.0;
        	priceTreshHold2 =90.0;
        	priceTreshHold3 =150.0;
		
		
        	SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd"); 
		
        	try {
        		//Date input test data
        		dateInput1 = f1.parse("2015-10-05");
        		dateInput2 = f1.parse("2015-10-06");
        		dateInput3 = f1.parse("2015-10-07");
        		dateInput4 = f1.parse("2015-10-08");
        		dateInput5 = f1.parse("2015-10-09");
        		dateInput6 = f1.parse("2015-10-10");
        	} catch (ParseException e) {
        		e.printStackTrace();
        	}
		//End
		
		
	}

	

	@Test
	public final void shoudReturnEmptyListWhenInputListIsEmpty() {
		transactionInputList = new ArrayList<String>();
		
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput1, 100.0).isEmpty());
	}

	@Test
	public final void shoudReturnEmptyListWhenDateIsNotFound() {
		Date transDate = new Date();
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, transDate, 100.0).isEmpty());
	}
	
	@Test
	public final void shouldPassWithDateInput1andpriceTreshHold1TestData() {
	    List<String> expected = new ArrayList<String>();
	    expected.add("cc1");expected.add("cc6");
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput1, priceTreshHold1).equals(expected));
	}
	
	
	@Test
	public final void shouldPassWithDateInput1andpriceTreshHold2TestData() {
	    List<String> expected = new ArrayList<String>();
	    expected.add("cc1");
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput1, priceTreshHold2).equals(expected));
	}
	
	
	@Test
	public final void shouldPassWithDateInput1andpriceTreshHold3TestData() {
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput1, priceTreshHold3).isEmpty());
	}
	
		
	
	@Test
	public final void shouldPassWithDateInput2andpriceTreshHold1TestData() {
	    List<String> expected = new ArrayList<String>();
	    expected.add("cc1");expected.add("cc5");
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput2, priceTreshHold1).equals(expected));
	}
	
	
	@Test
	public final void shouldPassWithDateInput2andpriceTreshHold2TestData() {
	    List<String> expected = new ArrayList<String>();
	    expected.add("cc1");
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput2, priceTreshHold2).equals(expected));
		
	}
	
	
	@Test
	public final void shouldPassWithDateInput2andpriceTreshHold3TestData() {
	    
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput2, priceTreshHold3).isEmpty());
	}
	
	
	@Test
	public final void shouldPassWithDateInput3andpriceTreshHold1TestData() {
	    List<String> expected = new ArrayList<String>();
	    expected.add("cc2");expected.add("cc4");
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput3, priceTreshHold1).equals(expected));
	}
	
	
	@Test
	public final void shouldPassWithDateInput3andpriceTreshHold2TestData() {
	    List<String> expected = new ArrayList<String>();
	    expected.add("cc2");
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput3, priceTreshHold2).equals(expected));
	}
	
	
	@Test
	public final void shouldPassWithDateInput3andpriceTreshHold3TestData() {
	    List<String> expected = new ArrayList<String>();
	    expected.add("cc2");
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput3, priceTreshHold3).equals(expected));
	}
	
	
	@Test
	public final void shouldPassWithDateInput4andpriceTreshHold1TestData() {
	    List<String> expected = new ArrayList<String>();
	    expected.add("cc3");expected.add("cc5");
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput4, priceTreshHold1).equals(expected));
	}
	
	
	@Test
	public final void shouldPassWithDateInput4andpriceTreshHold2TestData() {
	    List<String> expected = new ArrayList<String>();
	    expected.add("cc3");
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput4, priceTreshHold2).equals(expected));
	}
	
	
	@Test
	public final void shouldPassWithDateInput4andpriceTreshHold3TestData() {
	    
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput4, priceTreshHold3).isEmpty());
	}
	
	@Test
	public final void shouldPassWithDateInput5andpriceTreshHold1TestData() {
	    List<String> expected = new ArrayList<String>();
	    expected.add("cc1");expected.add("cc3");
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput5, priceTreshHold1).equals(expected));
	}
	
	
	@Test
	public final void shouldPassWithDateInput5andpriceTreshHold2TestData() {
	    
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput5, priceTreshHold2).isEmpty());
	}
	
	
	@Test
	public final void shouldPassWithDateInput5andpriceTreshHold3TestData() {
	    
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput5, priceTreshHold3).isEmpty());
	}
	
	
	@Test
	public final void shouldPassWithDateInput6andpriceTreshHold1TestData() {
	    List<String> expected = new ArrayList<String>();
	    expected.add("cc4");expected.add("cc5");
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput6, priceTreshHold1).equals(expected));
	}
	
	
	@Test
	public final void shouldPassWithDateInput6andpriceTreshHold2TestData() {
	    List<String> expected = new ArrayList<String>();
	    expected.add("cc4");expected.add("cc5");
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput6, priceTreshHold2).equals(expected));
	}
	
	
	@Test
	public final void shouldPassWithDateInput6andpriceTreshHold3TestData() {
	    List<String> expected = new ArrayList<String>();
	    expected.add("cc5");
		assertTrue(CreditCardTransUtil.getCreditCardTransFraud(transactionInputList, dateInput6, priceTreshHold3).equals(expected));
	}
	
}
