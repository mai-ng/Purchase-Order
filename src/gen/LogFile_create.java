package gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import utils.Environment;

public class LogFile_create {
	
		private PreparedStatement stmt;

		public LogFile_create(Connection conn) throws SQLException {
			stmt = conn
					.prepareStatement("SELECT orderExecutionCreate, userExecutedCreate "
							+ "FROM T_Create_log "
							+ "WHERE purchaseOrderId = ?; ");
		}


//		public ResultSet readLogTable_create(int po_id)
//				throws SQLException {
//			stmtselect_create.setInt(1, po_id);
//			ResultSet res = stmtselect_create.executeQuery();
//			return res;
//		}


		public boolean NoCreate_log(int po) {
			try {
				//executing a query to the database to check if
				//a given purchase order is already created or not,
				//returns false if it is successfully created, true otherwise.
				stmt.setInt(1, po);
				ResultSet res = stmt.executeQuery();
				if (res.next()) {
					System.err.print("[LogFile_create] ERROR: The purchase order ( "
							+ po + " ) is already created!");
					return false;
				} else
					return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}

		}

		public String Create_user(int po) {
			try {
				if (!NoCreate_log(po)) {
					stmt.setInt(1, po);
					ResultSet res = stmt.executeQuery();
					// get the user received the given purchase order
					return res.getString("userExecutedCreate");
				} else {
					System.err.print("\n[Log_create] ERROR: The purchase order ( "
							+ po + " ) has not been created yet!");
					return "nouser";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return "nouser";
			}
		}


		public String Create_order(int po) {
			try {
				if (!NoCreate_log(po)) {
					stmt.setInt(1, po);
					ResultSet res = stmt.executeQuery();
					// get the user received the given purchase order
					return res.getString("orderExecutionCreate");
				} else {
					System.err.print("\n[Log_create] ERROR: The purchase order ( "
							+ po + " ) has not been created yet!");
					return "undefined_time";
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return "undefined_time";
			}
		}


		public void AddCreate_log(int po, String usr) {
			try {
				Statement stm = Environment.getDBConnection().createStatement();

				String log = "INSERT INTO " + "T_Create_log ( purchaseOrderId ,userExecutedCreate) "
						+ "VALUES(" + po + ",'" + usr + "');";

				int res = stm.executeUpdate(log);

				if (res == 1) {
					System.out.println("[Log_create]: The method 'PurchaseOrder_create' has been logged successfully  on the purchase order " + po + " executed by "+ usr);
				} else {
					System.err.println("[Log_create] ERROR: The method 'PurchaseOrder_create' CANNOT be logged successfully  on the purchase order "
							+ po + " executed by " + usr);
				}
				stm.close();
			} catch (SQLException e) {
				System.err
						.println("\n [Log_create] ERROR: Cannot access to the database: "
								+ e.getErrorCode() + ": " + e.getMessage());
			}
		}
	}


