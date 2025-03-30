package NgocHieu.GHTKService; // Bạn có thể đổi tên package nếu cần

public enum EnumStatusGHTK {
    CANCELLED(-1, "Hủy đơn hàng"),
    NOT_RECEIVED_YET(0, "Chưa tiếp nhận"),
    RECEIVED(1, "Đã tiếp nhận"),
    PICKED_UP(2, "Đã lấy hàng/Đã nhập kho"),
    IN_TRANSIT(3, "Đã điều phối giao hàng/Đang giao hàng"),
    DELIVERED_OR_NOT_SCANNED(4, "Đã giao hàng/Chưa đối soát"),
    SCANNED(5, "Đã đối soát"),
    FAILED_PICKUP(6, "Không lấy được hàng"),
    RETURNED_PICKUP(7, "Hoãn lấy hàng"),
    FAILED_DELIVERY(8, "Không giao được hàng"),
    DELIVERY_DELAYED(9, "Delay giao hàng"),
    RETURNED_AND_SCANNED(10, "Đã đối soát công nợ trả hàng"),
    PICKUP_DISPATCHED(11, "Đã điều phối lấy hàng/Đang lấy hàng"),
    ORDER_RETURNED(12, "Đơn hàng bị hoàn"),
    RETURNING(13, "Đang trả hàng (COD cầm hàng đi trả)"),
    RETURNED(20, "Đã trả hàng (COD đã trả xong hàng)"),
    SHIPPER_PICKED_UP(123, "Shipper báo đã lấy hàng"),
    SHIPPER_CANNOT_PICKUP(127, "Shipper (nhân viên lấy/giao hàng) báo không lấy được hàng"),
    SHIPPER_DELAY_PICKUP(128, "Shipper báo delay lấy hàng"),
    SHIPPER_DELIVERED(45, "Shipper báo đã giao hàng"),
    SHIPPER_CANNOT_DELIVER(49, "Shipper báo không giao được giao hàng"),
    SHIPPER_DELAY_DELIVERY(410, "Shipper báo delay giao hàng");

    private final int statusId;
    private final String statusName;

    EnumStatusGHTK(int statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }

    public int getStatusId() {
        return statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public static EnumStatusGHTK fromId(int id) {
        for (EnumStatusGHTK status : values()) {
            if (status.statusId == id) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Status ID: " + id);
    }

    @Override
    public String toString() {
        return statusName + " (ID: " + statusId + ")";
    }
}
