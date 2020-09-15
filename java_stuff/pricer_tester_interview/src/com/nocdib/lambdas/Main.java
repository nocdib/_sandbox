package com.nocdib.lambdas;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("This is lambda country");
        LambdaInterface li = (text) -> { return text; };

        System.out.println(li.echo("Greg"));
        System.out.println(li.echo("Gary"));
        System.out.println(li.echo("Gizelle"));
    }

}
