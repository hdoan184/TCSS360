import java.math.BigDecimal;
import java.text.NumberFormat;

/**
 * A energy bill class that is used for tracking savings on a month to month basis.
 * Created for TCSS 360 - Winter 2018
 * @author
 */
public class EnergyBill {
    /**
     * The month the energy bill is for.
     */
    private String month;

    /**
     * The energy bill cost of the given month.
     */
    private BigDecimal cost;

    /**
     * Creates a new energy bill
     * @param theMonth the month the energy bill is for
     * @author
     */
    public EnergyBill(String theMonth)  {
        month = theMonth;
        cost = BigDecimal.ZERO;
    }

    /**
     * Alternative contstructor for making an energy bill from both a given month and cost
     * @param theMonth the month of the energy bill
     * @param theCost the cost of the energy bill
     * @author
     */
    public EnergyBill(String theMonth, String theCost)  {
        month = theMonth;
        cost = new BigDecimal(theCost);
    }

    /**
     * Updates the cost of the energy bill
     * @param theCost the new cost of the energy bill
     * @author
     */
    public void setCost(BigDecimal theCost)  {
        cost = theCost;
    }

    /**
     * Returns the cost of the given energy bill
     * @return cost of the given energy bill
     * @author
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * Returns the month of the given energy bill
     * @return month of the given energy bill
     * @author
     */
    public String getMonth()    {
        return month;
    }

    /**
     * Returns a pretty string version of the bill cost, with a dollar sign and decimal.
     * @author
     * @return Currency string version of the cost
     */
    public String getDollarCost() {
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        String money = fmt.format(Double.parseDouble(getCost().toString()));
        return money;
    }

    /**
     * Updates the month of the energy bill
     * @param theMonth the new month of the energy bill
     * @author
     */
    public void setMonth(String theMonth)   {
        month = theMonth;
    }

    /**
     * A to string method that returns the bill in a particular format used for saving/loading.
     * Format: "{month}%$%{cost}
     * @author
     * @return String representation of the energy bill in a particular format
     */
    public String toString()    {
        return getMonth() + "%$%" + getCost().toString();
    }

}
