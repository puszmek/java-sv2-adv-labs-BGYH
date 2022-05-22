package webshop;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WebshopServiceTest {

    WebshopService webshopService;

    @BeforeEach
    void setUp() {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/webshop_test?useUnicode=true");
            dataSource.setUserName("root");
//            dataSource.setPassword("");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect", e);
        }

        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.clean();
        flyway.migrate();

        UserDao userDao = new UserDao(dataSource);
        OrderDao orderDao = new OrderDao(dataSource);
        ProductDao productDao = new ProductDao(dataSource);

        webshopService = new WebshopService(userDao, orderDao, productDao);
        webshopService.loadProductsFromFile(Path.of("src/test/resources/products.csv"));
        webshopService.registerUser("jill_doe", "2345", "jill@doe.com", "12 Example Way");
        webshopService.registerUser("jack_doe", "1234", "jack@doe.com", "10 Example Way");
        webshopService.logIn("jack_doe", "1234");
    }

    @Test
    void testLogIn() {
        assertTrue(webshopService.isLoggedIn());
        assertEquals(2, webshopService.getCurrentUserId());
    }

    @Test
    void testLogInWithInvalidCredentials() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> webshopService.logIn("jack_doe_", "1234"));
        assertEquals("Invalid credentials", e.getMessage());
        e = assertThrows(IllegalArgumentException.class, () -> webshopService.logIn("jack_doe", "12345"));
        assertEquals("Invalid credentials", e.getMessage());
    }

    @Test
    void testAddProductToCart() {
        webshopService.addProductToCart(2, 5);
        assertEquals(1, webshopService.getCart().size());
        assertEquals(5, webshopService.getCart().get(2L));
        webshopService.addProductToCart(3, 10);
        assertEquals(2, webshopService.getCart().size());
        assertEquals(10, webshopService.getCart().get(3L));
        webshopService.addProductToCart(3, 2);
        assertEquals(2, webshopService.getCart().size());
        assertEquals(12, webshopService.getCart().get(3L));
    }

    @Test
    void testRemoveProductFromCart() {
        webshopService.addProductToCart(2, 5);
        webshopService.addProductToCart(3, 10);
        assertEquals(2, webshopService.getCart().size());
        webshopService.removeProductFromCart(2);
        assertEquals(1, webshopService.getCart().size());
        webshopService.removeProductFromCart(3);
        assertEquals(0, webshopService.getCart().size());
    }

    @Test
    void testPlaceOrder() {
        assertEquals(0, webshopService.listAllOrders().size());
        webshopService.addProductToCart(2, 5);
        webshopService.addProductToCart(3, 10);
        webshopService.placeOrder();
        LocalDateTime now = LocalDateTime.now();
        assertEquals(0, webshopService.getCart().size());
        List<Order> orders = webshopService.listAllOrders();
        assertEquals(2, orders.size());
        assertEquals(1, orders.get(0).getId());
        assertEquals(2, orders.get(0).getProductId());
        assertEquals(5, orders.get(0).getProductAmount());
        assertTrue(orders.get(0).getOrderDate().isAfter(now.minusMinutes(1)) && orders.get(0).getOrderDate().isBefore(now.plusMinutes(1)));
        assertEquals(2, orders.get(1).getId());
        assertEquals(3, orders.get(1).getProductId());
        assertEquals(10, orders.get(1).getProductAmount());
        assertTrue(orders.get(1).getOrderDate().isAfter(now.minusMinutes(1)) && orders.get(1).getOrderDate().isBefore(now.plusMinutes(1)));
    }

    @Test
    void testPlaceOrderWithEmptyCart() {
        Exception e = assertThrows(IllegalStateException.class, () -> webshopService.placeOrder());
        assertEquals("Cart is empty", e.getMessage());
    }
}
