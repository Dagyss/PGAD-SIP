package unlu.sip.pga.dto;

import java.math.BigDecimal;

public class TransactionDetailsDTO {

    private BigDecimal net_received_amount;
    private BigDecimal total_paid_amount;

    public TransactionDetailsDTO() {
    }

    public BigDecimal getNet_received_amount() {
        return net_received_amount;
    }

    public void setNet_received_amount(BigDecimal net_received_amount) {
        this.net_received_amount = net_received_amount;
    }

    public BigDecimal getTotal_paid_amount() {
        return total_paid_amount;
    }

    public void setTotal_paid_amount(BigDecimal total_paid_amount) {
        this.total_paid_amount = total_paid_amount;
    }
}
