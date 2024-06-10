package toyproject.simulated_stock.domain.stock.overall.mapper;

import org.mapstruct.Mapper;
import toyproject.simulated_stock.domain.stock.overall.dto.StockListResponseDto;
import toyproject.simulated_stock.domain.stock.overall.entity.StockList;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StockListMapper {
    List<StockListResponseDto> stockListToStockListResponseDto(List<StockList> stockLists);
}
