package com.sapefeecalcprashant.utility;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sapefeecalcprashant.utility.SapFeeCalcPrashantConstant.TRANSACTIONTYPE;

public class SapeFeeCalcPrashantUtils {

	public static Double parseMarketValue(String marketValue) {
		try{
			return Double.parseDouble(marketValue);
		}catch(Exception ex){
			return (double) 0;
		}
	}

	public static Boolean getPriority(String priority) {
		if(priority!= null){
			priority = priority.trim();
			if(priority.equals("Y")||priority.equals("y")){
				return true;
			} else {
				return false;
			}
		}else{
			return false;
		}
	}

	public static Date parseDate(String date) {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date convertedDate = sdf.parse(date);
			return convertedDate;
		}catch(Exception  ex){
			return null;
		}
	}
	
	public static String parseDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(date);
	}

	public static Integer parseTransactionType(String type) {
		if(type.equals("BUY")){
			return TRANSACTIONTYPE.BUY.getType();
		}if(type.equals("SELL")){
			return TRANSACTIONTYPE.SELL.getType();
		}if(type.equals("DEPOSIT")){
			return TRANSACTIONTYPE.DEPOSIT.getType();
		}if(type.equals("WITHDRAW")){
			return TRANSACTIONTYPE.WITHDRAW.getType();
		}
		return null;}

	public static String getTypeName(Integer transactionType) {
		if(transactionType == TRANSACTIONTYPE.BUY.getType()){
			return TRANSACTIONTYPE.BUY.getName()+"\t";
		} else if(transactionType == TRANSACTIONTYPE.SELL.getType()){
			return TRANSACTIONTYPE.SELL.getName()+"\t";
		} else if(transactionType == TRANSACTIONTYPE.DEPOSIT.getType()){
			return TRANSACTIONTYPE.DEPOSIT.getName();
		} else if(transactionType == TRANSACTIONTYPE.WITHDRAW.getType()){
			return TRANSACTIONTYPE.WITHDRAW.getName();
		}
		return null;
	}
	
	public static void writeLine(Writer w, List<String> values, char seperator) {
		try {
			boolean first = true;
			StringBuilder sb = new StringBuilder();
			for (String value : values) {
				if (!first) {
					sb.append(seperator);
				}
				sb.append(value);
				first = false;
			}
			sb.append("\n");
			w.append(sb.toString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
