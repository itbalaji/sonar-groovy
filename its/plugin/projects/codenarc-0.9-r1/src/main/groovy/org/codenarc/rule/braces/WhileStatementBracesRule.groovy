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
package org.codenarc.rule.braces

import org.codehaus.groovy.ast.stmt.WhileStatement
import org.codenarc.rule.AbstractAstVisitor
import org.codenarc.rule.AbstractAstVisitorRule
import org.codenarc.util.AstUtil

/**
 * Rule that checks that while statements use braces rather than a single statement.
 *
 * @author Chris Mair
 * @version $Revision: 139 $ - $Date: 2009-05-08 05:55:09 +0400 (Пт, 08 май 2009) $
 */
class WhileStatementBracesRule extends AbstractAstVisitorRule {
    String name = 'WhileStatementBraces'
    int priority = 2
    Class astVisitorClass = WhileStatementBracesAstVisitor
}

class WhileStatementBracesAstVisitor extends AbstractAstVisitor  {
    void visitWhileLoop(WhileStatement whileStatement) {
        if (isFirstVisit(whileStatement) && !AstUtil.isBlock(whileStatement.loopBlock)) {
            addViolation(whileStatement)
        }
        super.visitWhileLoop(whileStatement)
    }

}