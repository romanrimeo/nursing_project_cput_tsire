package za.ac.cput.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PreferenceRequest(
        @NotNull String studentNumber,
        @NotEmpty List<@NotNull Long> facilityIds
) {}