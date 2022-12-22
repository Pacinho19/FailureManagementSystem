package pl.pacinho.failuremanagementsystem.model.dto.mapper;

import pl.pacinho.failuremanagementsystem.model.dto.UserDto;
import pl.pacinho.failuremanagementsystem.model.entity.User;
import pl.pacinho.failuremanagementsystem.model.dto.FmsUserDto;
import pl.pacinho.failuremanagementsystem.model.enums.Department;
import pl.pacinho.failuremanagementsystem.model.enums.Role;

public class UserDtoMapper {

    public static User parseToEntity(FmsUserDto fmsUserDto) {
        return new User(fmsUserDto.firstName(), fmsUserDto.lastName(), fmsUserDto.username(), fmsUserDto.password(), 1, Role.ROLE_USER, Department.BINT);
    }

    public static UserDto parseToDto(User user) {
        if (user == null) return null;
        return new UserDto(user.getFirstName(), user.getLastName(), user.getDepartment());
    }

}
