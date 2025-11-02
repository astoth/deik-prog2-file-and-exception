package hu.deik.prog2.products.service.impl;

import hu.deik.prog2.products.model.Product;
import hu.deik.prog2.products.service.FileService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

public class FileServiceImpl implements FileService {

    private static String FILE_HEADER = "index;name;description;brand;category;price;currency;stock;ean;color;size;availability;internalID\n";

    @Override
    public List<Product> readFileByScanner(String filePath) throws FileNotFoundException {
        return Collections.emptyList();
    }

    @Override
    public List<Product> readFileByFileInputStream(String filePath) throws IOException {
       return Collections.emptyList();
    }

    @Override
    public List<Product> readFileByByteBuffer(String filePath) throws FileNotFoundException {
        return Collections.emptyList();
    }

    @Override
    public void writeFileByOutPutStream(String filePath, List<Product> products) {
    }

    private String getCSVLineRepresentation(Product product) {
        return new StringBuilder().append(product.index())
                .append(";")
                .append(product.name())
                .append(";")
                .append(product.description())
                .append(";")
                .append(product.brand())
                .append(";")
                .append(product.category())
                .append(";")
                .append(product.price())
                .append(";")
                .append( product.currency().getCurrencyCode())
                .append(";")
                .append(product.stock())
                .append(";")
                .append(product.ean())
                .append(";")
                .append(product.color())
                .append(";")
                .append(product.size())
                .append(";")
                .append(product.availability())
                .append(product.internalID())
                .append("\n")
                .toString();
    }

    private Product populateProduct(String[] columns) {
        Long index = Long.parseLong(columns[0]);
        String name = columns[1];
        String description = columns[2];
        String brand = columns[3];
        String category = columns[4];
        Float price = Float.parseFloat(columns[5]);
        Currency currency = Currency.getInstance(columns[6]);
        String stock = columns[7];
        String ean = columns[8];
        String color = columns[9];
        String size = columns[10];
        Boolean availability = Boolean.parseBoolean(columns[11]);
        Long internalID = Long.parseLong(columns[12].trim());

        return new Product(index, name, description, brand, category, price, currency, stock, ean, color, size, availability, internalID);
    }

}
