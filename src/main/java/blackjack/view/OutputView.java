package blackjack.view;

import blackjack.model.blackjack.Record;
import blackjack.model.blackjack.Result;
import blackjack.model.card.Card;
import blackjack.model.cards.Cards;
import blackjack.model.cards.Score;
import blackjack.model.player.Name;
import blackjack.model.player.Player;
import java.util.List;
import java.util.stream.Collectors;

public class OutputView {

    private OutputView() {
    }

    public static void printOpenCardMessage(Name dealerName, List<Name> playerNames) {
        System.out.printf("%s와 %s에게 2장을 나누었습니다.%n", dealerName.value(),
            formattedPlayerNames(playerNames));
    }

    private static String formattedPlayerNames(List<Name> names) {
        return names.stream()
            .map(Name::value)
            .collect(Collectors.joining(", "));
    }

    public static void printOpenCard(Name name, Cards cards) {
        System.out.printf("%s: %s%n", name.value(), formattedCardsText(cards));
    }

    public static void printCards(Name name, Cards cards) {
        System.out.printf("%s: %s%n", name.value(), formattedCardsText(cards));
    }

    private static String formattedCardsText(Cards openCard) {
        return openCard.values().stream()
            .map(OutputView::cardText)
            .collect(Collectors.joining(", "));
    }

    private static String cardText(Card card) {
        return card.rank().symbol() + card.suit().symbol();
    }

    private static String takenCards(Player player) {
        return player.cards().values().stream()
            .map(OutputView::cardText)
            .collect(Collectors.joining(", "));
    }

    public static void printDealerTakeCardMessage() {
        System.out.println("딜러는 16이하라 한장의 카드를 더 받았습니다.");
    }

    public static void printTotalScore(Name name, Cards cards, Score score) {
        System.out.printf("%s 카드: %s - 결과: %d%n", name.value(), formattedCardsText(cards),
            score.getValue());
    }

    public static void printRecords(List<Record> records) {
        System.out.println("## 최종 승패");
        System.out.printf("딜러: %d승 %d무 %d패%n", countByResult(records, Result.LOSS),
            countByResult(records, Result.DRAW), countByResult(records, Result.WIN));

        records.stream().
            forEach(record -> printEachPlayerRecord(record.name(), record.result()));
    }

    private static int countByResult(List<Record> records, Result result) {
        return (int) records.stream()
            .map(Record::result)
            .filter(r -> r == result).count();
    }

    private static void printEachPlayerRecord(Name name, Result result) {
        System.out.printf("%s: %s%n", name.value(), result.symbol());
    }
}
