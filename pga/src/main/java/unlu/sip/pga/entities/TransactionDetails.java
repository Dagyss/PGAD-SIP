package unlu.sip.pga.entities;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transaction_details")
public class TransactionDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "net_received_amount", nullable = false)
    private BigDecimal netReceivedAmount;

    @Column(name = "total_paid_amount", nullable = false)
    private BigDecimal totalPaidAmount;
}
