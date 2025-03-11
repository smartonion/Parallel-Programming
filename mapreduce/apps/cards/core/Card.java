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

import javax.annotation.concurrent.Immutable;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
@Immutable
public final class Card {
	private final Suit suit;
	private final Rank rank;

	public Card(Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}

	public Suit suit() {
		return suit;
	}

	public Rank rank() {
		return rank;
	}

	public static Card parse(String text) {
		Suit suit = Suit.parse(text.charAt(0));
		Rank rank = Rank.parse(text.substring(1));
		return new Card(suit, rank);
	}

	public String toPrettyString() {
		StringBuilder sb = new StringBuilder();
		sb.append(suit.unicode());
		if (rank.isNumericValuePresent()) {
			sb.append(rank.numericValue());
		} else {
			sb.append(rank.name().charAt(0));
		}
		return sb.toString();
	}

	public String toHtml() {
		StringBuilder sb = new StringBuilder();
		if (suit == Suit.HEARTS || suit == Suit.DIAMONDS) {
			sb.append("<font color=\"red\">");
		}
		sb.append(suit.unicode());
		sb.append(" ");
		sb.append(rank.name());
		if (suit == Suit.HEARTS || suit == Suit.DIAMONDS) {
			sb.append("</font>");
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" +
				suit +
				"," +
				rank +
				"]";
	}
}
