import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class ZooClub implements Serializable {
    private static final long serialVersionUID = 1L;

    private transient BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private Map<Person, List<Pet>> map;
    private transient BufferedWriter zooClubFileWriter;
    private transient String filePath = System.getProperty("user.dir") + "\\zooClub.txt";
    private transient String filePathSave = System.getProperty("user.dir") + "\\zooClubSave.zoo";
    private transient ZooClub zooLoad;

    public ZooClub() {
        this.map = new HashMap<>();
    }

    public BufferedWriter getZooClubFileWriter() {
        return zooClubFileWriter;
    }

    public void addPerson() throws IOException {
        System.out.println("Введіть учасника клубу");
        String person = reader.readLine().toUpperCase();
        if (map.containsKey(new Person(person))) {
            System.out.println("Сорян, такий учасник в клубі вже є");
        } else {
            map.put(new Person(person), new ArrayList<Pet>());
        }
    }

    public void addPet() throws IOException {
        System.out.println("Введіть учасника і його тваринку");
        String person = reader.readLine().toUpperCase();
        if (map.containsKey(new Person(person))) {
            map.get(new Person(person)).add(new Pet(reader.readLine().toUpperCase()));
        } else {
            System.out.println("Сорян, в нас немає такого учасника в клубі");
        }
    }

    public void removePet() throws IOException {
        System.out.println("Введіть учасника і тваринку яка здохла");
        String person = reader.readLine().toUpperCase();
        if (map.containsKey(new Person(person))) {
            map.get(new Person(person)).remove(new Pet(reader.readLine().toUpperCase()));
        } else {
            System.out.println("Сорян, в нас немає такого учасника в клубі");
        }
    }

    public void removePerson() throws IOException {
        System.out.println("Введіть учасника який відкинувася");
        map.remove(new Person(reader.readLine().toUpperCase()));
    }

    public void removeAllPet() throws IOException {
        System.out.println("Введіть тварин які повиздихували");
        String pet = reader.readLine().toUpperCase();
        Iterator<List<Pet>> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().remove(pet);
        }
    }

    public void printZoo() {
        for (Map.Entry entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    public void writeFile() {
        try {
            zooClubFileWriter = new BufferedWriter(new FileWriter(filePath, true));
            for (Map.Entry entry : map.entrySet()) {
                zooClubFileWriter.write(entry.getKey() + " - " + entry.getValue());
                zooClubFileWriter.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Зооклуб в буфері, і буде записаний у файл як тільки ви закінчите роботу з програмою");
    }

    public void reWriteFile() {
        try {
            zooClubFileWriter = new BufferedWriter(new FileWriter(filePath, false));
            for (Map.Entry entry : map.entrySet()) {
                zooClubFileWriter.write(entry.getKey() + " - " + entry.getValue());
                zooClubFileWriter.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Зооклуб в буфері, і буде записаний у файл як тільки ви закінчите роботу з програмою");
    }

    public void readFile() {
        try (BufferedReader zooClubFileReader = new BufferedReader(new FileReader(filePath))) {
            String str;
            while ((str = zooClubFileReader.readLine()) != null) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try (ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(filePathSave))) {
            save.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Файл збережений");
    }

    public ZooClub load() {
        try (ObjectInputStream load = new ObjectInputStream(new FileInputStream(filePathSave))) {
            zooLoad = (ZooClub) load.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Дані завантажено");
        return zooLoad;
    }

    public void menu() {
        System.out.println("Виберіть один із пунктів меню:\n" +
                "1. Додати учасника клубу\n" +
                "2. Додати тваринку до учасника клубу\n" +
                "3. Видалити тваринку з учасника клубу\n" +
                "4. Видалити учасника клубу\n" +
                "5. Видалити конкретну тваринку зі всіх власників\n" +
                "6. Вивести на екран зооклуб\n" +
                "7. Записати зооклуб в файл\n" +
                "8. Перезаписати\n" +
                "9. Зчитати з файлу\n" +
                "0. Вийи з програми\n" +
                "save -> Шоб зберегтись в програмі\n" +
                "load -> Щоб завантажити дані");
    }
}

