package info.itloser.annotationcomp;

import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;

import info.itloser.annotationlib.AutoParcel;

/**
 * author：zhaoliangwang on 2019/9/9 16:13
 * email：tc7326@126.com
 */

@SupportedAnnotationTypes("info.itloser.annotationlib.AutoParcel")
public class AutoParcelProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Collection<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(AutoParcel.class);

        List<TypeElement> types = new ImmutableList.Builder<TypeElement>().addAll(ElementFilter.typesIn(annotatedElements)).build();


        return true;
    }

//    private void processType(TypeElement type) {
//        String className = generatedSubclassName(type);
//        String source = generateClass(type, className);
//        writeSourceFile(className, source, type);
//    }

    private void writeSourceFile(String className, String text, TypeElement originatingType) {

        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(className, originatingType);
            Writer writer = sourceFile.openWriter();

            try {
                writer.write(text);
            } catch (IOException e) {
                writer.close();
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
