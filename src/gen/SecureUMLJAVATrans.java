package gen;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.Environment;

public class SecureUMLJAVATrans {
	private static String connectingUser;
	private static String currentRole;
	//private static PreparedStatement stmtSelectPermission;
	private static Connection dbconn = null;

	public SecureUMLJAVATrans(Connection conn) throws SQLException {
		setDbconn(conn);
//		stmtSelectPermission = conn
//				.prepareStatement("SELECT COUNT(*) FROM sys.database_permissions "
//						+ "WHERE USER_NAME(grantee_principal_id) = (?) "
//						+ "AND OBJECT_NAME(major_id) = (?) "
//						+ "AND permission_name = 'EXECUTE';");
	}

	
	/**
	 * check the permission for the connecting user on executing a given method (stored procedure).
	 * @param op is the method to be checked.
	 * @param usr is the user to be checked, This user should be the connecting user.
	 * @return true if the current role of the logged in user is authorized on the given stored procedure in the database. 
	 * Otherwise, @return false.
	 * @throws SQLException
	 */
	public String checkUserPermission(String op, String usr)
			throws SQLException {
		String access = "denied";
		String conUser = getConnectingUser();
		if (usr.equals(conUser)) {
			boolean isPermitted = isPermittedRole(op);
			if (isPermitted)
				access = "granted";
			else
				access = "denied";
		}
		return access;
	}
/*	
	
	/**
	 * check the existence of a permission for the current role on a given stored procedure.
	 * @param op is the name of the stored procedure to be checked.
	 * @return
	 * @throws SQLException
	 */
	public static boolean isPermittedRole(String op) throws SQLException {
		boolean access = false;
		PreparedStatement stmtSelectPermission;
		stmtSelectPermission = dbconn
				.prepareStatement("SELECT COUNT(*) FROM sys.database_permissions "
						+ "WHERE USER_NAME(grantee_principal_id) = (?) "
						+ "AND OBJECT_NAME(major_id) = (?) "
						+ "AND permission_name = 'EXECUTE';");
		try {
			stmtSelectPermission.setString(1, currentRole);
			stmtSelectPermission.setString(2, op);
			ResultSet resSet = stmtSelectPermission.executeQuery();
			if (resSet.next()) {
				if (resSet.getInt(1) > 0)
					access = true;
			}
			if (!access) {
				System.err.println("[STATIC_SECURITY] FAILED: The current role " +getCurrentRole() + " of the user "
						+ getConnectingUser()
						+ " does not have right to perform the method: "
						+ op);
			}
			resSet.close();
		} catch (SQLException e) {
			System.err.println("[STATIC_SECURITY] FAILED: SQLException "
					+ e.getMessage());
			e.printStackTrace();
			access = false;
		}
		return access;
	}
	
//	public static boolean createSession() {
//		System.out.println("--> LOGIN");
//		System.out.println("Enter username and password to login");
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		System.out.print("Username: ");
//		String usr = input.nextLine();
//		System.out.print("Password: ");
//		String password = input.nextLine();
//		if (Environment.connectDB(usr, password)) {
//			connectUser(usr, Environment.selectRole(usr));
//			return true;
//		}
//		return false;
//	}
	
	public static void connectUser(String usr, String  rol) {
			connectingUser = usr;
			currentRole = rol;
	}
	
	/**
	 * User logout the system
	 */
	public static void disconnectUser() {
		//String usr = connectingUser;
		setCurrentRole(null);
		setConnectingUser(null);
		System.out.println("[INFO] " + connectingUser
				+ " has logged out of the system\n");
		Environment.createSession();
	}
	
	public static String getConnectingUser() {
		return connectingUser;
	}

	public static void setConnectingUser(String usr) {
		connectingUser = usr;
	}

	public static String getCurrentRole() {
		return currentRole;
	}

	public static void setCurrentRole(String rol) {
		currentRole = rol;
	}


	public static Connection getDbconn() {
		return dbconn;
	}


	public static void setDbconn(Connection dbconn) {
		SecureUMLJAVATrans.dbconn = dbconn;
	}
}
