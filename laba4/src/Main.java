// 21ВП2 Малькова, Лакеева
// Вариант 8. Лаба 4
// Напишите функцию проверки вхождения списка L1 в список L2. Результат сохранить в файл. Создать итератор для коллекции.

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.FileWriter;
import java.io.IOException;

public class Main{
    public static boolean includesAll(ArrayList<String> l1, ArrayList<String> l2) {
        boolean f = false;
        Iterator<String> p1 = l1.iterator();
        Iterator<String> p2 = l2.iterator();
        while (p1.hasNext()) {
            Object s1 = p1.next();
            while (p2.hasNext()) {
                if(p2.next().equals(s1)) { // если объект второго равен объекту первого
                    f = true;
                    break;
                }
                else {
                    f = false;
                }
            }
        }
        return f;
    }

    public static void main(String[] args) {

        System.out.println("Введите 1-й список через запятую и пробел: ");
        Scanner in1 = new Scanner(System.in);
        String l1 = in1.nextLine();
        boolean check = Pattern.matches("^[^, ]+(, [^, \\n]+)*$", l1);
        while (!check){
            System.out.println("Введите элементы списка через одну запятую и один пробел");
            l1 = in1.nextLine();
            check = Pattern.matches("^[^, ]+(, [^, \\n]+)*$", l1);
        }
        Pattern p = Pattern.compile(", ");
        String[] list1 = p.split(l1);
        ArrayList<String> L1 = new ArrayList<>();
        Collections.addAll(L1, list1);

        System.out.println("Введите 2-й список через запятую и пробел: ");
        Scanner in2 = new Scanner(System.in);
        String l2 = in2.nextLine();
        check = Pattern.matches("^[^, ]+(, [^, \\n]+)*$", l2);
        while (!check){
            System.out.println("Введите элементы списка через одну запятую и один пробел");
            l2 = in2.nextLine();
            check = Pattern.matches("^[^, ]+(, [^, \\n]+)*$", l2);
        }
        String[] list2 = p.split(l2);
        ArrayList<String> L2 = new ArrayList<>();
        Collections.addAll(L2, list2);

        // второй список
//        ArrayList<String> L2 = new ArrayList<>();
//        L2.add("one");
//        L2.add("two");
//        L2.add("three");
//        L2.add("four");
//        L2.add("five");

        // первый список
//        ArrayList<String> L1 = new ArrayList<>();
//        L1.add("two");
//        L1.add("three");
//        L1.add("four");

        try {
            FileWriter writer = new FileWriter("results.txt", true);
            //String joined1 = String.join(", ", L1);
            //String joined2 = String.join(", ", L2);
            String text;
            if (includesAll(L1, L2)){
                text = l1 + "  ->  " + l2 + "  ->  L1 входит в L2";
            } else {
                text = l1 + "  ->  " + l2 + "  ->  L1 не входит в L2";
            }
            writer.write(text);
            writer.append('\n');
            writer.close();
        }
        catch(IOException e){
            System.out.println("Error: " + e.getMessage());
        }
    }
}
