
package NgocHieu.GHTKService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponseInfo {
    @JsonProperty("partner_id")
    private String orderId;
    
    private String label;
    private int area;
    private int fee;
    
    @JsonProperty("insurance_fee")
    private int insuranceFee;

    @JsonProperty("estimated_pick_time")
    private String estimatedPickTime;

    @JsonProperty("estimated_deliver_time")
    private String estimatedDeliverTime;

    @JsonProperty("tracking_id")
    private long trackingId;

    @JsonProperty("sorting_code")
    private String sortingCode;

    @JsonProperty("status_id")
    private int statusId;

    @JsonProperty("package_id")
    private String packageId;

    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public int getArea() { return area; }
    public void setArea(int area) { this.area = area; }

    public int getFee() { return fee; }
    public void setFee(int fee) { this.fee = fee; }

    public int getInsuranceFee() { return insuranceFee; }
    public void setInsuranceFee(int insuranceFee) { this.insuranceFee = insuranceFee; }

    public String getEstimatedPickTime() { return estimatedPickTime; }
    public void setEstimatedPickTime(String estimatedPickTime) { this.estimatedPickTime = estimatedPickTime; }

    public String getEstimatedDeliverTime() { return estimatedDeliverTime; }
    public void setEstimatedDeliverTime(String estimatedDeliverTime) { this.estimatedDeliverTime = estimatedDeliverTime; }

    public long getTrackingId() { return trackingId; }
    public void setTrackingId(long trackingId) { this.trackingId = trackingId; }

    public String getSortingCode() { return sortingCode; }
    public void setSortingCode(String sortingCode) { this.sortingCode = sortingCode; }

    public int getStatusId() { return statusId; }
    public void setStatusId(int statusId) { this.statusId = statusId; }

    public String getPackageId() { return packageId; }
    public void setPackageId(String packageId) { this.packageId = packageId; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("OrderInfo{");
        sb.append("orderId=").append(orderId);
        sb.append(", label=").append(label);
        sb.append(", area=").append(area);
        sb.append(", fee=").append(fee);
        sb.append(", insuranceFee=").append(insuranceFee);
        sb.append(", estimatedPickTime=").append(estimatedPickTime);
        sb.append(", estimatedDeliverTime=").append(estimatedDeliverTime);
        sb.append(", trackingId=").append(trackingId);
        sb.append(", sortingCode=").append(sortingCode);
        sb.append(", statusId=").append(statusId);
        sb.append(", packageId=").append(packageId);
        sb.append('}');
        return sb.toString();
    }
    
    
}

