package gen;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ADJAVATrans {
	private LogFile_create t_create;
	private LogFile_approve t_approve;
	//private LogFile_receive t_receive;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public ADJAVATrans(Connection conn) throws SQLException {
		t_create = new LogFile_create(conn);
		t_approve = new LogFile_approve(conn);
		//t_receive = new LogFile_receive(conn);
	}

	public String ADPurchaseOrder_create(int po_id, String usr) {
		return "";
	}

	public String ADPurchaseOrder_approve(int po_id, String usr) {
		return "";
	}

	/**
	 * @param po
	 * @param usr
	 * @return
	 */
	public String ADPurchaseOrder_receive(int po, String usr) {
		String access = "denied";
		boolean notyet_approve = t_approve.NoApprove_log(po);
		boolean notyet_create = t_create.NoCreate_log(po);
		String create_user = t_create.Create_user(po);
		String create_order = t_create.Create_order(po);
		String approve_order = t_approve.Approve_order(po);
		try {
			if (notyet_approve == false && notyet_create == false
					&& !usr.equals(create_user)
					&& sdf.parse(create_order).before(sdf.parse(approve_order)))
				access = "granted";
			else{
				System.out.println("[DYNAMIC_SECURITY] FAILED!");
				access = "denied";
			}
		} catch (ParseException e) {e.printStackTrace();}
		return access;
	}
}
