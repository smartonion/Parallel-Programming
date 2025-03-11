/*******************************************************************************
 * Copyright (C) 2016-2023 Dennis Cosgrove
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package mapreduce.apps.cards.core;

import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
@NotThreadSafe
public final class Deck implements Iterable<Card> {
	private final Card[] cards;

	public Deck(Card[] cards) {
		this.cards = cards;
	}

	public Deck(Card a, Card... bToZ) {
		cards = new Card[1 + bToZ.length];
		cards[0] = a;
		System.arraycopy(bToZ, 0, cards, 1, bToZ.length);
	}

	public static Deck createFull() {
		Card[] cards = new Card[Suit.values().length * Rank.values().length];
		int i = 0;
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				cards[i] = new Card(suit, rank);
				++i;
			}
		}
		return new Deck(cards);
	}

	public void shuffle(Random random) {
		ArrayUtils.shuffle(cards, random);
	}

	@Override
	public Iterator<Card> iterator() {
		return Arrays.asList(cards).iterator();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Deck cards1 = (Deck) o;
		return Arrays.equals(cards, cards1.cards);
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(cards);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + Arrays.toString(cards);
	}

	public String toPrettyString() {
		String textWithBrackets = Arrays.stream(cards).map(Card::toPrettyString).toList().toString();
		return textWithBrackets.substring(1, textWithBrackets.length() - 1);
	}

	public String toHtml() {
		String textWithBrackets = Arrays.stream(cards).map(Card::toHtml).toList().toString();
		return textWithBrackets.substring(1, textWithBrackets.length() - 1);
	}

	public static Deck parse(String text) {
		String[] texts = text.split(", ");
		return new Deck(Arrays.stream(texts).map(Card::parse).toArray(Card[]::new));
	}
}
