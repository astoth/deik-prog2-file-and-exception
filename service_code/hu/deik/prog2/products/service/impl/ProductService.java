package hu.deik.prog2.products.service.impl;

import hu.deik.prog2.products.model.Product;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ProductService {

    public void process(List<Product> products) {

        System.out.println("---- Üres Stream példa ----");
        Stream<Object> emptyStream = Stream.empty();
        System.out.println("Üres stream mérete: " + emptyStream.count());
        //emptyStream.forEach(System.out::println);

        System.out.println("---- Stream Builder példa ----");
        Stream<String> fruits = Stream.<String>builder()
                .add("alma")
                .add("körte")
                .add("barack")
                .add("szilva")
                .add("dió")
                .build();

        Stream<String> vegetables = Stream.<String>builder()
                .add("répa")
                .add("karfiol")
                .add("brokkoli")
                .add("fehérrépa")
                .add("zeller")
                .build();

        System.out.println("---- Összefűzött Stream példa ----");
        Stream<String> fruitsWithVegetables = Stream.concat(fruits, vegetables);
        fruitsWithVegetables.forEach(System.out::println);

        //        fruits.forEach(System.out::println); // Hibát okoz, mert a stream már fel lett használva és lezárva

        System.out.println("---- Map és String Join Collect példa ----");
        String fruits2 = Stream.of("alma", "körte", "barack", "szilva", "dió")
                .map(String::toUpperCase)
                .collect(Collectors.joining(";"));
        System.out.println(fruits2);

        System.out.println("AllMatch és AnyMatch példák");
        // AllMatch && Anymatch
        boolean allHaveLongName = Stream.of("alma", "körte", "barack", "szilva", "dió")
                .allMatch(s -> s.length() >= 5);
        System.out.println("Mindegyiknek hosszú neve van?: " + allHaveLongName);

        boolean atLeastOneHasLongName = Stream.of("alma", "körte", "barack", "szilva", "dió").anyMatch(s -> s.length() >= 5);
        System.out.println("Legalább egynek hosszú neve van?: " + atLeastOneHasLongName);


        System.out.println("---- FindAny és FindFirst példák ----");
        Stream.of("alma", "körte", "barack", "szilva", "dió")
                .filter(s -> s.length() >= 5)
                .findAny()
                .ifPresent(System.out::println);

        Stream.of("alma", "körte", "barack", "szilva", "dió")
                .filter(s -> s.length() >= 5)
                .findFirst()
                .ifPresent(System.out::println);

        System.out.println("---- Parallel Stream példa ----");
        List.of("alma", "körte", "barack", "szilva", "dió", "eper", "málna", "szeder", "áfonya", "cseresznye")
                .parallelStream()
                .filter(s -> s.length() >= 5)
                .forEach(System.out::println);

        System.out.println("---- Stream generálás példa ----");
        Stream<String> streamGenerated = Stream.generate(() -> "element").limit(10);
        streamGenerated.forEach(System.out::println);

        // Primitive Streams
        // Int és Long
        System.out.println("---- Range példa ----");
        IntStream.range(0, 10)
                .forEach(i -> System.out.println(i));

        System.out.println("---- Map, Filter és Sum példa ----");
        System.out.println(IntStream.range(0, 10)
                .filter(i -> i % 2 == 0)
                .map(i -> i * i)
                .sum());

        System.out.println("Filter, Map, Filter és Count példa ----");
        System.out.println(IntStream.range(0, 10)
                .filter(i -> i % 2 == 0)
                .map(i -> i * i)
                .filter(i -> i > 4)
                .count());

        System.out.println("---- TakeWhile , maptoObj példa ----");
        System.out.println(IntStream.range(0, 10)
                .takeWhile(i -> i < 5)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(";", "[", "]")));

        System.out.println("DropWhile és maptoObj példa ----");
        System.out.println(IntStream.range(0, 10)
                .dropWhile(i -> i < 5)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(";", "[", "]")));

        System.out.println("Skip példa ----");
        System.out.println(IntStream.range(0, 10)
                .skip(5)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(";", "[", "]")));

        System.out.println("Limit példa ----");
        System.out.println(IntStream.range(0, 10)
                .limit(5)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(";", "[", "]")));

        System.out.println("Reduce példa , a legnagyobb szám megkersése (, a max() metódus is használható)----");
        System.out.println(IntStream.range(0, 10)
                .reduce(0, (a, b) -> a > b ? a : b));

        System.out.println("Sorted, fordított sorrend példa ----");
        System.out.println(IntStream.range(0, 10)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .map(String::valueOf)
                .collect(Collectors.joining(";", "[", "]")));

        System.out.println("---- Indexelt forEach példa ----");
        IntStream.range(0, products.size())
                .forEach(i -> {
                    Product p = products.get(i);
                    System.out.println("Index: " + i + " Product: " + p);
                });


        System.out.println("---- Arrays.stream példa ----");
        String[] characters = new String[]{"a", "b", "c", "d", "e"};
        Stream<String> characterStream = Arrays.stream(characters);
        characterStream.forEach(System.out::println);


        System.out.println();
        System.out.println("---- Grill termékek ----");
        List<Product> grills = products.stream()
                .peek(p -> System.out.println(p.name())) // Kiírja a termék nevét a szűrés előtt az System.out-ra
                .filter(p -> p.name().contains("Grill"))
                .toList();
        grills.forEach(System.out::println);

        System.out.println();
        System.out.println("---- Home & Kitchen kategóriájú termékek összára ----");
        double priceOfAllGrill = products.stream()
//                .peek(p -> System.out.println(p.category()))
                .filter(p -> p.category().equals("Home & Kitchen"))
                .map(Product::price)
                .mapToDouble(Float::doubleValue)
                .sum();
        System.out.println("Price: " + priceOfAllGrill);


        System.out.println();
        System.out.println("---- Termékek kategória szerint csoportosítva ----");
        Map<String, List<Product>> productsByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::category));
        productsByCategory.forEach((category, productsInCategory) -> {
            System.out.println("Category: " + category);
            productsInCategory.forEach(System.out::println);
        });

        System.out.println();
        System.out.println("---- Termékek kategória szerint csoportosítva, darabszámmal együtt a kategóriában ----");
        products.stream()
                .collect(Collectors.groupingBy(Product::category, Collectors.counting()))
                .forEach((category, count) -> System.out.println(category + ": " + count));

        System.out.println();
        System.out.println("---- Termékek egyediség szerint csoportosítva, darabszámmal együtt ----");
        products.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())) // A Function.identity() visszaadja az aktuális elemet
                .forEach((brand, count) -> System.out.println(brand + ": " + count));

        System.out.println();
        System.out.println("---- Predicate definiálása, Brand névben 'LLC' szereplő termékek kiválogatása ----");
        Predicate<Product> predicate = p -> p.brand().contains("LLC");
        products.stream()
                .filter(predicate)
                .map(p -> p.name() + " - " + p.brand())
                .forEach(System.out::println);

        System.out.println();
        System.out.println("---- Predicate negációja, Brand névben 'LLC' NEM szereplő termékek kiválogatása ----");
        products.stream()
                .filter(predicate.negate())
                .map(p -> p.name() + " - " + p.brand())
                .forEach(System.out::println);

        System.out.println();
        System.out.println("---- Predicate or, Piros és sárga termékek kiválogatása ----");
        Predicate<Product> colourPredicateRed = p -> p.color() != null && p.color().contains("Red");
        Predicate<Product> colourPredicateYellow = p -> p.color() != null && p.color().contains("Yellow");
        products.stream()
                .filter(colourPredicateRed.or(colourPredicateYellow))
                .forEach(System.out::println);

        System.out.println();
        System.out.println("---- Predicate and, Piros-Sárga termékek kiválogatása (nincs ilyen termék) ----");
        products.stream()
                .filter(colourPredicateRed.and(colourPredicateYellow))
                .forEach(System.out::println);


        System.out.println();
        System.out.println("---- Elérhető termékek L vagy XL méretben ----");
        Predicate<Product> packageLOrXL = p -> p.size() != null && (p.size().contains("L") || p.size().contains("XL"));
        Predicate<Product> availablePredicate = p -> p.availability() != null && p.availability().equals("in_stock");
        List<Product> availableBigProducts = products.stream()
                .filter(packageLOrXL.and(availablePredicate))
                .toList();
        availableBigProducts.forEach(System.out::println);


        System.out.println();
        System.out.println("---- FindAny, Hosszú nevű termékek kiválogatása ----");
        products.stream()
                .filter(p -> p.name().length() >= 20)
                .findAny()
                .ifPresent(System.out::println);


        System.out.println();
        System.out.println("---- Elérhetőségenként a legdrágább termékek kiválogatása ----");
        Map<String, Optional<Product>> expensiveProductsByTheirCategories = products.stream()
                .collect(Collectors.groupingBy(Product::availability, Collectors.maxBy(Comparator.comparing(Product::price))));
        expensiveProductsByTheirCategories.forEach(
                (brand, productOpt) -> productOpt.ifPresent(p -> System.out.println(p.availability() + "\t\t\t\t\t\t\t\t\t\t\t\t" + p.price() + "\t\t\t\t\t\t\t\t\t\t\t\t" + p.name()))
        );


        System.out.println();
        System.out.println("---- Termékek elérhetőség és szín szerint csoportosítva ----");
        Map<String, Map<String, List<Product>>> groupedByAvailabilityAndColour = products.stream()
                .collect(Collectors.groupingBy(Product::availability, Collectors.groupingBy(Product::color)));
        for (Map.Entry<String, Map<String, List<Product>>> entry : groupedByAvailabilityAndColour.entrySet()) {
            System.out.println("Elérhetőség: " + entry.getKey());
            for (Map.Entry<String, List<Product>> subEntry : entry.getValue().entrySet()) {
                System.out.println("\tSzín: " + subEntry.getKey());
                subEntry.getValue().forEach(p -> System.out.println("\t\t" + p));
            }
        }

    }


}
