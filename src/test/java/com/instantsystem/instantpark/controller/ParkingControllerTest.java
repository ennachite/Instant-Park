package com.instantsystem.instantpark.controller;

import com.instantsystem.instantpark.model.ParkingInfo;
import com.instantsystem.instantpark.service.ParkingService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.doReturn;

@ContextConfiguration(classes = {ParkingController.class})
@WebMvcTest(ParkingController.class)
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@DisplayName("ParkingController_should")
class ParkingControllerTest {
    private final String PREFIX_PATH = "/api/v1/parkings";
    @Autowired
    private ParkingController parkingController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ParkingService parkingService;

    /**
     * Method under test: {@link ParkingController#getAllParkings()}
     */
    @Test
    @DisplayName("Should_return_all_parkings")
    void testGetAllParkings() throws Exception {
        // Arrange
        List<ParkingInfo> arrayList = List.of(new ParkingInfo());
        doReturn(arrayList).when(parkingService).getAllParkings();

        // Act
        mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));

    }

    /**
     * Method under test: {@link ParkingController#getNearbyParkings(Double, Double)}
     */
    @Test
    @DisplayName("Should_return_nearby_parkings")
    void testGetNearbyParkings() throws Exception {
        // Arrange
        Double latitude = 46.58595804860371;
        Double longitude = 0.3512954265806957;

        List<ParkingInfo> arrayList = List.of(new ParkingInfo());
        doReturn(arrayList).when(parkingService).getNearbyParkings(latitude, longitude);

        // Act
        mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_PATH + "/nearby").contentType(MediaType.APPLICATION_JSON)
                        .param("latitude", latitude.toString())
                        .param("longitude", longitude.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    /**
     * Method under test: {@link ParkingController#getParkingById(String)}
     */
    @Test
    @DisplayName("Should_return_parking_by_id")
    void testGetParkingById() throws Exception {
        // Arrange
        String id = "F4604__1923";
        ParkingInfo parkingInfo = new ParkingInfo();
        doReturn(parkingInfo).when(parkingService).getParkingById(id);

        // Act
        mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_PATH + "/{parkingId}", id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

//    /**
//     * Method under test: {@link ParkingController#getParkingsByName(String)}
//     */
//    @Test
//    @DisplayName("Should_return_parkings_by_name")
//    void testGetParkingsByName() throws Exception {
//        // Arrange
//        String name = "DES";
//        List<ParkingInfo> arrayList = List.of(new ParkingInfo());
//        doReturn(arrayList).when(parkingService).getParkingsByName(name);
//
//        // Act
//        mockMvc.perform(MockMvcRequestBuilders.get(PREFIX_PATH + "/search").contentType(MediaType.APPLICATION_JSON)
//                        .param("nom", name))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
//    }
}

