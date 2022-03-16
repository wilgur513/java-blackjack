package blackjack.model.player;

import static blackjack.model.card.Suit.CLOVER;
import static blackjack.model.card.Suit.DIAMOND;
import static blackjack.model.card.Suit.HEART;
import static blackjack.model.card.Suit.SPADE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import blackjack.model.card.Card;
import blackjack.model.card.Rank;
import blackjack.model.cards.Score;
import java.math.BigDecimal;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class DealerTest {

    private static final Card ACE = new Card(Rank.ACE, SPADE);
    private static final Card THREE = new Card(Rank.THREE, CLOVER);
    private static final Card FOUR = new Card(Rank.FOUR, HEART);
    private static final Card SIX = new Card(Rank.SIX, SPADE);
    private static final Card SEVEN = new Card(Rank.SEVEN, CLOVER);
    private static final Card JACK = new Card(Rank.JACK, HEART);
    private static final Card QUEEN = new Card(Rank.QUEEN, SPADE);

    @ParameterizedTest
    @MethodSource("provideCardsForDealer")
    @DisplayName("딜러 카드 발급 가능 여부 확인 테스트")
    void dealerPossibleTakeCard(Dealer dealer, boolean expect) {
        assertThat(dealer.isHittable()).isEqualTo(expect);
    }

    private static Stream<Arguments> provideCardsForDealer() {
        return Stream.of(
            Arguments.of(new Dealer(JACK, SIX), true),
            Arguments.of(new Dealer(JACK, SEVEN), false),
            Arguments.of(new Dealer(ACE, SIX), false),
            Arguments.of(new Dealer(ACE, ACE), false)
        );
    }

    @Test
    @DisplayName("딜러 카드 발급")
    void takeCards() {
        Dealer dealer = new Dealer(JACK, THREE);
        dealer.take(ACE);
        assertThat(dealer.score()).isEqualTo(new Score(14));
    }

    @Test
    @DisplayName("딜러 카드 발급 후 재 발급 불가")
    void takeCardAndIsHittable() {
        Dealer dealer = new Dealer(JACK, SIX);
        dealer.take(ACE);
        assertThat(dealer.isHittable()).isFalse();
    }

    @Test
    @DisplayName("딜러 카드 발급 실패")
    void takeInvalidCard() {
        Dealer dealer = new Dealer(JACK, SEVEN);
        assertThatThrownBy(() -> dealer.take(FOUR))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("딜러 카드 공개")
    void dealerOpenCard() {
        Dealer dealer = new Dealer(JACK, QUEEN);
        assertThat(dealer.openCards().values()).hasSize(1);
        assertThat(dealer.openCards().values()).contains(JACK);
    }

}
