/*
 * #%L
 * elk-reasoner
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Oxford University Computing Laboratory
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.semanticweb.elk.util;

import java.util.Set;

/**
 * 
 * Implementation of Multimap backed by an ArrayHashMap
 * 
 * @author Frantisek Simancik
 * 
 * @param <Key>
 * @param <Value>
 */

public class HashSetMultimap<Key, Value> extends ArrayHashMap<Key, Set<Value>>
		implements Multimap<Key, Value> {

	public HashSetMultimap() {
		super();
	}

	public HashSetMultimap(int i) {
		super(i);
	}

	public boolean contains(Key key, Value value) {
		Set<Value> record = get(key);
		if (record == null)
			return false;
		else
			return record.contains(value);
	}

	public boolean add(Key key, Value value) {
		Set<Value> record = get(key);
		if (record == null) {
			record = new ArrayHashSet<Value>(1);
			put(key, record);
		}
		return record.add(value);
	}
}