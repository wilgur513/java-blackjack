package blackjack.model.blackjack;

public enum Result {
    WIN("승"), DRAW("무"), LOSS("패");

    private final String symbol;

    Result(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }

    public Result reverse() {
        if (this == WIN) {
            return LOSS;
        } else if (this == LOSS) {
            return WIN;
        }
        return DRAW;
    }
}
