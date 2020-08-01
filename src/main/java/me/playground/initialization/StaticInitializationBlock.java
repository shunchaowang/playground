package me.playground.initialization;

public class StaticInitializationBlock {

    public static void main(String[] args) {
        System.out.println("In StaticInitializationBlock main");
        System.out.printf("Test static variable i = %d\n", Test.staticVar);
        Test test = Test.getInstance();
        System.out.printf("Lazy loaded Test j = %d\n", test.j);
    }
}

class Test {
    static int staticVar = 1;
    int j;
    static {
        staticVar = 10;
    }

    static class LazyHolder {
        static final Test instance = new Test();
    }

    private Test() {
        System.out.println("In Test Constructor");
    }

    static Test getInstance() {
        return LazyHolder.instance;
    }
}
