/**
 * 
 */
package utils;

import gen.SecureUMLJAVATrans;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Mai Nguyen
 *
 */
public class Environment {

	// JDBC driver name and database URL
	// private final static String DB_HOST = "10.140.7.60"; //IP address of
	// the computer where the JDBC is running
	private final static String DB_HOST = "192.168.1.32";
	private final static int DB_PORT = 57364;
	private final static String DB_NAME = "purchase_order";
	static final String DB_URL = "jdbc:sqlserver://" + DB_HOST + ":" + DB_PORT
			+ ";" + "databaseName=" + DB_NAME;

	// Database users credentials
//	private static String connectingUser;
//	private static String currentRole;

	private static java.sql.Connection dbconn = null;

	/**
	 * Create a connection with the database via a database user using his login
	 * name and password.
	 * 
	 * @param username
	 *            the user name
	 * @param password
	 *            the user password
	 * @return true if the connection is succeeded. Otherwise, @return false.
	 */
	public static boolean connectDB(String username, String password) {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			dbconn = DriverManager.getConnection(DB_URL, username, password);
			return true;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static java.sql.Connection getDBConnection() {
		return dbconn;
	}
	
//	/**
//	 * check the existence of a permission for the current role on a given stored procedure.
//	 * @param op is the name of the stored procedure to be checked.
//	 * @return
//	 * @throws SQLException
//	 */
//	public static boolean isPermittedRole(String op) throws SQLException {
//		boolean access = false;
//		PreparedStatement stmtSelectPermission;
//		stmtSelectPermission = dbconn
//				.prepareStatement("SELECT COUNT(*) FROM sys.database_permissions "
//						+ "WHERE USER_NAME(grantee_principal_id) = (?) "
//						+ "AND OBJECT_NAME(major_id) = (?) "
//						+ "AND permission_name = 'EXECUTE';");
//		try {
//			stmtSelectPermission.setString(1, currentRole);
//			stmtSelectPermission.setString(2, op);
//			ResultSet resSet = stmtSelectPermission.executeQuery();
//			if (resSet.next()) {
//				if (resSet.getInt(1) > 0)
//					access = true;
//			}
//			if (!access) {
//				System.err.println("[STATIC_SECURITY] FAILED: The current role " +Environment.getCurrentRole() + " of the user "
//						+ Environment.getConnectingUser()
//						+ " does not have right to perform the method: "
//						+ op);
//			}
//			resSet.close();
//		} catch (SQLException e) {
//			System.err.println("[STATIC_SECURITY] FAILED: SQLException "
//					+ e.getMessage());
//			e.printStackTrace();
//			access = false;
//		}
//		return access;
//	}
	
	

	/**
	 * User provides name and password to login to the database.
	 * 
	 * @return true if the connection is successful. Otherwise, @return false.
	 */
/*	public static boolean createSession() {
		System.out.println("--> LOGIN");
		System.out.println("Enter username and password to login");
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.print("Username: ");
		String usr = input.nextLine();
		System.out.print("Password: ");
		String password = input.nextLine();
		if (Environment.connectDB(usr, password)) {
			//Environment.setConnectingUser(username);
			connectingUser = usr;
			selectRole();
			return true;
		}
		return false;
	}*/
	
	public static boolean createSession() {
		System.out.println("--> LOGIN");
		System.out.println("Enter username and password to login");
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.print("Username: ");
		String usr = input.nextLine();
		System.out.print("Password: ");
		String password = input.nextLine();
		if (Environment.connectDB(usr, password)) {
			SecureUMLJAVATrans.connectUser(usr, selectRole(usr));
			return true;
		}
		return false;
	}
	
//	public static void connectUser(String usr, String  rol) {
//			connectingUser = usr;
//			currentRole = rol;
//	}

//	/**
//	 * User logout the system
//	 */
//	public static void logout() {
//		String usr = connectingUser;
//		setCurrentRole(null);
//		setConnectingUser(null);
//		System.out.println("[INFO] " + connectingUser
//				+ " has logged out of the system\n");
//		Environment.createSession();
//	}

	/**
	 * User can choose one of his assigned roles to be active in the considering
	 * session.
	 */
	public static String selectRole(String usr) {
		String selectedRole = "";
		Statement stm;
		try {
			stm = Environment.getDBConnection().createStatement();
			String get_roles_query = "SELECT r.name FROM sys.database_role_members AS m INNER JOIN sys.database_principals AS r ON m.role_principal_id = r.principal_id INNER JOIN sys.database_principals AS u ON u.principal_id = m.member_principal_id WHERE u.name = '"
					+ usr + "';";
			ResultSet res = stm.executeQuery(get_roles_query);
			ArrayList<String> list_roles = new ArrayList<String>();
			while (res.next()) {
				list_roles.add(res.getString(1));
			}
			res.close();
			stm.close();
			if (list_roles.isEmpty()) {
				System.out.println("Current user does not have any role!");
			}
			if (list_roles.size() == 1) {
				selectedRole = list_roles.get(0);
				return selectedRole;
			}

			while (true) {
				System.out.println("Choose a role for this session: ");
				show_list_roles(list_roles);
				int choose = input_number_range(list_roles.size());
				System.out.println(usr
						+ " has logged in and choose the role: "
						+ list_roles.get(choose));
				selectedRole = list_roles.get(choose);
				return selectedRole;
			}

		} catch (SQLException e) {
			System.err.println("[SQL] ERROR: " + e.getErrorCode() + ": "
					+ e.getMessage());
		}
		return selectedRole;
	}

	private static int input_number_range(int size) {
		System.out.println("Enter a number (0-" + (size - 1) + " ): ");
		@SuppressWarnings("unused")
		Scanner input = new Scanner(System.in);
		int choose = input.nextInt();
		while (choose < 0 || choose >= size) {
			System.out.println("Input number is not valided");
			System.out.println("Enter a number (0-" + (size - 1) + " ): ");
			choose = input.nextInt();
		}

		return choose;
	}

	private static void show_list_roles(ArrayList<String> list_roles) {
		for (int i = 0; i < list_roles.size(); i++) {
			System.out.println(i + " - " + list_roles.get(i));
		}
	}

}
