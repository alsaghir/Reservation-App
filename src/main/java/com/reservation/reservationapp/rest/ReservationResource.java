package com.reservation.reservationapp.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.reservation.reservationapp.convertor.RoomEntityToReservableRoomResponseConverter;
import com.reservation.reservationapp.entity.ReservationEntity;
import com.reservation.reservationapp.entity.RoomEntity;
import com.reservation.reservationapp.model.request.ReservationRequest;
import com.reservation.reservationapp.model.response.ReservableRoomResponse;
import com.reservation.reservationapp.model.response.ReservationResponse;
import com.reservation.reservationapp.repository.PageableRoomRepository;
import com.reservation.reservationapp.repository.ReservationRepository;
import com.reservation.reservationapp.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

/**
 * RestController is Controller + ResponseBody
 * <p>
 * Controller added as a bean
 * ResponseBody means the response should
 * be in the body of the http response
 */
@RestController
@RequestMapping(ResourceConstants.ROOM_RESERVATION_V1)
@CrossOrigin // Enables cross origin requests, this could be restricted and even avoided in production
public class ReservationResource {


    ObjectMapper objectMapper;
    ConversionService conversionService;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    PageableRoomRepository pageableRoomRepository;

    @Autowired
    RoomRepository roomRepository;

    /**
     * Using registerModule(Module module) to register module
     * that extends the capability of the object mapper like here
     * we are using {@link JavaTimeModule} for the
     * capability of serializing {@code java.time} objects with the Jackson core
     * @param objectMapper
     */
    @Autowired
    public ReservationResource(ObjectMapper objectMapper, ConversionService conversionService) {
        objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper = objectMapper;
        this.conversionService = conversionService;
    }

    @RequestMapping(path = "/{roomId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<RoomEntity> getRoomById(
            @PathVariable
                    Long roomId) {

        Optional<RoomEntity> roomEntity = roomRepository.findById(roomId);

        return new ResponseEntity<>(roomEntity.get(), HttpStatus.OK);
    }

    // example : room/reservation/v1?checkout=2017-03-18&checkin=2016-03-18&page=0&size=10
    @RequestMapping(path ="", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Page<ReservableRoomResponse> getAvailableRooms(
            @RequestParam(value = "checkin")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate checkin,
            @RequestParam(value = "checkout")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                    LocalDate checkout, Pageable pageable) {

        Page<RoomEntity> roomEntityList = pageableRoomRepository.findAll(pageable);


        //Function is alternative to Converter but here we used Converter first
        //then found that map method of {@link Page} needs {@link Function} implementation
        return roomEntityList.map(new RoomEntityToReservableRoomResponseConverter());
    }

    @RequestMapping(path = "", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReservationResponse> createReservation(
            @RequestBody ReservationRequest reservationRequest) {

        ReservationEntity reservationEntity = conversionService.convert(reservationRequest, ReservationEntity.class);
        reservationEntity = reservationRepository.save(reservationEntity);

        Optional<RoomEntity> roomEntity = roomRepository.findById(reservationRequest.getRoomId());
        roomEntity.get().addReservationEntity(reservationEntity);
        roomRepository.save(roomEntity.get());
        reservationEntity.setRoomEntity(roomEntity.get());

        ReservationResponse reservationResponse = conversionService.convert(reservationEntity, ReservationResponse.class);

        return new ResponseEntity<>(reservationResponse, HttpStatus.CREATED);
    }

    @RequestMapping(path = "", method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ReservationResponse> updateReservation(
            @RequestBody ReservationRequest reservationRequest) {

        try {
            System.out.println(objectMapper.writeValueAsString(reservationRequest));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ReservationResponse(), HttpStatus.OK);

    }

    @RequestMapping(path = "/{reservationId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
