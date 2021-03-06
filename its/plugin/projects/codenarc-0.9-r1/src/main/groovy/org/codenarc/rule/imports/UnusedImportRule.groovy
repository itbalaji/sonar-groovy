/*
 * Copyright 2008 the original author or authors.
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
package org.codenarc.rule.imports

import org.codenarc.rule.AbstractRule
import org.codenarc.source.SourceCode

/**
 * Rule that checks for an unreferenced import
 *
 * @author Chris Mair
 * @version $Revision: 201 $ - $Date: 2009-08-13 05:59:21 +0400 (Чт, 13 авг 2009) $
 */
class UnusedImportRule extends AbstractRule {

    String name = 'UnusedImport'
    int priority = 3

    void applyTo(SourceCode sourceCode, List violations) {
        processImports(sourceCode, violations)
        processStaticImports(sourceCode, violations)
    }

    private void processImports(SourceCode sourceCode, List violations) {
        sourceCode.ast?.imports?.each {importNode ->
            if (!findReference(sourceCode, importNode.alias, importNode.className)) {
                violations.add(createViolationForImport(sourceCode, importNode))
            }
        }
    }

    private void processStaticImports(SourceCode sourceCode, List violations) {
        sourceCode.ast?.staticImportAliases?.each {alias, classNode ->
            if (!findReference(sourceCode, alias)) {
                violations.add(createViolationForImport(sourceCode, classNode.name, alias))
            }
        }
    }

    private findReference(SourceCode sourceCode, String alias, String className = null) {
        def aliasSameAsNonQualifiedClassName = className && className.endsWith(alias)
        return sourceCode.lines.find { line ->
            if (!isImportStatementForAlias(line, alias)) {
                def aliasCount = line.count(alias)
                return aliasSameAsNonQualifiedClassName ?
                    aliasCount && aliasCount > line.count(className) : aliasCount
            }
        }
    }

    private isImportStatementForAlias(String line, String alias) {
        final IMPORT_PATTERN = /import\s+.*/ + alias
        return line =~ IMPORT_PATTERN
    }
}