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
package mapreduce.apps.friends.core;

import javax.annotation.concurrent.Immutable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
@Immutable
public class Account {
	private final AccountId id;
	private final Set<AccountId> friendIds;

	public Account(AccountId id, Set<AccountId> friendIds) {
		this.id = id;
		this.friendIds = friendIds;
	}

	public static Account parse(String idName, String textRepresentingFriendList) {
		return new Account(new AccountId(idName), createAccountIds(textRepresentingFriendList, ","));
	}

	public static Account parseSingleCharacterIds(char idName, String textRepresentingFriendList) {
		return new Account(new AccountId(Character.toString(idName)), createAccountIds(textRepresentingFriendList, ""));
	}

	public AccountId id() {
		return this.id;
	}

	public Set<AccountId> friendIds() {
		return this.friendIds;
	}

	@Override
	public String toString() {
		return "Account[" + id + ", friendIds:" + friendIds + ']';
	}

	private static Set<AccountId> createAccountIds(String textRepresentingFriendList, String splitRegex) {
		if (textRepresentingFriendList.isEmpty()) {
			return Collections.emptySet();
		} else {
			return new TreeSet<>(Arrays.stream(textRepresentingFriendList.split(splitRegex))
					.map(idName -> new AccountId(idName.trim())).toList());
		}
	}
}
