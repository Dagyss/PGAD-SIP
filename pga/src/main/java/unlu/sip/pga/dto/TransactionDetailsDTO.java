package unlu.sip.pga.dto;

public class TransactionDetailsDTO {

    private Integer net_received_amount;
    private Integer total_paid_amount;

    public TransactionDetailsDTO() {
    }

    public Integer getNet_received_amount() {
        return net_received_amount;
    }

    public void setNet_received_amount(Integer net_received_amount) {
        this.net_received_amount = net_received_amount;
    }

    public Integer getTotal_paid_amount() {
        return total_paid_amount;
    }

    public void setTotal_paid_amount(Integer total_paid_amount) {
        this.total_paid_amount = total_paid_amount;
    }
}
