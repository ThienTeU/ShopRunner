/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NgocHieu.GHTKService;

import com.fasterxml.jackson.annotation.JsonProperty;

class CancelResponse {
    @JsonProperty("success")
    String success;
    
    @JsonProperty("message")
    String message;

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "CancelResponse{" + "success=" + success + ", message=" + message + '}';
    }
    
}
