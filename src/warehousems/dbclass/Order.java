package warehousems.dbclass;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import utils.Environment;

/**
 * Class @Order presents for purchase_order table in database
 * @author Mai Nguyen
 *
 */
public class Order {
	
	private PreparedStatement stmtInsert;
	//private PreparedStatement stmtGetItems;
		
	/**
	 * @param order_id
	 * @param list_items
	 */
	public Order(Connection conn) throws SQLException{
		stmtInsert = conn.prepareStatement("insert into PurchaseOrder"+	"values(?);");
		//stmtGetItems = conn.prepareStatement("select poItems from PurchaseOrder"+
		//		" where Id=(?)");
	}
	
	/**
	 * Insert a new order into purchase_order table
	 * @param _pItems
	 * @throws SQLException
	 */
	public void insertOrder(String _poItems) throws SQLException{
		stmtInsert.setString(1, _poItems);
		stmtInsert.executeUpdate();
	}
	
	/**
	 * Check if an order exists or not
	 */
	public static boolean PurchaseOrder_isExisting(int _id){
		CallableStatement statem;
		boolean isExisting = false;
		try {
			statem = Environment.getDBConnection().prepareCall("{ call PurchaseOrder_isExisting (?, ?)}");
			statem.setInt(1, _id);
			
//			statem.setInt("poId", _id);
			statem.registerOutParameter(2, Types.INTEGER);
			statem.execute();
			int res = statem.getInt(2);
			if(res > 0){
				isExisting = true;
			}
//			int result = statem.getInt(1);
			
			if( isExisting ) System.out.println("[INFO] Order " + _id + " exists in database!");
			else System.out.println("[INFO] Order " + _id + " does not exist in database!");
			return isExisting;
		} catch (SQLException e) {
			System.err.println("[SQL] ERROR: " + e.getErrorCode() +": " + e.getMessage());
			return isExisting;         
		}
	}
	
/*	*//**
	 * Get list items of order
	 * @param _poId order id
	 * @return null if the list items is empty
	 * 			list items of order
	 * @throws SQLException
	 *//*
	public String getItems(int _poId) throws SQLException {
		stmtGetItems.setInt(1, _poId);
		ResultSet ret =  stmtInsert.executeQuery();
		if(ret.next()){
			String items = ret.getString(0);
			return items;
		}
		return null;
	}*/

}
