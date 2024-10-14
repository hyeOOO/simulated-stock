package toyproject.simulated_stock.domain.stock.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toyproject.simulated_stock.domain.stock.order.repository.UserStockRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserStockService {
    private final UserStockRepository userStockRepository;

}
