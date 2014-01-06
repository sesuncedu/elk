/**
 * 
 */
package org.semanticweb.elk.reasoner.saturation.tracing;

/*
 * #%L
 * ELK Reasoner
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2013 Department of Computer Science, University of Oxford
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

import org.semanticweb.elk.reasoner.saturation.conclusions.BaseConclusionVisitor;
import org.semanticweb.elk.reasoner.saturation.conclusions.Conclusion;
import org.semanticweb.elk.reasoner.saturation.context.Context;

/**
 * A conclusion visitor which processes {@link TracedConclusion}s and saves
 * their inferences using a {@link TraceStore.Writer}.
 * 
 * @author Pavel Klinov
 * 
 *         pavel.klinov@uni-ulm.de
 */
public class TracingConclusionInsertionVisitor extends
		BaseConclusionVisitor<Boolean, Context> {

	private final TraceStore.Writer traceWriter_;

	private final TracedConclusionVisitor<Boolean, Context> tracedVisitor_ = new BaseTracedConclusionVisitor<Boolean, Context>() {

		@Override
		protected Boolean defaultTracedVisit(TracedConclusion conclusion,
				Context context) {
			traceWriter_.addInference(context, conclusion);

			return true;
		}

	};

	/**
	 * 
	 */
	public TracingConclusionInsertionVisitor(TraceStore.Writer traceWriter) {
		traceWriter_ = traceWriter;
	}

	@Override
	protected Boolean defaultVisit(Conclusion conclusion, Context cxt) {
		return ((TracedConclusion) conclusion)
				.acceptTraced(tracedVisitor_, cxt);
	}

}