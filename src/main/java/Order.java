import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Order {
	private final int orderNo;
	private final Date orderDate;
	private final double totalCost;
	private final Customer customer;
	private final Cashier cashier;
	private final String address;
	private ArrayList<int[]> basket=new ArrayList<int[]>();
	private static int count = 1;
	private  static ArrayList<Order> orders=new ArrayList<Order>();
 	
	//Create a new order
	public Order(Date orderDate, Customer customer, Cashier cashier, String address, ArrayList<int[]> basket) {
		this.orderNo = count++;
		this.orderDate = orderDate;
		this.totalCost = this.calculateBasketCost(basket);
		this.customer = customer;
		this.cashier = cashier;
		this.address = address;
		this.basket = basket;
		orders.add(this);
	}
	
	/*Constructor for already created products read from .csv file
	There is non need to calculate the total cost again as it has been created*/
	public Order(int orderNo, Date orderDate, double totalCost, Customer customer, Cashier cashier, String address, ArrayList<int[]> basket) {
		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.totalCost = totalCost;
		this.customer = customer;
		this.cashier = cashier;
		this.address = address;
		this.basket = basket;
		orders.add(this);
	}
	
	public String getAddress() {
		return address;
	}

	//Getters and Setters
	public int getOrderNo() {
		return orderNo;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	
	public double getTotalCost() {
		return totalCost;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Cashier getCashier() {
		return cashier;
	}

	public ArrayList<int[]> getBasket() {
		return basket;
	}
	
	public static ArrayList<Order> getOrders() {
		return orders;
	}
	
	//Basket creation 
	public void addProductToBasket(int Id, int quantity) {
		Product prod = Storage.searchById(id);
		int[] prod= {Id, quantity};
		basket.add(prod);
	}
	
	//Calculate a product's cost 
	public static double calculateCost (int id, int quantity) {
		Product prod = Storage.searchById(id);
		double price = prod.getSalePrice(); 
		double cost = price * quantity;
		return cost; 
	}
	
	//Calculates the total cost of an order's basket
	public static double calculateBasketCost (ArrayList<int[]> basket) {
		double totalCost = 0;
		Product prod = Storage.searchById(id);
		for (int[] product : basket) {
			prod = Storage.searchById(product[0]);
			totalCost += prod.getSalePrice();
		}
		return totalCost;
	}
	

	//Prints everything on the Basket
	public static void printProduct(int id, int quantity) {
		Product prod = Storage.searchById(id);
		String name = prod.getName();
			double price = calculateCost (id, quantity);
		    System.out.println(id + " - " + name);
		    System.out.println("pieces: " + quantity );
		    System.out.println("price: " + price);   
	}
		
	//Prints a preview of the Order
	public void previewOrder (Cashier cashier, Customer customer, String address ArrayList<int[]> basket) {
		int cashierId = cashier.getIdUser();
		double totalCost = 0;
		System.out.println("Preview of Order");
		System.out.println();
		System.out.println("Cashier's Code: " + cashierId);
		System.out.println("Customer's data");
		if (Customer instanceof RegisteredCustomer) {
			String customername = get.CompanyName();
			System.out.println("Name: " + customername);
		} else {
			System.out.println("Guest");
		}
		System.out.println("Address: " + address);
		System.out.println("Products: ");
		for (int prod [] : basket) {
			printProduct(prod [0], prod [1]);
		}
		totalcost = calculateBasketCost(basket);
		printTotalCost (totalcost);
	}
	
	//Prints final Order
	public void finalOrder () {
		int cashierId = cashier.getIdUser();
		double totalcost = 0;
		System.out.println("Final Order");
		System.out.println();
		System.out.println();
		System.out.println("OrderNo: " + orderNo);
		System.out.println("Cashier's Code: " + cashId);
		System.out.println("Customer's data");
		if (Customer instanceof RegisteredCustomer) {
			String customername = get.CompanyName();
			System.out.println("Name: " + customername);
		} else {
			System.out.println("Guest");
		}
		System.out.println("Address: " + address);
		System.out.println("Products: ");
		for (int prod [] : basket) {
			printProduct(prod [0], prod [1]);
		}
		totalcost = calculateBasketCost(basket);
		printTotalCost (totalcost);
	}
	
	//Prints total cost 
	public void printTotalCost (double totalcost) {
		System.out.println("Order's total cost: " + totalCost);	
	}
	
	public static void createOrdersFromList(ArrayList<ArrayList<String>> orders) {
		int customerId, cashierId, orderNo;
		double totalCost;
		ArrayList<int[]> basket;
		Date orderDate;
		Customer customer;
		Cashier cashier;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
		for (ArrayList<String> order: orders) {
			orderNo = Integer.parseInt(order.get(0));
			orderDate = dateFormatter.parse(order.get(1));
			totalCost = Double.parseDouble(order.get(2));
			customerId = Integer.parseInt(order.get(3));
			basket = new ArrayList<int[]>();
			int[] temp;
			if (customerId == 0)
				customer = null;
			else
				customer = RegisterdCustomer.searchById();
			cashierId = Integer.parseInt(order.get(4));
			cashier = User.searchUserById(cashierId);
			for (int i = 5 ; i < orders.size(); i += 2) {
				temp[0] = Integer.parseInt(order.get(i));
				temp[1] = Integer.parseInt(order.get(i+1));
				basket.add(temp);
			}
			new Order(orderNo, orderDate, customer, cashier, totalCost, basket);
		}
		
	}
}