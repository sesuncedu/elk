/**
 * 
 */
package org.semanticweb.elk.proofs.expressions.derived;
/*
 * #%L
 * ELK Proofs Package
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2011 - 2014 Department of Computer Science, University of Oxford
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

import org.semanticweb.elk.owl.interfaces.ElkAxiom;
import org.semanticweb.elk.proofs.expressions.lemmas.ElkLemma;
import org.semanticweb.elk.proofs.inferences.readers.InferenceReader;

/**
 * @author Pavel Klinov
 *
 * pavel.klinov@uni-ulm.de
 */
public class DummyExpressionFactory implements DerivedExpressionFactory {

	@Override
	public DerivedAxiomExpressionImpl<?> create(ElkAxiom axiom) {
		return new DerivedAxiomExpressionImpl<ElkAxiom>(axiom, InferenceReader.DUMMY);
	}

	@Override
	public LemmaExpressionImpl create(ElkLemma lemma) {
		return new LemmaExpressionImpl(lemma, InferenceReader.DUMMY);
	}

	@Override
	public DerivedAxiomExpressionImpl<?> createAsserted(ElkAxiom axiom) {
		return new DerivedAxiomExpressionImpl<ElkAxiom>(axiom, InferenceReader.DUMMY, true);
	}

}