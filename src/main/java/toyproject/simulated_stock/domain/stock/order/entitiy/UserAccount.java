package toyproject.simulated_stock.domain.stock.order.entitiy;

import jakarta.persistence.*;
import lombok.Getter;
import toyproject.simulated_stock.api.exception.BusinessLogicException;
import toyproject.simulated_stock.api.exception.ExceptionCode;
import toyproject.simulated_stock.domain.member.entity.Member;

import java.math.BigDecimal;

@Entity
@Getter
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")  // Member와 연관 설정
    private Member member;

    private BigDecimal balance; //잔고

    //==비즈니스 메소드==//
    //잔액 차감
    public void withdraw(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new BusinessLogicException(ExceptionCode.NOT_ENOUGH_MONEY);
        }
        this.balance = this.balance.subtract(amount);
    }

    //잔액 추가 메소드
    public void deposit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    // UserAccount 생성 시 초기 값 설정
    public static UserAccount createAccount(Member member, BigDecimal initialBalance) {
        UserAccount account = new UserAccount();
        account.member = member;
        account.balance = initialBalance;
        return account;
    }
}
