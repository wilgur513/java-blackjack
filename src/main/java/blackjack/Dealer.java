package blackjack;

import blackjack.cards.Cards;
import java.util.List;

public class Dealer extends Player {

    public Dealer(Card card1, Card card2, Card... cards) {
        super("딜러", List.of(card1), Cards.mixHandCards(card1, card2, cards));
    }

    public Result match(Cards cards) {
        Score playerScore = cards.score();
        if (playerScore.isBust()) {
            return Result.WIN;
        }
        if (score().isBust()) {
            return Result.LOSS;
        }
        return compare(playerScore);
    }

    private Result compare(Score playerScore) {
        if (score().lessThan(playerScore)) {
            return Result.LOSS;
        }
        if (score().moreThan(playerScore)) {
            return Result.WIN;
        }
        return Result.DRAW;
    }

    public boolean isHittable() {
        return cards.toSoftHand().score().lessThan(new Score(17));
    }
}
