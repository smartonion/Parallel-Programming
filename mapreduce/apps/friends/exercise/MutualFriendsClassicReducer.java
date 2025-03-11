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
package mapreduce.apps.friends.exercise;

import edu.wustl.cse231s.NotYetImplementedException;
import edu.wustl.cse231s.util.SetIntersector;
import mapreduce.apps.friends.core.AccountId;
import mapreduce.apps.reducer.classic.exercise.ClassicReducer;

import java.util.List;
import java.util.Set;

/**
 * A reducer for the mutual friends program. Given sets of account IDs, it finds
 * the intersection of those sets. In other words, given a set of A's friends
 * and a set of B's friends, it will find the accounts that are friends of both
 * A and B.
 *
 * @author Martin Hristov
 * @author Finn Voichick
 * @author <a href="https://www.cse.wustl.edu/~dennis.cosgrove/">Dennis Cosgrove</a>
 */
public class MutualFriendsClassicReducer extends ClassicReducer<Set<AccountId>, Set<AccountId>> {
	@Override
	public Set<AccountId> reduce(List<Set<AccountId>> container) {
		SetIntersector<AccountId> setIntersector = new SetIntersector<>();

		throw new NotYetImplementedException();

	}
}
