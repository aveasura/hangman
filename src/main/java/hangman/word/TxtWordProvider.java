package hangman.word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class TxtWordProvider implements WordProvider {

    private final String wordsResource;
    private final List<String> words = new ArrayList<>();

    public TxtWordProvider(String wordsResource) {
        if (wordsResource == null || wordsResource.isBlank()) {
            throw new IllegalArgumentException("Имя ресурса не должно быть пустым");
        }
        this.wordsResource = wordsResource;
    }

    @Override
    public String getWord() {
        if (words.isEmpty()) {
            loadWordsFromClasspath();
        }

        if (words.isEmpty()) {
            throw new IllegalStateException("Список слов пуст. Проверьте ресурс: " + wordsResource);
        }

        return words.get(ThreadLocalRandom.current().nextInt(words.size()));
    }

    private void loadWordsFromClasspath() {
        ClassLoader cl = TxtWordProvider.class.getClassLoader();

        try (InputStream is = cl.getResourceAsStream(wordsResource)) {
            if (is == null) {
                throw new IllegalStateException("Ресурс не найден: " + wordsResource);
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
            throw new IllegalStateException("Ошибка чтения ресурса: " + wordsResource, e);
        }
    }
}
