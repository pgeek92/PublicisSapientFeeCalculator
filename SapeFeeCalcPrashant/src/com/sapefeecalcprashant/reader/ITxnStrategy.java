package com.sapefeecalcprashant.reader;

import com.sapefeecalcprashant.utility.SapFeeCalcPrashantConstant.FILETYPE;

/**
 * 
 * @author Prashant Gupta
 *
 */
public interface ITxnStrategy {

	public void writeTxnReportInCSVFile();

	public ITxnStrategy readFile(FILETYPE fileType);
}
