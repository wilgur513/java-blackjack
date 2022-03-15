package blackjack;

import static java.util.stream.Collectors.toUnmodifiableList;

import blackjack.model.blackjack.CardDispenser;
import blackjack.model.player.Dealer;
import blackjack.model.player.Player;
import blackjack.model.player.Players;
import blackjack.view.Answer;
import blackjack.view.InputView;
import blackjack.view.OutputView;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        try {
            run();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void run() {
        CardDispenser cardDispenser = new CardDispenser();
        Dealer dealer = new Dealer(cardDispenser.issue(), cardDispenser.issue());
        Players players = new Players(createPlayers(cardDispenser));
        playBlackjack(cardDispenser, dealer, players);
    }

    private static List<Player> createPlayers(CardDispenser cardDispenser) {
        List<String> names = InputView.inputNames();
        return names.stream()
            .map(n -> new Player(n, cardDispenser.issue(), cardDispenser.issue()))
            .collect(toUnmodifiableList());
    }

    private static void playBlackjack(CardDispenser cardDispenser, Dealer dealer, Players players) {
        printOpenCard(dealer, players);
        takeCards(cardDispenser, dealer, players);
        printResults(dealer, players);
    }

    private static void printOpenCard(Dealer dealer, Players players) {
        OutputView.printOpenCardMessage(dealer.name(), playerNames(players));
        OutputView.printOpenCard(dealer.name(), dealer.openCards());
        for (Player player : players.values()) {
            OutputView.printOpenCard(player.name(), player.openCards());
        }
    }

    private static List<String> playerNames(Players players) {
        return players.values().stream().map(Player::name).collect(toUnmodifiableList());
    }

    private static void takeCards(CardDispenser cardDispenser, Dealer dealer, Players players) {
        for (Player player : players.values()) {
            takePlayerCard(player, cardDispenser);
        }
        takeDealerCard(dealer, cardDispenser);
    }

    private static void takePlayerCard(Player gamer, CardDispenser cardDispenser) {
        while (gamer.isHittable() && isKeepTakeCard(gamer.name())) {
            gamer.take(cardDispenser.issue());
            OutputView.printCards(gamer.name(), gamer.cards());
        }
    }

    private static boolean isKeepTakeCard(String name) {
        Answer answer = InputView.isKeepTakeCard(name);
        return answer.isKeepGoing();
    }

    private static void takeDealerCard(Dealer dealer, CardDispenser cardDispenser) {
        while (dealer.isHittable()) {
            dealer.take(cardDispenser.issue());
            OutputView.printDealerTakeCardMessage();
        }
    }

    private static void printResults(Dealer dealer, Players players) {
        OutputView.printTotalScore(dealer.name(), dealer.cards(), dealer.score());
        for (Player player : players.values()) {
            OutputView.printTotalScore(player.name(), player.cards(), player.score());
        }
        OutputView.printRecords(players.match(dealer));
    }
}
