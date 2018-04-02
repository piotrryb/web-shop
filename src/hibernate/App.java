package hibernate;

import hibernate.shop.*;
import hibernate.shop.cart.Cart;
import hibernate.shop.cart.CartDetail;
import hibernate.shop.cart.CartRepository;
import hibernate.shop.complaint.ComplaintStatus;
import hibernate.shop.complaint.OrderComplaint;
import hibernate.shop.complaint.OrderComplaintRepository;
import hibernate.shop.order.Order;
import hibernate.shop.order.OrderDetail;
import hibernate.shop.order.OrderRepository;
import hibernate.shop.product.Product;
import hibernate.shop.product.ProductRepository;
import hibernate.shop.product.ProductType;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class App {

    public static void main(String[] args) {

        Product mercedesS = new Product("Mercedes S", ProductType.CAR,
                new Price(new BigDecimal(500000), new BigDecimal(600000)));

        Product mercedesA = new Product("Mercedes A", ProductType.CAR,
                new Price(new BigDecimal(100000), new BigDecimal(120000)));

        Product smallCar = new Product("red car", ProductType.TOY,
                new Price(new BigDecimal(30), new BigDecimal(34)));

        ProductRepository.saveProduct(mercedesS);
        ProductRepository.saveProduct(mercedesA);
        ProductRepository.saveProduct(smallCar);

        Optional<Product> product1 = ProductRepository.findOneById(1L);
        Optional<Product> product2 = ProductRepository.findOneById(2L);
        Optional<Product> product3 = ProductRepository.findOneById(3L);

        System.out.println(product1.map(Product::getName).orElse(""));
        System.out.println(product2.map(Product::getName).orElse(""));
        System.out.println(product3.map(Product::getName).orElse(""));

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

        OrderDetail orderDetail = OrderDetail.builder()
                .amount(BigDecimal.ONE)
                .product(product1.get())
                .price(product1.get().getPrice())
                .build();

        OrderDetail orderDetail2 = OrderDetail.builder()
                .amount(BigDecimal.TEN)
                .product(product3.get())
                .price(product3.get().getPrice())
                .build();

        OrderDetail orderDetail3 = OrderDetail.builder()
                .amount(BigDecimal.ONE)
                .product(product3.get())
                .price(product3.get().getPrice())
                .build();

        Order order = Order.builder()
                .userEmail("test@sda.pl")
                .totalGross(new BigDecimal(13))
                .totalNet(new BigDecimal(12))
                .orderDetailSet(new HashSet<>())
                .build();

        order.addOrderDetail(orderDetail);
        order.addOrderDetail(orderDetail2);
        order.addOrderDetail(orderDetail3);

        OrderRepository.saveOrder(order);

        ProductRepository.findAllNative()
                .forEach(p -> System.out.println("find all product from native: " + p.getName()));

        OrderRepository.findAllOrderWithProduct(1L)
                .forEach(p -> System.out.println("find order with product: " + p.getId()));

        List<Order> allOrderWithProduct = OrderRepository.findAll();

        for (Order o : allOrderWithProduct) {
            o.getOrderDetailSet().stream().forEach(od ->
                    System.out.println("order id " + od.getId() + " " + od.getProduct().getName()));
        }

        Set<Order> orderSet = new HashSet<>();
        orderSet.add(order);

        OrderComplaint orderComplaint = OrderComplaint.builder()
                .complaintStatus(ComplaintStatus.PENDING)
                .message("New complaint from John")
                .orderSet(orderSet).build();

        OrderComplaintRepository.saveOrderComplaint(orderComplaint);

        ProductRepository.findAllByNameLikeWithCriteria("merc")
                .forEach(p -> System.out.println("find all by merc: " + p.getName()));

        //find All Product where productType = (TOY or CAR) and is cheaper than parameter

        ProductRepository.findAllToyOrCarCheaperThan(new BigDecimal(600001))
                .forEach(p -> System.out.println("find all car or toy with price less than 600001 "
                        + p.getName() + " " + p.getPrice().getGrossPrice()));

        ProductRepository.findAllToyOrCarCheaperThanLikePredicate("merc")
                .forEach(p -> System.out.println("findAllToyOrCarWithPriceLessThan: " + p.getName()));

        CartDetail cartDetail = CartDetail.builder()
                .amount(BigDecimal.ONE)
                .product(product3.get())
                .price(product3.get().getPrice())
                .build();

        Cart cart = Cart.builder()
                .email("jan.kowalski@gmail.com")
                .cartDetailSet(new HashSet<>())
                .build();

        cart.addCartDetail(cartDetail);

        CartRepository.saveCart(cart);
    }
}
