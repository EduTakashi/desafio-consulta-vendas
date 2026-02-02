package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.ReportDTO;
import com.devsuperior.dsmeta.dto.SummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public List<ReportDTO> getReport(String minDate, String maxDate, String name) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate max = (maxDate == null || maxDate.isEmpty())
				? today
				: LocalDate.parse(maxDate);

		LocalDate min = (minDate == null || minDate.isEmpty())
				? max.minusYears(1L)
				: LocalDate.parse(minDate);

		String sellerName = (name == null) ? "" : name;

		return repository.getReport(min, max, sellerName);
	}

	public List<SummaryDTO> getSummary(String minDate, String maxDate) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate max = (maxDate == null || maxDate.isEmpty())
				? today
				: LocalDate.parse(maxDate);

		LocalDate min = (minDate == null || minDate.isEmpty())
				? max.minusYears(1L)
				: LocalDate.parse(minDate);

		return repository.getSummary(min, max);
	}
}
