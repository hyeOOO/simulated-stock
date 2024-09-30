package toyproject.simulated_stock.domain.stock.order.entitiy;

public enum DepositStatus {
    COMPLETED,  // 충전 완료
    CANCELLED,  // 충전 취소
    PENDING     // 충전 대기 중 (예: 결제가 아직 완료되지 않은 경우)
}