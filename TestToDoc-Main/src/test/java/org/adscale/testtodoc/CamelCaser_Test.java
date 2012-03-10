package org.adscale.testtodoc;

import org.junit.Assert;
import org.junit.Test;

public class CamelCaser_Test {
    public CamelCaser_Test() {
    }

    @Test
    public void camelCaseWords() {
        Assert.assertEquals("andrew oxenburgh", CamelCaser.camelCaseWord("andrewOxenburgh"));
        Assert.assertEquals("steven oxenburgh", CamelCaser.camelCaseWord("stevenOxenburgh"));
    }

    @Test
    public void camelCase_doesntPrependSpace() {
        Assert.assertEquals("andrew oxenburgh", CamelCaser.camelCaseWord("AndrewOxenburgh"));
    }

    @Test
    public void camelCase_keepsTestWordNotAtEnd() {
        Assert.assertEquals("this test should stay here", CamelCaser.camelCaseWord("ThisTestShouldStayHere"));
    }

    @Test
    public void camelCase_ignoresPrecedingTest() {
        Assert.assertEquals("thing", CamelCaser.camelCaseWord("testThing"));
    }

    @Test
    public void camelCase_underscoresFollowedByLittleLettersBecomeCommas() {
        Assert.assertEquals("under, score", CamelCaser.camelCaseWord("under_score"));
    }

    @Test
    public void camelCase_underscoresFollowedByBigLettersBecomeFullStops() {
        Assert.assertEquals("under. score", CamelCaser.camelCaseWord("under_Score"));
    }

    @Test
    public void camelCase_trainlingUnderscoreBecomesFullStop() {
        Assert.assertEquals("under.", CamelCaser.camelCaseWord("under_"));
    }
}