package hibernate;

import hibernate.shop.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Created by Lukasz on 27.09.2017.
 */
public class App {

    public static void main(String[] args) throws Exception {

        Product merc = new Product("Mercedes S", ProductType.CAR,
                new Price(new BigDecimal(500000), new BigDecimal(600000)));

        Product mercA = new Product("Mercedes A", ProductType.CAR,
                new Price(new BigDecimal(100000), new BigDecimal(120000)));

        Product smallCar = new Product("red car", ProductType.TOY,
                new Price(new BigDecimal(30), new BigDecimal(34)));

        ProductRepository.saveProduct(merc);
        ProductRepository.saveProduct(mercA);
        ProductRepository.saveProduct(smallCar);

        Optional<Product> product1 = ProductRepository.findOneById(1L);
        Optional<Product> product2 = ProductRepository.findOneById(2L);
        Optional<Product> product3 = ProductRepository.findOneById(3L);

        System.out.println(product1.map(p -> p.getName()).orElse(""));
        System.out.println(product2.map(p -> p.getName()).orElse(""));
        System.out.println(product3.map(p -> p.getName()).orElse(""));

        ProductRepository.findAll().forEach(p -> System.out.println("findAll() " + p.getName()));

        ProductRepository.findAllByProductType(ProductType.CAR)
                .forEach(p -> System.out.println("type car: " + p.getName()));
        ProductRepository.findAllByProductType(ProductType.TOY)
                .forEach(p -> System.out.println("type toy: " + p.getName()));

        Long carCount = ProductRepository.countAllByProductType(ProductType.CAR);
        System.out.println("car in db " + carCount);

        ProductRepository.findAllWithPriceLess(new BigDecimal(40))
                .forEach(p -> System.out.println("product with price less than 40: " + p.getName()
                        + ", vat price: " + p.getPrice().getVatPrice()));

        ProductRepository.findAllByName("cedes")
                .forEach(p -> System.out.println("find all by cedes: " + p.getName()));

        Optional<Product> toyOptional = ProductRepository.findOneById(3L);
        if (toyOptional.isPresent()) {
            Product toy = toyOptional.get();
            toy.getPrice().setGrossPrice(toy.getPrice().getGrossPrice().add(BigDecimal.ONE));
            toy.getPrice().setNetPrice(toy.getPrice().getNetPrice().add(BigDecimal.ONE));

            ProductRepository.saveProduct(toy);
        }

        ProductRepository.deleteById(2L);

        Order order = Order.builder()
                .userEmail("test@sda.pl")
                .totalGross(new BigDecimal(13))
                .totalNet(new BigDecimal(12))
                .build();
        OrderRepository.saveOrder(order);


        List<Order> all = OrderRepository.findAll();

        ProductRepository.findAllNative()
                .forEach(p -> System.out.println("find all product from native: " + p.getName()));
    }
}
