package toyproject.simulated_stock.domain.stock.order.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.math.BigDecimal;

@Entity
@Getter
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private BigDecimal balance; //잔고

    //==비즈니스 메소드==//
    //잔액 차감
    public void withdraw(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new RuntimeException("잔액이 부족합니다.");
        }
        this.balance = this.balance.subtract(amount);
    }

    //잔액 추가 메소드
    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }
}
