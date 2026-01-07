package dev.vundirov.orderservice.api.dto;

import dev.vundirov.orderservice.api.dto.get.RequestGetOrderDto;
import dev.vundirov.orderservice.api.dto.post.RequestPostOrderDto;
import dev.vundirov.orderservice.domain.entity.OrderEntity;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
  OrderEntity toEntity(RequestPostOrderDto requestPostOrderDto);

  @AfterMapping
  default void linkItems(@MappingTarget OrderEntity order) {
    order.getItems()
            .forEach(item -> item.setOrder(order));
  }

  RequestPostOrderDto toOrderDto(OrderEntity order);

  OrderEntity toEntity(RequestGetOrderDto requestGetOrderDto);

  @AfterMapping
  default void linkItemEntities(@MappingTarget OrderEntity orderEntity) {
    orderEntity.getItems()
            .forEach(itemEntity -> itemEntity.setOrder(orderEntity));
  }

  RequestGetOrderDto toRequestGetOrderDto(OrderEntity orderEntity);
}