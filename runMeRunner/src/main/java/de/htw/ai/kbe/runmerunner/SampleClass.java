package de.htw.ai.kbe.runmerunner;

public class SampleClass {
    @RunMe
    public void test(){
        System.out.println("Some Output1");
    }

    public void test2(){
        System.out.println("Some Output2");
    }
    @RunMe
    private void test3(){
        System.out.println("Some Output3");
    }

    private void test4(){
        System.out.println("Some Output4");
    }

    @RunMe
    public void test5(int a){
        System.out.println("Some Output3" + a);
    }
}
