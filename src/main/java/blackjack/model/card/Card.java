package blackjack.model.card;

import java.util.Objects;

public final class Card {

    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public int hardRank() {
        return rank.hard();
    }

    public int softRank() {
        return rank.soft();
    }

    public boolean isAce() {
        return rank.isAce();
    }

    public Rank rank() {
        return rank;
    }

    public Suit suit() {
        return suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return rank == card.rank && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    @Override
    public String toString() {
        return rank.hard() + "-" + suit;
    }

}
