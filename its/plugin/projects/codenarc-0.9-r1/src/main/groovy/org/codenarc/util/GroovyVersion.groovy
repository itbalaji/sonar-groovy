/*
 * Copyright 2010 the original author or authors.
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
package org.codenarc.util

/**
 * Utility methods to determine the version of Groovy currently executing.
 *
 * @author Chris Mair
 * @version $Revision: 324 $ - $Date: 2010-04-19 00:01:21 +0400 (Пн, 19 апр 2010) $
 */
class GroovyVersion {

    static String getVersion() {
        try {
            return GroovySystem.getVersion()
        }
        catch(MissingMethodException e) {
            return new org.codehaus.groovy.runtime.InvokerHelper().version
        }
    }

    static boolean isGroovy1_7() {
        return getVersion().startsWith("1.7")
    }

    static boolean isGroovy1_6() {
        return getVersion().startsWith("1.6")
    }

    static boolean isGroovy1_5() {
        return getVersion().startsWith("1.5")
    }

    private GroovyVersion() { }
}
