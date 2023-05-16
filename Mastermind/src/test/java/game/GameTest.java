package game;

import enums.Colors;
import enums.Labels;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game();
    }

    @Test
    public void triesCounterShouldShowCorrectValue() {
        //given
        final int expectedTries = 2;

        game.setCorrectColors(Colors.BLUE,
                              Colors.BLUE,
                              Colors.BLUE,
                              Colors.BLUE);

        game.guessColors(Colors.RED,
                         Colors.RED,
                         Colors.RED,
                         Colors.RED);

        game.guessColors(Colors.RED,
                         Colors.RED,
                         Colors.RED,
                         Colors.RED);


        //when
        final int tries = game.getTriesCounter();

        //then
        assertEquals(expectedTries, tries);
    }


    @ParameterizedTest
    @MethodSource("colorSchemesGameStatusSource")
    public void isGameWonShouldShowCorrectValue(final List<Colors> correctColors,
                                                final List<Colors> guessedColors,
                                                final int numberOfTries,
                                                final boolean expectedGameStatus) {
        //given
        game.setCorrectColors(correctColors.get(0),
                              correctColors.get(1),
                              correctColors.get(2),
                              correctColors.get(3));

        game.guessColors(guessedColors.get(0),
                         guessedColors.get(1),
                         guessedColors.get(2),
                         guessedColors.get(3));

        game.setTriesCounter(numberOfTries);

        //when
        final boolean gameStatus = game.isGameWon();

        //then
        assertEquals(expectedGameStatus, gameStatus);
    }

    @ParameterizedTest
    @MethodSource("colorSchemesCountersSource")
    public void correctnessCountersShouldShowCorrectValue(final List<Colors> correctColors,
                                                          final List<Colors> guessedColors,
                                                          final int expectedCorrectCounter,
                                                          final int expectedPartlyCounter,
                                                          final int expectedIncorrectCounter) {
        //given
        game.setCorrectColors(correctColors.get(0),
                              correctColors.get(1),
                              correctColors.get(2),
                              correctColors.get(3));

        game.guessColors(guessedColors.get(0),
                         guessedColors.get(1),
                         guessedColors.get(2),
                         guessedColors.get(3));

        //when
        final int correctCounter = game.getCorrectlyGuessed();
        final int partlyCounter = game.getPartlyGuessed();
        final int incorrectCounter = game.getIncorrectlyGuessed();

        //then
        assertEquals(expectedCorrectCounter, correctCounter);
        assertEquals(expectedPartlyCounter, partlyCounter);
        assertEquals(expectedIncorrectCounter, incorrectCounter);
    }

    @ParameterizedTest
    @MethodSource("colorSchemesLabelSource")
    public void labelsShouldBeCorrectlyAssigned(final List<Colors> correctColors,
                                                final List<Colors> guessedColors,
                                                final List<Labels> expectedLabels) {
        //given
        game.setCorrectColors(correctColors.get(0),
                              correctColors.get(1),
                              correctColors.get(2),
                              correctColors.get(3));

        game.guessColors(guessedColors.get(0),
                         guessedColors.get(1),
                         guessedColors.get(2),
                         guessedColors.get(3));

        //when
        final Map<Integer, Labels> labels = game.getLabels();

        //then
        assertEquals(expectedLabels.get(0), labels.get(0));
        assertEquals(expectedLabels.get(1), labels.get(1));
        assertEquals(expectedLabels.get(2), labels.get(2));
        assertEquals(expectedLabels.get(3), labels.get(3));
    }

    private static Stream<Arguments> colorSchemesLabelSource() {
        return Stream.of(
                Arguments.of(List.of(Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.RED,
                                     Colors.GREEN),
                             List.of(Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.GREEN,
                                     Colors.RED),
                             List.of(Labels.CORRECT,
                                     Labels.CORRECT,
                                     Labels.PARTLY,
                                     Labels.PARTLY)),

                Arguments.of(List.of(Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.RED,
                                     Colors.GREEN),
                             List.of(Colors.BLUE,
                                     Colors.GREEN,
                                     Colors.BLUE,
                                     Colors.PURPLE),
                             List.of(Labels.CORRECT,
                                     Labels.PARTLY,
                                     Labels.INCORRECT,
                                     Labels.PARTLY)),

                Arguments.of(List.of(Colors.BLUE,
                                     Colors.RED,
                                     Colors.RED,
                                     Colors.RED),
                             List.of(Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.GREEN),
                             List.of(Labels.CORRECT,
                                     Labels.INCORRECT,
                                     Labels.INCORRECT,
                                     Labels.INCORRECT)),

                Arguments.of(List.of(Colors.BLUE,
                                     Colors.RED,
                                     Colors.RED,
                                     Colors.RED),
                             List.of(Colors.RED,
                                     Colors.RED,
                                     Colors.RED,
                                     Colors.BLUE),
                             List.of(Labels.PARTLY,
                                     Labels.CORRECT,
                                     Labels.CORRECT,
                                     Labels.PARTLY))
        );
    }

    private static Stream<Arguments> colorSchemesCountersSource() {
        return Stream.of(
                Arguments.of(List.of(Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.RED,
                                     Colors.GREEN),
                             List.of(Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.GREEN,
                                     Colors.RED),
                             2,
                             2,
                             0),

                Arguments.of(List.of(Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.RED,
                                     Colors.GREEN),
                             List.of(Colors.BLUE,
                                     Colors.GREEN,
                                     Colors.BLUE,
                                     Colors.PURPLE),
                             1,
                             2,
                             1),

                Arguments.of(List.of(Colors.BLUE,
                                     Colors.RED,
                                     Colors.RED,
                                     Colors.RED),
                             List.of(Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.GREEN),
                             1,
                             0,
                             3),

                Arguments.of(List.of(Colors.BLUE,
                                     Colors.RED,
                                     Colors.RED,
                                     Colors.RED),
                             List.of(Colors.RED,
                                     Colors.RED,
                                     Colors.RED,
                                     Colors.BLUE),
                             2,
                             2,
                             0)
        );
    }
    private static Stream<Arguments> colorSchemesGameStatusSource() {
        return Stream.of(
                Arguments.of(List.of(Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.RED,
                                     Colors.GREEN),
                             List.of(Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.RED,
                                     Colors.GREEN),
                             10,
                             true),

                Arguments.of(List.of(Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.RED,
                                     Colors.GREEN),
                             List.of(Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.RED,
                                     Colors.GREEN),
                             11,
                             false),

                Arguments.of(List.of(Colors.BLUE,
                                     Colors.RED,
                                     Colors.RED,
                                     Colors.RED),
                             List.of(Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.BLUE,
                                     Colors.GREEN),
                             10,
                             false)
        );
    }
}
