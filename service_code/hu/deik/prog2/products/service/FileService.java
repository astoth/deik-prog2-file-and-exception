package hu.deik.prog2.products.service;

import hu.deik.prog2.products.model.Product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FileService {

    List<Product> readFileByScanner(String filePath) throws FileNotFoundException;

    List<Product> readFileByFileInputStream(String filePath) throws IOException;

    List<Product> readFileByByteBuffer(String filePath) throws FileNotFoundException;

    void writeFileByOutPutStream(String filePath, List<Product> products) throws FileNotFoundException;

}
