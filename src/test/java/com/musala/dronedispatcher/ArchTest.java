package com.musala.dronedispatcher;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.musala.dronedispatcher");

        noClasses()
            .that()
                .resideInAnyPackage("com.musala.dronedispatcher.service..")
            .or()
                .resideInAnyPackage("com.musala.dronedispatcher.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.musala.dronedispatcher.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
