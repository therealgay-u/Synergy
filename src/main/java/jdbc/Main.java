package jdbc;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AdminService adminService = new AdminService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to City Maintenance System");
        System.out.println("1. Login");
        System.out.println("2. Signup");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (adminService.login(username, password)) {
                System.out.println("Login successful!");
                /*
                System.out.println("1. Assign Workers");
                System.out.println("2. Check Street Lights");
                int action = scanner.nextInt();
               
                if (action == 1) {
                    adminService.assignWorkers();
                }
              
                if (action == 2) {
                    adminService.checkStreetLights();
                }
                */
            } else {
                System.out.println("Invalid username or password.");
            }
        } else if (choice == 2) {
        	System.out.print("Enter product key: ");
            String productKey = scanner.nextLine();
            
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            System.out.print("Enter email: ");
            String email = scanner.nextLine();

            adminService.signup(username, password, name, email, productKey);
	         
        } 
        else {
            System.out.println("Invalid option.");
        }

        scanner.close();
    }
}
