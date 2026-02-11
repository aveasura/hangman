# Hangman (Console, Java 21)

Консольная версия игры "Виселица" на Java 21.  
Учебный pet-проект с акцентом на разделение ответственности и чистую архитектуру для CLI-приложения.

## Возможности

- Главное меню:
  - `1` — начать новую игру
  - `2` — выход
- Случайный выбор слова из `src/main/resources/data.txt`
- Отображение состояния игры в консоли:
  - ASCII-виселица по числу ошибок
  - количество ошибок
  - количество оставшихся попыток
  - прогресс слова (скрытые/открытые буквы)
- Ограничение по ошибкам (`MAX_ERRORS = 6`)
- Проверка пользовательского ввода:
  - пустой ввод
  - ввод не одной буквы
  - повторный ввод буквы

## Архитектура

Проект разделён на слои:

- `hangman.game` - доменная логика игры  
  (`Game`, `GuessResult`, `WordProgress`, `Slot`, `OpenedSlot`, `HiddenSlot`)
- `hangman.word` - источник слов  
  (`WordProvider`, `TxtWordProvider`)
- `hangman.io` - абстракция ввода
- `hangman.io.console` - консольный UI  
  (`ConsoleMenu`, `ConsoleRenderer`, `ConsoleInputProvider`)
- `hangman.factory` - сборка зависимостей / создание `Game`
- `hangman.Main` - точка входа

## Доменные инварианты

- В `Game` слово хранится в канонической форме: `trim + lower-case (Locale.ROOT)`.
- Это защитный слой на случай изменений реализаций `WordProvider`.
- Полное слово раскрывается только после завершения игры (`revealWord()`).

## Примечания по реализации
- Приложение однопоточное.
- Состояние консоли рендерится снапшотом: ASCII + метрики + (при необходимости) приглашение к вводу.
- Тесты в рамках данной учебной итерации не добавлялись.
- Файл-словарь считается корректным источником слов.

## Запуск

```bash
git clone https://github.com/aveasura/hangman.git
cd hangman
mvn clean compile
mvn exec:java -Dexec.mainClass=hangman.Main
```

## ТЗ
- Проект выполнен по учебному ТЗ курса Java Backend Learning Course (ZhukovSD):
https://zhukovsd.github.io/java-backend-learning-course/projects/hangman/