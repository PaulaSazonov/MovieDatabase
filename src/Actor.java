import java.util.ArrayList;

public class Actor {
	private String name;
	ArrayList <Movie> movies;
	

	public Actor(String name) {
		this.name = name;
		this.movies = new ArrayList <Movie>();
	}
	public Actor(String name, String[] movieNames) {
		this.name = name;
		this.movies = new ArrayList <Movie>();
		for(String movie : movieNames) {
			if (!getMovieNamesAsStrings().contains(movie)) {
				this.movies.add(new Movie(movie));
			}
		}
	}

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
	public String getMovieTitles() {
		StringBuilder s = new StringBuilder();
		for (Movie m : this.movies) {
			s.append(m.getName() + ", ");
		}
		return s.toString();
	}
	public double getActorsRating() {
		double total = 0.0;
		double amount = 0.0;
		for (Movie m : this.movies) {
			if(m.rating != 0.0) {
				total += m.getRating();
				amount++;
			}
			
		}
		return total / amount;
	}
	public ArrayList<String> getMovieNamesAsStrings() {
		ArrayList<String> movieNames = new ArrayList<>();
		for (Movie m : this.movies) {
			movieNames.add(m.getName());
		}
		return movieNames;
	}
}
