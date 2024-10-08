/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.flink.cep.nfa.aftermatch;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.flink.cep.nfa.sharedbuffer.EventId;

/** Discards every partial match that started with the same event, emitted match was started. */
public final class SkipToNextStrategy extends SkipRelativeToWholeMatchStrategy {

    public static final SkipToNextStrategy INSTANCE = new SkipToNextStrategy();

    private static final long serialVersionUID = -6490314998588752621L;

    private SkipToNextStrategy() {}

    @Override
    protected EventId getPruningId(final Collection<Map<String, List<EventId>>> match) {
        EventId pruningId = null;
        for (Map<String, List<EventId>> resultMap : match) {
            for (List<EventId> eventList : resultMap.values()) {
                pruningId = min(pruningId, eventList.get(0));
            }
        }

        return pruningId;
    }

    @Override
    public String toString() {
        return "SkipToNextStrategy{}";
    }
}
