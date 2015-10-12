/**
 * 
 */
package com.creditcard.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
/**
 * @author arielb
 *
 */
public class CreditCardTransUtil {
	
	
	/**
	 * Produces  a list of hashed credit card number that have been identified as fraudelent. 
	 * From the given list of credit crad transaction
	 * A credit card will be identified as fraudulent if the sum of prices for a unique hashed credit card number,
	 * for a given day, exceeds the price threshold treshHoldPrice.
	 *
	 * @param transactionInputList  a list of comma separted string that holds the credit card transactions,
	 * 			                    a credit card transaction is comprised of the following elements;
	 *                              <ul>
     *	 			                <li>hashed credit card number </li>
     *				                <li>timestamp - of format yyyyy--MM-ddThh:mm:ss</li>
     *				                <li>price - of format dollars.cents</li>
     *                                 Transactions are to be received as a comma separated string of 
     *                                 elements example:
     *                                 '10d7ce2f43e35fa57d1bbf8b1e2, 2014-04-29T13:15:54, 10.00'
     *                              </ul>
	 * @param transDate             the given date ignoring time
	 * 
	 * @param treshHoldPrice        the threshhold price to identify fraud credit card transaction 
	 * 
	 * @return                      returns list of fraudelent credit card number as List of String           
	 */
	public static List<String> getCreditCardTransFraud(List<String> transactionInputList,Date transDate, double treshHoldPrice){

		UnaryOperator<String> groupbyCCNumerAndDate = (transListElement) -> {
			String[] strElement = transListElement.split(",");  //split each transaction list delimited by comma to extract information 
			String ccNum =strElement[0];                        //index 0  is the hashed credit card number
			String trDate =strElement[1].substring(0, 10);      // index 1 is trans date first 10 characters date in yyyy-MM-dd format  excluding  time
			return ccNum+","+trDate;                            //return groupingby as <cardNum>,<transaction for the day>; 
			                                                    //this will also serve as the key of the returned hashmap of the stream
		};    
		
		BiFunction<String, String, Boolean> filterByTransDate = (transListElement, filterDateString) -> {
			String[] strElement = transListElement.split(",");  //split each transaction list delimited by comma to extract information 
			String trDate =strElement[1].substring(0, 10);      // index 1 is trans date first 10 characters date in yyyy-MM-dd format  excluding  time
			return trDate.equals(filterDateString);             //return filter expression; to allow stream capture only list whose trans date equal to filter date
		};
			    
		Function <String,Double> getPrice = (transListElement) -> {	    
			         return Double.parseDouble(transListElement.toString().split(",")[2]);  //index 2 is the price ; this the field to be summed
	    };

		UnaryOperator<String> getCCNumber = (transMapElement) -> {
			String[] strElement = transMapElement.split(",");   //this is the key of the CCTransbyCCNumberDatettlPrice hashmap delimited by comma 
			String ccNum =strElement[0];                        //index 0  is the hashed credit card number
			return ccNum;                                        //returning credit card number
		};    
    
	    

       //Begin: Stream transactionInputList to create hashmap of credit card group by trans date and total price
	    	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd"); 
	    	String filterDateString = dateFormatter.format(transDate); // convert input filter date to string  in yyyy-MM-dd format
	    	Map<String, Double> CCTransbyCCNumberDatettlPrice =
				transactionInputList
			        .stream()
			        .filter((transListElement) -> { return filterByTransDate.apply(transListElement.toString(), filterDateString);})
			        .collect(
			        		Collectors.groupingBy(
			        	       (transListElement) -> {
			        	    	  return groupbyCCNumerAndDate.apply(transListElement.toString());
			        	        },
			            		Collectors.summingDouble(
			            		  (transListElement) -> {
			            			  return getPrice.apply(transListElement.toString());
			            		   }
			            		)
			        		 )                     
			         );   
	   //End            
	
	
	  //Begin: Stream CCTransbyCCNumberDatettlPrice above to create fraudelent list by filtering elements that exceeds tresh hold price 	
	  List<String> CCFraudelentList =
			(List<String>) CCTransbyCCNumberDatettlPrice.entrySet()
	    .stream()
	    .filter((transMapElement) -> { return transMapElement.getValue()>treshHoldPrice;})
	    .map((transMapElement) -> { return getCCNumber.apply(transMapElement.getKey());})
	    .collect(Collectors.toList());                     
	  //End	           
	  
	  Collections.sort(CCFraudelentList);  //sort ascending before returning list
	  return CCFraudelentList;
	}
}
