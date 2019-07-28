package com.sapefeecalcprashant.reader;

import java.io.File;

import com.sapefeecalcprashant.utility.SapFeeCalcPrashantConstant.FILETYPE;


public class XMLTxnReader extends FeeCalculationLogic implements ITxnStrategy {

	
	@Override
	public void readTransaction(File transactionFile) {
		//TODO need to be implemented
	}

	@Override
	public ITxnStrategy readFile(FILETYPE fileType) {
		if(fileType == FILETYPE.XML){
			return TxnReaderUtility.getTrasactionReaderInstance().readXmlFile();
		}
		return null;
	}

}
