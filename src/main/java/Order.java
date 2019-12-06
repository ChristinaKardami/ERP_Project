import java.util.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Order {
	private final int orderNo;
	private final String orderDate;
	private final double totalCost;
	private final Customer customer;
	private final Cashier cashier;
	private ArrayList<int[]> basket=new ArrayList<int[]>();
	private static int count = 1;
	private  static ArrayList<Order> orders=new ArrayList<Order>();
 	
	//Create a new order
	public Order(String orderDate, Customer customer, Cashier cashier, ArrayList<int[]> basket) {
		this.orderNo = count++;
		this.orderDate = orderDate;
		this.totalCost = this.calculateBasketCost(basket);
		this.customer = customer;
		this.cashier = cashier;
		this.basket = basket;
		orders.add(this);
	}
	
	/*Constructor for already created products read from .csv file
	There is non need to calculate the total cost again as it has been created*/
	public Order(int orderNo, String orderDate, double totalCost, Customer customer, Cashier cashier, ArrayList<int[]> basket) {
		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.totalCost = totalCost;
		this.customer = customer;
		this.cashier = cashier;
		this.basket = basket;
		orders.add(this);
	}

	//Getters and Setters
	public int getOrderNo() {
		return orderNo;
	}
	
	public String getOrderDate() {
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
	
	public static int getIdCounter() {
		return count;
	}
	
	//Add product to basket 
	public void addProductToBasket(int id, int quantity) {
		int[] products= {id, quantity};
		basket.add(products);
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
		double productCost;
		Product prod;
		for (int[] product : basket) {
			prod = Storage.searchById(product[0]);
			productCost = prod.getSalePrice() * product[1];
			totalCost += productCost;
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
	public static void previewOrder (Cashier cashier, Customer customer, ArrayList<int[]> basket) {
		int cashierId = cashier.getIdUser();
		double totalCost = 0;
		System.out.println("Preview of Order");
		System.out.println();
		System.out.println("Cashier's Code: " + cashierId);
		System.out.println("Customer's data");
		if (customer instanceof RegisteredCustomer) {
			String customerName = customer.getCompanyName();
			System.out.println("Name: " + customerName);
		} else {
			System.out.println("Guest");
		}
		System.out.println("Address: " + customer.getAddress());
		System.out.println("Products: ");
		for (int prod [] : basket) {
			printProduct(prod [0], prod [1]);
		}
		double totalcost = calculateBasketCost(basket);
		System.out.println("Order's total cost: " + totalCost);	
	}
	
	//Prints final Order
	public void finalOrder () {
		int cashierId = cashier.getIdUser();
		double totalcost = 0;
		System.out.println("Final Order");
		System.out.println();
		System.out.println();
		System.out.println("OrderNo: " + orderNo);
		System.out.println("Cashier's Code: " + cashierId);
		System.out.println("Customer's data");
		if (customer instanceof RegisteredCustomer) {
			String customerName = customer.getCompanyName();
			System.out.println("Name: " + customerName);
		} else {
			System.out.println("Guest");
		}
		System.out.println("Address: " + customer.getAddress());
		System.out.println("Products: ");
		for (int prod [] : basket) {
			printProduct(prod [0], prod [1]);
		}
		totalcost = calculateBasketCost(basket);
		System.out.println("Order's total cost: " + totalCost);	
	}
	
	//contains the prompts in order to make a registered customer order
	public static void makeOrder(Cashier cashier, boolean isRegistered) {
		Scanner in = new Scanner(System.in);
		String ans;
		Customer customer;
		ArrayList<int[]> basket;
		int id;
		for(;;) {
			try {
				if (isRegistered) {
					System.out.println("*** Registered Customer Order Menu ***");
					System.out.print("Enter the id of the user the order is about. To cancel, press \"enter\": ");
					ans = in.nextLine();
					if (ans.equals("")) {
						System.out.println("Process cancelled, returning to previous menu...");
						return;
					}
					id = Integer.parseInt(ans);
					customer = RegisteredCustomer.searchById(id);
				} else {
					System.out.println("*** Guest Customer Order Menu ***");
					customer = null;
				}
				basket = fillBasket();
				Order.previewOrder(cashier, customer, basket);
				
				for(;;) {
					System.out.print("Confirm order? (Y/N): ");
					ans = in.nextLine();
					if (ans.toLowerCase().equals("y") || ans.toLowerCase().equals("yes")) {
						Order.confirmOrder(cashier, customer, basket);
						return;
					} else if (ans.toLowerCase().equals("n") || ans.toLowerCase().equals("no")) {
						System.out.println("Process cancelled. Returning to previous menu...");
						return;
					}
					System.out.println("Please enter either \"Yes\"(\"Y\") or \"No\"(\"N\").\n"
							+ "Try again...");
				}
			} catch (NoSuchElementException e) {
				System.out.println("Customer with such id does not exist. Try again...");
				continue;
			} catch (NumberFormatException e) {
				System.out.println("Invalid input given. Try again...");
				continue;
			}
		}
	}
	
	public static void confirmOrder(Cashier cashier, Customer customer, ArrayList<int[]> basket) {
		for (int[] product : basket) {
			try {
				//remove the products from storage
				Storage.removeProductQuantity(product[0], product[1]);
			} catch (NoSuchElementException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String orderDate = getCurrentDate();
		new Order(orderDate, customer, cashier, basket);
	}
	
	public static String getCurrentDate() {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		return dateFormatter.format(date);
	}
	
	//contains the prompts in order for the user to fill the order basket
	public static ArrayList<int[]> fillBasket() {
		Scanner in = new Scanner(System.in);
		String input;
		ArrayList<int[]> tempBasket = new ArrayList<int[]>();
		int[] idQuantity = new int[2];
		int productId, quantity;
		for (;;) {
			try {
				System.out.print("Enter the id of the product you want to buy.\n"
						+ "Press only \"enter\" when you do not want to add any products: ");
				input = in.nextLine();
				if (input.equals("")) {
					return tempBasket;
				}
				productId = Integer.parseInt(input);
				System.out.println("Enter the quantity of the product you want to shop");
				quantity = in.nextInt();
				if (Storage.checkIfQuantityIsEnough(productId, quantity) == true) {
					idQuantity[0] = productId;
					idQuantity[1] = quantity;
					tempBasket.add(idQuantity);
				} else {
					System.out.println("There are not enough product units available.\n"
							+ "Change the quantity or order more. Try Again...");
					continue;
				}
			} catch (NumberFormatException e) {
				System.out.println("Product id must be an integer larger than 0. Try again...");
			} catch (InputMismatchException e) {
				System.out.println("The quantity must be an integer larger than 0. Try again...");
				in.nextLine();
			} catch (NoSuchElementException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static void createOrdersFromList(ArrayList<ArrayList<String>> orders) {
		int customerId, cashierId, orderNo;
		double totalCost;
		ArrayList<int[]> basket;
		String orderDate;
		Customer customer;
		Cashier cashier;
		int[] temp = null;
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		for (ArrayList<String> order: orders) {
			orderNo = Integer.parseInt(order.get(0));
			orderDate = dateFormatter.format((order.get(1)));
			totalCost = Double.parseDouble(order.get(2));
			customerId = Integer.parseInt(order.get(3));
			basket = new ArrayList<int[]>();
			if (customerId == 0)
				customer = null;
			else
				customer = RegisteredCustomer.searchById(customerId);
			cashierId = Integer.parseInt(order.get(4));
			cashier = (Cashier) User.searchUserById(cashierId);
			for (int i = 5 ; i < orders.size(); i += 2) {
				temp[0] = Integer.parseInt(order.get(i));
				temp[1] = Integer.parseInt(order.get(i+1));
				basket.add(temp);
			}
			new Order(orderNo, orderDate, totalCost, customer, cashier, basket);
		}
		
	}
}