package hangman.app;

public class Main {

    private static final String DEFAULT_WORDS_RESOURCE = "data.txt";

    public static void main(String[] args) {
        String wordsResource = resolveWordsResource(args);

        try (HangmanApplication app = new HangmanApplication(wordsResource)) {
            app.start();
        } catch (IllegalStateException e) {
            System.err.printf("Game startup failed (%s): %s%n", wordsResource, e.getMessage());
        }
    }

    private static String resolveWordsResource(String[] args) {
        if (args.length == 0 || args[0].isBlank()) {
            return DEFAULT_WORDS_RESOURCE;
        }
        return args[0].trim();
    }
}