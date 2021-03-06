/*
 * Copyright 2010 James Pether Sörling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *	$Id$
 *  $HeadURL$
*/
package com.hack23.cia.service.external.esv.api;

import org.junit.runner.RunWith;
import org.junit.runners.AllTests;

import com.hack23.cia.testfoundation.AbstractUnitTest;
import com.tocea.easycoverage.framework.checkers.ArrayIndexOutOfBoundExceptionChecker;
import com.tocea.easycoverage.framework.checkers.BijectiveEqualsChecker;
import com.tocea.easycoverage.framework.checkers.CloneChecker;
import com.tocea.easycoverage.framework.checkers.NPEConstructorChecker;
import com.tocea.easycoverage.framework.checkers.NullValueEqualsChecker;
import com.tocea.easycoverage.framework.checkers.SetterChecker;
import com.tocea.easycoverage.framework.checkers.ToStringNotNullChecker;
import com.tocea.easycoverage.framework.junit.JUnitTestSuiteProvider;

import junit.framework.TestSuite;

/**
 * The Class ApiDtoSanityTest.
 */
@RunWith(AllTests.class)
public final class ApiDtoSanityTest extends AbstractUnitTest {


	/**
	 * Suite.
	 *
	 * @return the test suite
	 */
	public static TestSuite suite() {
		final JUnitTestSuiteProvider testSuiteProvider = new JUnitTestSuiteProvider();

		testSuiteProvider.addClass(GovernmentBodyAnnualOutcomeSummary.class);
		testSuiteProvider.addClass(GovernmentBodyAnnualSummary.class);
		testSuiteProvider.addClass(GovernmentOperationPeriodOutcome.class);
		
		testSuiteProvider.addClassChecker(ToStringNotNullChecker.class);
		testSuiteProvider.addClassChecker(BijectiveEqualsChecker.class);
		testSuiteProvider.addClassChecker(CloneChecker.class);
		testSuiteProvider.addClassChecker(NPEConstructorChecker.class);
		testSuiteProvider.addClassChecker(NullValueEqualsChecker.class);

		testSuiteProvider.addMethodChecker(SetterChecker.class);
		testSuiteProvider.addMethodChecker(ArrayIndexOutOfBoundExceptionChecker.class);

		return testSuiteProvider.getTestSuite();
	}

}
