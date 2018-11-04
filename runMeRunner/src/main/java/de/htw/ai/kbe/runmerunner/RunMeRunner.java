package de.htw.ai.kbe.runmerunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RunMeRunner {

    public static void main(String[] args) throws Exception {
        //Declare 3 Lists for each possible output
        List<String> methodsWithAnnotationList = new ArrayList<>();
        List<String> methodsWithoutAnnotationList = new ArrayList<>();
        List<String> notInvokableMethodsWithAnnotationList = new ArrayList<>();

        //Pass arguments to EasyArguments class to extract them
        EasyArguments options = new EasyArguments(args);
        String className = options.getClassName();
        String reportFile = options.getReportFile();
        Class clazz;
        try{
            //Get Class
            clazz = Class.forName(className);
        }catch(ClassNotFoundException e){
            System.out.println("Class not found!");
            LogWriter.writeLog(reportFile, "Class not found!");
            throw e;
        }

        //Get Constructor of clazz
        Constructor constr = clazz.getConstructor();
        //Instantiate clazz
        Object o = constr.newInstance();

        //Get all Methods of clazz
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

    //Gets Methods of given class and returns them as an array
    public static Method[] getMethods(Class clazz) {
        return clazz.getDeclaredMethods();
    }


    //Check Methods for Annotations and assign them to the right list
    //Invoke Methods with @RunMe Annotation, if that fails, add them to the proper list with reason
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

    //Prepares a String that is written into the reportFile
    public static void createReport(String reportFile, List<String> methodsWithAnnotationList, List<String> methodsWithoutAnnotationList, List<String> notInvokableMethotsWithAnnotationList) {

    }

    //Prints a list of type String
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
