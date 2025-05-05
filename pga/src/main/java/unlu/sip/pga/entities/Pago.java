package unlu.sip.pga.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "pago")
public class Pago {
    @Id
    @Column(name = "payment_id", length = 100, nullable = false, unique = true)
    private String id;

    @Column(name = "date_time_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTimeCreated;

    @Column(name = "date_time_approved")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTimeApproved;

    @Column(name = "money_release_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date moneyReleaseDate;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "status_detail", length = 200)
    private String statusDetail;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transaction_details_id", referencedColumnName = "id")
    private TransactionDetails transactionDetails;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;
}