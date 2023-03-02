import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName) {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void menu() {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q")) {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option) {
        switch (option) {
            case "t" -> searchTitles();
            case "c" -> searchCast();
            case "k" -> searchKeywords();
            case "g" -> listGenres();
            case "r" -> listHighestRated();
            case "h" -> listHighestRevenue();
            default -> System.out.println("Invalid choice!");
        }
    }

    private void searchTitles() {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<>();

        // search through ALL movies in collection
        for (Movie movie : movies) {
            String movieTitle = movie.getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.contains(searchTerm)) {
                //add the Movie object to the results list
                results.add(movie);
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort) {
        for (int j = 1; j < listToSort.size(); j++) {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void displayMovieInfo(Movie movie) {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast() {
        //search
        System.out.println("Enter a cast member");
        String search = scanner.nextLine();
        search = search.toLowerCase();

        ArrayList<String> castResults = new ArrayList<>();
        ArrayList<Movie> results = new ArrayList<>();

        //searches through all movies
        for (Movie movie : movies) {
            String movieCast = movie.getCast();
            String[] castList = movieCast.split("\\|");

            //searches through the cast of that movie
            for (String cast : castList) {
                if (cast.toLowerCase().contains(search)) {
                    //check for dupes
                    if (!castResults.contains(cast)) {
                        castResults.add(cast);
                    }
                }
            }
        }
        castResults.sort(String::compareTo);

        for (int i = 0; i < castResults.size(); i++){
            System.out.println(i+1+". "+castResults.get(i));
        }

        System.out.println("Which actor would you like to learn more about?");
        System.out.println("Enter the number");
        int search2 = scanner.nextInt();
        while (search2 > castResults.size() || search2 < 1){
            System.out.println("Enter the number (invalid choice)");
            search2 = scanner.nextInt();
            scanner.nextLine();
        }
        String actor = castResults.get(search2-1);

        for (Movie movie: movies){
            String[] movieCast = movie.getCast().split("\\|");
            for (String name: movieCast){
                if (name.equalsIgnoreCase(actor)){
                    results.add(movie);
                }
            }
        }
        sortResults(results);
        System.out.println(actor);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();

    }

    private void searchKeywords() {
        System.out.println("Enter a keyword");
        String search = scanner.nextLine();
        search = search.toLowerCase();

        ArrayList<Movie> results = new ArrayList<>();
        for (Movie movie : movies) {
            String keywords = movie.getKeywords();
            keywords = keywords.toLowerCase();

            if (keywords.contains(search)) {
                //add the Movie object to the results list
                results.add(movie);
            }
        }
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres()
    {
        ArrayList<String> allGenres = new ArrayList<>();
        ArrayList<Movie> results = new ArrayList<>();
        String[] genres = new String[0];

        //searches through all movies
        for (Movie movie : movies) {
            String movieGenres = movie.getGenres();
            genres = movieGenres.split("\\|");
        }
        for (String genre : genres){
            if (!allGenres.contains(genre.toLowerCase())){
                allGenres.add(genre);
            }
        }
        allGenres.sort(String::compareTo);
        for (int i = 0; i < allGenres.size(); i++){
            System.out.println(i+1+". " + allGenres.get(i));
        }
    }

    private void listHighestRated()
    {

    }

    private void listHighestRevenue()
    {

    }

    private void importMovieList(String fileName)
    {
        try
        {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null)
            {
                String[] movieFromCSV = line.split(",");

                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);
                movies.add(nextMovie);
            }
            bufferedReader.close();
        }
        catch(IOException exception)
        {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }
    }
}