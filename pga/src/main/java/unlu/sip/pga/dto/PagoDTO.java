package unlu.sip.pga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    
    private String paymentId;
    private String dateCreated;
    private String dateApproved;
    private String moneyReleaseDate;
    private String status;
    private String statusDetail;
    private TransactionDetailsDTO transactionDetails;
    private Integer usuarioId;
}
