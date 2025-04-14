import java.util.Scanner;

public class BankingSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        User user = new User();
        String username = "";

        while (true) {
            System.out.println("\n1. Register\n2. Login\n3. Exit");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();
            sc.nextLine(); // consume newline

            if (option == 1) {
                System.out.print("Enter username: ");
                String uname = sc.nextLine();
                System.out.print("Enter password: ");
                String pass = sc.nextLine();
                if (user.register(uname, pass)) {
                    System.out.println("Registration successful!");
                } else {
                    System.out.println("Registration failed.");
                }
            } else if (option == 2) {
                System.out.print("Enter username: ");
                String uname = sc.nextLine();
                System.out.print("Enter password: ");
                String pass = sc.nextLine();
                if (user.login(uname, pass)) {
                    System.out.println("Login successful!");
                    username = uname;
                    break;
                } else {
                    System.out.println("Invalid credentials.");
                }
            } else {
                System.out.println("Exiting...");
                System.exit(0);
            }
        }

        // Logged-in user options
        while (true) {
            System.out.println("\n1. Check Balance\n2. Deposit\n3. Withdraw\n4. Logout");
            System.out.print("Choose an option: ");
            int opt = sc.nextInt();

            try {
                if (opt == 1) {
                    double bal = user.getBalance(username);
                    System.out.println("Balance: $" + bal);
                } else if (opt == 2) {
                    System.out.print("Enter amount to deposit: ");
                    double amt = sc.nextDouble();
                    user.deposit(username, amt);
                    System.out.println("Deposited successfully.");
                } else if (opt == 3) {
                    System.out.print("Enter amount to withdraw: ");
                    double amt = sc.nextDouble();
                    user.withdraw(username, amt);
                    System.out.println("Withdrawn successfully.");
                } else if (opt == 4) {
                    System.out.println("Logging out...");
                    break;
                } else {
                    System.out.println("Invalid option.");
                }
            } catch (Exception e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }

        sc.close();
    }
}
