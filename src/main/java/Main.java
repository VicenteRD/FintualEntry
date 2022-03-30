import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {

    private static final String DATE_FORMAT;
    private static final String HUMAN_DATE_FORMAT;
    private static final DateTimeFormatter DATE_FORMATTER;

    static {
        DATE_FORMAT = "dd/MM/yyyy";
        HUMAN_DATE_FORMAT = DATE_FORMAT.toLowerCase();
        DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);
    }

    private static Portfolio portfolio = new Portfolio();

    public static void main(String[] args) {
        portfolio = new Portfolio();

        Scanner scanner = new Scanner(System.in);

        System.out.printf("Please enter the start date for the simulation (%s): ", HUMAN_DATE_FORMAT);
        LocalDate start = persistentDateInput(scanner);

        System.out.printf("Please enter the end date for the simulation (%s): ", HUMAN_DATE_FORMAT);
        LocalDate end = persistentDateInput(scanner);

        if (end.isBefore(start)) {
            System.err.println("Invalid dates, the end date cannot be before the start.");
            return;
        } else if (end.isEqual(start)) {
            System.err.println("Invalid dates, both are equal.");
            return;
        }

        double startingCapital = portfolio.fundsAtDate(start);
        double profit = portfolio.profit(startingCapital, end);
        double profitPercent = (profit / startingCapital) * 100;
        double finalFunds = startingCapital + profit;
        double annualizedReturnPercent = portfolio.annualizedReturn(start, end) * 100;

        System.out.printf("Your starting capital: $%.2f\n", startingCapital);
        System.out.printf("Your final funds: $%.2f\n", finalFunds);
        System.out.printf("Your profit for the given period is: $%.2f (%.2f%%)\n", profit, profitPercent);
        System.out.printf("With an annualized return of: %.2f%%\n", annualizedReturnPercent);
    }

    private static LocalDate parseDate(String input) {
        try {
            return LocalDate.parse(input, DATE_FORMATTER);
        } catch (NullPointerException ex) {
            System.err.println("Input not found.");
        } catch (RuntimeException ex) {
            System.err.println("Invalid input, please try again.");
        }
        return null;
    }

    private static LocalDate persistentDateInput(Scanner scanner) {
        LocalDate input;
        do {
            String startInput = scanner.nextLine();
            input = parseDate(startInput);
        } while (input == null);
        return input;
    }
}
