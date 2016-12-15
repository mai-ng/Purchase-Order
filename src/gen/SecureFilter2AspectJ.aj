package gen;

import java.sql.SQLException;

import utils.Environment;

public aspect SecureFilter2AspectJ {

	private ActionsHistoryJAVATrans logC;
	private ADJAVATrans dynamicC;
	private SecureUMLJAVATrans staticC;	

	private void initSecureFilter() {
		try {
			staticC = new SecureUMLJAVATrans(
					Environment.getDBConnection());
			dynamicC = new ADJAVATrans(
					Environment.getDBConnection());
			logC = new ActionsHistoryJAVATrans(Environment.getDBConnection());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	pointcut pc_PurchaseOrder_create(int po): args(po) && 
			call(* *.PurchaseOrder_create(int));
	String around(int po):pc_PurchaseOrder_create(po){
		initSecureFilter();
		String result = "KO";
		String usr = SecureUMLJAVATrans.getConnectingUser();
		try {
			// check static security rules
			String staticRight = staticC.checkUserPermission(
					thisJoinPoint.getSignature().getName(), usr);
			if (staticRight == "granted") {
				System.out.println("[STATIC_SECURITY] PASSED! ");
				// check dynamic security rules
				String dynamicRight = dynamicC
						.ADPurchaseOrder_create(po, usr);
				if (dynamicRight == "granted") {
					System.out.println("[DYNAMIC_SECURITY] PASSED!");
					// proceed the method execution
					result = proceed(po);
					if (result == "OK") {
						System.out.println("[ASPECTJ] "
								+ thisJoinPoint.getSignature().getName()
								+ " is succeeded. Going to write method-log ");
						logC.LogPurchaseOrder_create(po, usr);
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("[ASPECTJ] SQLException: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	pointcut pc_PurchaseOrder_approve(int po): args(po) && 
			call(* *.PurchaseOrder_approve(int));
	String around(int po):pc_PurchaseOrder_approve(po){
		initSecureFilter();
		String result = "KO";
		String usr = SecureUMLJAVATrans.getConnectingUser();

		try {
			// check static security rules
			String staticRight = staticC.checkUserPermission(
					thisJoinPoint.getSignature().getName(), usr);
			if (staticRight == "granted") {
				System.out.println("[STATIC_SECURITY] PASSED! ");
				// check dynamic security rules
				String dynamicRight = dynamicC
						.ADPurchaseOrder_approve(po, usr);
				if (dynamicRight == "granted") {
					System.out.println("[DYNAMIC_SECURITY] PASSED!");
					// proceed the method execution
					result = proceed(po);
					if (result == "OK") {
						System.out.println("[ASPECTJ] "
								+ thisJoinPoint.getSignature().getName()
								+ " is succeeded. Going to write method-log ");
						logC.LogPurchaseOrder_approve(po, usr);
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("[ASPECTJ] SQLException: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	pointcut pc_PurchaseOrder_receive(int po): args(po) && 
			call(* *.PurchaseOrder_receive(int));
	String around(int po): pc_PurchaseOrder_receive(po){
		initSecureFilter();
		String result = "KO";
		String usr = SecureUMLJAVATrans.getConnectingUser();

		try {
			// check static security rules
			String staticRight = staticC.checkUserPermission(
					thisJoinPoint.getSignature().getName(), usr);
			if (staticRight == "granted") {
				System.out.println("[STATIC_SECURITY] PASSED! ");
				// check dynamic security rules
				String dynamicRight = dynamicC
						.ADPurchaseOrder_receive(po, usr);
				if (dynamicRight == "granted") {
					System.out.println("[DYNAMIC_SECURITY] PASSED!");
					// proceed the method execution
					result = proceed(po);
					if (result == "OK") {
						System.out.println("[ASPECTJ] "
								+ thisJoinPoint.getSignature().getName()
								+ " is succeeded. Going to write method-log ");
						logC.LogPurchaseOrder_receive(po, usr);
					}
				}
			}
		} catch (SQLException e) {
			System.err.println("[ASPECTJ] SQLException: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

}
