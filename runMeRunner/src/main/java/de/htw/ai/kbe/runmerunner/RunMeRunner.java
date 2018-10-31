package de.htw.ai.kbe.runmerunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RunMeRunner {

    public static void main(String[] args) throws Exception {
        List<String> methodsWithAnnotationList = new ArrayList<>();
        List<String> methodsWithoutAnnotationList = new ArrayList<>();
        List<String> notInvokableMethodsWithAnnotationList = new ArrayList<>();

        EasyArguments options = new EasyArguments(args);
        String className = options.getClassName();
        String reportFile = options.getReportFile();

        System.out.println(className);
        System.out.println(reportFile);

        //String className = "de.htw.ai.kbe.runmerunner.SampleClass";

        Class clazz = Class.forName(className);
        Constructor constr = clazz.getConstructor();
        Object o = constr.newInstance();

        Method[] methods = getMethods(clazz);

        insertSickMethodNameHere(methods, o, methodsWithAnnotationList, methodsWithoutAnnotationList, notInvokableMethodsWithAnnotationList);

        listPrintHelper(methodsWithAnnotationList);
        listPrintHelper(methodsWithoutAnnotationList);
        listPrintHelper(notInvokableMethodsWithAnnotationList);

        createReport(reportFile, methodsWithAnnotationList, methodsWithoutAnnotationList, notInvokableMethodsWithAnnotationList);

        //Dev Purpose
        //printMethodsWithAnnotation(clazz);
        //printMethodsWithoutAnnotation(clazz);
        //printNotInvocableMethodsWithAnnotation(clazz);
    }

    public static Method[] getMethods(Class clazz) {
        return clazz.getDeclaredMethods();
    }


    public static void insertSickMethodNameHere(Method[] methods, Object o, List<String> methodsWithAnnotationList, List<String> methodsWithoutAnnotationList, List<String> notInvokableMethotsWithAnnotationList) {

        for (int i = 0; i < methods.length; i++) {
            //When Annotation is present
            if (methods[i].isAnnotationPresent(RunMe.class)) {
                //add Method name to list
                methodsWithAnnotationList.add(methods[i].getName());

                try {
                    //try to invoke method
                    methods[i].invoke(o);
                } catch (IllegalAccessException e) {
                    notInvokableMethotsWithAnnotationList.add(methods[i].getName() + ": IllegalAccessException");
                } catch (InvocationTargetException e) {
                    notInvokableMethotsWithAnnotationList.add(methods[i].getName() + ": InvocationTargetException");
                } catch (IllegalArgumentException e) {
                    notInvokableMethotsWithAnnotationList.add(methods[i].getName() + ": IllegalArgumentException");
                }
            } else {
                methodsWithoutAnnotationList.add(methods[i].getName());
            }
        }
    }

    public static void createReport(String reportFile, List<String> methodsWithAnnotationList, List<String> methodsWithoutAnnotationList, List<String> notInvokableMethotsWithAnnotationList) {

    }

    public static void listPrintHelper(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }


    //Dev Purpose only!
//    public static void printMethodsWithAnnotation(Class clazz) {
//        Method[] methods = clazz.getDeclaredMethods();
//        System.out.println("Methodennamen mit @RunMe:");
//        for (int i = 0; i < methods.length; i++) {
//            if (methods[i].isAnnotationPresent(RunMe.class)) {
//                System.out.println(methods[i].getName());
//            }
//        }
//    }
//
//    public static void printMethodsWithoutAnnotation(Class clazz) {
//        Method[] methods = clazz.getDeclaredMethods();
//        System.out.println("Methodennamen ohne @RunMe:");
//        for (int i = 0; i < methods.length; i++) {
//            if (!methods[i].isAnnotationPresent(RunMe.class)) {
//                System.out.println(methods[i].getName());
//            }
//        }
//    }
//
//    public static void printNotInvocableMethodsWithAnnotation(Class clazz) {
//        Method[] methods = clazz.getDeclaredMethods();
//        System.out.println("„Nicht-invokierbare“ Methoden mit @RunMe:");
//        for (int i = 0; i < methods.length; i++) {
//            if (methods[i].isAnnotationPresent(RunMe.class) && methods[i].getModifiers() == 2) {
//                System.out.println(methods[i].getName() + ": Private Method");
//            }
//        }
//    }
}
