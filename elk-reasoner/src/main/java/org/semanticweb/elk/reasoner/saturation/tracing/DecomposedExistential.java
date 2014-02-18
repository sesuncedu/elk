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

import org.semanticweb.elk.reasoner.indexing.hierarchy.IndexedObjectSomeValuesFrom;
import org.semanticweb.elk.reasoner.saturation.conclusions.BackwardLink;
import org.semanticweb.elk.reasoner.saturation.conclusions.BackwardLinkImpl;
import org.semanticweb.elk.reasoner.saturation.conclusions.Subsumer;
import org.semanticweb.elk.reasoner.saturation.context.Context;
import org.semanticweb.elk.reasoner.saturation.tracing.util.TracingUtils;

/**
 * @author Pavel Klinov
 *
 * pavel.klinov@uni-ulm.de
 */
public class DecomposedExistential extends BackwardLinkImpl implements Inference, BackwardLink {

	private final Context inferenceContext_;
	
	private final IndexedObjectSomeValuesFrom existential_;
	
	/**
	 * 
	 */
	public DecomposedExistential(IndexedObjectSomeValuesFrom subsumer, Context source) {
		super(source, subsumer.getRelation());
		existential_ = subsumer;
		inferenceContext_ = source;
	}
	
	@Override
	public <R, C> R acceptTraced(InferenceVisitor<R, C> visitor, C parameter) {
		return visitor.visit(this, parameter);
	}

	public Subsumer getExistential() {
		return TracingUtils.getSubsumerWrapper(existential_);
	}

	@Override
	public Context getInferenceContext(Context defaultContext) {
		return inferenceContext_;
	}
	
	@Override
	public String toString() {
		return super.toString() + " (decomposition)";
	}
}
