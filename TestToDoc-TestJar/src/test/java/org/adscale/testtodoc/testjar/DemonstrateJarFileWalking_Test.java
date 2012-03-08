package org.adscale.testtodoc.testjar;

import org.junit.*;

import static org.junit.Assert.assertTrue;

public class DemonstrateJarFileWalking_Test {
    @Test
    public void ATestWeWantToView() {
        assertTrue(true);
    }

    public void unannotated() {
    }

    @NonJUnitAnnotation
    public void differentAnnotation() {
    }
}
