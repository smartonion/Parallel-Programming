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
package hashtable.notthreadsafe.client;

import edu.wustl.cse231s.text.core.TextSection;
import edu.wustl.cse231s.text.resources.TextSectionResource;
import hashtable.notthreadsafe.exercise.NotThreadSafeHashTable;

import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class PutClient {
	public static void main(String[] args) {
		int chainCount = 8;
		Map<Character, String> mapFirstCharacterToLastEncounteredWord = new NotThreadSafeHashTable<>(chainCount,
				Objects::hashCode);

		for (TextSection textSection : TextSectionResource.GETTYSBURG_ADDRESS.textSections()) {
			for (String word : textSection.words()) {
				if (word.length() > 0) {
					char firstCharacterLowerCased = Character.toLowerCase(word.charAt(0));
					mapFirstCharacterToLastEncounteredWord.put(firstCharacterLowerCased, word);
				}
			}
		}

		for (char ch = 'a'; ch <= 'z'; ++ch) {
			String value = mapFirstCharacterToLastEncounteredWord.get(ch);
			System.out.println(ch + ": " + toString(value));
		}
	}

	private static String toString(String value) {
		return value != null ? value : "";
	}
}
