package blackjack.model.player;

import static blackjack.model.blackjack.Result.DRAW;
import static blackjack.model.blackjack.Result.LOSS;
import static blackjack.model.blackjack.Result.WIN;

import blackjack.model.blackjack.Record;
import blackjack.model.blackjack.Result;
import blackjack.model.card.Card;
import blackjack.model.cards.Cards;
import blackjack.model.cards.MaxScoreCalculator;
import blackjack.model.cards.Score;

public final class Dealer extends Player {

    private static final MaxScoreCalculator MAX_SCORE_CALCULATOR = new MaxScoreCalculator();
    private static final String NAME = "딜러";
    private static final Score HIT_BOUNDARY = new Score(17);
    private static final int OPEN_CARD_COUNT = 1;

    public Dealer(Card card1, Card card2, Card... cards) {
        this(new Cards(card1, card2, cards));
    }

    private Dealer(Cards cards) {
        super(NAME, cards);
    }

    public Record match(Player player) {
        return new Record(player.name(), playerResult(player));
    }

    public Result playerResult(Player player) {
        if (player.isBust()) {
            return LOSS;
        } else if (isBust()) {
            return WIN;
        }
        return compareWith(player);
    }

    private Result compareWith(Player player) {
        if(player.lessScoreThan(this)) {
            return LOSS;
        } else if(player.moreScoreThan(this)) {
            return WIN;
        }
        return DRAW;
    }

    @Override
    public Cards openCards() {
        return cards().openedCards(OPEN_CARD_COUNT);
    }

    @Override
    public boolean isHittable() {
        return maxScore().lessThan(HIT_BOUNDARY);
    }

    private Score maxScore() {
        return MAX_SCORE_CALCULATOR.calculate(cards());
    }
}
