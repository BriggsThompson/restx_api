package api.model;


public class RentalCost {

    public enum INTERVAL {
        DAY, WEEK, MONTH;
    }

    private INTERVAL interval;
    private Integer cost;
    private Integer totalCost;

    public RentalCost(INTERVAL interval, Integer cost, Integer totalCost) {
        this.interval = interval;
        this.cost = cost;
        this.totalCost = totalCost;
    }

    public INTERVAL getInterval() {
        return interval;
    }

    public void setInterval(INTERVAL interval) {
        this.interval = interval;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("RentalCost{")
                .append("interval='").append(interval.toString()).append('\'')
                .append(", cost='").append(cost).append('\'')
                .append(", totalCost='").append(totalCost).append('\'')
                .append("}").toString();
    }

}
