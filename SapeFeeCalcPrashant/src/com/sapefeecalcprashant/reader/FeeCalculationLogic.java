package com.sapefeecalcprashant.reader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sapefeecalcprashant.utility.SapFeeCalcPrashantConstant.TRANSACTIONFEES;
import com.sapefeecalcprashant.utility.SapFeeCalcPrashantConstant.TRANSACTIONTYPE;
import com.sapefeecalcprashant.model.Transaction;
import com.sapefeecalcprashant.utility.SapeFeeCalcPrashantUtils;

/**
 * 
 * @author Prashant Gupta
 *
 */
public abstract class FeeCalculationLogic {

	public List<Transaction> transactionList;

	public abstract void readTransaction(File transactionFile);

	/**
	 * This method will save the transaction into list. later on in database.
	 * 
	 * @param transaction
	 */
	public void saveTransaction(Transaction transaction) {

		if (transactionList == null) {
			transactionList = new ArrayList<Transaction>();
		}

		transactionList.add(calculateTransactionFee(transaction));
	}

	/**
	 * This method will update the fee according to transaction.
	 * 
	 * @param transaction
	 * @return 
	 */
	private Transaction calculateTransactionFee(Transaction transaction) {
		if (isIntradayTransaction(transaction)) {
			transaction.setTransactionFees(TRANSACTIONFEES.TEN.getFees());
		} else {

			if (transaction.getPriority()) {
				transaction.setTransactionFees(TRANSACTIONFEES.FIVE_HUNDREAD.getFees());
			} else {
				if (transaction.getTransactionType() == TRANSACTIONTYPE.SELL.getType()
						|| transaction.getTransactionType() == TRANSACTIONTYPE.WITHDRAW.getType()) {
					transaction.setTransactionFees(TRANSACTIONFEES.HUNDREAD.getFees());
				} else if (transaction.getTransactionType() == TRANSACTIONTYPE.BUY.getType()
						|| transaction.getTransactionType() == TRANSACTIONTYPE.DEPOSIT.getType()) {
					transaction.setTransactionFees(TRANSACTIONFEES.FIFTY.getFees());
				}
			}
		}
		return transaction;
	}

	/**
	 * This method checks whether transaction is IntraDay or not.
	 * 
	 * @param transaction
	 * @return
	 */
	private boolean isIntradayTransaction(Transaction transaction) {
		boolean isIntraDayTransaction = false;
		Transaction temp = null;
		if (transactionList.size() > 0) {
			for (Transaction trans : transactionList) {
				if (trans.getClientId().equals(transaction.getClientId())
						&& trans.getSecurityId().equals(transaction.getSecurityId())
						&& trans.getTransactionDate().equals(transaction.getTransactionDate())) {
					if ((trans.getTransactionType() == TRANSACTIONTYPE.BUY.getType()
							&& transaction.getTransactionType() == TRANSACTIONTYPE.SELL.getType())
							|| (trans.getTransactionType() == TRANSACTIONTYPE.SELL.getType()
									&& transaction.getTransactionType() == TRANSACTIONTYPE.BUY.getType())) {
						isIntraDayTransaction = true;
						temp = trans;
						break;
					}
				}
			}

			if (temp != null) {
				transactionList.remove(temp);
				temp.setTransactionFees(TRANSACTIONFEES.TEN.getFees());
				transactionList.add(temp);
			}

		} else {
			isIntraDayTransaction = false;
		}
		return isIntraDayTransaction;
	}

	/**
	 * 
	 * @param transactionAttributes
	 * @return
	 */
	public Transaction getTransaction(String[] transactionAttributes) {
		Transaction transaction = new Transaction();
		transaction.setExternalTransactionID(transactionAttributes[0]);
		transaction.setClientId(transactionAttributes[1]);
		transaction.setSecurityId(transactionAttributes[2]);
		transaction.setTransactionType(SapeFeeCalcPrashantUtils.parseTransactionType(transactionAttributes[3]));
		transaction.setTransactionDate(SapeFeeCalcPrashantUtils.parseDate(transactionAttributes[4]));
		transaction.setMarketValue(SapeFeeCalcPrashantUtils.parseMarketValue(transactionAttributes[5]));
		transaction.setPriority(SapeFeeCalcPrashantUtils.getPriority(transactionAttributes[6]));
		return transaction;
	}

	/**
	 * This method will write required data in CSV file
	 */
	public void writeTxnReportInCSVFile() {
		char SEPERATOR = ',';
		String csvFile = "Sample_Output.csv";
		FileWriter writer = null;
		try {
			writer = new FileWriter(new File(new File("").getAbsolutePath(), "resource/" + csvFile));
			// For addition of header
			SapeFeeCalcPrashantUtils.writeLine(writer, Arrays.asList("Client Id", "Transaction Type", "Transaction Date", "Priority", "Processing Fee"), SEPERATOR);

			Collections.sort(transactionList, new Transaction());
			// For addition of values
			for (Transaction transaction : transactionList) {
				List<String> valueList = new ArrayList<String>();
				valueList.add(transaction.getClientId());
				valueList.add(SapeFeeCalcPrashantUtils.getTypeName(transaction.getTransactionType()));
				valueList.add(SapeFeeCalcPrashantUtils.parseDate(transaction.getTransactionDate()));
				valueList.add(transaction.getPriority() ? "Y" : "N");
				valueList.add(transaction.getTransactionFees().toString());
				SapeFeeCalcPrashantUtils.writeLine(writer, valueList, SEPERATOR);
			}
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if(writer != null) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}
}
