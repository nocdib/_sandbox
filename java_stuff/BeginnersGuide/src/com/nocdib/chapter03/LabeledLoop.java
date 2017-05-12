package com.nocdib.chapter03;

/**
 * Created by 207515 on 5/11/17.
 */
public class LabeledLoop {

    static void example(){
        for(int x=0;x<4;x++){
            one:{
                two:{
                    three:{
                        System.out.printf("x = %d\n", x);
                        if(x==1) break one;
                        if(x==2) break two;
                        if(x==3) break three;
                    }
                    System.out.println("Broke at THREE");
                }
                System.out.println("Broke at TWO");
            }
            System.out.println("Broke at ONE");
        }//for
        System.out.println("For loop exit");
    }
}
