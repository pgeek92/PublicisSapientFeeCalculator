package com.sapefeecalcprashant.reader;

import java.io.File;

import com.sapefeecalcprashant.utility.SapFeeCalcPrashantConstant.FILETYPE;

public class TxnReaderUtility {

	private static TxnReaderUtility txnReaderUtility;
	private static CSVTxnReader csvTxnReader;
	private static XMLTxnReader xmlTxnReader;
	private static TextTxnReader textTxnReader;

	public static TxnReaderUtility getTrasactionReaderInstance() {
		if (null == txnReaderUtility) {
			synchronized (TxnReaderUtility.class){
				if (null == txnReaderUtility) {
					txnReaderUtility = new TxnReaderUtility();
					csvTxnReader= new CSVTxnReader();
					xmlTxnReader = new XMLTxnReader();
					textTxnReader = new TextTxnReader();
				}
			}
		}
		return txnReaderUtility;
	}

	public CSVTxnReader readExcelFile(){
		return csvTxnReader;
	}

	public XMLTxnReader readXmlFile(){
		return xmlTxnReader;
	}

	public TextTxnReader readTextFile(){
		return textTxnReader;
	}

	public ITxnStrategy readFile(FILETYPE fileType, File transactionFile) {
		switch (fileType) {
		case CSV:
			csvTxnReader.readTransaction(transactionFile);
			return csvTxnReader;
		case TEXT:
			textTxnReader.readTransaction(transactionFile);
			return textTxnReader;
		case XML:
			xmlTxnReader.readTransaction(transactionFile);
			return xmlTxnReader;
		default:
			return null;
		}
	}
}
