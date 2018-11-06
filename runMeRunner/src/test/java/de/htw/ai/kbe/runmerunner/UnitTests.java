package de.htw.ai.kbe.runmerunner;


import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Class.forName;
import static org.junit.Assert.assertEquals;

public class UnitTests {


    @Test
    public void parseOnlyClassName(){
        String[] args={"-c", "de.htw.ai.kbe.runmerunner.SampleClass"};
        EasyArguments options = new EasyArguments(args);
        String className = options.getClassName();
        String reportFile = options.getReportFile();
        assertEquals("de.htw.ai.kbe.runmerunner.SampleClass", className);
        assertEquals("report.txt", reportFile);
    }

    @Test
    public void parseClassNameAndReportFile(){
        String[] args={"-c", "de.htw.ai.kbe.runmerunner.SampleClass", "-o", "doenertier"};
        EasyArguments options = new EasyArguments(args);
        String className = options.getClassName();
        String reportFile = options.getReportFile();
        assertEquals("de.htw.ai.kbe.runmerunner.SampleClass", className);
        assertEquals("doenertier", reportFile);
    }

    @Test(expected = NoClassParameterDefinedException.class)
    public void parseStupidStuff(){
        String[] args ={"lustiger Satz"};
        EasyArguments options = new EasyArguments(args);
        String className= options.getClassName();
    }

    @Test(expected = NoClassParameterDefinedException.class)
    public void parseMinusC(){
        String[] args={"-c"};
        EasyArguments options = new EasyArguments(args);
        String className=options.getClassName();
    }

    @Test(expected = NoClassParameterDefinedException.class)
    public void parseMinusO(){
        String[] args={"-o"};
        EasyArguments options = new EasyArguments(args);
        String className=options.getClassName();
    }

    @Test
    public void parseClassNameIgnoreSecondParam(){
        String[] args={"-c", "de.htw.ai.kbe.runmerunner.SampleClass", "fjiosdfjiodshjf"};
        EasyArguments options = new EasyArguments(args);
        String className = options.getClassName();
        String reportFile = options.getReportFile();
        assertEquals("de.htw.ai.kbe.runmerunner.SampleClass", className);
        assertEquals("report.txt", reportFile);
    }

    @Test
    public void findAllMethodsWithRunMeAnnotation() throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        String[] args={"-c", "de.htw.ai.kbe.runmerunner.SampleClass"};
        List<String> methodsWithAnnotationList = new ArrayList<>();
        methodsWithAnnotationList.add("test");
        methodsWithAnnotationList.add("test3");
        methodsWithAnnotationList.add("test5");

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

        Constructor constr = clazz.getConstructor();
        Object o = constr.newInstance();
        Method[] methods = RunMeRunner.getMethods(clazz);

        List<String> actualList = new ArrayList<>();
        List<String> placeholder = new ArrayList<>();
        List<String> placeholder2 = new ArrayList<>();

        RunMeRunner.insertSickMethodNameHere(methods, o, actualList, placeholder, placeholder2);
        assertEquals(methodsWithAnnotationList, actualList);
    }

    @Test
    public void findAllMethodsWithoutRunMeAnnotation() throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, NoSuchMethodException {
        String[] args={"-c", "de.htw.ai.kbe.runmerunner.SampleClass"};
        List<String> methodsWithoutAnnotationList = new ArrayList<>();
        methodsWithoutAnnotationList.add("test2");
        methodsWithoutAnnotationList.add("test4");


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

        Constructor constr = clazz.getConstructor();
        Object o = constr.newInstance();
        Method[] methods = RunMeRunner.getMethods(clazz);

        List<String> actualList = new ArrayList<>();
        List<String> placeholder = new ArrayList<>();
        List<String> placeholder2 = new ArrayList<>();

        RunMeRunner.insertSickMethodNameHere(methods, o, placeholder, actualList, placeholder2);
        assertEquals(methodsWithoutAnnotationList, actualList);
    }

    @Test
    public void findAllNotInvokableMethodsWithRunMeAnnotation() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, ClassNotFoundException {
        String[] args={"-c", "de.htw.ai.kbe.runmerunner.SampleClass"};
        List<String> notInvokableMethotsWithAnnotationList = new ArrayList<>();
        notInvokableMethotsWithAnnotationList.add("test3: IllegalAccessException");
        notInvokableMethotsWithAnnotationList.add("test5: IllegalArgumentException");


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

        Constructor constr = clazz.getConstructor();
        Object o = constr.newInstance();
        Method[] methods = RunMeRunner.getMethods(clazz);

        List<String> actualList = new ArrayList<>();
        List<String> placeholder = new ArrayList<>();
        List<String> placeholder2 = new ArrayList<>();

        RunMeRunner.insertSickMethodNameHere(methods, o, placeholder, placeholder2, actualList);
        assertEquals(notInvokableMethotsWithAnnotationList, actualList);
    }

    @Test(expected = ClassNotFoundException.class)
    public void parseNonExistingClassName() throws ClassNotFoundException {
        String[] args={"-c", "dominikistbuff"};
        EasyArguments options = new EasyArguments(args);
        String className = options.getClassName();
        String reportFile = options.getReportFile();
        Class clazz;
        try {
            clazz = forName("dominikistbuff");
        } catch (ClassNotFoundException e) {
            throw e;
        }
    }
}
