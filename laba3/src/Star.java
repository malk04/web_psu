// Класс Звезда
class Star extends Cosmic_body{
    private double luminosity; // Светимость
    private String opening_date; // Дата открытия

    // Конструктор
    public Star(String name, int distance_to_sun, double diameter, String opening_date, double weight, double luminosity){
        super(name, distance_to_sun, diameter, weight);
        this.luminosity = luminosity;
        this.opening_date = opening_date;
    }

    public double getLuminosity(){
        return luminosity;
    } // Получить светимость
    public String getOpening_date(){
        return opening_date;
    } // Получить дату открытия
    public void setLuminosity(double l){
        luminosity = l;
    } // Записать светимость
    public void setOpening_date(String o){
        opening_date = o;
    } // Записать дату открытия

    // Переопределенный метод класса Object
    @Override
    public String toString(){
        return "Звезда: " + super.getName() + "\nРасстояние до солнца (млн км): " + super.getDistance_to_sun() +
                "\nДиаметр: " + super.getDiameter() + "\nМасса (10^24 кг): " +
                super.getWeight() + "\nДата открытия: " + this.opening_date + "\nСветимость (10^26 Вт): " + this.luminosity + "\n\n";
    }

    // редактировать поле
    public void edit(String f, String v){
        switch (f) {
            case ("имя"):
                super.setName(v);
                break;
            case ("расстояние до солнца"):
                super.setDistance_to_sun(Integer.parseInt(v));
                break;
            case ("диаметр"):
                super.setDiameter(Double.parseDouble(v));
                break;
            case ("дата открытия"):
                opening_date = v;
                break;
            case ("вес"):
                super.setWeight(Double.parseDouble(v));
                break;
            case ("светимость"):
                luminosity = Double.parseDouble(v);
                break;
            default:
                break;
        }
    }
}