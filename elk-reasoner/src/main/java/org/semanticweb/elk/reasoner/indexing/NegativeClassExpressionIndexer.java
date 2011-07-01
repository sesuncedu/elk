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
/**
 * @author Yevgeny Kazakov, May 13, 2011
 */
package org.semanticweb.elk.reasoner.indexing;


/**
 * For indexing negative occurrences of class expressions.
 * 
 * @author Yevgeny Kazakov
 * @author Frantisek Simancik
 * 
 */
class NegativeClassExpressionIndexer implements
		IndexedClassExpressionVisitor<Void> {

	protected final AxiomIndexer axiomIndexer;
	
	NegativeClassExpressionIndexer(AxiomIndexer axiomIndexer) {
		this.axiomIndexer = axiomIndexer;
	}

/*
	public IndexedClassExpression visit(
			ElkObjectIntersectionOf elkObjectIntersectionOf) {

		IndexedObjectIntersectionOf result = (IndexedObjectIntersectionOf) 
			axiomIndexer.ontologyIndex.getCreateIndexedClassExpression(elkObjectIntersectionOf);
		if (result.negativeOccurrenceNo++ == 0) {

			int conjunctionSize = elkObjectIntersectionOf.getClassExpressions()
					.size();
			assert conjunctionSize > 1;
			int i = 0;
			IndexedClassExpression prefixConjunction = null;
			for (ElkClassExpression element : elkObjectIntersectionOf
					.getClassExpressions()) {
				i++;
				IndexedClassExpression indexedElement = element.accept(this);
				if (i == 1) {
					prefixConjunction = indexedElement;
					continue;
				} else if (i < conjunctionSize) {
					List<ElkClassExpression> arguments = new ArrayList<ElkClassExpression>(
							2);
					arguments.add(prefixConjunction.getClassExpression());
					arguments.add(element);
					prefixConjunction = ElkObjectIntersectionOf.create(
							arguments).accept(this);
				} else {
					prefixConjunction.addNegativeConjunctionByConjunct(result, indexedElement);
					indexedElement.addNegativeConjunctionByConjunct(result, prefixConjunction);
				}
			}
		}
		return result;
	}

	
	public IndexedClassExpression visit(ElkObjectSomeValuesFrom classExpression) {
		
		IndexedObjectSomeValuesFrom result = (IndexedObjectSomeValuesFrom)
			axiomIndexer.ontologyIndex.getCreateIndexedClassExpression(classExpression);
		if (result.negativeOccurrenceNo++ == 0) {
			result.setRelation(classExpression.getObjectPropertyExpression().accept(
					axiomIndexer.objectPropertyExpressionIndexer));
			result.setFiller(classExpression.getClassExpression().accept(this));
			result.getFiller().addNegativeExistential(result);
		}
		return result;
	}
*/
	
	public Void visit(IndexedClass indexedClass) {
		indexedClass.negativeOccurrenceNo += axiomIndexer.multiplicity;
		assert indexedClass.negativeOccurrenceNo >= 0;
		
		axiomIndexer.ontologyIndex.removeIfNoOccurrence(indexedClass);
		return null;
	}

	public Void visit(IndexedObjectIntersectionOf indexedObjectIntersectionOf) {
		int oldOccurrenceNo = indexedObjectIntersectionOf.negativeOccurrenceNo;
		indexedObjectIntersectionOf.negativeOccurrenceNo += axiomIndexer.multiplicity;
		assert indexedObjectIntersectionOf.negativeOccurrenceNo >= 0;
		
		if (oldOccurrenceNo == 0 && indexedObjectIntersectionOf.negativeOccurrenceNo == 1) {
			indexedObjectIntersectionOf.firstConjunct.addNegConjunctionByConjunct(
					indexedObjectIntersectionOf, indexedObjectIntersectionOf.secondConjunct);
			indexedObjectIntersectionOf.secondConjunct.addNegConjunctionByConjunct(
					indexedObjectIntersectionOf, indexedObjectIntersectionOf.firstConjunct);
		}
		
		if (oldOccurrenceNo == 1 && indexedObjectIntersectionOf.negativeOccurrenceNo == 0) {
			indexedObjectIntersectionOf.firstConjunct.removeNegConjunctionByConjunct(
					indexedObjectIntersectionOf, indexedObjectIntersectionOf.secondConjunct);
			indexedObjectIntersectionOf.secondConjunct.removeNegConjunctionByConjunct(
					indexedObjectIntersectionOf, indexedObjectIntersectionOf.firstConjunct);
		}

		indexedObjectIntersectionOf.firstConjunct.accept(this);
		indexedObjectIntersectionOf.secondConjunct.accept(this);
		
		axiomIndexer.ontologyIndex.removeIfNoOccurrence(indexedObjectIntersectionOf);
		return null;
	}

	public Void visit(IndexedObjectSomeValuesFrom indexedObjectSomeValuesFrom) {
		int oldOccurrenceNo = indexedObjectSomeValuesFrom.negativeOccurrenceNo;
		indexedObjectSomeValuesFrom.negativeOccurrenceNo += axiomIndexer.multiplicity;
		assert indexedObjectSomeValuesFrom.negativeOccurrenceNo >= 0;
		
		if (oldOccurrenceNo == 0 && indexedObjectSomeValuesFrom.negativeOccurrenceNo == 1) {
			indexedObjectSomeValuesFrom.filler.addNegExistential(indexedObjectSomeValuesFrom);
		}
		
		if (oldOccurrenceNo == 1 && indexedObjectSomeValuesFrom.negativeOccurrenceNo == 0) {
			indexedObjectSomeValuesFrom.filler.removeNegExistential(indexedObjectSomeValuesFrom);
		}
		
		axiomIndexer.objectPropertyExpressionIndexer.visit(indexedObjectSomeValuesFrom.relation);
		indexedObjectSomeValuesFrom.filler.accept(this);

		axiomIndexer.ontologyIndex.removeIfNoOccurrence(indexedObjectSomeValuesFrom);
		return null;
	}

}