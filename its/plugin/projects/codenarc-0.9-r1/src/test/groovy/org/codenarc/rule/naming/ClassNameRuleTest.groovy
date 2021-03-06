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
package org.codenarc.rule.naming

import org.codenarc.rule.AbstractRuleTestCase
import org.codenarc.rule.Rule

/**
 * Tests for ClassNameRule
 *
 * @author Chris Mair
 * @version $Revision: 292 $ - $Date: 2010-01-17 06:09:23 +0300 (Вс, 17 янв 2010) $
 */
class ClassNameRuleTest extends AbstractRuleTestCase {

    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == 'ClassName'
    }

    void testRegexIsNull() {
        rule.regex = null
        shouldFailWithMessageContaining('regex') { applyRuleTo('println 1') }
    }

    void testApplyTo_DoesNotMatchDefaultRegex() {
        final SOURCE = " class _MyClass { } "
        assertSingleViolation(SOURCE, 1, '_MyClass')
    }

    void testApplyTo_MatchesDefaultRegex() {
        final SOURCE = " class MyClass { } "
        assertNoViolations(SOURCE)
    }

    void testApplyTo_WithPackage_MatchesDefaultRegex() {
        final SOURCE = '''
            package org.codenarc.sample
            class MyClass { }
        '''
        assertNoViolations(SOURCE)
    }

    void testApplyTo_DoesNotMatchCustomRegex() {
        rule.regex = /z.*/
        final SOURCE = " class MyClass { } "
        assertSingleViolation(SOURCE, 1, 'MyClass')
    }

    void testApplyTo_MatchesCustomRegex() {
        rule.regex = /z.*/
        final SOURCE = " class zClass { } "
        assertNoViolations(SOURCE)
    }

    void testApplyTo_NoClassDefinition_DoesNotMatchRegex() {
        final SOURCE = '''
            if (isReady) {
                println 'ready'
            }
        '''
        rule.regex = /z.*/
        assertNoViolations(SOURCE)
    }

    void testApplyTo_ClosureDefinition() {
        final SOURCE = '''
            class MyClass {
                def c = { println 'ok' }
            }
        '''
        assertNoViolations(SOURCE)
    }

    protected Rule createRule() {
        return new ClassNameRule()
    }

}