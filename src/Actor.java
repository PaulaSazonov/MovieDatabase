import java.util.ArrayList;

public class Actor {
	private String name;
	ArrayList<Movie> movies;

	public Actor(String name) {
		this.name = name;
		this.movies = new ArrayList<Movie>();
	}

	public Actor(String name, String[] movieNames) {
		this.name = name;
		this.movies = new ArrayList<Movie>();
		for (String movie : movieNames) {
			if (!getMovieNamesAsStrings().contains(movie)) {
				this.movies.add(new Movie(movie));
			}
		}
	}

	// Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Movie> getMovies() {
		return movies;
	}

	public void setMovies(ArrayList<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public String toString() {
		return name + " : " + getMovieTitles() + ", rating: " + getActorsRating();
	}

	// For the purposes of this exercise, it is sufficient that the Actors match in
	// name to be considered the same object, therefore equals and hashcode are
	// overridden to avoid duplicates
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Actor)) {
			return false;
		}
		Actor other = (Actor) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	// for printing out stuff to the console for testing purposes
	public String getMovieTitles() {
		StringBuilder s = new StringBuilder();
		for (Movie m : this.movies) {
			s.append(m.getName() + ", ");
		}
		return s.toString();
	}

	/**
	 * A method that calculates the average rating of an Actor's movies. If no
	 * ratings are found for movies, 0.0 is returned.
	 * 
	 * @return a double representing an Actor's average rating for their movies is
	 *         returned, 0.0 if no ratings are found.
	 */
	public double getActorsRating() {
		double total = 0.0;
		double amount = 0.0;
		for (Movie m : this.movies) {
			if (m.rating != 0.0) {
				total += m.getRating();
				amount++;
			}
		}
		if (amount > 0) {
			return total / amount;
		}
		return 0.0;
	}

	/**
	 * A helper method
	 * 
	 * @return a list of movies' names as Strings
	 */
	public ArrayList<String> getMovieNamesAsStrings() {
		ArrayList<String> movieNames = new ArrayList<>();
		for (Movie m : this.movies) {
			movieNames.add(m.getName());
		}
		return movieNames;
	}
}
