/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NgocHieu.GHTKService;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderStatusResponse {
    @JsonProperty("success")
    boolean success;
    
    @JsonProperty("message")
    String message;
    
    @JsonProperty("order") // Map với label_id trong JSON
    OrderStatusInfo order;
}
