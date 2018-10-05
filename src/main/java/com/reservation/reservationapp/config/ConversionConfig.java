package com.reservation.reservationapp.config;

import com.reservation.reservationapp.convertor.ReservationEntityToReservationResponseConverter;
import com.reservation.reservationapp.convertor.ReservationRequestToReservationEntityConverter;
import com.reservation.reservationapp.convertor.RoomEntityToReservableRoomResponseConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

/**
 * Converter provides nice clean way
 * to convert data coming from tables
 * to a model that will be the response
 * of your API
 */
@Configuration
public class ConversionConfig {

    /**
     * Converters used to convert data from one type to another
     * here we are adding all out converters to Set of Converter
     * so we can use them automatically
     */
    private Set<Converter> getConverters() {
        Set<Converter> converters = new HashSet<Converter>();
        converters.add(new RoomEntityToReservableRoomResponseConverter());
        converters.add(new ReservationRequestToReservationEntityConverter());
        converters.add(new ReservationEntityToReservationResponseConverter());

        return converters;
    }


    /**
     * Setting conversion service as bean
     */
    @Bean
    public ConversionService conversionService() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(getConverters());
        bean.afterPropertiesSet();

        return bean.getObject();
    }

}
