package za.ac.cput.factory;

import za.ac.cput.domain.Staff;
import za.ac.cput.domain.StaffRole;

public class StaffFactory {
    public static Staff createStaff(
            String name,
            String email,
            String password,
            StaffRole role,
            Integer yearLevelAssigned
    ) {

        if (name == null || name.isEmpty())
            throw new IllegalArgumentException("Staff name is required");

        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Email is required");

        if (password == null || password.isEmpty())
            throw new IllegalArgumentException("Password is required");

        if (role == null)
            throw new IllegalArgumentException("Role is required");

        return Staff.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .yearLevelAssigned(yearLevelAssigned)
                .build();
    }
}
