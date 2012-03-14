package org.adscale.testtodoc;

import org.junit.Assert;
import org.junit.Test;

/**
 * Copyright 2012 Andrew Oxenburgh
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class CamelCaser_Test {
    public CamelCaser_Test() {
    }

    @Test
    public void camelCaseWords() {
        Assert.assertEquals("andrew oxenburgh", CamelCaser.camelCaseWord("andrewOxenburgh"));
        Assert.assertEquals("steven oxenburgh", CamelCaser.camelCaseWord("stevenOxenburgh"));
    }

    @Test
    public void wontPrependSpace() {
        Assert.assertEquals("andrew oxenburgh", CamelCaser.camelCaseWord("AndrewOxenburgh"));
    }

    @Test
    public void keepsTestWordNotAtEnd() {
        Assert.assertEquals("this test should stay here", CamelCaser.camelCaseWord("ThisTestShouldStayHere"));
    }

    @Test
    public void ignoresLeading_test_word() {
        Assert.assertEquals("thing", CamelCaser.camelCaseWord("testThing"));
    }

    @Test
    public void underscoresFollowedByLittleLettersBecomeCommas() {
        Assert.assertEquals("under, score", CamelCaser.camelCaseWord("under_score"));
    }

    @Test
    public void underscoresFollowedByBigLettersBecomeFullStops_DontThey() {
        Assert.assertEquals("u. s", CamelCaser.camelCaseWord("u_S"));
    }

    @Test
    public void trailingUnderscoreBecomesFullStop() {
        Assert.assertEquals("under.", CamelCaser.camelCaseWord("under_"));
    }
}