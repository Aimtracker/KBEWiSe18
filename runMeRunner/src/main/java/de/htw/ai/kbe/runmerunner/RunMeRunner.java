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

        /**
         * Aufruf der Methode
         * @param methods Methoden der Klasse
         * @param o Objekt das von der Klasse instanziiert wurde
         * @param methodsWithAnnotationList
         * @param methodsWithoutAnnotationList
         * @param notInvokableMethodsWithAnnotationList
         */
        insertSickMethodNameHere(methods, o, methodsWithAnnotationList, methodsWithoutAnnotationList, notInvokableMethodsWithAnnotationList);

        listPrintHelper(methodsWithAnnotationList);
        listPrintHelper(methodsWithoutAnnotationList);
        listPrintHelper(notInvokableMethodsWithAnnotationList);

        createReport(reportFile, methodsWithAnnotationList, methodsWithoutAnnotationList, notInvokableMethodsWithAnnotationList);
    }

    //Gets Methods of given class and returns them as an array
    public static Method[] getMethods(Class clazz) {
        return clazz.getDeclaredMethods();
    }

// Definition der Methode
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
                    notInvokableMethotsWithAnnotationList.add(methods[i].getName() + ": IllegalAccessException"); //private, protected
                } catch (InvocationTargetException e) {
                    notInvokableMethotsWithAnnotationList.add(methods[i].getName() + ": InvocationTargetException"); //falsches objekt übergeben
                } catch (IllegalArgumentException e) {
                    notInvokableMethotsWithAnnotationList.add(methods[i].getName() + ": IllegalArgumentException"); //methode wurde falsche parameter übergeben
                }
            } else {
                methodsWithoutAnnotationList.add(methods[i].getName());
            }
        }
    }

    //Prepares a String that is written into the reportFile
    public static void createReport(String reportFile, List<String> methodsWithAnnotationList, List<String> methodsWithoutAnnotationList, List<String> notInvokableMethotsWithAnnotationList) {
        StringBuilder sb = new StringBuilder();
        //sb.append("Methodennamen ohne @RunMe:\n");
        System.out.println("Methodennamen ohne @RunMe:\n");
        for(String mwa:methodsWithoutAnnotationList){
            System.out.println(mwa);
        }
        System.out.println("Methodennamen mit @RunMe:\n");
        for(String mwa:methodsWithAnnotationList){
            System.out.println(mwa);
        }
        System.out.println("Nicht-invokierbare Methoden mit @RunMe:\n");
        for(String mwa:notInvokableMethotsWithAnnotationList){
            System.out.println(mwa);
        }
    }

    //Prints a list of type String
    public static void listPrintHelper(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
