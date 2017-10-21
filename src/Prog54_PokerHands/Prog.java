package Prog54_PokerHands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import base.EulerProgBase;

public class Prog extends EulerProgBase {
	public static void main(String[] args) {
		System.out.println(new Prog().run());
	}

	String fileName = "./src/Prog54_PokerHands/p054_poker.txt";
	ArrayList<String> HANDS = new ArrayList<String>();

	public Prog() {
		load();
	}

	private void load() {
		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			stream.forEach(s -> HANDS.add(s));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String BruteForce() {
		int count = 0;
		for (String hand : HANDS) {
			// Parse cards and divide among players
			String[] cards = hand.split(" ");
			if (cards.length != 10)
				throw new AssertionError();
			Card[] player1 = new Card[5];
			Card[] player2 = new Card[5];
			for (int i = 0; i < 5; i++) {
				player1[i] = new Card(cards[i + 0]);
				player2[i] = new Card(cards[i + 5]);
			}

			// Compare hand scores
			if (getScore(player1) > getScore(player2))
				count++;
		}
		return Integer.toString(count);
	}

	// Returns a score for the given hand. If handX beats handY then getScore(handX)
	// > getScore(handY), and if
	// handX is a draw with handY then getScore(handX) = getScore(handY) (even if
	// the hands have different cards).
	// Note that scores need not be consecutive - for example even if scores 1 and 3
	// exist, there might be no
	// hand that produces a score of 2. The comparison property is the only
	// guarantee provided by getScore().
	private static int getScore(Card[] hand) {
		if (hand.length != 5)
			throw new IllegalArgumentException();

		int[] rankCounts = new int[13]; // rankCounts[i] is the number of cards with the rank of i
		int flushSuit = hand[0].suit; // flushSuit is in the range [0,3] if all cards have that suit; otherwise -1
		for (Card card : hand) {
			rankCounts[card.rank]++;
			if (card.suit != flushSuit)
				flushSuit = -1;
		}

		// rankCountHist[i] is the number of times a rank count of i occurs.
		// For example if there is exactly one triplet, then rankCountHist[3] = 1.
		int[] rankCountHist = new int[6];
		for (int count : rankCounts)
			rankCountHist[count]++;

		int bestCards = get5FrequentHighestCards(rankCounts, rankCountHist);
		int straightHighRank = getStraightHighRank(rankCounts);

		// Main idea: Encode the hand type in the top bits, then encode up to 5 cards in
		// big-endian (4 bits each).
		if (straightHighRank != -1 && flushSuit != -1)
			return 8 << 20 | straightHighRank; // Straight flush
		else if (rankCountHist[4] == 1)
			return 7 << 20 | bestCards; // Four of a kind
		else if (rankCountHist[3] == 1 && rankCountHist[2] == 1)
			return 6 << 20 | bestCards; // Full house
		else if (flushSuit != -1)
			return 5 << 20 | bestCards; // Flush
		else if (straightHighRank != -1)
			return 4 << 20 | straightHighRank; // Straight
		else if (rankCountHist[3] == 1)
			return 3 << 20 | bestCards; // Three of a kind
		else if (rankCountHist[2] == 2)
			return 2 << 20 | bestCards; // Two pairs
		else if (rankCountHist[2] == 1)
			return 1 << 20 | bestCards; // One pair
		else
			return 0 << 20 | bestCards; // High card
	}

	// Encodes 5 card ranks into 20 bits in big-endian, starting with the most
	// frequent cards,
	// breaking ties by highest rank. For example, the set of ranks {5,5,T,8,T} is
	// encoded as
	// the sequence [T,T,5,5,8] because pairs come before singles and highest pairs
	// come first.
	private static int get5FrequentHighestCards(int[] ranks, int[] ranksHist) {
		int result = 0;
		int count = 0;

		for (int i = ranksHist.length - 1; i >= 0; i--) {
			for (int j = ranks.length - 1; j >= 0; j--) {
				if (ranks[j] == i) {
					for (int k = 0; k < i && count < 5; k++, count++)
						result = result << 4 | j;
				}
			}
		}

		if (count != 5)
			throw new IllegalArgumentException();
		return result;
	}

	// Returns the rank of the highest card in the straight, or -1 if the set of
	// cards does not form a straight.
	// This takes into account the fact that ace can be rank 0 (i.e. face value 1)
	// or rank 13 (value immediately after king).
	private static int getStraightHighRank(int[] ranks) {
		outer: for (int i = ranks.length - 1; i >= 3; i--) {
			for (int j = 0; j < 5; j++) {
				if (ranks[(i - j + 13) % 13] == 0)
					continue outer; // Current offset is not a straight
			}
			return i; // Straight found
		}
		return -1;
	}

	private static final class Card {

		public final int rank;
		public final int suit;

		public Card(int rank, int suit) {
			if (rank < 0 || rank >= 13 || suit < 0 || suit >= 4)
				throw new IllegalArgumentException();
			this.rank = rank;
			this.suit = suit;
		}

		public Card(String str) {
			this("23456789TJQKA".indexOf(str.charAt(0)), "SHCD".indexOf(str.charAt(1)));
		}

		public boolean equals(Object obj) {
			if (!(obj instanceof Card))
				return false;
			Card other = (Card) obj;
			return rank == other.rank && suit == other.suit;
		}

		public int hashCode() {
			return rank * 4 + suit;
		}

	}

	@Override
	public String Smart() {
		long sum = 0;
		return String.valueOf(sum);
	}

}
