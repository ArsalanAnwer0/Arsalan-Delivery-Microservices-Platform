package dev.vundirov.orderservice.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.math.BigDecimal;
import java.util.Objects;


@Entity
// для триггера @Lazy доступ к БД, тк с @Lazy общаемся с прокси
@Getter
@Setter
@Table(name = "items")
public class ItemEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @PositiveOrZero
  @Column(name = "quantity", nullable = false)
  private Long quantity;

  @Column(name = "cost", nullable = false, precision = 19, scale = 2)
  private BigDecimal cost = BigDecimal.valueOf(0);

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "order_id")
  private OrderEntity order;

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> objectEffectiveClass =
            o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer()
                    .getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass =
            this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer()
                    .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != objectEffectiveClass) {
      return false;
    }
    ItemEntity itemEntity = (ItemEntity) o;
    return getId() != null && Objects.equals(getId(), itemEntity.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer()
            .getPersistentClass()
            .hashCode() : getClass().hashCode();
  }
}
