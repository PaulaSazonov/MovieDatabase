import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MovieDatabaseTest {
	MovieDatabase basey;

	@Before
	public void setUp() throws Exception {
		basey = new MovieDatabase();
	}

	@Test
	public void testMovieFileReading() {
		assertFalse(basey.dataFromMovieFile().isEmpty());
		assertEquals(basey.dataFromMovieFile().get(0)[0], "Brad Pitt" );
	}
	@Test
	public void testRatingsFileReading() {
		assertFalse(basey.dataFromRatingsFile().isEmpty());
		assertEquals(basey.dataFromRatingsFile().get(0)[0], "Original Sin");
	}
	@Test
	public void testAddingAMovie() {
		basey.addMovie("Testington", new String[]{"Sarah Jessica Tester", "Johnny Test", "Leonardo DiTestio"});
		assertTrue(basey.getMovieListAsStringsOfNames().contains("Testington"));
		assertTrue(basey.getActorListAsStringsOfNames().contains("Leonardo DiTestio"));
		assertTrue(basey.getActorListAsStringsOfNames().contains("Johnny Test"));
	}
	@Test
	public void testAddingARating() {
		basey.makeActorsAndMoviesOutOfData(basey.mapMovieFileData(basey.dataFromMovieFile()));
		basey.setRatingsFromData(basey.mapRatingsFileData(basey.dataFromRatingsFile()));
		basey.connectMoviesWithActors();
		
		assertTrue(basey.getMovie("Fools Rush In").getRating() == 0.0);
		basey.addRating("Fools Rush In", 50.0);
		assertTrue(basey.getMovie("Fools Rush In").getRating() == 50.0);
	}
	@Test
	public void testGetBestActor() {
		basey.makeActorsAndMoviesOutOfData(basey.mapMovieFileData(basey.dataFromMovieFile()));
		basey.setRatingsFromData(basey.mapRatingsFileData(basey.dataFromRatingsFile()));
		basey.connectMoviesWithActors();
		
		assertFalse(basey.getBestActor().isEmpty());
		assertTrue(basey.getActorListAsStringsOfNames().contains(basey.getBestActor()));
		assertTrue(basey.getActor(basey.getBestActor()).getActorsRating() == 100.0);
	}
	@Test
	public void testGetBestMovie() {
		basey.makeActorsAndMoviesOutOfData(basey.mapMovieFileData(basey.dataFromMovieFile()));
		basey.setRatingsFromData(basey.mapRatingsFileData(basey.dataFromRatingsFile()));
		basey.connectMoviesWithActors();
		
		assertFalse(basey.getBestMovie().isEmpty());
		assertTrue(basey.getMovieListAsStringsOfNames().contains(basey.getBestMovie()));
		assertTrue(basey.getMovie(basey.getBestMovie()).getRating() == 100.0);
	}
	@Test
	public void testObjectConnections () {
		basey.makeActorsAndMoviesOutOfData(basey.mapMovieFileData(basey.dataFromMovieFile()));
		basey.setRatingsFromData(basey.mapRatingsFileData(basey.dataFromRatingsFile()));
		basey.connectMoviesWithActors();
		
		assertTrue(basey.getMovie("Troy").getActorNamesAsStrings().contains("Brad Pitt"));
		assertTrue(basey.getActor("Brad Pitt").getMovieNamesAsStrings().contains("Troy"));
		assertTrue(basey.getMovieList().contains(basey.getMovie("Troy")));
		assertTrue(basey.getActorList().contains(basey.getActor("Brad Pitt")));
		
		
	}

}
