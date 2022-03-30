import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Stock {

    private final StockSimulationType simulationType;
    private final double amplitudeFactor;

    private final Map<Long, Double> simulatedBuffer = new HashMap<>();

    public Stock(StockSimulationType simulationType) {
        this(simulationType, 1);
    }

    public Stock(StockSimulationType simulationType, double amplitudeFactor) {
        this.simulationType = simulationType;
        this.amplitudeFactor = amplitudeFactor;
    }

    /**
     * Returns the price of the stock at a given date.
     *
     * @param at The date to get the price for.
     * @return The price at the given date.
     */
    public double price(LocalDate at) {
        long epochDay = at.toEpochDay();
        if (simulatedBuffer.containsKey(epochDay)) {
            return simulatedBuffer.get(epochDay);
        }

        double price = this.simulate(at.getDayOfMonth(), at.getMonthValue(), at.getYear());
        simulatedBuffer.put(epochDay, price);
        return price;
    }

    /**
     * Simple simulation of the stock's price given a simulation type, a day, a month and a year.
     * This is merely for simplicity's and demonstration's sake.
     * Simulated prices are stored in a buffer and associated to the date, so the price for a given date is always
     * the same.
     *
     * @return The simulated price given the information
     */
    private double simulate(int day, int month, int year) {
        double absoluteDay = (year * 365) * (month * 30) + day;
        switch (simulationType) {
            case POSITIVE_LINEAR:
                return absoluteDay;
            case NEGATIVE_LINEAR:
                return 1000000 - absoluteDay;
            case SQUARE:
                return Math.pow((year / 10d) + month + day, 2);
            case RANDOM:
                // fall-through
            default:
                double dateFactor = (year / 100d) + (10 * month) + (5 * day);
                return amplitudeFactor * dateFactor * Math.random();
        }
    }
}
