import java.util.ArrayList;
import java.util.Iterator;

public class Main{
    public static boolean includesAll(ArrayList l1, ArrayList l2) {
        boolean f = false;
        Iterator p1 = l1.iterator();
        Iterator p2 = l2.iterator();
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
        // первый список
        ArrayList<String> L2 = new ArrayList<>();
        L2.add("one");
        L2.add("two");
        L2.add("three");
        L2.add("four");
        L2.add("five");

        //второй список
        ArrayList<String> L1 = new ArrayList<>();
        L1.add("two");
        L1.add("three");
        L1.add("four");

        // результат
        System.out.println("Результат: " + includesAll(L1, L2));
        System.out.println("Метод containsAll: " + L2.containsAll(L1));

        // добавим в первый список элемент, которого нет во втором
        L1.add("ten");

        // результат
        System.out.println("Результат: " + includesAll(L1, L2));
        System.out.println("Метод containsAll: " + L2.containsAll(L1));
    }
}
