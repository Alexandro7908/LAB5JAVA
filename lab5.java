import java.util.*;
import java.io.*;
import java.util.stream.*;

public class lab5 {

    public class zad1 {
        interface FractionInterface {
            double getValue();
            void setChislitel(int ch);
            void setZnamenatel(int zn);
        }

        class Fraction implements FractionInterface {
            protected int chislitel;
            protected int znamenatel;

            public Fraction(int ch, int zn) {
                setZnamenatel(zn);
                setChislitel(ch);
            }

            public void setChislitel(int ch) { this.chislitel = ch; }

            public void setZnamenatel(int zn) {
                if (zn == 0)
                    throw new IllegalArgumentException("Знаменатель не может быть 0");
                if (zn < 0) {
                    this.chislitel = -this.chislitel;
                    this.znamenatel = -zn;
                } else {
                    this.znamenatel = zn;
                }
            }

            public double getValue() { return (double) chislitel / znamenatel; }

            public String toString() { return chislitel + "/" + znamenatel; }

            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Fraction)) return false;
                Fraction f = (Fraction) o;
                return chislitel == f.chislitel && znamenatel == f.znamenatel;
            }
        }

        class CachedFraction extends Fraction {
            private Double cache;

            public CachedFraction(int ch, int zn) { super(ch, zn); cache = null; }

            public double getValue() {
                if (cache == null) cache = (double) chislitel / znamenatel;
                return cache;
            }

            public void setChislitel(int ch) { super.setChislitel(ch); cache = null; }

            public void setZnamenatel(int zn) { super.setZnamenatel(zn); cache = null; }

            public String toString() { return super.toString() + " (кэшир.)"; }
        }
        
        public void run() {
            Scanner sc = new Scanner(System.in);

            System.out.println("Введите первую дробь:");
            CachedFraction f1 = inputFraction(sc);

            System.out.println("\nВведите вторую дробь:");
            CachedFraction f2 = inputFraction(sc);

            System.out.println("\nПервая дробь: " + f1 + ", значение: " + f1.getValue());
            System.out.println("Вторая дробь: " + f2 + ", значение: " + f2.getValue());

            System.out.println("\nСравнение дробей:");
            if (f1.equals(f2))
                System.out.println(f1 + " = " + f2);
            else
                System.out.println(f1 + " != " + f2);

            System.out.println("\nДемонстрация кэша:");
            System.out.println("Первый вызов значения первой дроби: " + f1.getValue());
            System.out.println("Второй вызов значения первой дроби (из кэша): " + f1.getValue());

            System.out.println("\nИзменим знаменатель первой дроби на -2");
            f1.setZnamenatel(2);
            System.out.println("Первая дробь после изменения: " + f1 + ", новое значение: " + f1.getValue());
        }

        private CachedFraction inputFraction(Scanner sc) {
            int ch = getInt(sc, "Введите числитель: ");
            int zn;
            while (true) {
                zn = getInt(sc, "Введите знаменатель: ");
                if (zn != 0) break;
                System.out.println("Ошибка: знаменатель не может быть 0");
            }
            return new CachedFraction(ch, zn);
        }

        private int getInt(Scanner sc, String msg) {
            while (true) {
                System.out.print(msg);
                try {
                    return Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: введите целое число");
                }
            }
        }
    }
    public class zad2 {
        interface Meower {
            void meow();
        }

        class Cat implements Meower {
            private String name;
            private int meowCount;

            public Cat(String name) {
                this.name = name;
                this.meowCount = 0;
            }

            public void meow() {
                System.out.println(name + ": мяу!");
                meowCount++;
            }

            public int getMeowCount() {
                return meowCount;
            }

            public String toString() {
                return "кот: " + name;
            }
        }

        void callAllMeowers(List<Meower> meowers) {
            for (Meower m : meowers) {
                m.meow();
            }
        }

        public void run() {
            Scanner sc = new Scanner(System.in);

            System.out.print("Сколько котов вы хотите создать? ");
            int n = getInt(sc);

            List<Cat> cats = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                System.out.print("Введите имя кота #" + (i + 1) + ": ");
                String name = sc.nextLine();
                cats.add(new Cat(name));
            }

            List<Meower> meowers = new ArrayList<>(cats);

            System.out.println("\nВсе коты мяукают:");
            callAllMeowers(meowers);
            System.out.println("\nКоты мяукают снова:");
            callAllMeowers(meowers);

            System.out.println("\nИтоговое количество мяуканий:");
            for (Cat c : cats) {
                System.out.println(c + " мяукал(а) " + c.getMeowCount() + " раз(а)");
            }
        }

        private int getInt(Scanner sc) {
            while (true) {
                try {
                    return Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.print("Ошибка: введите целое число: ");
                }
            }
        }
    }
    public class zad34 {
        private List<Integer> L;
        private int E;

        public zad34() {
            L = new ArrayList<>();
        }

        public String toString() {
            return "Список L: " + L;
        }

        public void run() {
            Scanner sc = new Scanner(System.in);

            int n = getInt(sc, "Введите количество элементов списка L: ");

            for (int i = 0; i < n; i++) {
                int val = getInt(sc, "Введите элемент " + i + ": ");
                L.add(val);
            }

            E = getInt(sc, "Введите элемент E: ");

            System.out.println(toString());

            int index = L.indexOf(E);
            if (index != -1) {
                L.addAll(index + 1, new ArrayList<>(L));
                System.out.println("Список после вставки: " + L);
            } else {
                System.out.println("Элемент E не найден в списке, список не изменён: " + L);
            }
        }

        private int getInt(Scanner sc, String msg) {
            while (true) {
                System.out.print(msg);
                try {
                    return Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: введите целое число");
                }
            }
        }
    }
    public class zad46 {

        class Applicant {
            String lastName;
            String firstName;
            int[] scores = new int[3];

            public Applicant(String lastName, String firstName, int s1, int s2, int s3) {
                this.lastName = lastName;
                this.firstName = firstName;
                this.scores[0] = s1;
                this.scores[1] = s2;
                this.scores[2] = s3;
            }

            public boolean isAllowed() {
                int sum = 0;
                for (int s : scores) {
                    if (s < 30) return false;
                    sum += s;
                }
                return sum >= 140;
            }

            public String toString() {
                return lastName + " " + firstName;
            }
        }

        public void run() {
            Scanner sc = new Scanner(System.in);

            int N;
            while (true) {
                System.out.print("Введите количество абитуриентов (≤500): ");
                try {
                    N = Integer.parseInt(sc.nextLine());
                    if (N > 0 && N <= 500) break;
                } catch (NumberFormatException e) {}
                System.out.println("Ошибка: введите корректное число от 1 до 500.");
            }

            List<Applicant> admitted = new ArrayList<>();

            System.out.println("Введите данные абитуриентов (Фамилия Имя Баллы1 Баллы2 Баллы3):");
            for (int i = 0; i < N; i++) {
                while (true) {
                    System.out.print((i+1) + ": ");
                    String line = sc.nextLine().trim();
                    String[] parts = line.split("\\s+");
                    if (parts.length != 5) {
                        System.out.println("Ошибка: нужно 5 полей. Попробуйте ещё раз.");
                        continue;
                    }
                    try {
                        String lastName = parts[0];
                        String firstName = parts[1];
                        if (lastName.isEmpty() || lastName.length() > 20) {
                            System.out.println("Ошибка: фамилия должна быть не пустой и не длиннее 20 символов.");
                            continue;
                        }

                        if (firstName.isEmpty() || firstName.length() > 15) {
                            System.out.println("Ошибка: имя должно быть не пустым и не длиннее 15 символов.");
                            continue;
                        }

                        int s1 = Integer.parseInt(parts[2]);
                        int s2 = Integer.parseInt(parts[3]);
                        int s3 = Integer.parseInt(parts[4]);

                        if (s1 < 0 || s1 > 100 || s2 < 0 || s2 > 100 || s3 < 0 || s3 > 100) {
                            System.out.println("Ошибка: баллы должны быть от 0 до 100.");
                            continue;
                        }

                        Applicant app = new Applicant(lastName, firstName, s1, s2, s3);
                        if (app.isAllowed()) admitted.add(app);

                        break;
                    } catch (NumberFormatException e) {
                        System.out.println("Ошибка: баллы должны быть целыми числами.");
                    }
                }
            }

            System.out.println("\nАбитуриенты, допущенные к экзаменам в первом потоке:");
            for (Applicant a : admitted) {
                System.out.println(a);
            }
        }
    }
    public class zad58 {

        private String filePath;

        public zad58() {
            this.filePath = "";
        }

        public String toString() {
            return "" + filePath;
        }

        public void run() {
            Scanner sc = new Scanner(System.in);
            System.out.print("Введите путь к файлу с текстом: ");
            String filePath = sc.nextLine();

            Set<Character> allLetters = new HashSet<>();
            for (char c = 'а'; c <= 'я'; c++) allLetters.add(c);

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(filePath)))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.toLowerCase();
                    for (char ch : line.toCharArray()) {
                        allLetters.remove(ch);
                    }
                }

                System.out.println("Кол-во букв, которых нет в тексте: " + allLetters.size());
                System.out.println("Отсутствующие буквы: " + allLetters);

            } catch (IOException e) {
                System.out.println("Ошибка чтения файла: " + e.getMessage());
            }
        }
    }
    public class zad62 {

        private List<Integer> L;
        private Queue<Integer> queue;

        public zad62() {
            this.L = new ArrayList<>();
            this.queue = new LinkedList<>();
        }

        public String toString() {
            return "L = " + L + ", Очередь = " + queue;
        }

        public void run() {
            Scanner sc = new Scanner(System.in);

            int n = getInt(sc, "Введите количество элементов списка L: ");

            for (int i = 0; i < n; i++) {
                int val = getInt(sc, "Введите элемент " + i + ": ");
                L.add(val);
            }

            queue.addAll(L);
            List<Integer> reversed = new ArrayList<>(L);
            Collections.reverse(reversed);
            queue.addAll(reversed);

            System.out.println("\nРезультат работы:");
            System.out.println(this);
        }

        private int getInt(Scanner sc, String msg) {
            while (true) {
                System.out.print(msg);
                try {
                    return Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: введите целое число.");
                }
            }
        }
    }
    public class zad71 {

        class Point {
            private double x;
            private double y;

            public Point(double x, double y) {
                this.x = x;
                this.y = y;
            }

            public double getX() { return x; }
            public double getY() { return y; }

            public String toString() {
                return "{" + x + ";" + y + "}";
            }

            public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Point)) return false;
                Point p = (Point) o;
                return Double.compare(p.x, x) == 0 && Double.compare(p.y, y) == 0;
            }

            public int hashCode() {
                return Objects.hash(x, y);
            }
        }

        class Line {
            private Point start;
            private Point end;

            public Line(Point start, Point end) {
                this.start = start;
                this.end = end;
            }

            public String toString() {
                return "Линия от " + start + " до " + end;
            }
        }

        class Polyline {
            private List<Point> points;

            public Polyline(List<Point> points) {
                this.points = points;
            }

            public String toString() {
                return "Ломаная " + points;
            }
        }

        private List<Point> points;
        private Polyline polyline;

        public zad71() {
            points = new ArrayList<>();
            polyline = null;
        }

        public String toString() {
            return "" + polyline;
        }

        public void run() {
            Scanner sc = new Scanner(System.in);

            int n = getInt(sc, "Введите количество точек: ");

            for (int i = 0; i < n; i++) {
                System.out.println("Точка " + i + ":");
                double x = getDouble(sc, "  X = ");
                double y = getDouble(sc, "  Y = ");
                points.add(new Point(x, y));
            }

            polyline = new Polyline(
                    points.stream()
                            .distinct()
                            .map(p -> new Point(p.getX(), Math.abs(p.getY())))
                            .sorted(Comparator.comparingDouble(Point::getX))
                            .collect(Collectors.toList())
            );

            System.out.println("\nРезультат:");
            System.out.println(this);
        }

        private int getInt(Scanner sc, String msg) {
            while (true) {
                System.out.print(msg);
                try {
                    return Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: введите целое число.");
                }
            }
        }

        private double getDouble(Scanner sc, String msg) {
            while (true) {
                System.out.print(msg);
                try {
                    return Double.parseDouble(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: введите число.");
                }
            }
        }
    }
    public class zad72 {

        private String filePath;
        private Map<Integer, List<String>> groupedPeople;

        public zad72() {
            this.filePath = "";
            this.groupedPeople = new HashMap<>();
        }

        public String toString() {
            return "" + groupedPeople;
        }

        public void run() {
            Scanner sc = new Scanner(System.in);
            System.out.print("Введите путь к файлу с людьми: ");
            filePath = sc.nextLine();

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

                groupedPeople = reader.lines()
                        .map(String::trim)
                        .filter(line -> !line.isEmpty())
                        .map(line -> line.split(":"))
                        .filter(parts -> parts.length == 2 && !parts[1].isEmpty())
                        .map(parts -> new AbstractMap.SimpleEntry<>(
                                Integer.parseInt(parts[1].trim()),
                                capitalize(parts[0].trim())
                        ))
                        .collect(Collectors.groupingBy(
                                Map.Entry::getKey,
                                HashMap::new,
                                Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                        ));

                System.out.println("\nРезультат:");
                System.out.println(this);

            } catch (IOException e) {
                System.out.println("Ошибка чтения файла: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: номер должен быть числом.");
            }
        }

        private String capitalize(String name) {
            if (name.isEmpty()) return name;
            return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
        }
    }

    void main(String[] args) {
        lab5 lab = new lab5();
//        lab.new zad1().run();
//        lab.new zad2().run();
//        lab.new zad34().run();
//        lab.new zad46().run();
//        lab.new zad58().run();
//        lab.new zad62().run();
//        lab.new zad71().run();
        lab.new zad72().run();
    }
}
