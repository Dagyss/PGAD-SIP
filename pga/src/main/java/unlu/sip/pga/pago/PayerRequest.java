package unlu.sip.pga.pago;

public class PayerRequest {
    private String email;
    private String firstName;
    private IdentificationRequest identification;

    // Getters y setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public IdentificationRequest getIdentification() {
        return identification;
    }

    public void setIdentification(IdentificationRequest identification) {
        this.identification = identification;
    }
}