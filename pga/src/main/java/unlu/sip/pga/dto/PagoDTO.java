package unlu.sip.pga.dto;

public class PagoDTO {
    
    private String payment_id;
    private String date_created;
    private String date_approved;
    private String money_release_date;
    private String status;
    private String status_detail;
    private TransactionDetailsDTO transaction_details;

    public PagoDTO() {
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getDate_approved() {
        return date_approved;
    }

    public void setDate_approved(String date_approved) {
        this.date_approved = date_approved;
    }

    public String getMoney_release_date() {
        return money_release_date;
    }

    public void setMoney_release_date(String money_release_date) {
        this.money_release_date = money_release_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_detail() {
        return status_detail;
    }

    public void setStatus_detail(String status_detail) {
        this.status_detail = status_detail;
    }

    public TransactionDetailsDTO getTransaction_details() {
        return transaction_details;
    }

    public void setTransaction_details(TransactionDetailsDTO transaction_details) {
        this.transaction_details = transaction_details;
    }
}
