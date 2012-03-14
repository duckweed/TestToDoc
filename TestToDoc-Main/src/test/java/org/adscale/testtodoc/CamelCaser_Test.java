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
    public void underscoresFollowedByBigLettersBecomeFullStops() {
        Assert.assertEquals("under. score", CamelCaser.camelCaseWord("under_Score"));
    }

    @Test
    public void trailingUnderscoreBecomesFullStop() {
        Assert.assertEquals("under.", CamelCaser.camelCaseWord("under_"));
    }
}