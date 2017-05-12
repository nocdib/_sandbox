package com.nocdib.chapter02;

/**
 * Created by 207515 on 5/9/17.
 */
class NumberFormatting {
    static void example(){
        int a=5, b=3;
        float c=5.0f, d=2.0f;
        double e=5.0, f=0.3;

        System.out.printf("%d / %d = %d\n", a, b, a/b);
        System.out.printf("%d %% %d = %d\n", a, b, a%b);
        System.out.println();
        System.out.printf("%.2f / %.2f = %.2f\n", c, d, c/d);
        System.out.printf("%.2f %% %.2f = %.2f\n", c, d, c%d);
        System.out.println();
        System.out.printf("%f / %f = %f\n", c, d, c/d);
        System.out.printf("%f %% %f = %f\n", c, d, c%d);
    }
}
