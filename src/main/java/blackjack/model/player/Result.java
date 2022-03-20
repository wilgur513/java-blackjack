package blackjack.model.player;

import java.math.BigDecimal;

public enum Result {
    WIN(BigDecimal.ONE), LOSS(new BigDecimal("-1")),
    DRAW(BigDecimal.ZERO), BLACKJACK(new BigDecimal("1.5"));

    private BigDecimal profitRate;

    Result(BigDecimal profitRate) {
        this.profitRate = profitRate;
    }

    Money profit(Money money) {
        return money.multiply(profitRate);
    }
}
