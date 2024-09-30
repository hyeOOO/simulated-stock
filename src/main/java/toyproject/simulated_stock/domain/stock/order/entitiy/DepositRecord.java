package toyproject.simulated_stock.domain.stock.order.entitiy;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
public class DepositRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;  // 사용자 ID

    private BigDecimal amount;  // 충전 금액

    private LocalDateTime depositTime;  // 충전 시간

    private String paymentMethod;  // 결제 방식 (카드, 계좌이체 등)

    @ManyToOne
    @JoinColumn(name = "user_account_id")
    private UserAccount userAccount;  // 충전이 발생한 계좌와의 연관 관계

    @Enumerated(EnumType.STRING)
    private DepositStatus status;  // 충전 상태 (완료, 취소 등)
}
