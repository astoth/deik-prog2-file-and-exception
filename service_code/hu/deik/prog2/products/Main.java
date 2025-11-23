package hu.deik.prog2.products;


import hu.deik.prog2.products.model.Product;
import hu.deik.prog2.products.service.FileService;
import hu.deik.prog2.products.service.impl.FileServiceImpl;
import hu.deik.prog2.products.service.impl.ProductService;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        FileService fileService = new FileServiceImpl();
        ProductService productService = new ProductService();

        List<Product> products = fileService.readFileByScanner("products.csv");
        productService.process(products);

//        products.forEach(System.out::println);
//        System.out.println("------------------------------");
//        System.out.println();
//
//        List<Product> products2 = fileService.readFileByFileInputStream("products.csv");
//        products2.forEach(System.out::println);
//        System.out.println("------------------------------");
//        System.out.println();
//
//        List<Product> products3 = fileService.readFileByBufferedReader("products.csv");
//        products3.forEach(System.out::println);
//        System.out.println("------------------------------");
//        System.out.println();
//
//        System.out.println("Azonos a products és products2 listák? :" + products.equals(products2));
//        System.out.println("Azonos a products és products3 listák? :" +products.equals(products3));
//        System.out.println("Products lista mérete: "+products.size());
//
//        List<Product> uniqueProducts = Main.getUniqueProducts(products);
//        System.out.println("Egyedi termékek lista mérete: " + uniqueProducts.size());
//        System.out.println("Egyforma az egyedi és teljes termék listák mérete? : " + (uniqueProducts.size() == products.size()));
//        System.out.println("------------------------------");
//        System.out.println();
//
//        List<Product> duplicatedProducts = Main.getProductsWithMultipleOccurrence(products);
//        System.out.println("Duplikált termék rekordok:");
//        duplicatedProducts.forEach(System.out::println);
//        System.out.println("------------------------------");
//        System.out.println();
//
//        System.out.println("Duplikált termék rekordok kirása fájlba");
//        fileService.writeFileByOutPutStream("duplicated-products.csv", duplicatedProducts);
        // "D:\\duplicated-products.csv" // Példa Windows esetére ha nem a projekt könyvtárba szeretnénk menteni

    }

    private static List<Product> getUniqueProducts(List<Product> products) {
        Set<Product> uniqueProducts = new LinkedHashSet<>(products);
        return uniqueProducts.stream().toList();
    }

    private static List<Product> getProductsWithMultipleOccurrence(List<Product> products) {
        List<Product> productsWithMultipleOccurrence = new ArrayList<>();
        Map<Product, Integer> productCounts = new HashMap<>();
        for (Product product : products) {
            if (!productCounts.containsKey(product)) {
                productCounts.put(product, 1);
            } else {
                productCounts.put(product, productCounts.get(product) + 1);
            }
        }

        productCounts.forEach((product, count) -> {
            if (count > 1) {
                productsWithMultipleOccurrence.add(product);
            }
        });
        return productsWithMultipleOccurrence;
    }

}
