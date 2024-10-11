package toyproject.simulated_stock.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.mapstruct.ap.internal.model.GeneratedType;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stockCode;
    private Long userId;

    private LocalDateTime createdDate;

    public static Favorite createFavorite(String stockCode, Long userId) {
        Favorite favorite = new Favorite();
        favorite.stockCode = stockCode;
        favorite.userId = userId;
        favorite.createdDate = LocalDateTime.now();
        return favorite;
    }
}
