/**
 * 
 */
package gen;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Mai Nguyen
 * The methods call the corresponding stored procedures in the database.
 */
public class FunctionalRequirement {
	private Connection dbcon;
	public FunctionalRequirement(Connection conn) {
		dbcon = conn;
	}
	/**
	 *  Call the stored procedure 'purchase_order_create' in the database, 
	 * which execute the statement of inserting a record with a list of given items on the table 'purchase_order'
	 * @param _items
	 * @return true if the stored procedure is executed (the statement returns the row count), 
	 * otherwise, return false (the statement returns nothing)
	 */
	public String PurchaseOrder_create(int po){
		String result = "KO";
		CallableStatement statem;
		try {
			statem = dbcon.prepareCall("{call PurchaseOrder_create(?)}");
			statem.setInt("po_id", po);
			int res = statem.executeUpdate();
			if(res > 0){
				System.out.println("[INFO] Order " + po + " is created!");
				result = "OK";
			}
			statem.close();
		} catch (SQLException e) {
			System.err.println("[SQL] ERROR: " + e.getErrorCode() +": " + e.getMessage());
		}
		return result;
	}
	
	/** 
	 * Call the stored procedure 'purchase_order_approve' in the database, 
	 * which execute the statement of modifying the value of the column 'isApproved' to 'TRUE' on the table 'purchase_order'
	 * of a given order.
	 * @param po the identification of the order
	 * @return true if the stored procedure is executed (the statement returns the row count), 
	 * otherwise, return false (the statement returns nothing)
	 */
	public String PurchaseOrder_approve(int po){
		String result = "KO";
		CallableStatement statem;
		try {
			statem = dbcon.prepareCall("{call PurchaseOrder_approve(?)}");
			statem.setInt("po_id", po);
			int res = statem.executeUpdate();
			if(res > 0){
				System.out.println("[INFO] Order " + po + " is approved!");
				result = "OK";
			}
			statem.close();
		} catch (SQLException e) {
			System.err.println("[SQL] ERROR: " + e.getErrorCode() +": " + e.getMessage());
		}
		return result;
	}
	
	/**
	 * Call the stored procedure 'purchase_order_receiveGoods' in the database, 
	 * which execute the statement of modifying the value of the column 'isReveivedGoods' to 'TRUE' on the table 'purchase_order'
	 * of a given order.
	 * @param po the identification of the order
	 * @return true if the stored procedure is executed (the statement returns the row count), 
	 * otherwise, return false (the statement returns nothing)
	 */
	public String PurchaseOrder_receive(int po){
		String result = "KO";
		CallableStatement statem;
		try {
			statem = dbcon.prepareCall("{call PurchaseOrder_receive(?)}");
			
			statem.setInt("po_id", po);			
			int res = statem.executeUpdate();
			if(res > 0){
				System.out.println("[INFO] Order " + po + " is completed!");
				result = "OK";
			}
			statem.close();
		} catch (SQLException e) {
			System.err.println("[SQL] ERROR: " + e.getErrorCode() +": " + e.getMessage());
		}
		return result;
	}
	
}
