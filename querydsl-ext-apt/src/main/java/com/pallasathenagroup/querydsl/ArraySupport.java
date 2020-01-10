package com.pallasathenagroup.querydsl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.mysema.codegen.model.SimpleType;
import com.mysema.codegen.model.Type;
import com.querydsl.apt.Extension;
import com.querydsl.apt.TypeUtils;
import com.querydsl.codegen.AbstractModule;
import com.querydsl.codegen.CodegenModule;
import com.querydsl.codegen.TypeMappings;
import org.hibernate.annotations.TypeDef;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import java.util.Map;
import java.util.Set;

public class ArraySupport implements Extension {


    private static void registerTypes(AbstractModule module) {
        RoundEnvironment roundEnvironment = module.get(RoundEnvironment.class);

        for (Element element : roundEnvironment.getElementsAnnotatedWith(TypeDef.class)) {
            AnnotationMirror typeDefMirror = TypeUtils.getAnnotationMirrorOfType(element, TypeDef.class);
            AnnotationValue defaultForTypeValue = getAnnotationValue(typeDefMirror, "defaultForType");
            if (defaultForTypeValue != null && defaultForTypeValue.getValue() instanceof ArrayType) {
                ArrayType arrayTypeMirror = (ArrayType) defaultForTypeValue.getValue();
                DeclaredType componentDeclaredType = (DeclaredType) arrayTypeMirror.getComponentType();
                TypeElement componentTypeElement = (TypeElement) componentDeclaredType.asElement();
                String qualifiedName = componentTypeElement.getQualifiedName().toString();
                String simpleName = componentTypeElement.getSimpleName().toString();
                String packageName = qualifiedName.substring(0, qualifiedName.length() - simpleName.length() - 1);

                SimpleType enumType = new SimpleType(qualifiedName, packageName, simpleName);
                Type enumArrayType = enumType.asArrayType();
                Class arrayPathClass = PostgresqlArrayPath.class;
                module.get(TypeMappings.class).register(
                        enumArrayType,
                        new SimpleType(
                                arrayPathClass.getName(),
                                arrayPathClass.getPackage().getName(),
                                arrayPathClass.getSimpleName(),
                                enumArrayType, enumType)
                );
            }
        }

    }


    private static AnnotationValue getAnnotationValue(AnnotationMirror annotationMirror, String key) {
        for(Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet() ) {
            if(entry.getKey().getSimpleName().toString().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    private static void addImports(AbstractModule module, String packageName) {
        @SuppressWarnings("unchecked")
        Set<String> imports = module.get(Set.class, CodegenModule.IMPORTS);
        if (imports.isEmpty()) {
            imports = ImmutableSet.of(packageName);
        } else {
            Set<String> old = imports;
            imports = Sets.newHashSet();
            imports.addAll(old);
            imports.add(packageName);
        }
        module.bind(CodegenModule.IMPORTS, imports);
    }

    public void addSupport(AbstractModule module) {
        registerTypes(module);
        addImports(module,"com.pallasathenagroup.querydsl");
    }


}
