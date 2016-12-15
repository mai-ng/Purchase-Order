package gen;

import java.sql.Connection;
import java.sql.SQLException;

public class ActionsHistoryJAVATrans {	
	private LogFile_create t_create;
	private LogFile_approve t_approve;
	private LogFile_receive t_receive;

	public ActionsHistoryJAVATrans(Connection conn) throws SQLException {
		t_create = new LogFile_create(conn);
		t_approve = new LogFile_approve(conn);
		t_receive = new LogFile_receive(conn);
	}
	
	public void LogPurchaseOrder_create(int po, String usr) {
		t_create.AddCreate_log(po, usr);
	}
	
	public void LogPurchaseOrder_approve(int po, String usr) {
			t_approve.AddApprove_log(po, usr);
	}
	
	public void LogPurchaseOrder_receive(int po, String usr) {
			t_receive.AddReceive_log(po, usr);
	}	
}
