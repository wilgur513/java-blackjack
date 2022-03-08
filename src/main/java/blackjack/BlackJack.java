package blackjack;

import java.util.List;

public class BlackJack {

    public static boolean play(List<String> dealerCards, List<String> playerCards) {
        int dealerScore = totalScore(dealerCards);
        int playerScore = totalScore(playerCards);
        if (isBust(dealerScore)) {
            return false;
        }
        return dealerScore > playerScore;
    }

    private static boolean isBust(int dealerScore) {
        return dealerScore > 21;
    }

    private static int totalScore(List<String> dealerCards) {
        int score = 0;
        for (String s : dealerCards) {
            String value = s.substring(0, 1);
            score += number(value);
        }
        return score;
    }

    private static int number(String value) {
        if (value.equals("J") || value.equals("Q") || value.equals("K")) {
            return 10;
        }
        if (value.equals(("A"))) {
            return 11;
        }
        return Integer.parseInt(value);
    }
}
