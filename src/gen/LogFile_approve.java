package gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.Environment;

public class LogFile_approve {

	private PreparedStatement stmt;

	public LogFile_approve(Connection conn) throws SQLException {
		stmt = conn
				.prepareStatement("SELECT orderExecutionApprove, userExecutedApprove "
						+ "FROM T_Approve_log " + "WHERE purchaseOrderId = ?; ");
	}

//	public ResultSet readLogTable_approve(int po_id) throws SQLException {
//		stmt.setInt(1, po_id);
//		ResultSet res = stmt.executeQuery();
//		return res;
//	}

	public boolean NoApprove_log(int po) {
		try {
			//executing a query to the database to check if
			//a given purchase order is already approved or not,
			//returns false if it is successfully approved, true otherwise.
			stmt.setInt(1, po);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				System.err
						.print("[Log_approve] ERROR: The purchase order ( "
								+ po + " ) is already approved!");
				return false;
			} else
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public String Approve_user(int po) {
		try {
			if (!NoApprove_log(po)) {
				stmt.setInt(1, po);
				ResultSet res = stmt.executeQuery();
				//return the user who approved the given purchase order
				return res.getString("userExecutedApprove");
			} else {
				System.err
						.print("\n[Log_approve] ERROR: The purchase order ( "
								+ po + " ) has not been approved yet!");
				return "nouser";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "nouser";
		}
	}

	public String Approve_order(int po) {
		try {
			if (!NoApprove_log(po)) {
				stmt.setInt(1, po);
				ResultSet res = stmt.executeQuery();
				//returns the executed time of the approval
				return res.getString("orderExecutionApprove");
			} else {
				System.err
						.print("\n[Log_approve] ERROR: The purchase order ( "
								+ po + " ) has not been approved yet!");
				return "undefined_time";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "undefined_time";
		}
	}

	public void AddApprove_log(int po, String usr) {
		try {
			Statement stm = Environment.getDBConnection().createStatement();

			String log = "INSERT INTO "
					+ "T_Approve_log ( purchaseOrderId ,userExecutedApprove) "
					+ "VALUES(" + po + ",'" + usr + "');";

			int res = stm.executeUpdate(log);

			if (res == 1) {
				System.out
						.println("[Log_approve]: The method 'PurchaseOrder_approve' has been logged successfully  on the purchase order "
								+ po + " executed by " + usr);
			} else {
				System.err.println("[Log_approve] ERROR: The method 'PurchaseOrder_approve' CANNOT be logged successfully  on the purchase order "
								+ po + " executed by " + usr);
			}
			stm.close();
		} catch (SQLException e) {
			System.err
					.println("\n [Log_approve] ERROR: Cannot access to the database: "
							+ e.getErrorCode() + ": " + e.getMessage());
		}
	}
}
