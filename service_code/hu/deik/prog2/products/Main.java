package hu.deik.prog2.products;


import hu.deik.prog2.products.model.Product;
import hu.deik.prog2.products.service.FileService;
import hu.deik.prog2.products.service.impl.FileServiceImpl;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        FileService fileService = new FileServiceImpl();

        List<Product> products = fileService.readFileByScanner("products.csv");
        products.forEach(System.out::println);

        List<Product> products2 = fileService.readFileByFileInputStream("products.csv");
        products2.forEach(System.out::println);

        List<Product> products3 = fileService.readFileByByteBuffer("products.csv");
        products3.forEach(System.out::println);

        System.out.println(products.equals(products2));
        System.out.println(products.equals(products3));
        System.out.println(products.size());

        List<Product> uniqueProducts = Main.getUniqueProducts(products);
        System.out.println(uniqueProducts.size() == products.size());
        System.out.println(uniqueProducts.size());
        System.out.println("-----");
        List<Product> duplicatedProducts = Main.getProductsWithMultipleOccurrence(products);
        duplicatedProducts.forEach(System.out::println);

        System.out.println("Write duplicated products into a file.");
        fileService.writeFileByOutPutStream("D:\\duplicated-products.csv", duplicatedProducts);
    }

    private static List<Product> getUniqueProducts(List<Product> products) {
        return Collections.emptyList();
    }

    private static List<Product> getProductsWithMultipleOccurrence(List<Product> products) {
        return Collections.emptyList();
    }

}
