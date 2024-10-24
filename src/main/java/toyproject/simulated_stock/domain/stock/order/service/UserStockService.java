package toyproject.simulated_stock.domain.stock.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toyproject.simulated_stock.api.auth.exception.AuthException;
import toyproject.simulated_stock.domain.member.entity.Member;
import toyproject.simulated_stock.domain.member.repository.FavoriteRepository;
import toyproject.simulated_stock.domain.member.repository.MemberRepository;
import toyproject.simulated_stock.domain.stock.order.repository.StockOrderRepository;
import toyproject.simulated_stock.domain.stock.order.repository.UserAccountRepository;
import toyproject.simulated_stock.domain.stock.order.repository.UserStockRepository;

import java.math.BigDecimal;

import static toyproject.simulated_stock.api.exception.ErrorCode.MEMBER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserStockService {


}
