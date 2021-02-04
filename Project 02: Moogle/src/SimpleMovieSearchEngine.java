// Name: Tharit Chantanalertvilai
// Student ID: 6188068
// Section: 2

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SimpleMovieSearchEngine implements BaseMovieSearchEngine {
	public Map<Integer, Movie> movies;
	
	@Override
	public Map<Integer, Movie> loadMovies(String movieFilename) {
		
		// YOUR CODE GOES HERE
		movies = new HashMap<Integer,Movie>();
		Pattern pattern1 = Pattern.compile("(\\d+)(,)(\"*)(,*)(.+)(\\()(\\d+)(\\))(\"*)(,)(.*)");
		String nextLine = null;

		try
		{
			FileReader fr = new FileReader(movieFilename);
			BufferedReader bf = new BufferedReader(fr);

			while((nextLine = bf.readLine()) != null)
			{
				Matcher match1 = pattern1.matcher(nextLine);
				if(match1.find())
				{
					int mid = Integer.parseInt(match1.group(1));
					String title = match1.group(5);
					int year = Integer.parseInt(match1.group(7));
					Movie info = new Movie(mid, title, year);
					String[] sp = match1.group(11).split("\\|");

					for(String tag : sp)
					{
						info.addTag(tag);
					}
					movies.put(mid, info);
				}
			}
			System.out.println("");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return movies;
	}

	@Override
	public void loadRating(String ratingFilename) {

		// YOUR CODE GOES HERE
		String nextLine = null;
		try
		{
			BufferedReader bf = new BufferedReader(new FileReader(ratingFilename));
			try
			{
				while((nextLine = bf.readLine()) != null)
				{
					Pattern pattern1 = Pattern.compile("(\\d+),(\\d+),(\\d+.\\d+),(.*)");
					Matcher match1 = pattern1.matcher(nextLine);

					if(match1.find())
					{
						int m = Integer.parseInt(match1.group(1));
						int r = Integer.parseInt(match1.group(2));
						double u = Double.parseDouble(match1.group(3));
						int t = Integer.parseInt(match1.group(4));
						
						if(movies.get(r) != null)
						{
							movies.get(r).addRating(new User(m), movies.get(r), u, t);
						}
					}
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		catch(Exception e)
			{
				e.printStackTrace();
			}
	}

	@Override
	public void loadData(String movieFilename, String ratingFilename) {
	
		// YOUR CODE GOES HERE
		loadMovies(movieFilename);
		loadRating(ratingFilename);	
	}

	@Override
	public Map<Integer, Movie> getAllMovies() {

		// YOUR CODE GOES HERE
		
		return movies;
	}

	@Override
	public List<Movie> searchByTitle(String title, boolean exactMatch) {

		// YOUR CODE GOES HERE
		List<Movie> movieList = new ArrayList<Movie>();
		for(Movie i : movies.values())
		{
			if(i.getTitle().toLowerCase().contains(title))
			{
				movieList.add(i);
			}
		}
		return movieList;
	}

	@Override
	public List<Movie> searchByTag(String tag) {

		// YOUR CODE GOES HERE
		List<Movie> movieList = new ArrayList<Movie>();
		for(Movie i : movies.values())
		{
			if(i.getTags().contains(tag))
			{
				movieList.add(i);
			}
		}
		return movieList;
	}

	@Override
	public List<Movie>searchByYear(int year) {

		// YOUR CODE GOES HERE
		List<Movie> movieList = new ArrayList<Movie>();
		for(Movie i : movies.values())
		{
			if(i.getYear() == year)
			{
				movieList.add(i);
			}
		}
		return movieList;
	}

	@Override
	public List<Movie> advanceSearch(String title, String tag, int year) {

		// YOUR CODE GOES HERE
		List<Movie> movieList = new LinkedList<Movie>();
		if(title == null)
		{
			for(Movie i : movies.values())
			{
				if(i.getYear() == year && i.getTags().contains(tag))
				{
					movieList.add(i);
				}
			}
		}
		else if(tag == null)
		{
			for(Movie j : movies.values())
			{
				if(j.getTitle().toLowerCase().contains(title))
				{
					if(j.getYear() == year && j.getTitle().toLowerCase().contains(title))
					{
						movieList.add(j);
					}
				}	
			}
		}
		else if(year < 0)
		{
			for(Movie k : movies.values())
			{
				if(k.getTags().contains(tag) && k.getTitle().toLowerCase().contains(title))
				{
					movieList.add(k);
				}
			}
		}
		else
		{
			for(Movie l : movies.values())
			{
				if(l.getTitle().toLowerCase().contains(title))
				{
					if(l.getTags().contains(tag))
					{
						if(l.getYear() == year)
						{
							movieList.add(l);
						}
					}
				}
			}
		}
		return movieList;
	}

	@Override
	public List<Movie> sortByTitle(List<Movie> unsortedMovies, boolean asc) {

		// YOUR CODE GOES HERE
		boolean check = asc;
		if(check == true)
		{
			unsortedMovies.sort(Comparator.comparing(Movie::getTitle));
		}
		else
		{
			unsortedMovies.sort(Comparator.comparing(Movie::getTitle).reversed());
		}
		return unsortedMovies;
	}

	@Override
	public List<Movie> sortByRating(List<Movie> unsortedMovies, boolean asc) {

		// YOUR CODE GOES HERE
		boolean check = asc;
		if(check == true)
		{
			unsortedMovies.sort(Comparator.comparing(Movie::getMeanRating));
		}
		// else
		// {
		// 	unsortedMovies.sort(Comparator.comparing(Movie::getMeanRating).reversed());
		// }
		return unsortedMovies;
	}

}
