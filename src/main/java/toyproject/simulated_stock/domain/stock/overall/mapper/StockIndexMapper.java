package toyproject.simulated_stock.domain.stock.overall.mapper;

import org.mapstruct.Mapper;
import toyproject.simulated_stock.domain.stock.overall.dto.StockIndexResponseDto;
import toyproject.simulated_stock.domain.stock.overall.entity.StockIndex;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockIndexMapper {
    List<StockIndexResponseDto> stockIndexToStockIndexResponseDto(List<StockIndex> stockIndex);
}
