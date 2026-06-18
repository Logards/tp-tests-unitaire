package com.example.tptestsunitaires.book

import com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAnyPackage
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses
import io.kotest.core.spec.style.FunSpec

class HexaTests : FunSpec({
    val basePackage = "com.example.tptestsunitaires"
    val importedClasses = ClassFileImporter()
        .withImportOption(ImportOption.DoNotIncludeTests())
        .importPackages(basePackage)

    test("domain should not depend on infrastructure") {
        val rule = noClasses()
            .that()
            .resideInAPackage("$basePackage.domain..")
            .should()
            .dependOnClassesThat()
            .resideInAPackage("$basePackage.infrastructure..")

        rule.check(importedClasses)
    }

    test("domain should only depend on itself and standard libraries") {
        val rule = classes()
            .that()
            .resideInAPackage("$basePackage.domain..")
            .should()
            .onlyDependOnClassesThat(
                resideInAnyPackage(
                    "$basePackage.domain..",
                    "java..",
                    "kotlin..",
                    "kotlinx..",
                    "org.jetbrains.annotations.."
                )
            )

        rule.check(importedClasses)
    }

    test("use cases should reside in domain") {
        val rule = classes()
            .that()
            .haveSimpleNameEndingWith("UseCase")
            .should()
            .resideInAPackage("$basePackage.domain..")

        rule.check(importedClasses)
    }

    test("controllers should reside in infrastructure") {
        val rule = classes()
            .that()
            .haveSimpleNameEndingWith("Controller")
            .should()
            .resideInAPackage("$basePackage.infrastructure..")

        rule.check(importedClasses)
    }
})