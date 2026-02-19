package hangman.word;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class FileWordProvider implements WordProvider {

    private final String wordsResource;
    private final List<String> words = new ArrayList<>();

    public FileWordProvider(String wordsResource) {
        if (wordsResource == null || wordsResource.isBlank()) {
            throw new IllegalArgumentException("The resource name must not be empty");
        }
        this.wordsResource = wordsResource;
        loadWordsFromClasspath();
    }

    @Override
    public String getWord() {
        return words.get(ThreadLocalRandom.current().nextInt(words.size()));
    }

    private void loadWordsFromClasspath() {
        ClassLoader cl = FileWordProvider.class.getClassLoader();

        try (InputStream is = cl.getResourceAsStream(wordsResource)) {
            if (is == null) {
                File file = new File(wordsResource).getAbsoluteFile();
                throw new IllegalStateException("Resource not found: " + file);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String normalized = line.trim().toLowerCase(Locale.ROOT);
                    if (!normalized.isEmpty()) {
                        words.add(normalized);
                    }
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Error reading resource: " + wordsResource, e);
        }
    }
}
