import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        //Заполнение коллекции персон
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }

        //Список совершеннолетних (старше 18 лет)
        List<Person> adults = persons.stream()
                .filter(x -> x.getAge() > 18)
                .collect(Collectors.toList());

        System.out.println("Совершеннолетних: " + adults.size() + " чел.");

        //Список призывников (мужчин в возрасте от 18 до 27 лет)
        List<String> recruits = persons.stream()
                .filter(x -> x.getAge() >= 18 && x.getAge() <= 27 && x.getSex() == Sex.MAN)
                .map(Person::getFamily)
                .collect(Collectors.toList());

        System.out.println("Призывников: " + recruits.size() + " чел.");


        /* Список, людей с высшим образованием от 18 до 60 лет для женщин
        * и до 65 лет для мужчин, отсортированный по фамилии
        */
        List<String> workers = persons.stream()
                .filter(x -> {
                    int age = x.getAge();
                    Sex sex = x.getSex();
                    Education edu = x.getEducation();

                    if (age >= 18 && edu == Education.HIGHER) {
                        if (sex == Sex.MAN && age <65 ) return true;
                        if (sex == Sex.WOMAN && age <60 ) return true;
                    }
                    return false;
                })
                .map(Person::getFamily)
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Работников с высшим образованием: " + workers.size() + " чел.");
    }
}
