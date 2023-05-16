package game;

import enums.Colors;
import enums.Labels;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private final Map<Integer, Colors> correctColors = new HashMap<>();
    private final Map<Integer, Colors> guessedColors = new HashMap<>();
    @Getter
    private final Map<Integer, Labels> labels = new HashMap<>();
    @Getter
    @Setter
    private int triesLimit = 10;
    @Setter
    @Getter
    private int triesCounter = 0;
    @Getter
    private int correctlyGuessed = 0;
    @Getter
    private int partlyGuessed = 0;
    @Getter
    private int incorrectlyGuessed = 0;

    public void setCorrectColors(final Colors firstColor,
                                 final Colors secondColor,
                                 final Colors thirdColor,
                                 final Colors fourthColor) {
        correctColors.put(0, firstColor);
        correctColors.put(1, secondColor);
        correctColors.put(2, thirdColor);
        correctColors.put(3, fourthColor);
    }

    public void guessColors(final Colors firstColor,
                            final Colors secondColor,
                            final Colors thirdColor,
                            final Colors fourthColor) {
        guessedColors.put(0, firstColor);
        guessedColors.put(1, secondColor);
        guessedColors.put(2, thirdColor);
        guessedColors.put(3, fourthColor);

        triesCounter++;
        correctlyGuessed = 0;
        partlyGuessed = 0;
        incorrectlyGuessed = 0;

        checkGuessedColorsCorrectness();
    }

    public boolean isGameWon() {
        return correctlyGuessed == 4 && triesCounter <= triesLimit;
    }

    private void checkGuessedColorsCorrectness() {
        for (int key = 0; key < 4; key++) {
            checkIfCorrectlyGuessed(key);
        }

        for (int key = 0; key < 4; key++) {
            if (labels.get(key)
                      .equals(Labels.INCORRECT)) {
                checkIfPartlyCorrectlyGuessed(key);
            }
        }
    }

    private void checkIfCorrectlyGuessed(final int key) {
        if (correctColors.get(key)
                         .equals(guessedColors.get(key))) {
            correctlyGuessed++;
            labels.put(key, Labels.CORRECT);
        } else {
            incorrectlyGuessed++;
            labels.put(key, Labels.INCORRECT);
        }
    }

    private void checkIfPartlyCorrectlyGuessed(final int labelKey) {
        for (int key = 0; key < 4; key++) {
            if (labelKey != key &&
                correctColors.get(labelKey).equals(guessedColors.get(key))) {
                partlyGuessed++;
                incorrectlyGuessed--;
                labels.put(labelKey, Labels.PARTLY);
                break;
            }
        }
    }
}
