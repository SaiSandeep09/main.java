import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class OnlineReservationSystem {

    private static Map<String, String> userCredentials = new HashMap<>();
    private static Map<String, ReservationSystem> usersReservations = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeUsers();
        loginPage();
    }

    private static void initializeUsers() {
        // Predefined user credentials
        userCredentials.put("Sandeep", "cutm@1234");
        userCredentials.put("user2", "password2");

        // Initialize reservation systems for users
        for (String username : userCredentials.keySet()) {
            usersReservations.put(username, new ReservationSystem());
        }
    }

    private static void loginPage() {
        System.out.println("Welcome to the Online Reservation System!");

        int loginAttempts = 3;

        while (loginAttempts > 0) {
            System.out.print("Enter username: ");
            String username = scanner.next();
            System.out.print("Enter password: ");
            String password = scanner.next();

            if (validateCredentials(username, password)) {
                System.out.println("Login successful!");
                displayMainMenu(username);
                break;
            } else {
                System.out.println("Invalid username or password. Please try again.");
                loginAttempts--;
            }
        }

        if (loginAttempts == 0) {
            System.out.println("Too many unsuccessful login attempts. Exiting.");
        }
    }

    private static boolean validateCredentials(String username, String password) {
        return userCredentials.containsKey(username) && userCredentials.get(username).equals(password);
    }

    private static void displayMainMenu(String username) {
        while (true) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. View Seat Map");
            System.out.println("2. Reserve Seat");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. Logout");

            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        viewSeatMap(username);
                        break;
                    case 2:
                        reserveSeat(username);
                        break;
                    case 3:
                        cancelReservation(username);
                        break;
                    case 4:
                        System.out.println("Logout successful!");
                        return;
                    default:
                        System.out.println("Invalid option! Please enter a number between 1 and 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // consume the invalid input
            }
        }
    }

    private static void viewSeatMap(String username) {
        usersReservations.get(username).viewSeatMap();
    }

    private static void reserveSeat(String username) {
        usersReservations.get(username).reserveSeat(scanner);
    }

    private static void cancelReservation(String username) {
        usersReservations.get(username).cancelReservation(scanner);
    }
}

class Seat {
    private final String seatNumber;
    private final double price;
    private final String prize;
    private boolean reserved;

    public Seat(String seatNumber, double price, String prize) {
        this.seatNumber = seatNumber;
        this.price = price;
        this.prize = prize;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public String getPrize() {
        return prize;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }
}
class ReservationSystem {
    private static final int NUM_SEATS = 10;
    private static final boolean[] seats = new boolean[NUM_SEATS];

    public void viewSeatMap() {
        System.out.println("\nCurrent Seat Map:");
        for (int i = 0; i < NUM_SEATS; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();

        for (boolean seat : seats) {
            System.out.print(seat ? "X " : "O ");
        }
        System.out.println();
    }

    public void reserveSeat(Scanner scanner) {
        System.out.print("\nEnter seat number (1-10): ");
        int seatNumber;
        try {
            seatNumber = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // consume the invalid input
            return;
        }

        if (isValidSeatNumber(seatNumber)) {
            if (seats[seatNumber - 1]) {
                System.out.println("Seat already reserved!");
            } else {
                seats[seatNumber - 1] = true;
                Seat reservedSeat = new Seat(Integer.toString(seatNumber), 550,"Congratulations Seat reserved!");
                System.out.println("Pay Rs.550 to reserve seat ,  " + reservedSeat.getPrize());
            }
        } else {
            System.out.println("Invalid seat number! Please enter a number between 1 and 10.");
        }
    }

    public void cancelReservation(Scanner scanner) {
        System.out.print("\nEnter seat number (1-10): ");
        int seatNumber;
        try {
            seatNumber = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // consume the invalid input
            return;
        }

        if (isValidSeatNumber(seatNumber)) {
            if (!seats[seatNumber - 1]) {
                System.out.println("Seat not reserved!");
            } else {
                seats[seatNumber - 1] = false;
                System.out.println("Reservation canceled!");
            }
        } else {
            System.out.println("Invalid seat number! Please enter a number between 1 and 10.");
        }
    }

    private static boolean isValidSeatNumber(int seatNumber) {
        return seatNumber >= 1 && seatNumber <= NUM_SEATS;
    }
}