package unlu.sip.pga.pago;

public class PagoRequest {
    private Double transactionAmount;
    private String token;
    private String description;
    private Integer installments;
    private String paymentMethodId;
    private PayerRequest payer;

        // Getters y setters
    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInstallments() {
        return installments;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public PayerRequest getPayer() {
        return payer;
    }

    public void setPayer(PayerRequest payer) {
        this.payer = payer;
    }

}