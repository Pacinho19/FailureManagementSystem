package pl.pacinho.failuremanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.pacinho.failuremanagementsystem.controller.NotificationWSController;
import pl.pacinho.failuremanagementsystem.exception.NotificationNotFoundException;
import pl.pacinho.failuremanagementsystem.model.entity.Notification;
import pl.pacinho.failuremanagementsystem.model.entity.Task;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.repository.NotificationRepository;
import pl.pacinho.failuremanagementsystem.ui.model.enums.NotificationMessage;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationWSController notificationWSController;
    private final TaskFollowService taskFollowService;

    public List<Notification> findUnreadByUser(User user) {
        return notificationRepository.findAllByUserAndReadDateIsNullOrderByIdDesc(user);
    }

    @Transactional
    public void read(long id) {
        notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id))
                .setReadDate(new Date());
    }

    public void add(String text, User user, Task task) {
        Notification notification = new Notification();
        notification.setText(text);
        notification.setUser(user);
        notification.setAddDate(new Date());
        notification.setTask(task);
        notificationRepository.save(notification);
        notificationWSController.notification(user, text, getUnreadCountByUser(user));
    }


    public void addNotifications(NotificationMessage notificationMessage, long number, Task task, User user) {
        getUsersForNotifications(task, user)
                .forEach(
                        u -> add(notificationMessage.getMessage(number), u, task)
                );
    }

    private List<User> getUsersForNotifications(Task task, User user) {
        return Stream.of(
                        Arrays.asList(user.getId() != task.getOwner().getId() ? task.getOwner() : null,
                                task.getExecutor() != null && user.getId() != task.getExecutor().getId() ? task.getExecutor() : null),
                        taskFollowService.getUsersByTaskNumber(task.getNumber())
                )
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .toList();
    }

    @Transactional
    public void readAllByUsername(String name) {
        notificationRepository.findAllByUserUsernameEqualsAndReadDateIsNull(name)
                .forEach(n -> n.setReadDate(new Date()));
    }

    public long getUnreadCountByUser(User user) {
        return notificationRepository.countByUserAndReadDateIsNullOrderByIdDesc(user);
    }
}