//21ВП2 Малькова, Лакеева
//8 вариант
//Составить регулярное выражение, определяющее является ли заданная строка IP адресом, записанным в десятичном виде.
//  пример правильных выражений: 127.0.0.1, 255.255.255.0.
//  пример неправильных выражений: 1300.6.7.8, abc.def.gha.bcd.

import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class laba2 {
        public static void main(String[] args){
            System.out.println("Введите IP-адрес");
            Scanner in = new Scanner(System.in);
            String IP = in.nextLine();
            boolean check = Pattern.matches("^[^, ]+(, [^, \\n]+)*$", IP);
            while (!check){
                System.out.println("Введите IP-адреса через одну запятую и один пробел");
                IP = in.nextLine();
                check = Pattern.matches("^[^, ]+(, [^, \\n]+)*$", IP);
            }
            Pattern p1 = Pattern.compile(", ");
            String[] words = p1.split(IP);
            for (int i = 0; i < words.length; i++){
                Pattern p = Pattern.compile( "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
                Matcher m = p.matcher(words[i]);
                boolean f = m.matches();
                if(!f)
                    System.out.println(words[i] + " Не является IP-адресом. Примеры корректного IP-адреса: " +
                            "127.0.0.1, 255.255.255.0");
                else
                    System.out.println(words[i] + " Это IP-адрес");
            }
        }
}

