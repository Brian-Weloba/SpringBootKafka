package dev.brianweloba.orderservice.repository;

import dev.brianweloba.orderservice.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    @Override
    Optional<OrderItem> findById(UUID uuid);
}
