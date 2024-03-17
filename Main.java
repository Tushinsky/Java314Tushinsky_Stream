import person.GenderImpl;
import person.Person;
import product.CategoryImpl;
import product.Product;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        // фильтрация и сборка объектов типа PERSON
        System.out.println("фильтрация и сборка объектов типа PERSON");
        createPersons();
        // трансформация данных
        System.out.println("трансформация данных");
        dataTransform();
        // группировка и подсчёт
        System.out.println("группировка и подсчёт");
        groupAndCalculate();
        // сортировка и поиск
        System.out.println("сортировка и поиск");
        sortedAndSearch();
        // обработка данных из файла
        System.out.println("обработка данных из файла");
        readDataFile();
        // параллельная обработка
        System.out.println("параллельная обработка");
        parallelUpData();
    }

    /**
     * Фильтрует и собирает объекты типа PERSON
     */
    private static void createPersons() {
        List<Person> personList = new ArrayList<>();// список персон
        String[] personName = {"Анна","Виктория","Галина","Елена","Жанна","Андрей","Борис","Владимир","Геннадий","Дмитрий"};
        Random random = new Random();
        for(int i = 0; i < 10; i++) {
            short age = (short) (Math.random() * 10 + 15);// генерируем случайный возраст
            int a = random.nextInt(10);// генерируем случайный возраст
            String sex;
            if(a < 5) {
                sex = GenderImpl.FEMALE;
            } else {
                sex = GenderImpl.MALE;
            }
            Person person = new Person(personName[a], (short) (age + 10), sex);
            personList.add(person);// заполняем список
        }
        // выведем список
        personList.forEach(System.out::print);
        // отберём только женщин старше 18 лет и выведем их имена
        List<String> list = new ArrayList<>();
        personList.stream().filter((person -> person.getAge() > 18)).filter(person ->
                Objects.equals(person.getGender(), GenderImpl.FEMALE)).forEach(person -> list.add(person.getName()));
        System.out.println();
        list.forEach(System.out::println);
    }

    private static void dataTransform() {
        // список чисел в строковом представлении
        List<String> stringList = Arrays.asList("3","2","3","4","12","24","15","45","67","453","23","46","67");
        List<Integer> integerList = new ArrayList<>();// список чисел
        stringList.forEach(s -> integerList.add(Integer.valueOf(s)));// заполняем список, преобразуя данные
        StringBuilder builder = new StringBuilder();
        // фильтруем чётные числа, преобразуем их в строковое представление и собираем их в строку, разделённую ","
        integerList.stream().filter(n -> (n % 2) == 0).forEach(n -> builder.append(n).append(';'));
        builder.delete(builder.length() - 1, builder.length());
        System.out.println(builder);
    }

    private static void groupAndCalculate() {
        // создаём список для хранения объектов Product
        List<Product> productList = new ArrayList<>();
        // создадим несколько объектов типа Product и добавим их в список
        String[] categoryName = {CategoryImpl.FISH, CategoryImpl.FRUIT, CategoryImpl.MEAT, CategoryImpl.VEGETABLE};
        String[] productName = {"карп","молоко","яблоко","банан","треска","свинина","творог","лук","укроп","лосось",};
        for (int i = 0; i < 20; i++) {
            // заполняем список случайными данными
            String name = productName[new Random().nextInt(10)];
            String category = categoryName[new Random().nextInt(4)];
            short count = (short) new Random().nextInt(15);
            double price = new Random().nextInt(200) + 0.19;
            Product product = new Product(name,category,count,price);
            productList.add(product);
        }
        System.out.println(Arrays.toString(productList.toArray()));
        // фильтруем по категориям, подсчитываем количество и суммарную стоимость продуктов
        for (String s : categoryName) {
            int count;// количество продуктов указанной категории
            count = productList.stream().filter(p -> p.getCategory().equals(s)).mapToInt(Product::getCount).sum();
            double price;
            price = productList.stream().filter(p -> p.getCategory().equals(s)).mapToDouble(p -> p.getPrice() * p.getCount()).sum();
            System.out.println(s + " count=" + count + ", price=" + price);
        }
    }

    private static void sortedAndSearch() {
        // список чисел в строковом представлении
        List<Integer> integerList = Arrays.asList(3,2,3,4,12,24,15,45,453,23,46,67);
        integerList.sort((o1, o2) -> o2 - o1);// сортировка по убыванию
        integerList.stream().limit(3).forEach(System.out::println);
    }

    private static void readDataFile() {
        // выбираем файл для чтения
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setMultiSelectionEnabled(false);// запрещаем множественный выбор
        // задаём фильтр файлов
        FileFilter filter = new FileFilter() {

            @Override
            public boolean accept(File f) {
                return f.getName().toLowerCase().endsWith(".csv") ||
                        f.getName().toLowerCase().endsWith(".txt") ||
                        f.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Файлы с разделителями, текстовые файлы (*.csv,*.txt)";
            }
        };
        chooser.setFileFilter((filter));// устанавливаем фильтр
        int result = chooser.showOpenDialog(null);

        // проверяем сделал ли выбор пользователь
        if(result == JFileChooser.APPROVE_OPTION) {
            // если выбор сделан
            try {
                System.out.println("file - " + chooser.getSelectedFile().getCanonicalPath());
                FileInputStream fis = new FileInputStream(chooser.getSelectedFile());
                InputStreamReader isr = new InputStreamReader(fis,"Windows-1251");
                BufferedReader reader = new BufferedReader(isr);
                List<String> stringData = new ArrayList<>();// список для хранения строк файла
                String line;
                // читаем данные в список
                while((line = reader.readLine()) != null) {
                    stringData.add(line);
                }
                String string = JOptionPane.showInputDialog("Введите слово для фильтра", "слово");
                int count = (int) stringData.stream().filter(s -> s.contains(string)).count();
                System.out.println("Количество строк, содержащих слово <" + string + ">=" + count);
            } catch (IOException ex) {
                Logger.getLogger(null).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static void parallelUpData() {
        // список целых чисел
        List<Integer> integerList = new ArrayList<>();
        // заполняем его
        for(int i = 1; i <= 1000; i++) {
            integerList.add(i);
        }
        double sum = 0;// начальное значение суммы квадратов чисел списка
        long finish;
        long start = System.currentTimeMillis();
        for (Integer i : integerList) {
            sum = sum + (i * i);
        }
        finish = System.currentTimeMillis();
        System.out.println("time=" + (finish - start));
        System.out.println("summa=" + sum);
        start = System.currentTimeMillis();
        sum = integerList.stream().mapToDouble(i -> i * i).sum();
        finish = System.currentTimeMillis();
        System.out.println("time_1=" + (finish - start));
        System.out.println("summa=" + sum);
    }
}