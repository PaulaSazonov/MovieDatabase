import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class MovieDatabase {
	private ArrayList<Movie> movieList;
	private ArrayList<Actor> actorList;

	public MovieDatabase () {
		this.movieList = new ArrayList<Movie>();
		this.actorList = new ArrayList<Actor>();
	}
	/**
	 * A method that takes in the name of a movie and a list of actors for that movie. If a movie is already in the database, it ignored. If the movie is a new movie, a Movie object is created and added to the movieList. If any actors are new, they will be added to the actorList. 
	 * @param name
	 * @param actors
	 */
	public void addMovie(String name, String[] actors) {
		if(!getMovieListAsStringsOfNames().contains(name)) {
			movieList.add(new Movie(name, actors));
		}
	}
	/**
	 * adds a rating to a movie
	 * @param name
	 * @param rating
	 */
	public void addRating(String name, double rating) {
		for (Movie m : this.movieList) {
			if (m.getName().equalsIgnoreCase(name)) {
				m.setRating(rating);
			}
		}
	}
	/**
	 * Overwrites the current rating of a movie with a new rating
	 * @param name
	 * @param newRating
	 */
	public void updateRating(String name, double newRating) {
		addRating(name, newRating);
	}
	/**
	 * A method that searches and returns the name of the actor that has the best average rating for their movies.
	 * @return name of the actor with highest rating
	 */
	public String getBestActor() {
		double bestRating = 0.0;
		String best = ""; 
		for (Actor a : actorList) {
			if (a.getActorsRating() != 0.0) {
				if (a.getActorsRating() > bestRating) {
					bestRating = a.getActorsRating();
					best = a.getName();
				}
			}
		}
		return best;
	}
	/**
	 * A method that searches and returns the name of the movie that has the best rating.
	 * @return name of the movie with highest rating
	 */
	public String getBestMovie() {
		double bestRating = 0.0;
		String best = "";
		for (Movie m : movieList) {
			if (m.getRating() != 0.0) {
				if(m.getRating() > bestRating) {
					bestRating = m.getRating();
					best = m.getName();
				}
			}

		}
		return best;
	}
	// Getters and Setters
	public ArrayList<Movie> getMovieList() {
		return this.movieList;
	}
	public ArrayList<Actor> getActorList() {
		return this.actorList;
	}
	public void setMovieList(ArrayList<Movie> movieList) {
		this.movieList = movieList;
	}
	public void setActorList(ArrayList<Actor> actorList) {
		this.actorList = actorList;
	}
	/**
	 * A helper method that reads the data from an external text file and splits it according to a delimiter
	 * @return a list of String arrays is returned, first array containing the actor's name, and subsequent arrays the names of the movies the actor has appeared in
	 */
	public List<String[]> dataFromMovieFile() {
		List<String[]> actorHelperList = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("C:\\Users\\Administrator\\eclipse-workspace\\MovieDatabase\\src\\movies.txt"), StandardCharsets.ISO_8859_1)) {
			actorHelperList = br.lines()
					.map(line -> line.split(","))
					.collect(Collectors.toList());
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		return actorHelperList;
	}
	/**
	 * A helper method to restructure the data into a map representing an actor (key = actor, value = movies that actor has acted in)
	 * @param listToMap is raw data to be modified
	 * @return a HashMap containing data to make Actor objects is returned
	 */
	public HashMap<String, String[]> mapMovieFileData(List<String[]> listToMap) {
		HashMap<String, String[]> actorAsMap = new HashMap<String, String[]>();
		String[] movies;
		for (int i = 0; i < listToMap.size(); i++) {
			movies = new String[listToMap.get(i).length-1];
			for (int j = 1; j < listToMap.get(i).length; j++) {
				movies[j-1] = listToMap.get(i)[j].trim();
			}
			actorAsMap.put(listToMap.get(i)[0], movies);
		}
		return actorAsMap;
	}
	/**
	 * A helper method that does the final restructuring of the data, by making Actor and Movie objects out of the data, and adding them into the database
	 * @param actorAsMap is received containing the Actor's name and movie names
	 */
	public void makeActorsAndMoviesOutOfData(HashMap<String, String[]> actorAsMap) {
		Set set = actorAsMap.entrySet();
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry)iterator.next();
			this.actorList.add(new Actor((String)(mentry.getKey()), (String[])mentry.getValue()));
			for (String movie : (String[])mentry.getValue()) {
				if (!getMovieListAsStringsOfNames().contains(movie)) {
					this.movieList.add(new Movie(movie));
				}
			}
		}
	}
	public ArrayList<String> getMovieListAsStringsOfNames() {
		ArrayList<String> movieTitles = new ArrayList<>();
		for (Movie m : movieList) {
			movieTitles.add(m.getName());
		}
		return movieTitles;
	}
	/**
	 * A helper method that reads the data from an external text file and splits it according to a delimiter
	 * @return a list of String arrays is returned, the first array containing the name of the movie, the second array containing the rating from Rotten Tomatoes
	 */
	public List<String[]> dataFromRatingsFile() {
		List<String[]> ratingsHelperList = new ArrayList<>();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("C:\\Users\\Administrator\\eclipse-workspace\\MovieDatabase\\src\\ratings.txt"), StandardCharsets.ISO_8859_1)) {
			ratingsHelperList = br.lines()
					.skip(1)
					.map(line -> line.split("\t"))
					.collect(Collectors.toList());
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		return ratingsHelperList;
	}
	/**
	 * A helper method to restructure the data into a map of movie and the rating as a key-value-pair
	 * @param listToMap a list of string arrays returned by the helper method that read data from file
	 * @return a HashMap containing the movie's name and rating is returned
	 */
	public HashMap<String, Double> mapRatingsFileData(List<String[]> listToMap) {
		HashMap<String, Double> ratingsMap = new HashMap<String, Double>();
		for (String[] entry  : listToMap) {
			ratingsMap.put(entry[0].trim(), Double.parseDouble(entry[1]));
		}
		return ratingsMap;
	}
	/**
	 * A method that sets the ratings to Movie objects that are to be found by name in MovieDatabase's movieList
	 * @param ratingsMap a HashMap containing the movie's name and rating
	 */
	public void setRatingsFromData(HashMap<String, Double> ratingsMap) {
		for (Movie m : this.movieList) {
			if (ratingsMap.keySet().contains(m.getName())) {
				m.setRating(ratingsMap.get(m.getName()));
			}
		}
	}
	/**
	 * A method which connects the Movie objects with Actor objects, by checking if a Movie is found in the database and then adding the Actor object into a Movie object's list of actors.
	 */
	public void connectMoviesWithActors() {
		for (Movie m : this.movieList) {
			for (Actor a : this.actorList) {
				for (Movie am : a.getMovies()) {
					if (am.equals(m)) {
						am.setRating(m.getRating());
						m.actors.add(a);
					}
				}
			}
		}
	}
	public void connectActorsWithMovies() {
		for (Actor a : this.actorList) {
			for (Movie m : this.movieList) {
				for (Actor ma : m.getActors()) {
					if (ma.equals(a)) {
						
						a.movies.add(m);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {

		MovieDatabase movieDatabase = new MovieDatabase();

		movieDatabase.makeActorsAndMoviesOutOfData(movieDatabase.mapMovieFileData(movieDatabase.dataFromMovieFile()));
		movieDatabase.setRatingsFromData(movieDatabase.mapRatingsFileData(movieDatabase.dataFromRatingsFile()));

		movieDatabase.connectMoviesWithActors();
		//movieDatabase.connectActorsWithMovies();


/*		for (Movie a : movieDatabase.movieList) {
			System.out.println(a);
		}*/

		for (Actor a : movieDatabase.actorList) {
			System.out.println(a);
		}


		
		







	}
}
