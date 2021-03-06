package ERP_Core;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This class represents the Store keeper. The store keeper got the ability to view information
 * about the products and the suppliers. There is also the ability to edit and create new suppliers
 * and to make a new resupply order.
 *
 * @version 1.0
 * @author Nick Mavromaras
 * @author George Liargovas
 */
public class Storekeeper extends User {
  /**
   * The constructor for creating a new Storekeeper object.
   *
   * @param name the name of the Storekeeper
   * @param surname the surname of the Storekeeper
   * @param username the username of the Storekeeper
   * @param password the password of the Storekeeper
   */
  public Storekeeper(String name, String surname, String username, String password) {
    super(name, surname, username, password);
  }
  
  /**
   * The constructor for loading a previously created Storekeeper object to the ERP system.
   *
   * @param idUser the id given to the Storekeeper the first time he was created.
   * @param name the name of the Storekeeper
   * @param surname the surname of the Storekeeper
   * @param username the username of the Storekeeper
   * @param password the password of the Storekeeper
   */
  public Storekeeper(int idUser, String name, String surname, String username, String password) {
    super(idUser, name, surname, username, password);
  }
  
  /**
   * Checks if the given user credentials exist in the list and if they do, the user is logged in
   * and the specific user menu is called.
   *
   * @param username the user's username as a String
   * @param password the user's password as a String
   * @throws Exception Exception in case the credentials do not match for any Storekeeper in the
   *     list
   */
  public static void login(String username, String password) throws Exception {
    Storekeeper storekeeper;
    for (User user : User.getUsers()) {
      if (user instanceof Storekeeper) {
        if (username.equals(user.getUsername()) && password.equals(user.getPassword())) {
          storekeeper = (Storekeeper) user;
          System.out.printf("Welcome %s %s!\n", storekeeper.getName(), storekeeper.getSurname());
          storekeeper.getMenu();
          return;
        }
      }
    }
    throw new Exception("Invalid Credentials");
  }
  
  /** Prints the Store keeper's menu. */
  private void printMenu() {
    System.out.print(
        "--- Storekeeper Menu ---\n"
            + "1) Storage\n"
            + "2) Suppliers\n"
            + "3) Make a new Storage Ressuply Order\n"
            + "4) Save changes\n"
            + "5) Logout and Return to Main Menu\n"
            + "Option: ");
  }
  
  /** Prints the product menu. */
  public void printStorageMenu() {
    System.out.print(
        "--- Product Menu ---\n"
            + "1) View All Products\n"
            + "2) Search for specific products (by product name)\n"
            + "3) Search for specific products (by product id)\n"
            + "4) Add a new product\n"
            + "5) Delete an existing product\n"
            + "6) Return to previous menu\n"
            + "Option: ");
  }
  
  /** Prints the suppliers menu. */
  public void printCustomerMenu() {
    System.out.print(
        "--- Supplier Menu ---\n"
            + "1)  View All Suppliers\n"
            + "2)  Search for Specific Suppliers (by name)\n"
            + "3)  Search for Specific Suppliers (by Supplier id)\n"
            + "4)  Search for Specific Suppliers (by telephone number)\n"
            + "5)  Add a new Supplier\n"
            + "6)  Delete a Supplier\n"
            + "7)  Edit Supplier Telephone\n"
            + "8)  Edit Supplier Address\n"
            + "9)  View Storage Ressuply Order History\n"
            + "10) View Storage Ressuply Order History from Specific Supplier\n"
            + "11) Return to Previous Menu\n"
            + "Option: ");
  }
  
  /**
   * The Storekeeper's menu. Several options are available. When the return option is selected, the
   * user is logged out and returned to the main menu.
   */
  @Override
  public void getMenu() {
    int ch;
    Scanner in = new Scanner(System.in);
    for (; ; ) {
      this.printMenu();
      try {
        ch = in.nextInt();
        switch (ch) {
          case 1:
            getStorageMenu();
            break;
          case 2:
            getSupplierMenu();
            break;
          case 3:
            StorageOrder.makeOrder(this);
            break;
          case 4:
        	Main.saveAllListsToCsv();
          	System.out.println("Changes saved!" );
          	break;
          case 5:
            return;
        }
      } catch (InputMismatchException e) {
        System.err.println("Your option must be an integer number. TTry again...");
        // clear scanner buffer
        in.nextLine();
      }
    }
  }
  
  /**
   * The product menu. Several options are available. When the return option is selected, the user
   * is returned to the previous menu.
   */
  public void getStorageMenu() {
    Scanner in = new Scanner(System.in);
    int ans;
    for (; ; ) {
      this.printStorageMenu();
      try {
        ans = in.nextInt();
        switch (ans) {
          case 1:
            Storage.printAllProductsWithQuantities();
            break;
          case 2:
            Storage.searchAndPrintProductsByNameMenu();
            break;
          case 3:
            Storage.searchAndPrintProductByIdMenu();
            break;
          case 4:
            Storage.createNewProductMenu();
            break;
          case 5:
            Storage.deleteProductMenu();
            break;
          case 6:
            return;
        }
      } catch (InputMismatchException e) {
        System.err.println("Your option must be an integer number. Try again...");
        // clear scanner buffer
        in.nextLine();
      }
    }
  }
  
  /**
   * The suppliers menu. Several options are available. When the return option is selected, the user
   * is returned to the previous menu.
   */
  public void getSupplierMenu() {
    Scanner in = new Scanner(System.in);
    int ans;
    for (; ; ) {
      this.printCustomerMenu();
      try {
        ans = in.nextInt();
        switch (ans) {
          case 1:
            Supplier.printAllSuppliers();
            break;
          case 2:
            Supplier.searchAndPrintSupplierBynameMenu();
            break;
          case 3:
            Supplier.searchAndPrintSupplierByidMenu();
            break;
          case 4:
            Supplier.searchAndPrintSupplierByTelephoneMenu();
            break;
          case 5:
            Supplier.registerNewSupplierMenu();
            break;
          case 6:
            Supplier.deleteSupplierMenu();
            break;
          case 7:
            Supplier.changeSupplierTelephoneMenu();
            break;
          case 8:
            Supplier.changeSupplierAddressMenu();
            break;
          case 9:
            StorageOrder.printStorageOrderHistory();
            break;
          case 10:
            StorageOrder.printOrderHistoryMenu();
            break;
          case 11:
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
