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
import za.ac.cput.domain.Facility;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class FacilityControllerIT {

    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;

    @Test
    void createFacility_returns201AndFacility() throws Exception {
        Facility facility = Facility.builder()
                .name("Test Hospital")
                .type("HOSPITAL")
                .address("123 Test St")
                .contactPerson("Dr. Test")
                .contactNumber("021-555-1234")
                .build();

        mvc.perform(post("/api/facilities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(facility)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Hospital"))
                .andExpect(jsonPath("$.type").value("HOSPITAL"))
                .andExpect(jsonPath("$.facilityId").exists()); // Changed from $.id to $.facilityId
    }

    @Test
    void createFacility_invalidData_returns400() throws Exception {
        Facility facility = Facility.builder()
                .name("")  // Invalid: empty name
                .type("HOSPITAL")
                .address("123 Test St")
                .build();

        mvc.perform(post("/api/facilities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(facility)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Sql("/insert-facility.sql")
    void getFacilityById_returnsFacility() throws Exception {
        mvc.perform(get("/api/facilities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("District 6"))
                .andExpect(jsonPath("$.type").value("HOSPITAL"));
    }

    @Test
    void getFacilityById_notFound_returns404() throws Exception {
        mvc.perform(get("/api/facilities/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql("/insert-facility.sql")
    void getAllFacilities_returnsList() throws Exception {
        mvc.perform(get("/api/facilities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name").value("District 6"))
                .andExpect(jsonPath("$[1].name").value("Community Clinic"));
    }

    @Test
    @Sql("/insert-facility.sql")
    void getFacilityByName_returnsFacility() throws Exception {
        mvc.perform(get("/api/facilities/search/name")
                        .param("name", "District 6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("District 6"));
    }

    @Test
    @Sql("/insert-facility.sql")
    void getFacilitiesByType_returnsList() throws Exception {
        mvc.perform(get("/api/facilities/search/type")
                        .param("type", "CLINIC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].type").value("CLINIC"))
                .andExpect(jsonPath("$[1].type").value("CLINIC"));
    }

    @Test
    @Sql("/insert-facility.sql")
    void updateFacility_returnsUpdated() throws Exception {
        Facility updated = Facility.builder()
                .name("Updated Hospital")
                .type("HOSPITAL")
                .address("456 New St")
                .contactPerson("Dr. Updated")
                .contactNumber("021-555-5678")
                .build();

        mvc.perform(put("/api/facilities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Hospital"))
                .andExpect(jsonPath("$.address").value("456 New St"));
    }

    @Test
    @Sql("/insert-facility.sql")
    void deleteFacility_returns204() throws Exception {
        mvc.perform(delete("/api/facilities/1"))
                .andExpect(status().isNoContent());

        // Verify deletion
        mvc.perform(get("/api/facilities/1"))
                .andExpect(status().isNotFound());
    }
}