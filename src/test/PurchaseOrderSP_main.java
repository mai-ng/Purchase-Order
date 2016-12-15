package test;

import gen.FunctionalRequirement;
import gen.SecureUMLJAVATrans;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import utils.Environment;
import warehousems.dbclass.Order;

public class PurchaseOrderSP_main {
	
	
	private static final int OP_CREATE_ORDER = 1;
	private static final int OP_APPROVE_ORDER = 2;
	private static final int OP_RECEIVE_ORDER = 3;
	private static final int OP_CHECK_IS_EXISTING = 4;
	private static final int OP_SHOW_ALL_ORDERS = 5;
	//private static final int OP_SHOW_METHOD_AUDIT = 6;
	private static final int OP_LOG_OUT = 7;
	private static final int OP_EXIT = 8;
	public static ArrayList<OrderModel> listOrder = new ArrayList<OrderModel>();

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args){
		System.out.println("\n*** ----------------------------***");
		System.out.println("***      ACCOUNTING SYSTEM      ***");
		System.out.println("*** ----------------------------***\n");
		while(!Environment.createSession()){
			System.out.println("[INFO] You must log in!\n");
		}
		
		System.out.println("\n\n[INFO] Welcome " + SecureUMLJAVATrans.getConnectingUser()+" !\n");
		// Start working on the system
		FunctionalRequirement system = new FunctionalRequirement(Environment.getDBConnection());
		get_all_orders();
		
		int quit = 0;
		while(quit==0){
			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			show_options();
			int operation = input.nextInt();
			switch(operation){
			case OP_CREATE_ORDER:
				create_order(system);
				get_all_orders();
				break;
			case OP_APPROVE_ORDER:
				System.out.println("Choose an order to approve: ");
				approve_order(system);
				get_all_orders();
				break;
			case OP_RECEIVE_ORDER:
				System.out.println("Choose an order to receive: ");
				receive_good(system);
				get_all_orders();
				break;
			case OP_CHECK_IS_EXISTING:
				check_isExisting();
				break;
			case OP_SHOW_ALL_ORDERS:
				show_all_orders();
				break;
//			case OP_RELOAD_LIST_ORDERS:
//				get_all_orders();
//				break;
//			case OP_SHOW_METHOD_AUDIT:
//				show_method_audit();
//				break;
			case OP_LOG_OUT:
				SecureUMLJAVATrans.disconnectUser();
				break;
			case OP_EXIT:
				quit = 1;
				break;
			default:
				System.out.println("Your choice is not valid choice!\n");
			}
//			get_all_orders();
		}
		try {
			Environment.getDBConnection().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("\n----> Bye...");
		System.exit(0);
	}
/*
	private static void show_method_audit() {
		System.out.println("[INFO] Going to show method audit table");
		String query = "select * from method_log;";
		Statement statem;
		try {
			statem = Environment.getDBConnection().createStatement();
			ResultSet res = statem.executeQuery(query);
			System.out.println("Id \t method_name \t method_params \t order_id \t item_id \t supplier_id \t executed_user \t executed_moment");
			while(res.next()){
				System.out.println(res.getInt("log_id")+" \t " +  
									res.getString("method_name")+" \t "+ 
									res.getString("method_params")+" \t "+
									res.getInt("order_id")+" \t " +
									res.getInt("item_id")+" \t " +
									res.getInt("supplier_id")+" \t " +
									res.getString("executed_user")+" \t "+  
									res.getString("executed_moment"));
			}
			res.close();
			statem.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
*/
	/**
	 * 
	 */
	private static void show_options() {
		
		System.out.println("\n1. Create an order");
		System.out.println("2. Approve an order");
		System.out.println("3. Receive an order");
		System.out.println("4. Check existing");
		System.out.println("5. Show all orders");
//		System.out.println("6. Reload list orders");
//		System.out.println("6. Show method audit");
		System.out.println("7. Log out");
		System.out.println("8. Exit");
		System.out.print("Choose your action: ");
	}

	private static void check_isExisting() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the order id: ");
		int order_id = input.nextInt();
		System.out.println("[INFO] The order is going to be checked :" + order_id);
		Order.PurchaseOrder_isExisting(order_id);
	}

	private static void get_all_orders() {
		listOrder.clear();
		Statement statem;
		try {
			statem = Environment.getDBConnection().createStatement();
			String get_list_orders = "select * from PurchaseOrder;";
			ResultSet res = statem.executeQuery(get_list_orders);
			while(res.next()){
				OrderModel ord = new OrderModel();
					ord.setOrder_id(res.getInt("Id"));
					ord.setCreatedDate(res.getString("createdDate"));
					ord.setIsApproved(res.getInt("isApproved"));
					ord.setIsReceived(res.getInt("isReceived"));
					listOrder.add(ord);
			}
			res.close();
			statem.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Show all orders which have been loaded locally
	 * 
	 * */
	public static void show_all_orders(){
		System.out.println("LIST ORDER");
		System.out.println("---------------------------");
		System.out.println("Id  \t createdDate \t\t isApproved \t isReceived");
		for(int i=0; i < listOrder.size();i++){
			System.out.println(listOrder.get(i).toStringInList());
		}
	}
	
	/**
	 * @param system 
	 * @throws Exception 
	 * 
	 */
	private static void receive_good(FunctionalRequirement system){
		OrderModel order_to_completed = choose_an_order();
		System.out.println("[INFO] The order is going to be received:");
		System.out.println(order_to_completed.toString());
		system.PurchaseOrder_receive(order_to_completed.getOrder_id());
	}

	private static OrderModel choose_an_order() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		boolean chooseOK = false;
		while(!chooseOK){
			System.out.println("Input the order id: ");
			int order_id = input.nextInt();
			for(int i=0;i<listOrder.size();i++){
				if(order_id == listOrder.get(i).getOrder_id()){
					chooseOK = true;
					return listOrder.get(i);
				}
			}
			
			System.out.println("[INFO] The order "+order_id+" does not exists in list");
		}
		
		return null;
	}

	/**
	 * @param system 
	 * @throws Exception 
	 * 
	 */
	private static void approve_order(FunctionalRequirement system){
		OrderModel order_to_validate = choose_an_order();
		System.out.println("[INFO] Order is going to be approved: ");
		System.out.println(order_to_validate.toString());
		system.PurchaseOrder_approve(order_to_validate.getOrder_id());
//		if(result) order_to_validate.setIs_approved(1);
	}

	/**
	 * @param system 
	 * @throws Exception 
	 * 
	 */
	private static void create_order(FunctionalRequirement system){
		system.PurchaseOrder_create(input_id());
	}

	private static int input_id() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.println("Enter ID of the order: ");
		return input.nextInt();
	}
/*	
	private static String input_string() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.println("Enter list item in order: ");
		return input.nextLine();
	}
*/
}
/**
 * This is the class just for testing
 * 
 * @author Mai Nguyen
 *
 */
class OrderModel{
    int PurchaseOrder_id;
	
	String createdDate;
	int isApproved;	
	int  isReceived;
		
	/**
	 * @param PurchaseOrder_id
	 * @param list_items
	 */
	public OrderModel() {
	}

	/**
	 * @return the is_approved
	 */
	public int isIsApproved() {
		return isApproved;
	}

	/**
	 * @param i the is_approved to set
	 */
	public void setIsApproved(int i) {
		this.isApproved = i;
	}



	/**
	 * @return the order_id
	 */
	public int getOrder_id() {
		return PurchaseOrder_id;
	}

	public void setOrder_id(int order_id) {
		this.PurchaseOrder_id = order_id;
	}


	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public int getIsReceived() {
		return isReceived;
	}

	public void setIsReceived(int x) {
		this.isReceived = x;
	}

	public int getIsApproved() {
		return isApproved;
	}

	public String toStringInList() {
		return PurchaseOrder_id +"\t"+ createdDate +"\t\t" + isApproved+"\t"+isReceived;
	}
	
}
