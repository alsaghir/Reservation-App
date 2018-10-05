package com.reservation.reservationapp.repository;

import com.reservation.reservationapp.entity.RoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageableRoomRepository extends PagingAndSortingRepository<RoomEntity, Long> {
	
	Page<RoomEntity> findById(Long id, Pageable page);

}
