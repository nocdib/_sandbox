public class SameMemberName {

    public static void main(String[] args) {
        ClassA Aclass = new ClassA();
        ClassB Bclass = new ClassB();

        System.out.println(Aclass + "\n" + Bclass);  
    }
}

class ClassA {

    int a, b, c;

    public ClassA() {
        a = 1;
        b = 2;
        c = 3;
    }

    public String toString(){
        return "a = " + a + ", b = " + b + ", c = " + c;
    }
}

class ClassB {

    String a, c;
    int b;

    public ClassB() {
        super();
        a = "This";
        b = 13;
        c = "string";
    }

    public String toString(){                                                               
        return "a = " + a + ", b = " + b + ", c = " + c;
    }    
}
