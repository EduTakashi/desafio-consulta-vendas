package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.ReportDTO(s.id, s.date, s.amount, s.seller.name) " +
            "FROM Sale s " +
            "WHERE s.date BETWEEN :minDate AND :maxDate " +
            "AND UPPER(s.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
    List<ReportDTO> getReport(@Param("minDate") LocalDate minDate,
                              @Param("maxDate") LocalDate maxDate,
                              @Param("name") String name);



    @Query("SELECT new com.devsuperior.dsmeta.dto.SummaryDTO(s.seller.name, SUM(s.amount)) " +
            "FROM Sale s " +
            "WHERE s.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY s.seller.name")
    List<SummaryDTO> getSummary(@Param("minDate") LocalDate minDate,
                                @Param("maxDate") LocalDate maxDate);
}
