package pl.pacinho.failuremanagementsystem.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.pacinho.failuremanagementsystem.model.enums.Department;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private Department department;

    public String getName(){
        return firstName + " " + lastName;
    }

}