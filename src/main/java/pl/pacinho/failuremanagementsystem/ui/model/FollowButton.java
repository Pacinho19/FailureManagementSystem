package pl.pacinho.failuremanagementsystem.ui.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FollowButton {

    FOLLOW("btn btn-danger", true),
    UNFOLLOW("btn btn-secondary", false);

    private final String styleClass;
    private final boolean value;
}
