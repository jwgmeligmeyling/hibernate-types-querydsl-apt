package com.pallasathenagroup.querydsl;

import com.pallasathenagroup.querydsl.array.PostgresqlArrayPath;
import com.querydsl.apt.TypeUtils;
import com.querydsl.codegen.AbstractModule;
import com.querydsl.codegen.CodegenModule;
import com.querydsl.codegen.Extension;
import com.querydsl.codegen.TypeMappings;
import com.querydsl.codegen.utils.model.SimpleType;
import com.querydsl.codegen.utils.model.Type;
import com.querydsl.codegen.utils.model.TypeCategory;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.util.Types;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArraySupport implements Extension {


    private static void registerTypes(AbstractModule module) {
        RoundEnvironment roundEnvironment = module.get(RoundEnvironment.class);
        ProcessingEnvironment processingEnvironment = module.get(ProcessingEnvironment.class);
        Types typeUtils = processingEnvironment.getTypeUtils();

        for (Element element : roundEnvironment.getElementsAnnotatedWith(TypeDefs.class)) {
            AnnotationMirror typeDefsMirror = TypeUtils.getAnnotationMirrorOfType(element, TypeDefs.class);
            for (AnnotationValue value : typeDefsMirror.getElementValues().values()) {
                for (AnnotationMirror typeDefMirror : ((List<AnnotationMirror>) value.getValue())) {
                    AnnotationValue defaultForTypeValue = getAnnotationValue(typeDefMirror, "defaultForType");
                    if (defaultForTypeValue != null && defaultForTypeValue.getValue() instanceof ArrayType) {
                        ArrayType arrayTypeMirror = (ArrayType) defaultForTypeValue.getValue();
                        if (arrayTypeMirror.getComponentType() instanceof DeclaredType) {
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
                                            enumArrayType,
                                            enumType)
                            );
                        } else if ( arrayTypeMirror.getComponentType() instanceof PrimitiveType ){
                            PrimitiveType componentPrimitiveType = (PrimitiveType) arrayTypeMirror.getComponentType();
                            String primitiveTypeName = componentPrimitiveType.getKind().name().toLowerCase();
                            SimpleType enumType = new SimpleType(TypeCategory.SIMPLE, primitiveTypeName, "", primitiveTypeName,
                                    true, true, Collections.emptyList());

                            TypeElement boxedTypeElement = typeUtils.boxedClass(componentPrimitiveType);
                            String qualifiedName = boxedTypeElement.getQualifiedName().toString();
                            String simpleName = boxedTypeElement.getSimpleName().toString();
                            String packageName = qualifiedName.substring(0, qualifiedName.length() - simpleName.length() - 1);
                            SimpleType boxedTypeElementSimpleType = new SimpleType(qualifiedName, packageName, simpleName);

                            Type enumArrayType = enumType.asArrayType();
                            Class arrayPathClass = PostgresqlArrayPath.class;
                            module.get(TypeMappings.class).register(
                                    enumArrayType,
                                    new SimpleType(
                                            arrayPathClass.getName(),
                                            arrayPathClass.getPackage().getName(),
                                            arrayPathClass.getSimpleName(),
                                            enumArrayType,
                                            boxedTypeElementSimpleType)
                            );
                        }
                    }
                }
            }
        }

        for (Element element : roundEnvironment.getElementsAnnotatedWith(TypeDef.class)) {
            AnnotationMirror typeDefMirror = TypeUtils.getAnnotationMirrorOfType(element, TypeDef.class);
            AnnotationValue defaultForTypeValue = getAnnotationValue(typeDefMirror, "defaultForType");
            if (defaultForTypeValue != null && defaultForTypeValue.getValue() instanceof ArrayType) {
                ArrayType arrayTypeMirror = (ArrayType) defaultForTypeValue.getValue();
                if ( arrayTypeMirror.getComponentType() instanceof DeclaredType) {
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
                } else if ( arrayTypeMirror.getComponentType() instanceof PrimitiveType ){
                    PrimitiveType componentPrimitiveType = (PrimitiveType) arrayTypeMirror.getComponentType();
                    String primitiveTypeName = componentPrimitiveType.getKind().name().toLowerCase();
                    SimpleType enumType = new SimpleType(TypeCategory.SIMPLE, primitiveTypeName, "", primitiveTypeName,
                            true, true, Collections.emptyList());

                    TypeElement boxedTypeElement = typeUtils.boxedClass(componentPrimitiveType);
                    String qualifiedName = boxedTypeElement.getQualifiedName().toString();
                    String simpleName = boxedTypeElement.getSimpleName().toString();
                    String packageName = qualifiedName.substring(0, qualifiedName.length() - simpleName.length() - 1);
                    SimpleType boxedTypeElementSimpleType = new SimpleType(qualifiedName, packageName, simpleName);

                    Type enumArrayType = enumType.asArrayType();
                    Class arrayPathClass = PostgresqlArrayPath.class;
                    module.get(TypeMappings.class).register(
                            enumArrayType,
                            new SimpleType(
                                    arrayPathClass.getName(),
                                    arrayPathClass.getPackage().getName(),
                                    arrayPathClass.getSimpleName(),
                                    enumArrayType,
                                    boxedTypeElementSimpleType)
                    );
                }
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
            imports = Collections.singleton(packageName);
        } else {
            Set<String> old = imports;
            imports = new HashSet<>();
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
