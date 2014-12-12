package api.model;


import java.util.Map;

public class RentalCost {

    public enum INTERVAL {
        DAY, WEEK, MONTH;
    }

    private Integer totalCost;
    private Map<INTERVAL, Integer> costInterval;

    public RentalCost() {}

    public Integer getTotalCost() {
        return totalCost;
    }

    public Map<INTERVAL, Integer> getCostInterval() {
        return costInterval;
    }

    public RentalCost setCostInterval(Map<INTERVAL, Integer> costInterval) {
        this.costInterval = costInterval;
        return this;
    }

    public RentalCost setTotalCost(Integer totalCost) {
        this.totalCost = totalCost;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(", totalCost='").append(totalCost).append("'");


        if (this.costInterval != null && this.costInterval.size() > 0) {
            sb.append(", costInterval= [");
            for (INTERVAL interval : this.costInterval.keySet()) {
                sb.append("{ interval='").append(interval.toString()).append("'},");
            }
            sb.append("]");
        }
        sb.append("}");

        return sb.toString();
    }

}
