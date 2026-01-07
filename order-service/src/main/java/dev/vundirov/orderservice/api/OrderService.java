package dev.vundirov.orderservice.api;

import dev.vundirov.orderservice.api.dto.OrderMapper;
import dev.vundirov.orderservice.api.dto.get.RequestGetOrderDto;
import dev.vundirov.orderservice.api.dto.post.RequestPostOrderDto;
import dev.vundirov.orderservice.domain.entity.OrderEntity;
import dev.vundirov.orderservice.domain.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderService{
private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

  private final OrderMapper orderMapper;

  private final OrderRepository orderRepository;

  public RequestGetOrderDto getOne(Long id) {
    Optional<OrderEntity> orderEntityOptional = orderRepository.findById(id);
    return orderMapper.toRequestGetOrderDto(
            orderEntityOptional.orElseThrow(() ->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Entity with id `%s` not found".formatted(id)
                    )));
  }

  public RequestPostOrderDto create(RequestPostOrderDto dto) {
    logger.info("Creating order: {}", dto);
    OrderEntity orderEntity = orderMapper.toEntity(dto);
    OrderEntity resultOrderEntity = orderRepository.save(orderEntity);
    RequestPostOrderDto orderDto = orderMapper.toOrderDto(resultOrderEntity);
    logger.info("Created: {}", orderDto);
    return orderDto;
  }
}
