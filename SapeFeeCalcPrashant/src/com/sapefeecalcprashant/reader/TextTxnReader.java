package com.sapefeecalcprashant.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.sapefeecalcprashant.utility.SapFeeCalcPrashantConstant.FILETYPE;
import com.sapefeecalcprashant.model.Transaction;


public class TextTxnReader extends FeeCalculationLogic implements ITxnStrategy {
	@Override
	public void readTransaction(File transactionFile) {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(transactionFile));
			while ((line = br.readLine()) != null) {
				String[] transactionAttributes = line.split(cvsSplitBy);
				Transaction transaction = getTransaction(transactionAttributes); 
				saveTransaction(transaction);

			}		 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public ITxnStrategy readFile(FILETYPE fileType) {
		if(fileType == FILETYPE.TEXT){
			return TxnReaderUtility.getTrasactionReaderInstance().readTextFile();
		}
		return null;
	}
}
