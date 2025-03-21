/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NgocHieu.GHTKService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // Bỏ qua các field không cần
public class OrderStatusInfo {
    @JsonProperty("label_id") 
    private String labelId;  // Đổi tên biến để khớp với Java convention

    @JsonProperty("partner_id")
    private String partnerId;

    @JsonProperty("status")
    private int status;

    // Getters và Setters
    public String getLabelId() { return labelId; }
    public void setLabelId(String labelId) { this.labelId = labelId; }

    public String getPartnerId() { return partnerId; }
    public void setPartnerId(String partnerId) { this.partnerId = partnerId; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "labelId='" + labelId + '\'' +
                ", partnerId='" + partnerId + '\'' +
                ", status=" + status +
                '}';
    }
}
