package za.ac.cput.dto;

import java.time.LocalDateTime;

public record PreferenceResponse(
        Long preferenceId,
        Long facilityId,
        String facilityName,
        short rank,
        LocalDateTime createdAt
) {}