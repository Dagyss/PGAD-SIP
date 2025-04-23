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
    private String status_detail;
    private TransactionDetailsDTO transaction_details;
    private Integer idUsuario;
}
