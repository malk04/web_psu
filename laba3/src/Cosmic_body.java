import java.util.ArrayList;

// Абстрактный класс Космического тела
abstract class Cosmic_body {
    private static ArrayList<Cosmic_body> arr = new ArrayList<>(); //Список, хранящий объекты класса Cosmic_body
    private static final int from_earth_to_sun = 150; // Расстояние от Земли до Солнца
    private String name; // Имя
    private int distance_to_sun; // Расстояние до Солнца
    private double diameter; // Диаметр
    private double weight; // Вес
    // Конструктор
    public Cosmic_body(String name, int distance_to_sun, double diameter, double weight) {
        this.name = name;
        this.distance_to_sun = distance_to_sun;
        this.diameter = diameter;
        this.weight = weight;
    }
    public String getName(){
        return name;
    } // Получить имя
    public int getDistance_to_sun(){
        return distance_to_sun;
    } // Получить расстояние до Солнца
    public double getDiameter(){
        return diameter;
    } // Получить диаметр
    public double getWeight(){
        return weight;
    } // Получить вес
    public void setDiameter(double d){
        this.diameter = d;
    } // Записать диаметр
    public void setName(String n){
        this.name = n;
    } // Записать имя
    public void setDistance_to_sun(int d){
        this.distance_to_sun = d;
    } // Записать расстояние до Солнца
    public void setWeight(double w){
        this.weight = w;
    } // Записать вес

    public void arr_push(){
        arr.add(this);
    } // Добавить объект в массив

    // Найти самую далекую планету от Солнца
    public static String theFurthest(){
        Cosmic_body o = arr.get(0);
        for (int i=0; i<arr.size() ;i++){
            if ( (arr.get(i) instanceof Planet) && (arr.get(i).getDistance_to_sun()>o.getDistance_to_sun()))
                o = arr.get(i);
        }
        return "Самая далекая планета от Солнца:\n" + o.getName() +"\n";
    }

    // Найти планеты, которые ближе к Солнцу, чем Земля
    public static String closer_than_Earth(){
        String result="";
        for (int i=0; i<arr.size(); i++){
            if ((arr.get(i) instanceof Planet) && (arr.get(i).getDistance_to_sun()<Cosmic_body.from_earth_to_sun))
                result+=arr.get(i).getName()+" ";
        }
        return "Планеты, которые ближе к Солнцу, чем Земля:\n" + result + "\n";
    }

    // Упорядочить массив по возрастанию расстояния от Солнца
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

    // Найти объект с определенным именем
    public static Cosmic_body find(String n) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i).getName().equals(n)) {
                return arr.get(i);
            }
        }
        return null;
    }

    // редактирование поля
    public abstract void edit(String f, String v);
}
