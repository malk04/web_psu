// 21ВП2 Малькова Анастасия, Лакеева Софья
// Ввести с консоли t целых чисел и поместить их в массив.
// На консоль вывести дробную часть десятичной дроби р = m/n
// для первых двух целых положительных чисел n и m, расположенных подряд.

import java.util.Scanner;
public class MyClass {
    public static void main(String[] args) {
        System.out.println("21ВП2 Малькова, Лакеева\n" + "Ввести с консоли t целых чисел и поместить их в массив.\n" +
                "На консоль вывести дробную часть десятичной дроби р = m/n\n" +
                "для первых двух целых положительных чисел n и m, расположенных подряд.");
        final int t = 7;
        int array[] = new int[t];
        System.out.printf("Введите %d целых чисел:\n", t);
        Scanner in = new Scanner(System.in);
        for (int i=0; i<t; i++) {
            while (!in.hasNextInt()){
                System.out.println("Разрешён ввод только целых чисел");
                in.next();
            }
            array[i] = in.nextInt();
        }
        double p = -1;
        for (int i=0; i<t-1; i++){
            if (array[i] > 0)
                if (array[i+1]>0)
                {
                    p = array[i+1]/(double)array[i];
                    double frac = p-(int)p;
                    System.out.println("Дробная часть " + frac);
                    break;
                }
        }
        if (p==-1)
            System.out.println("Двух целых положительных чисел, расположенных подряд, не найдено");
        in.close();
    }
}