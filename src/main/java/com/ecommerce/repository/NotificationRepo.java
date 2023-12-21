package com.ecommerce.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Notifications;
import com.ecommerce.model.Status;

public interface NotificationRepo extends JpaRepository<Notifications, Long> {

	public boolean existsByTitle(String title);

	public Optional<Notifications> findByIdAndStatus(Long id, Status active);

}
