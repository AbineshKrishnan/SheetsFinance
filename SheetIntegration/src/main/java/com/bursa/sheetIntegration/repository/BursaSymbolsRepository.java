package com.bursa.sheetIntegration.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bursa.sheetIntegration.entity.BursaSymbols;

@Repository
public interface BursaSymbolsRepository extends JpaRepository<BursaSymbols, UUID> {
	
	@Query(value = "SELECT * FROM bursa_symbols bs WHERE company_name LIKE %:name% OR symbol LIKE %:name%", nativeQuery = true)
	List<BursaSymbols> getSymbols(String name);

}
