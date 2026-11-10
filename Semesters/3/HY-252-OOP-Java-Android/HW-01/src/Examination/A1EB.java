package Examination1;

import java.util.Scanner;

public class A1EB
{
    public static void main (String[] args)
    {
        System.out.println();

        // #1:
        int[] A = new int[5];
        for (int i = 0; i < 5; i++)
        {
            System.out.print("Give me an integer: ");
            Scanner in = new Scanner(System.in);
            int aNumber = in.nextInt();
            A[i] = aNumber;
        }

        // #2:
        int MINVALUE = -1, MAXVALUE = 0;
        for (int i = 0; i < 5; i++)
        {
            if (MINVALUE == -1) MINVALUE = A[i];
            if (A[i] > MAXVALUE) MAXVALUE = A[i];
            if (A[i] < MINVALUE) MINVALUE = A[i];
        }
        System.out.println("MIN = " + MINVALUE);
        System.out.println("MAX = " + MAXVALUE);

        // #3:
        double MMM = (((double)MINVALUE + (double)MAXVALUE) / 2);
        System.out.println("MMM = " + MMM);

        // #4:
        for (int i = 0; i < MINVALUE; i++)
            System.out.print(MINVALUE);
        System.out.println();
        for (int i = 0; i < MAXVALUE; i++)
            System.out.print(MAXVALUE);
        System.out.println();

        // #5:
        double[] B = new double[5];
        for (int i = 0; i < 5; i++)
            B[i] = (A[i] - MAXVALUE);
        for (int i = 0; i < 5; i++)
            System.out.println("B[" + i + "]=" + (int)B[i]);
        double sum = 0;
        for (int i = 0; i < 5; i++)
            sum += B[i];
        System.out.println("Το άθροισμα των στοιχείων του Β είναι: " + (int)sum);
    }
}

// The End :)