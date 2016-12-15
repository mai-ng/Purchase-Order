package gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.Environment;

public class LogFile_receive {

	private PreparedStatement stmt;

	public LogFile_receive(Connection conn) throws SQLException {
		stmt = conn
				.prepareStatement("SELECT orderExecutionReceive, userExecutedReceive "
						+ "FROM T_Receive_log " + "WHERE purchaseOrderId = ?; ");
	}

//	public ResultSet readLogTable_receive(int po_id) throws SQLException {
//		stmtselect_receive.setInt(1, po_id);
//		ResultSet res = stmtselect_receive.executeQuery();
//		return res;
//	}

	public boolean NoReceive_log(int po) {
		try {
			//executing a query to the database to check if
			//a given purchase order is already received or not,
			//returns false if it is successfully received, true otherwise.
			stmt.setInt(1, po);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				System.err
						.print("[Log_receive] ERROR: The purchase order ( "
								+ po + " ) is already received!");
				return false;
			} else
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public String Receive_user(int po) {
		try {
			if (!NoReceive_log(po)) {
				stmt.setInt(1, po);
				ResultSet res = stmt.executeQuery();
				// get the user received the given purchase order
				return res.getString("userExecutedReceive");
			} else {
				System.err
						.print("\n[Log_receive] ERROR: The purchase order ( "
								+ po + " ) has not been received yet!");
				return "nouser";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "nouser";
		}
	}

	public String Receive_order(int po) {
		try {
			if (!NoReceive_log(po)) {
				stmt.setInt(1, po);
				ResultSet res = stmt.executeQuery();
				// get the user received the given purchase order
				return res.getString("orderExecutionReceive");
			} else {
				System.err
						.print("\n[Log_receive] ERROR: The purchase order ( "
								+ po + " ) has not been received yet!");
				return "undefined_time";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "undefined_time";
		}
	}

	public void AddReceive_log(int po, String usr) {
		try {
			Statement stm = Environment.getDBConnection().createStatement();

			String log = "INSERT INTO "
					+ "T_Receive_log ( purchaseOrderId ,userExecutedReceive) "
					+ "VALUES(" + po + ",'" + usr + "');";

			int res = stm.executeUpdate(log);

			if (res == 1) {
				System.out
						.println("[Log_receive]: The method 'PurchaseOrder_receive' has been logged successfully  on the purchase order "
								+ po + " executed by " + usr);
			} else {
				System.err
						.println("[Log_receive] ERROR: The method 'PurchaseOrder_receive' CANNOT be logged successfully  on the purchase order "
								+ po + " executed by " + usr);
			}
			stm.close();
		} catch (SQLException e) {
			System.err
					.println("\n [Log_receive] ERROR: Cannot access to the database: "
							+ e.getErrorCode() + ": " + e.getMessage());
		}
	}
}
