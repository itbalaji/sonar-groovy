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
package org.codenarc.ant

import org.codenarc.test.AbstractTestCase

/**
 * Tests for CodeNarcTask that use the Groovy AntBuilder.
 *
 * @author Chris Mair
 * @version $Revision: 284 $ - $Date: 2010-01-15 06:21:18 +0300 (Пт, 15 янв 2010) $
 */
class CodeNarc_AntBuilderTest extends AbstractTestCase {

    private static final XML = 'xml'
    private static final HTML = 'html'
    private static final TEXT = 'text'
    private static final HTML_REPORT_FILE = 'AntBuilderTestHtmlReport.html'
    private static final XML_REPORT_FILE = 'AntBuilderTestXmlReport.xml'
    private static final TEXT_REPORT_FILE = 'AntBuilderTestTextReport.txt'
    private static final TITLE = 'Sample Project'
    private static final RULESET_FILES = [
            'rulesets/basic.xml',
            'rulesets/imports.xml'].join(',')

    void testAntTask_Execute_UsingAntBuilder() {
        def ant = new AntBuilder()

        ant.taskdef(name:'codenarc', classname:'org.codenarc.ant.CodeNarcTask')

        ant.codenarc(ruleSetFiles:RULESET_FILES) {
            fileset(dir:'samples/src') {
                include(name:"**/*.groovy")
            }
//           fileset(dir:'src/main/groovy') {
//               include(name:"**/*.groovy")
//           }
//           fileset(dir:'src/test/groovy') {
//               include(name:"**/*.groovy")
//           }
           report(type:HTML) {
               option(name:'title', value:TITLE)
               option(name:'outputFile', value:HTML_REPORT_FILE)
           }
           report(type:XML) {
               option(name:'title', value:TITLE)
               option(name:'outputFile', value:XML_REPORT_FILE)
           }
           report(type:TEXT) {
               option(name:'title', value:TITLE)
               option(name:'outputFile', value:TEXT_REPORT_FILE)
           }
        }
        verifyHtmlReportFile()
        verifyXmlReportFile()
        verifyTextReportFile()
    }

    private void verifyHtmlReportFile() {
        def file = new File(HTML_REPORT_FILE)
        assert file.exists()
        assertContainsAllInOrder(file.text, [TITLE, 'org/codenarc', 'Rule Descriptions'])
    }

    private void verifyXmlReportFile() {
        def file = new File(XML_REPORT_FILE)
        assert file.exists()
        assertContainsAllInOrder(file.text, ['<?xml version', TITLE, 'org/codenarc', '<Rules>'])
    }

    private void verifyTextReportFile() {
        def file = new File(TEXT_REPORT_FILE)
        assert file.exists()
        assertContainsAllInOrder(file.text, ['CodeNarc Report', TITLE, 'www.codenarc.org'])
    }
}