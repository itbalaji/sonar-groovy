/*
 * Copyright 2009 the original author or authors.
 * 
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
 */
package org.codenarc.rule.logging

import org.codenarc.rule.AbstractRuleTestCase
import org.codenarc.rule.Rule

/**
 * Tests for SystemOutPrintRule
 *
 * @author Chris Mair
 * @version $Revision: 292 $ - $Date: 2010-01-17 06:09:23 +0300 (Вс, 17 янв 2010) $
 */
class SystemOutPrintRuleTest extends AbstractRuleTestCase {

    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == 'SystemOutPrint'
    }

    void testApplyTo_SystemOutPrintln_NoArgs() {
        final SOURCE = '''
            System.out.println()
        '''
        assertSingleViolation(SOURCE, 2, 'System.out.println()')
    }

    void testApplyTo_SystemOutPrintln_String() {
        final SOURCE = '''
            System.out.println("yes")
        '''
        assertSingleViolation(SOURCE, 2, 'System.out.println("yes")')
    }

    void testApplyTo_SystemOutPrintln_Int() {
        final SOURCE = '''
            System.out.println(1234)
        '''
        assertSingleViolation(SOURCE, 2, 'System.out.println(1234)')
    }

    void testApplyTo_SystemOutPrint_Int() {
        final SOURCE = '''
            System.out.print(1234)
        '''
        assertSingleViolation(SOURCE, 2, 'System.out.print(1234)')
    }

    void testApplyTo_SystemOutPrintf() {
        final SOURCE = '''
            System.out.printf("%d", 1234)
            System.out.printf("%d %d", 1234, -99)
        '''
        assertTwoViolations(SOURCE, 2, 'System.out.printf("%d", 1234)', 3, 'System.out.printf("%d %d", 1234, -99)')
    }

    void testApplyTo_SystemOutPrint_WithinClosure() {
        final SOURCE = '''
            class MyClass {
                def myClosure = {
                    System.out.print(1234)
                }
            }
        '''
        assertSingleViolation(SOURCE, 4, 'System.out.print(1234)')
    }

    void testApplyTo_PrintlnButNotSystemOut() {
        final SOURCE = '''
            void testSomething() {
                println "123"
            }
        '''
        assertNoViolations(SOURCE)
    }

    protected Rule createRule() {
        return new SystemOutPrintRule()
    }
}