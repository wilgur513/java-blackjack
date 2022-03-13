package blackjack.model.player;

import blackjack.model.card.Card;
import blackjack.model.cards.Cards;
import blackjack.model.cards.Score;
import blackjack.model.cards.ScoreCards;

public class Player {

    private static final Score HIT_BOUNDARY = new Score(21);
    private static final int OPEN_CARD_COUNT = 2;

    private final ScoreCards cards;
    private final Name name;
    private final int openCardCount;

    public Player(Name name, Card card1, Card card2, Card... cards) {
        this(name, Cards.of(card1, card2, cards), OPEN_CARD_COUNT);
    }

    Player(Name name, Cards cards, int openCardCount) {
        this.name = name;
        this.cards = Cards.bestScoreCards(cards);
        this.openCardCount = openCardCount;
    }

    public final Name name() {
        return name;
    }

    public Cards openCards() {
        return cards.openedCards(openCardCount);
    }

    public final Cards cards() {
        return Cards.copyOf(cards);
    }

    public final void take(Card card) {
        if (!isHittable()) {
            throw new IllegalStateException("카드를 더 이상 발급 받을 수 없습니다.");
        }
        cards.take(card);
    }

    public final Score score() {
        return cards.score();
    }

    public final boolean isBust() {
        return score().isBust();
    }

    public boolean isHittable() {
        return cards.lessThan(HIT_BOUNDARY);
    }
}
