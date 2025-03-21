package com.mung.mungtique.mungshop.adaptor.out.persistence.mungshoprepo;

import com.mung.mungtique.mungshop.domain.MungShopReservation;
import com.mung.mungtique.mungshop.domain.MungShopReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface MungShopReservationRepo extends JpaRepository<MungShopReservation, Long> {
    List<MungShopReservation> findByMungShopIdAndStatus(Long mungShopId, MungShopReservationStatus status);

    @Modifying
    @Query("UPDATE MungShopReservation m SET m.status = :newStatus " +
            "WHERE m.mungShopId = :mungShopId " +
            "AND m.reservationTime = :reservationTime " +
            "AND m.status = :oldStatus")
    int updateStatusIfAvailable(@Param("mungShopId") Long mungShopId,
                                @Param("reservationTime") String reservationTime,
                                @Param("oldStatus") MungShopReservationStatus oldStatus,
                                @Param("newStatus") MungShopReservationStatus newStatus);

    @Query("SELECT r FROM MungShopReservation r " +
            "WHERE r.status = :status " +
            "AND r.updateAt < :updatedBefore")
    List<MungShopReservation> findExpiredReservationsByStatus(
            @Param("status") MungShopReservationStatus status,
            @Param("updatedBefore") LocalDateTime updatedBefore);
}
