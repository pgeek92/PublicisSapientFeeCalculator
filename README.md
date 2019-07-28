# Problem Statement
Sapient has won the contract to implement processing fee calculator for a major investment bank (henceforth referred as client). Client receives transactions
from various external sources.
These transactions are received in a pre-configured format, for example, CSV, EXCEL, XML or a simple pipe delimited format text file placed at a file 
location. 
The client needs to calculate the processing fees for the transaction and generate a report which can be sent for invoicing.

### Objective
* Read the transactions into the system. The various transaction attributes are listed below.
* Execute the processing rules (mentioned below) over input transactions.
* Provide API to get the summary report in a particular format (format mentioned below) 
  *<b>Note</b> : The code should handle CSV format input however the design should be extensible to support other input formats as well in future.
  
#### Transaction Attributes

|       Attribute Name        |                         Attribute Description                                   |
|:---------------------------:|:-------------------------------------------------------------------------------:|
|External Transaction Id      | Unique Identifier                                                               |
|Client Id                    | Unique Id for each client                                                       |
|Security Id                  | Unique  Id  for  the  security for  example  RELIND  for reliance industries    | 
|Transaction Type             | Buy, Sell, Deposit, Withdraw                                                    |
|Transaction Date             | Date in MM/dd/yyyy                                                              |
|Market Value                 | The current market value of this transaction                                    |
|Priority Flag                | Value Y,N                                                                       |

### Processing Rules

#### Intraday transactions
Intra-day transactions are the ones where security (like IBM Equity) is bought & sold on the same day.
* Intra-day transactions will have two transactions having same Client Id, Security Id & Transaction Date but opposite Transaction Type i.e. 
  * one transaction would be 'Sell' & other would be'Buy'.
* Each intra-day transaction should be charged <b>$10</b> for both the Buy & Sell legs.

#### Normal transactions
A nominal fee is charged to process each transaction. Fee calculation is based on following rules: 
* <b>$500</b> for a transaction with high priority (denoted by the priority field in the transaction) 
* <b>$100</b> for a transaction with normal priority and Transaction Type is Sell and Withdraw 
* <b>$50</b> for a transaction with normal priority and Transaction Type Code is Buy and Deposit

### Summary Report Definition
The summary report should contain the processing fee to be charged from each client, for a particular transaction type, transaction date and priority. 
* The processing fee should be grouped by client Id, transaction type, Transaction date, & priority flag. 
* Report should further be sorted by the Client Id, Transaction Type, Transaction Date and Priority.
*
| Client Id | Transaction Type | Transaction Date | Priority | Processing Fee |
|:---------:|:----------------:|:----------------:|:--------:|:--------------:|
|           |                  |                  |          |                |

 