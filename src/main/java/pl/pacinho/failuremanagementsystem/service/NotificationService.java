package pl.pacinho.failuremanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pacinho.failuremanagementsystem.model.entity.Notification;
import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.repository.NotificationRepository;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public List<Notification> findUnreadByUser(User user) {
        return notificationRepository.findAllByUserAndReadDateIsNull(user);
    }

    @Transactional
    public void read(Notification notification) {
        notification.setReadDate(new Date());
    }

    public void add(String text, User user, Task task) {
        Notification notification = new Notification();
        notification.setText(text);
        notification.setUser(user);
        notification.setAddDate(new Date());
        notification.setTask(task);
        notificationRepository.save(notification);
    }

    public Notification findById(long id) {
        return notificationRepository.getById(id);
    }

    @Transactional
    public void setAllAsRead(User user) {
        notificationRepository.findAllByUserAndReadDateIsNull(user)
                .forEach(n -> n.setReadDate(new Date()));
    }
}
