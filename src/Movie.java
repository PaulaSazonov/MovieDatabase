import java.util.ArrayList;

public class Movie {
	private String name;
	ArrayList<Actor> actors;
	double rating;

	public Movie(String name) {
		this.name = name;
		this.actors = new ArrayList<Actor>();
	}

	public Movie(String name, String[] actorNames) {
		this.name = name;
		this.actors = new ArrayList<Actor>();
		for (String a : actorNames) {
			if (!getActorNamesAsStrings().contains(a)) {
				actors.add(new Actor(a));
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

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public ArrayList<Actor> getActors() {
		return actors;
	}

	/**
	 * A helper method
	 * 
	 * @return a list of actors' names as Strings is returned
	 */
	public ArrayList<String> getActorNamesAsStrings() {
		ArrayList<String> actorNames = new ArrayList<>();
		for (Actor a : this.actors) {
			actorNames.add(a.getName());
		}
		return actorNames;
	}

	@Override
	public String toString() {
		return name + ", actors: " + actorNamesAsString() + ", rating: " + rating;
	}

	// For printing out stuff on the console in test purposes
	public String actorNamesAsString() {
		StringBuilder s = new StringBuilder();
		for (Actor a : this.actors) {
			s.append(a.getName() + ", ");
		}
		return s.toString();
	}

	// For the purposes of this exercise, it is sufficient that the Movies match in
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
		if (!(obj instanceof Movie)) {
			return false;
		}
		Movie other = (Movie) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
}
