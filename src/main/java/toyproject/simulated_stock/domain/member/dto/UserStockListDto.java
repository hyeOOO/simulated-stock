package toyproject.simulated_stock.domain.member.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import toyproject.simulated_stock.domain.stock.order.entitiy.MarketType;
import toyproject.simulated_stock.domain.stock.order.entitiy.UserAccount;
import toyproject.simulated_stock.domain.stock.order.entitiy.UserStock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStockListDto {
    private String memberId;
    private String stockCode;
    private String stockName;      // 주식 종목 이름
    private int quantity;          // 보유 주식 수량
    private BigDecimal avgPrice;  // 매수 평균가
    private BigDecimal minPrice; // 최소 매수가
    private BigDecimal maxPrice; // 최대 매수가
    private BigDecimal totalPrice; //매수시 가격과 개수에 따른 누적합
    private MarketType mrtgType; // KOSPI, KOSDAQ, KONEX

    public static UserStockListDto fromEntity(UserStock userStock){
        UserStockListDto dto = new UserStockListDto();
        dto.memberId = userStock.getMemberId();
        dto.stockCode = userStock.getStockCode();
        dto.stockName = userStock.getStockName();
        dto.quantity = userStock.getQuantity();
        dto.avgPrice = userStock.getAvgPrice();
        dto.minPrice = userStock.getMinPrice();
        dto.maxPrice = userStock.getMaxPrice();
        dto.totalPrice = userStock.getTotalPrice();
        dto.mrtgType = userStock.getMrtgType();

        return dto;
    }
}
