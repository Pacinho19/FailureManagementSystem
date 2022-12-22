package pl.pacinho.failuremanagementsystem.model.dto;

import pl.pacinho.failuremanagementsystem.model.enums.Department;

public record FmsUserDto (String firstName, String lastName, String username, String password, Department department) {}