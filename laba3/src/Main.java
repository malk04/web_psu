import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String args[]){
        Planet Earth = new Planet("Земля", 150, 12.8, 6, 1,365);
        Planet Mercury = new Planet("Меркурий", 58, 4.9, 0.32, 0, 58);
        Planet Venus = new Planet("Венера", 108, 12.1, 4.86, 0, 255);
        Planet Mars = new Planet("Марс", 288, 6.8, 0.61, 2,687);
        Planet Jupiter = new Planet("Юпитер", 778, 142.6, 1906.98, 16,4380);
        Planet Saturn = new Planet("Сатурн", 1426, 120.2, 570.9, 17,10950);
        Planet Uranus = new Planet("Уран", 2869, 49, 87.24, 14,30660);
        Planet Neptune = new Planet("Нептун", 4496, 50.2, 103.38, 2,60225);
        Planet Pluto = new Planet("Плутон", 5900, 2.8, 0.1, 1,90885);

        Star Sun = new Star("Солнце", 0 , 1392, "5 век до нашей эры", 200000, 3.827);
        Star Sirius = new Star("Сириус", 63000000, 2366, "1844", 400000, 97.2);
        Star Aldebaran = new Star("Альдебаран", 441000000, 36192, "17 век", 3000000, 1680.1);

        Earth.arr_push();
        Mercury.arr_push();
        Venus.arr_push();
        Mars.arr_push();
        Jupiter.arr_push();
        Saturn.arr_push();
        Uranus.arr_push();
        Neptune.arr_push();
        Pluto.arr_push();

        Sun.arr_push();
        Sirius.arr_push();
        Aldebaran.arr_push();

        System.out.println(Cosmic_body.theFurthest());
        System.out.println(Cosmic_body.closer_than_Earth());
        ArrayList<Cosmic_body> s_arr = Cosmic_body.sort_array();
        for (Cosmic_body o : s_arr)
            System.out.println(o.toString());

        System.out.print("Введите имя космического тела: ");
        Scanner in1 = new Scanner(System.in);
        String name = in1.nextLine();
        if (Cosmic_body.find(name)==null){
            System.out.println("Объект с таким именем не найден");
        }
        else{
            System.out.println(Cosmic_body.find(name));
            System.out.print("Введите новое значение диаметра: ");
            Scanner in2 = new Scanner(System.in);
            while (!in2.hasNextDouble()){
                System.out.print("Разрешён ввод только дробных чисел! Повторите ввод: ");
                in2.next();
            }
            double d = in2.nextDouble();
            System.out.println(Cosmic_body.edit(Cosmic_body.find(name), d));
        }
// for (Cosmic_body o : s_arr)
// System.out.println(o.toString()); // чтобы проверить, что диаметр реально изменился
    }
}

abstract class Cosmic_body {
    private static ArrayList<Cosmic_body> arr = new ArrayList<>();
    private static final int from_earth_to_sun = 150;
    private String name;
    private int distance_to_sun;
    private double diameter;
    private double weight;
    public Cosmic_body(String name, int distance_to_sun, double diameter, double weight) {
        this.name = name;
        this.distance_to_sun = distance_to_sun;
        this.diameter = diameter;
        this.weight = weight;
    }
    public String getName(){
        return name;
    }
    public int getDistance_to_sun(){
        return distance_to_sun;
    }
    public double getDiameter(){
        return diameter;
    }

    public void setDiameter(double d){
        this.diameter = d;
    }

    public double getWeight(){
        return weight;
    }

    public void arr_push(){
        arr.add(this);
    }

    public static String theFurthest(){
        Cosmic_body o = arr.get(0);
        for (int i=0; i<arr.size() ;i++){
            if ( (arr.get(i) instanceof Planet) && (arr.get(i).getDistance_to_sun()>o.getDistance_to_sun()))
                o = arr.get(i);
        }
        return "Самая далекая планета от Солнца:\n" + o.getName() +"\n";
    }

    public static String closer_than_Earth(){
        String result="";
        for (int i=0; i<arr.size(); i++){
            if ((arr.get(i) instanceof Planet) && (arr.get(i).getDistance_to_sun()<Cosmic_body.from_earth_to_sun))
                result+=arr.get(i).getName()+" ";
        }
        return "Планеты, которые ближе к Солнцу, чем Земля:\n" + result + "\n";
    }

    static public ArrayList<Cosmic_body> sort_array(){
        for (int j= arr.size()-1; j>0; j--){
            for (int i=0; i<=j-1; i++){
                if (arr.get(i).getDistance_to_sun()>arr.get(i+1).getDistance_to_sun()){
                    Cosmic_body s = arr.get(i);
                    arr.set(i, arr.get(i+1));
                    arr.set(i+1, s);
                }
            }
        }
        return arr;
    }

    public static Cosmic_body find(String n) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getName().equals(n)) {
                return arr.get(i);
            }
        }
        return null;
    }

    public static Cosmic_body edit(Cosmic_body o, double d){
        o.setDiameter(d);
        return o;
    }
}

class Planet extends Cosmic_body {
    private int satellite;
    private int period;
    public Planet(String name, int distance_to_sun, double diameter, double weight, int satellite, int period){
        super(name, distance_to_sun, diameter, weight);
        this.satellite = satellite;
        this.period=period;
    }

    public int getSatellite(){
        return satellite;
    }

    public int getPeriod(){
        return period;
    }

    @Override
    public String toString(){
        return "Планета: " + super.getName() + "\nРасстояние до солнца (млн км): " + super.getDistance_to_sun() +
                "\nДиаметр: " + super.getDiameter() + "\nПериод обращения: " + this.getPeriod() +
                "\nЧисло спутников: " + this.getSatellite() + "\nМасса (10^24 кг): " + super.getWeight() + "\n\n";
    }
}

class Star extends Cosmic_body{
    private double luminosity;
    private String opening_date;
    public Star(String name, int distance_to_sun, double diameter, String opening_date, double weight, double luminosity){
        super(name, distance_to_sun, diameter, weight);
        this.luminosity = luminosity;
        this.opening_date = opening_date;
    }

    private double getLuminosity(){
        return luminosity;
    }

    public String getOpening_date(){
        return opening_date;
    }

    @Override
    public String toString(){
        return "Звезда: " + super.getName() + "\nРасстояние до солнца (млн км): " + super.getDistance_to_sun() +
                "\nДиаметр: " + super.getDiameter() + "\nМасса (10^24 кг): " +
                super.getWeight() + "\nДата открытия: " + this.opening_date + "\nСветимость (10^26 Вт): " + this.luminosity + "\n\n";
    }
}