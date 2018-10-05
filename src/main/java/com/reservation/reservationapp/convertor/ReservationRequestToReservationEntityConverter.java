package com.reservation.reservationapp.convertor;

import com.reservation.reservationapp.entity.ReservationEntity;
import com.reservation.reservationapp.model.request.ReservationRequest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;

import java.util.function.Function;

/**
 * Function is alternative to Converter but here we used Converter first
 * then found that map method of {@link Page} needs {@link Function} implementation
 */
public class ReservationRequestToReservationEntityConverter implements Converter<ReservationRequest, ReservationEntity>, Function<ReservationRequest, ReservationEntity> {

    @Override
    public ReservationEntity convert(ReservationRequest source) {

        ReservationEntity reservationEntity = new ReservationEntity();
        reservationEntity.setCheckin(source.getCheckin());
        reservationEntity.setCheckout(source.getCheckout());
        if (null != source.getId())
            reservationEntity.setId(source.getId());

        return reservationEntity;
    }

    @Override
    public ReservationEntity apply(ReservationRequest source) {
        return convert(source);
    }
}
