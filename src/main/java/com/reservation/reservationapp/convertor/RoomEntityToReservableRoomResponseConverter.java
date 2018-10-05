package com.reservation.reservationapp.convertor;

import com.reservation.reservationapp.entity.RoomEntity;
import com.reservation.reservationapp.model.Links;
import com.reservation.reservationapp.model.Self;
import com.reservation.reservationapp.model.response.ReservableRoomResponse;
import com.reservation.reservationapp.rest.ResourceConstants;
import org.springframework.core.convert.converter.Converter;

import java.util.function.Function;


public class RoomEntityToReservableRoomResponseConverter implements Converter<RoomEntity, ReservableRoomResponse>, Function<RoomEntity, ReservableRoomResponse> {

	@Override
	public ReservableRoomResponse convert(RoomEntity source) {
		
		ReservableRoomResponse reservationResponse = new ReservableRoomResponse();
		reservationResponse.setId(source.getId());
		reservationResponse.setRoomNumber(source.getRoomNumber());
		reservationResponse.setPrice( Integer.valueOf(source.getPrice()) );
		
		Links links = new Links();
		Self self = new Self();
		self.setRef(ResourceConstants.ROOM_RESERVATION_V1 + "/" + source.getId());
		links.setSelf(self);
		
		reservationResponse.setLinks(links);
		
		return reservationResponse;
	}

	@Override
	public ReservableRoomResponse apply(RoomEntity roomEntity) {
		return convert(roomEntity);
	}
}
