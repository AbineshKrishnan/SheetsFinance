package com.bursa.sheetIntegration.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bursa.sheetIntegration.entity.UsSymbols;

@Repository
public interface UsSymbolsRepository extends JpaRepository<UsSymbols, UUID> {
	
	@Query(value = "SELECT * FROM us_symbols us WHERE company_name LIKE %:name% OR symbol LIKE %:name%", nativeQuery = true)
	List<UsSymbols> getSymbols(String name);

}
