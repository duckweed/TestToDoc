package org.adscale.testtodoc.testjar;

import org.junit.Test;

import static org.junit.Assert.*;

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
