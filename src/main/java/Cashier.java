import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cashier extends User {
	
	public Cashier(String name, String surname, String username, String password) {
		super(name, surname, username, password);
	}
	
	public Cashier(int idUser, String name, String surname, String username, String password) {
		super(idUser, name, surname, username, password);
	}
	
	public static void login(String username, String password) throws Exception {
		Cashier cashier;
		for (User user : User.getUsers()) {
			if (user instanceof Cashier) {
				if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
					cashier = (Cashier)user;
					System.out.printf("Welcome %s %s!\n", cashier.getName(), cashier.getSurname());
					cashier.getMenu();
					return;
				}
			}
		}
		throw new Exception("Invalid Credentials");
	}
	
	public static void printProductMenu() {
		System.out.print("--- Product Menu ---\n"
				+ "1) View All Products\n"
				+ "2) Search for Specific Products (by product id)\n"
				+ "3) Search for Specific Products (by product name)\n"
				+ "4) Return to Previous Menu\n"
				+ "Option: ");
	}
	
	public static void printCustomerMenu() {
		System.out.print("--- Customer Menu ---\n"
				+ "1) View All Customers\n"
				+ "2) Search for Specific Customers (by customer name)\n"
				+ "3) Search for Specific Customer (by customer id)\n"
				+ "4) Search for Specific Customer (by telephone number)\n"
				+ "5) Add a new Customer\n"
				+ "6) Edit Customer Telephone\n"
				+ "7) Edit Customer Address\n"
				+ "8) Delete a Customer\n"
				+ "9) View Order History\n"
				+ "10) View Specific Customer Order History\n"
				+ "11) Return to Previous Menu\n"
				+ "Option: ");
	}
	
	public static void printMenu() {
		System.out.print("--- Cashier Menu ---\n"
				+ "1) Products\n"
				+ "2) Customers\n"
				+ "3) Make a New Customer Order\n"
				+ "4) Logout and Return to Main Menu\n"
				+ "Option: ");
	}
	
	public static void printNewOrderMenu() {
		System.out.print("--- New Order Menu ---\n"
				+ "Who does the order concern?\n"
				+ "1) Already Registered Customer\n"
				+ "2) Guest Customer (not registered)\n"
				+ "3) Return To Previous Menu\n"
				+ "Option: ");
	}
	
	public void getProductMenu() {
		Scanner in = new Scanner(System.in);
		int ans;
		for(;;) {
			printProductMenu();
			try {
				ans = in.nextInt();
				switch (ans) {
				case 1:
					Storage.printAllProductsWithQuantities();
					break;
				case 2:
					Storage.searchAndPrintProductByIdMenu();
					break;
				case 3:
					Storage.searchAndPrintProductsByNameMenu();
					break;
				case 4:
					in.close();
					return;
				}
			} catch (InputMismatchException e) {
				System.err.println("Your option must be an integer number. Try again...");
				// clear scanner buffer
				in.nextLine();
			}
		}
	}
	
	public void getCustomerMenu() {
		Scanner in = new Scanner(System.in);
		int ans;
		for(;;) {
			printCustomerMenu();
			try {
				ans = in.nextInt();
				switch (ans) {
				case 1:
					RegisteredCustomer.printAllCustomers();
					break;
				case 2:
					RegisteredCustomer.searchAndPrintCustomerByNameMenu();
					break;
				case 3:
					RegisteredCustomer.searchAndPrintCustomerByIdMenu();
					break;
				case 4:
					RegisteredCustomer.searchAndPrintCustomerByTelephoneMenu();
					break;
				case 5:
					RegisteredCustomer.registerNewCustomerMenu();
					break;
				case 6:
					RegisteredCustomer.changeCustomerTelephoneMenu();
					break;
				case 7:
					RegisteredCustomer.changeCustomerAddressMenu();
					break;
				case 8:
					RegisteredCustomer.deleteCustomerMenu();
					break;
				case 9:
					//TODO method that displays order history
					break;
				case 10:
					//TODO method that displays specific customer order history
					break;
				case 11:
					in.close();
					return;
				}
			} catch (InputMismatchException e) {
				System.err.println("Your option must be an integer number. Try again...");
				// clear scanner buffer
				in.nextLine();
			}
		}
	}
	
	public void makeOrderMenu() {
		Scanner in = new Scanner(System.in);
		int ans;
		for(;;) {
			printNewOrderMenu();
			try {
				ans = in.nextInt();
				switch (ans) {
				case 1:
					Order.makeOrder(this, true);
					break;
				case 2:
					Order.makeOrder(this, false);
					break;
				case 3:
					in.close();
					return;
				}
			} catch (InputMismatchException e) {
				System.err.println("Your option must be an integer number. Try again...");
				// clear scanner buffer
				in.nextLine();
			}
		}
	}
	
	@Override
	public void getMenu() {
		Scanner in = new Scanner(System.in);
		int ans;
		for(;;) {
			printMenu();
			try {
				ans = in.nextInt();
				switch (ans) {
				case 1:
					getProductMenu();
					break;
				case 2:
					getCustomerMenu();
					break;
				case 3:
					makeOrderMenu();
					break;
				case 4:
					in.close();
					return;
				}
			} catch (InputMismatchException e) {
				System.err.println("Your option must be an integer number. Try again...");
				// clear scanner buffer
				in.nextLine();
			}
		}
	}
}