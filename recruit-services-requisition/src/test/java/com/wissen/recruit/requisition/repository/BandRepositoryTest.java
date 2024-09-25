package com.wissen.recruit.requisition.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.wissen.recruit.requisition.entity.Band;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DataJpaTest
class BandRepositoryTest {

	@Autowired
	BandRepository bandRepository;

	Band bandDetails;

	@BeforeEach
	void init() {
		bandDetails = Band.builder().value("Foo").label("Foo").build();
		bandRepository.save(bandDetails);

	}

	@Test
	void test_getAllBands_success() {
		List<Band> actualResult = bandRepository.findAll();
		assertThat(actualResult).isNotNull().hasSize(1);
		assertThat(actualResult.get(0).getValue()).endsWith("Foo");
	}

	@Test
	void test_getAllBands_Fail() {
		bandRepository.deleteAll();
		List<Band> actualResult = bandRepository.findAll();
		assertThat(actualResult).isEmpty();
	}
	
	@Test
	void test_createBand() {
		Band expectedResults = bandRepository.save(Band.builder().value("Foo").label("Foo").build());
		assertThat(expectedResults).isNotNull();
		assertThat(expectedResults.getBandId()).isPositive();
		assertThat(expectedResults.getValue()).isEqualTo("Foo");
	}

}
