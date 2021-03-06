package ERP_Core;

import java.util.Date;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * This class represents an order for resupplying the shop's storage, made in the ERP system.
 *
 * @author George Liargovas
 * @author Aggela Trivola
 */
public class StorageOrder {
  /** The counter for the unique orders created. */
  private static int counter = FileHandler.getStorageOrderCounterFromFile();
  /** The unique number of each order. */
  private final int storageOrderNumber;
  /** The date the order was made. */
  private final String storageOrderDate;
  /** The total cost of the order */
  private final double totalCost;
  /** The supplier object who the order concerned. */
  private final Supplier supplier;
  /** The storekeeper object who made the order. */
  private final Storekeeper storekeeper;
  /** The list of supplies that the order contains. */
  public ArrayList<int[]> supplies = new ArrayList<int[]>();
  /** The list of orders made */
  public static ArrayList<StorageOrder> orders = new ArrayList<StorageOrder>();

  /**
   * Constructor for creating a new order.
   *
   * @param storageOrderDate the date as a String when the order was created in "dd/MM/yyyy
   *     hh:mm:ss" format
   * @param totalCost the total cost of the basket products as double, in the same currency as the
   *     products
   * @param supplier the Supplier object of the order
   * @param storekeeper the Storekeeper object of the order, who made the order
   * @param supplies the products and their quantities added to the storage with the order
   */
  public StorageOrder(
      String storageOrderDate,
      Supplier supplier,
      Storekeeper storekeeper,
      ArrayList<int[]> supplies,
      double totalCost) {
    this.storageOrderNumber = ++counter;
    this.storageOrderDate = storageOrderDate;
    this.supplies = supplies;
    this.totalCost = totalCost;
    this.supplier = supplier;
    this.storekeeper = storekeeper;
    orders.add(this);
  }

  /**
   * Constructor for already created products read from .csv file. There is non need to calculate
   * the total cost again as it has been created.
   *
   * @param storageOrderNumber the id number that was assigned to the order when it was first
   *     created
   * @param storageOrderDate the date as a String when the order was created in "dd/MM/yyyy
   *     hh:mm:ss" format
   * @param totalCost the total cost of the basket products as double, in the same currency as the
   *     products
   * @param supplier the Supplier object of the order
   * @param storekeeper the Storekeeper object of the order, who made the order
   * @param supplies the list of the products resupplied by the order
   */
  public StorageOrder(
      int storageOrderNumber,
      String storageOrderDate,
      double totalCost,
      Supplier supplier,
      Storekeeper storekeeper,
      ArrayList<int[]> supplies) {
    this.storageOrderNumber = storageOrderNumber;
    this.storageOrderDate = storageOrderDate;
    this.totalCost = totalCost;
    this.supplier = supplier;
    this.storekeeper = storekeeper;
    this.supplies = supplies;
    orders.add(this);
  }

  /**
   * Returns the Date of the order.
   *
   * @return storageOrderDate, type String
   */
  public String getStorageOrderDate() {
    return storageOrderDate;
  }

  /**
   * Returns the total cost.
   *
   * @return TotalCost, type double
   */
  public double getTotalCost() {
    return totalCost;
  }

  /**
   * Returns the object supplier.
   *
   * @return the supplier
   */
  public Supplier getSupplier() {
    return supplier;
  }

  /**
   * Returns the object Storekeeper.
   *
   * @return storekeeper
   */
  public Storekeeper getStorekeeper() {
    return storekeeper;
  }

  /**
   * Returns the Number of the Order.
   *
   * @return storageOrderNumber, type int
   */
  public int getStorageOrderNumber() {
    return storageOrderNumber;
  }

  /**
   * Returns the supplies from the list.
   *
   * @return supplies
   */
  public ArrayList<int[]> getSupplies() {
    return supplies;
  }

  /**
   * Returns the arrayList which contains the orders storage.
   *
   * @return ArrayList with StorageOrder objects
   */
  public static ArrayList<StorageOrder> getOrders() {
    return orders;
  }

  /**
   * Returns the counter of the storage orders created.
   *
   * @return counter of orders as an int
   */
  public static int getIdCounter() {
    return counter;
  }

  /** Prints all the storage orders. */
  public static void printStorageOrderHistory() {
    int counter = 1;
    System.out.println("\n\n***Order History***\n");
    for (StorageOrder order : orders) {
      System.out.printf("-> Storage Order #%d\n", counter++);
      order.printStorageOrderFormatted();
    }
  }

  /**
   * Contains the menu for printing the order history from a specific supplier according to user
   * input.
   */
  public static void printOrderHistoryMenu() {
    Scanner in = new Scanner(System.in);
    int input;
    for (; ; ) {
      try {
        System.out.print("\nEnter the id of the supplier to get order history: ");
        input = in.nextInt();
        printOrderHistory(input);
        return;
      } catch (InputMismatchException e) {
        System.err.println("Invalid input given. Id must be an integer. Try again...");
      } catch (Exception e) {
        e.printStackTrace();
        System.err.println("Something went wrong. Returning to previous menu");
        return;
      }
    }
  }

  /**
   * Prints the orders of a supplier with a specific given id.
   *
   * @param id the id of the supplier
   */
  public static void printOrderHistory(int id) {
    int counter = 0;
    for (StorageOrder order : orders) {
      if (order.getSupplier() != null && order.getSupplier().getId() == id) {
        if (counter == 0) {
          System.out.printf("\nPrinting orders made from supplier with id %d:\n\n", id);
        }
        System.out.printf("\n-> Order #%d\n", ++counter);
        order.printStorageOrderFormatted();
      }
    }
    if (counter == 0) {
      System.out.printf("Customer with id %d has not made any orders\n", id);
    }
  }

  /**
   * Prints a product's quantity with a given id, in the format used to print the storage order
   * information.
   *
   * @param id the id of the product
   * @param quantity the quantity of the product
   */
  public static void printProduct(int id, int quantity) {
    Product prod = Storage.searchById(id);
    String name = prod.getName();
    System.out.printf(
        "Product id: %d | Product name: %s | Quantity: %d",
        prod.getProductId(), prod.getName(), quantity);
  }

  /**
   * Prints a preview of the order, in the appropriate format.
   *
   * @param storekeeper the Storekeeper who made the order
   * @param supplier the Supplier who the order concerns
   * @param supplies the supplies list that the supplier resupplied
   * @param totalCost the total cost of the order
   */
  public static void previewOrder(
      Storekeeper storekeeper, Supplier supplier, ArrayList<int[]> supplies, double totalCost) {
    int storekeeperId = storekeeper.getIdUser();
    System.out.println("***Preview of Storage Order***");
    System.out.println();
    System.out.println("---Storekeeper's Code: " + storekeeperId);
    System.out.println();
    System.out.println("---Supplier data");
    if (supplier != null) {
      System.out.println("Name: " + supplier.getName());
      System.out.println("Address: " + supplier.getAddress());
    } else {
      System.out.println("Supplier Deleted");
    }
    System.out.println("\n---Supplies List: ");
    for (int[] prod : supplies) {
      printProduct(prod[0], prod[1]);
      System.out.println();
    }
    System.out.println();
    System.out.println("---Storage order's total cost: " + totalCost);
  }

  /** Prints the storage order after it has been confirmed, in the appropriate format. */
  public void printFinalOrder() {
    System.out.println("\n***Final Order Report***");
    this.printStorageOrderFormatted();
    System.out.println("***End of report***\n");
  }

  /** Prints the storage order in the appropriate format. */
  public void printStorageOrderFormatted() {
    int storekeeperId = storekeeper.getIdUser();
    System.out.println();
    System.out.println();
    System.out.println("---Storage Order id: " + this.getStorageOrderNumber());
    System.out.println("---Storage Order Date: " + this.getStorageOrderDate());
    System.out.println("---Storekeeper's Code: " + storekeeperId);
    System.out.println("---Customer's data");
    if (supplier != null) {
      System.out.println("Name: " + this.supplier.getName());
      System.out.println("Address: " + this.supplier.getAddress());
    } else {
      System.out.println("Supplier Deleted");
    }
    System.out.println("\n---Product Basket: ");
    for (int[] prod : this.getSupplies()) {
      try {
        printProduct(prod[0], prod[1]);
      } catch (NoSuchElementException e) {
        System.out.println("This product has been removed from the storage.");
      }
      System.out.println();
    }
    System.out.println("---Order's total cost: " + totalCost);
  }

  /**
   * Contains the prompts in order to make a storage order.
   *
   * @param storekeeper the Storekeeper who made the order
   */
  public static void makeOrder(Storekeeper storekeeper) {
    Scanner in = new Scanner(System.in);
    String ans;
    double totalCost;
    Supplier supplier;
    ArrayList<int[]> supplies;
    int id;
    for (; ; ) {
      try {
        for (; ; ) {
          System.out.println("*** Storage Order Menu ***");
          System.out.print(
              "Enter the id of the supplier the order is about. " + "To cancel, press \"enter\": ");
          ans = in.nextLine();
          if (ans.equals("")) {
            System.out.println("Process cancelled, returning to previous menu...");
            return;
          }
          id = Integer.parseInt(ans);
          supplier = Supplier.searchById(id);
          if (supplier == null) {
            System.out.println("Registered customer with such id does not exist. Try again...");
            continue;
          }
          break;
        }
        supplies = fillBasket();
        for (; ; ) {
          try {
            System.out.print("Enter the total cost of the order: ");
            totalCost = in.nextDouble();
            in.nextLine();
            if (totalCost > 0) {
              break;
            } else {
              System.out.println("Price must be larger than 0. Try again...");
              continue;
            }
          } catch (InputMismatchException e) {
            System.err.println("Please enter a number. Try again...");
            in.nextLine();
          }
        }
        StorageOrder.previewOrder(storekeeper, supplier, supplies, totalCost);
        for (; ; ) {
          System.out.print("Confirm storge order? (Y/N): ");
          ans = in.nextLine();
          if (ans.toLowerCase().equals("y") || ans.toLowerCase().equals("yes")) {
            StorageOrder.confirmOrder(storekeeper, supplier, supplies, totalCost);
            return;
          } else if (ans.toLowerCase().equals("n") || ans.toLowerCase().equals("no")) {
            System.out.println("Process cancelled. Returning to previous menu...");
            return;
          }
          System.out.println(
              "Please enter either \"Yes\"(\"Y\") or \"No\"(\"N\").\n" + "Try again...");
        }
      } catch (NoSuchElementException e) {
        System.out.println("Supplier with such id does not exist. Try again...");
        continue;
      } catch (NumberFormatException e) {
        System.out.println("Invalid input given. Try again...");
        continue;
      }
    }
  }

  /**
   * Contains the prompts in order for the user to fill the order basket.
   *
   * @return the supplies of the order
   */
  public static ArrayList<int[]> fillBasket() {
    Scanner in = new Scanner(System.in);
    String input;
    ArrayList<int[]> tempBasket = new ArrayList<int[]>();
    int[] tempProduct = new int[2];
    boolean alreadyExists;
    for (; ; ) {
      try {
        tempProduct = new int[2];
        System.out.print(
            "Enter the id of the product you want to get.\n"
                + "Press only \"enter\" when you do not want to add any products: ");
        input = in.nextLine();
        if (input.equals("")) {
          return tempBasket;
        }
        tempProduct[0] = Integer.parseInt(input);
        System.out.print("Enter the quantity of the product you want to shop: ");
        tempProduct[1] = in.nextInt();
        in.nextLine();
        alreadyExists =
            checkIfProductAlreadyExistsAndAddQuantity(tempProduct[0], tempProduct[1], tempBasket);
        if (alreadyExists == false) {
          if (Storage.searchById(tempProduct[0]) != null) {
            tempBasket.add(tempProduct);
          } else {
            throw new NoSuchElementException();
          }
        }
      } catch (NumberFormatException e) {
        System.out.println("Product id must be an integer larger than 0. Try again...");
        in.nextLine();
      } catch (InputMismatchException e) {
        System.out.println("The quantity must be an integer larger than 0. Try again...");
        in.nextLine();
      } catch (NoSuchElementException e) {
        System.out.println("Product with such id does not exist. Try again...");
      }
    }
  }

  /**
   * Checks if a given product id exists in the given ArrayList. If it does, the existing's product
   * quantity is increased, otherwise, a new entry with the new id and quantity is created on the
   * ArrayList.
   *
   * @param id the product id to add
   * @param quantity the product quantity to add
   * @param supplies the supplies list already ordered
   * @return true if product exists already, else false
   */
  public static boolean checkIfProductAlreadyExistsAndAddQuantity(
      int id, int quantity, ArrayList<int[]> supplies) {
    for (int[] line : supplies) {
      if (line[0] == id) {
        line[1] += quantity;
        return true;
      }
    }
    return false;
  }

  /**
   * Makes the needed changes in the storage product quantities, and creates the order object with
   * the system's current date and time.
   *
   * @param storekeeper the storekeeper who made the order
   * @param supplier the supplier who the order concerns
   * @param supplies the supplies list
   * @param totalCost the total cost of the order
   */
  public static void confirmOrder(
      Storekeeper storekeeper, Supplier supplier, ArrayList<int[]> supplies, double totalCost) {
    for (int[] product : supplies) {
      try {
        // add products to storage
        Storage.addProductQuantity(product[0], product[1]);
      } catch (NoSuchElementException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    String orderDate = getCurrentDate();
    StorageOrder newOrder = new StorageOrder(orderDate, supplier, storekeeper, supplies, totalCost);
    newOrder.printFinalOrder();
  }

  /**
   * Utility method that returns the system's date and time as a String.
   *
   * @return the system's current date and time
   */
  public static String getCurrentDate() {
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    Date date = new Date();
    return dateFormatter.format(date);
  }

  /**
   * Creates a new Storage Order from an Arraylist of strings by parsing its contents.
   *
   * @param orders an ArrayList of Strings with the parsed contents of the storage orders file
   */
  public static void createStorageOrdersFromList(ArrayList<ArrayList<String>> orders) {
    int supplierId;
    int storekeeperId;
    int orderNo;
    double totalCost;
    ArrayList<int[]> supplies;
    String orderDate;
    Supplier supplier = null;
    Storekeeper storekeeper;
    int[] temp = null;
    for (ArrayList<String> order : orders) {
      supplies = new ArrayList<int[]>();
      orderNo = Integer.parseInt(order.get(0));
      orderDate = order.get(1);
      totalCost = Double.parseDouble(order.get(2));
      supplierId = Integer.parseInt(order.get(3));
      storekeeperId = Integer.parseInt(order.get(4));
      try {
        supplier = Supplier.searchById(supplierId);
      } catch (NoSuchElementException e) {
        supplier = null;
      }
      try {
        storekeeper = (Storekeeper) User.searchUserById(storekeeperId);
      } catch (NoSuchElementException e) {
        storekeeper = null;
      }
      for (int i = 5; i < order.size(); i += 2) {
        temp = new int[2];
        temp[0] = Integer.parseInt(order.get(i));
        temp[1] = Integer.parseInt(order.get(i + 1));
        supplies.add(temp);
      }
      new StorageOrder(orderNo, orderDate, totalCost, supplier, storekeeper, supplies);
    }
  }
}
