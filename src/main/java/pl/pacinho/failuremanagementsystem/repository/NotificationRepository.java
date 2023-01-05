package pl.pacinho.failuremanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pacinho.failuremanagementsystem.model.entity.Notification;
import pl.pacinho.failuremanagementsystem.model.entity.User;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserAndReadDateIsNullOrderByIdDesc(User user);

    List<Notification> findAllByUserUsernameEqualsAndReadDateIsNull(String name);
}