package unlu.sip.pga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {
    
    private String payment_id;
    private String date_created;
    private String date_approved;
    private String money_release_date;
    private String status;
    private String status_detail;
    private TransactionDetailsDTO transaction_details;
    private Integer idUsuario;
}
