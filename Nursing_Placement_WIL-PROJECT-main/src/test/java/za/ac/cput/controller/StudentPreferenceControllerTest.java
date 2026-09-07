package za.ac.cput.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class StudentPreferenceControllerIT {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;

    @Test
    @Sql({"/insert-student.sql", "/insert-facility.sql"})
    void savePreferences_returns201AndRankedList() throws Exception {
        var dto = new za.ac.cput.dto.PreferenceRequest("219117675", List.of(1L, 2L));

        mvc.perform(post("/api/preferences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].rank").value(1))
                .andExpect(jsonPath("$[1].rank").value(2))
                .andExpect(jsonPath("$[0].facilityId").value(1))
                .andExpect(jsonPath("$[1].facilityId").value(2));
    }

    @Test
    void savePreferences_invalidDto_returns400() throws Exception {
        // Use a valid student number but invalid facility to test validation
        var dto = new za.ac.cput.dto.PreferenceRequest("", List.of(1L)); // empty student number

        mvc.perform(post("/api/preferences")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql({"/insert-student.sql", "/insert-facility.sql", "/insert-preferences.sql"})
    void getPreferences_returnsOrderedList() throws Exception {
        mvc.perform(get("/api/preferences/219117675"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].rank").value(1))
                .andExpect(jsonPath("$[1].rank").value(2))
                .andExpect(jsonPath("$[2].rank").value(3))
                .andExpect(jsonPath("$[0].facilityName").value("District 6"))
                .andExpect(jsonPath("$[1].facilityName").value("Community Clinic"))
                .andExpect(jsonPath("$[2].facilityName").value("Woodstock Health Center"));
    }

    @Test
    void getPreferences_unknownStudent_returnsEmpty() throws Exception {
        mvc.perform(get("/api/preferences/999999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", empty()));
    }

    @Test
    @Sql({"/insert-student.sql", "/insert-facility.sql", "/insert-preferences.sql"})
    void deletePreferences_removesAllPreferences() throws Exception {
        // First verify preferences exist
        mvc.perform(get("/api/preferences/219117675"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));

        // Delete preferences
        mvc.perform(delete("/api/preferences/219117675"))
                .andExpect(status().isNoContent());

        // Verify preferences are gone
        mvc.perform(get("/api/preferences/219117675"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", empty()));
    }
}