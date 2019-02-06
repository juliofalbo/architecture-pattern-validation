package com.juliofalbo.poc.archunit.architectureRules;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMember;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchUnitRunner;
import com.tngtech.archunit.lang.*;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import static com.tngtech.archunit.core.domain.Formatters.formatLocation;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.resideInAPackage;
import static com.tngtech.archunit.core.domain.JavaModifier.PUBLIC;
import static com.tngtech.archunit.core.domain.properties.HasModifiers.Predicates.modifier;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.all;

import com.tngtech.archunit.core.domain.properties.HasModifiers;
import com.tngtech.archunit.core.domain.properties.HasOwner.Functions.Get;

@RunWith(ArchUnitRunner.class)
@AnalyzeClasses(packages = "com.juliofalbo.poc.archunit")
public class ResourceRule {


    private final static String PATH_PACKAGE_RESOURCE = "com.juliofalbo.poc.archunit.resources";

    @ArchTest
    static final ArchRule resourcesShouldBeInSpecificPackage =
            classes().that()
                    .haveNameMatching(".*Resource")
                    .should()
                    .resideInAPackage(PATH_PACKAGE_RESOURCE)
                    .as("Resources should reside in a package 'com.juliofalbo.poc.archunit.resources'");

    @ArchTest
    public static final ArchRule onlyResourcesMayUseRestControllerAnnotation =
            noClasses()
                    .that()
                    .resideOutsideOfPackage(PATH_PACKAGE_RESOURCE)
                    .should()
                    .accessClassesThat()
                    .areAssignableTo(RestController.class)
                    .as("Only Resources may use the " + RestController.class.getSimpleName());

    @ArchTest
    public static ArchRule allPublicMethodsInTheResourceLayerShouldReturnResponseEntityObject =
            all(methods())
                    .that(areDefinedInAPackage(PATH_PACKAGE_RESOURCE))
                    .and(arePublic())
                    .should(returnType(ResponseEntity.class))
                    .because("User ResponseEntity to return any thing in endpoints.");

    private static ClassesTransformer<JavaMethod> methods() {
        return new AbstractClassesTransformer<JavaMethod>("methods") {
            @Override
            public Iterable<JavaMethod> doTransform(JavaClasses javaClasses) {
                List<JavaMethod> methods = new ArrayList<>();
                for (JavaClass javaClass : javaClasses) {
                    methods.addAll(javaClass.getMethods());
                }
                return methods;
            }
        };
    }

    private static DescribedPredicate<? super JavaMember> areDefinedInAPackage(final String packageIdentifier) {
        return Get.<JavaClass>owner().is(resideInAPackage(packageIdentifier));
    }

    private static DescribedPredicate<HasModifiers> arePublic() {
        return modifier(PUBLIC).as("are public");
    }

    private static ArchCondition<JavaMethod> returnType(final Class<?> type) {
        return new ArchCondition<JavaMethod>("return type " + type.getName()) {
            @Override
            public void check(JavaMethod method, ConditionEvents events) {
                boolean typeMatches = method.getReturnType().isAssignableTo(type);
                String message = String.format("%s returns %s in %s",
                        method.getFullName(), method.getReturnType().getName(),
                        formatLocation(method.getOwner(), 0));
                events.add(new SimpleConditionEvent(method, typeMatches, message));
            }
        };
    }
}
