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

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public enum Suit {
	SPADES('\u2660'), HEARTS('\u2665'), DIAMONDS('\u2666'), CLUBS('\u2663');

	private final char unicode;

	private Suit(char unicode) {
		this.unicode = unicode;
	}

	public char unicode() {
		return unicode;
	}

	public String toHtml() {
		if (this == HEARTS || this == DIAMONDS) {
			return "<font color=\"red\">" + unicode + "</font>";
		} else {
			return Character.toString(unicode);
		}
	}

	public static Suit parse(char ch) {
		for (Suit suit : values()) {
			if (suit.unicode == ch) {
				return suit;
			}
		}
		throw new IllegalArgumentException(Character.toString(ch));
	}

}
