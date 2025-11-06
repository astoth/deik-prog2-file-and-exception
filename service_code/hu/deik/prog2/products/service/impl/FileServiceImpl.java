package hu.deik.prog2.products.service.impl;

import hu.deik.prog2.products.model.Product;
import hu.deik.prog2.products.model.ProductException;
import hu.deik.prog2.products.service.FileService;

import java.io.*;
import java.util.*;
import java.util.stream.Stream;

public class FileServiceImpl implements FileService {

    private static String FILE_HEADER = "index;name;description;brand;category;price;currency;stock;ean;color;size;availability;internalID\n";

    @Override
    public List<Product> readFileByScanner(String filePath) throws FileNotFoundException { //A `throws` kulcs szóval jelezzük, hogy a metódus ellenőrzőtt kivételt dobhat
        Scanner scanner = new Scanner(new File(filePath));
        int lineId = 0;
        List<Product> products = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (lineId++ == 0) {
                continue; // skip header
            }
            String[] columns = line.split(";");
            products.add(populateProduct(columns));
        }
        return products;
    }

    @Override
    public List<Product> readFileByFileInputStream(String filePath) throws IOException {
        List<Product> products = new ArrayList<>();
        FileInputStream fileInputStream = null;
        try { //Az Intellij IDEA figyelmezetet, hogy használhatjuk a try-with-resources szerkezetet is
            //de itt bemutatjuk a sima try-catch-finally szerkezetet
            fileInputStream = new FileInputStream(filePath);
            StringBuilder sb = new StringBuilder();
            int lineId = 0;

            int ch = 0;
            while ((ch = fileInputStream.read()) != -1) {
                sb.append((char) ch);
                if (ch == '\n') {
                    if (lineId++ == 0) {
                        sb.setLength(0); // reset the StringBuilder
                        continue; // skip header
                    }
                    String line = sb.toString();
                    Product product = populateProduct(line.split(";"));
                    products.add(product);
                    sb.setLength(0); // reset the StringBuilder
                }
            }
        } catch (FileNotFoundException e) { //Igyekezzünk elsőnek a specifikusabb kivételeket elkapni és haladni az általánosabbak felé
            throw new ProductException("A megadott fájl nem található: " + filePath); // Egyedi kivétel dobása
        } catch (IOException e) { //Igyekezzünk elsőnek a specifikusabb kivételeket elkapni és haladni az általánosabbak felé
            throw new ProductException("Nem sikerült beolvasni az adatokat.", e); // Egyedi kivétel dobása
        } catch (
                RuntimeException e) { // Ennek a catch ágnak nincs sok értelme itt, de bemutatjuk a több catch ág használatát is, igyekezzünk elsőnek a specifikusabb kivételeket elkapni
            throw e; //Újra dobjuk a kivételt, mert itt nem akarunk mit kezdeni vele
        } finally { // A finally block mindig végrehajtásra kerül, függetlenül attól, hogy volt-e kivétel vagy sem (feltéve ha az alkalmazás fut még ezen ponton)
            if (fileInputStream != null) {
                fileInputStream.close(); //Mindig zárjuk le az errőforrásokat (fájl, adatbázis, hálózat stb.) a használat után
            }
        }
        // A kivételek felsorolása egy sorban így nézne ki ha mindegyiket ugyanúgy szeretnénk kezelni.} catch (FileNotFoundException e) { //Igyekezzünk elsőnek a specifikusabb kivételeket elkapni és haladni az általánosabbak felé
        //catch (FileNotFoundException | IOException | RuntimeException e) { //Ez a felsorolás nem valid, mert a RuntimeException az IOException szülő osztálya és így nem lehet egy catch ágban kezelni őket

        return products;
    }

    @Override
    public List<Product> readFileByBufferedReader(String filePath) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        List<Product> products = new ArrayList<>();

        Stream<String> data = bufferedReader.lines().skip(1);
        data.forEach(line -> {
            String[] columns = line.split(";");
            Product product = populateProduct(columns);
            products.add(product);
        });

        return products;
    }

    @Override
    public void writeFileByOutPutStream(String filePath, List<Product> products) {
        try (OutputStream outputStream = new FileOutputStream(filePath)) { //try-with-resources szerkezet használata, az erőforrás lezáródik automatikusab, nem kell explixit lezárnunk azokat mint a fentebbi példa esetén
            outputStream.write(FILE_HEADER.getBytes());
            products.forEach(product -> {
                String line = this.getCSVLineRepresentation(product);
                try { //Ez egy nested exception kezelés, mert a lambda kifejezésen belül nem dobhatunk ellenőrzött kivételt, csak nem ellenőrzöttet
                    //Ezért a IOException-t RuntimeException-be csomagoljuk, de lehete az egyedi ProductException is
                    outputStream.write(line.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e); //A Throw kulcs szóval dobjuk a kivételt
                }
            });
        } catch (IOException e) {
            throw new ProductException("Nem sikerült az adatokat kiírni a fájlba.", e);
        }
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
                .append(product.currency().getCurrencyCode())
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
