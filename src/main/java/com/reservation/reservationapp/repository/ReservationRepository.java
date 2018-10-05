package com.reservation.reservationapp.repository;


import com.reservation.reservationapp.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
}