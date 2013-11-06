package it.f2informatica.test.mongodb.repositories;

import it.f2informatica.mongodb.MongoDataEvent;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class MongoDataEventTest {

	@Test
	public void saveEvent() {
		assertThat(MongoDataEvent.SAVE.toString()).isEqualTo("SAVE");
	}

	@Test
	public void editEvent() {
		assertThat(MongoDataEvent.EDIT.toString()).isEqualTo("EDIT");
	}

	@Test
	public void deleteEvent() {
		assertThat(MongoDataEvent.DELETE.toString()).isEqualTo("DELETE");
	}

	@Test
	public void detailEvent() {
		assertThat(MongoDataEvent.DETAIL.toString()).isEqualTo("DETAIL");
	}

	@Test
	public void searchEvent() {
		assertThat(MongoDataEvent.SEARCH.toString()).isEqualTo("SEARCH");
	}

}
