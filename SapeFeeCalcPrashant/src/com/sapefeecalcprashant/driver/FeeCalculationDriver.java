package com.sapefeecalcprashant.driver;

import java.io.File;

import com.sapefeecalcprashant.utility.SapFeeCalcPrashantConstant.FILETYPE;
import com.sapefeecalcprashant.reader.ITxnStrategy;
import com.sapefeecalcprashant.reader.TxnReaderUtility;

public class FeeCalculationDriver {
	public static void main(String[] args) {
		// Read CSV/TEXT/XML
		File transactionfile = new File(new File("").getAbsolutePath(), "resource/Sample_Input.csv");
		ITxnStrategy iTxnStrategy = TxnReaderUtility.getTrasactionReaderInstance().readFile(FILETYPE.CSV, transactionfile);
		iTxnStrategy.writeTxnReportInCSVFile();
	}
}
