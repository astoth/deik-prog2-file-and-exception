package hu.deik.prog2.products.model;

import java.util.Currency;

public record Product(
        Long index,
        String name,
        String description,
        String brand,
        String category,
        Float price,
        Currency currency,
        String stock,
        String ean,
        String color,
        String size,
        String availability,
        Long internalID
) {
}
