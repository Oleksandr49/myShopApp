package shopApp.unitTests.services;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        CartServiceTests.class,
        CustomerServiceTests.class,
        DetailsServiceTests.class,
        ItemServiceTests.class,
        JwtServiceTests.class,
        OrderServiceTests.class,
        PayPallClientTests.class,
        ProductServiceTests.class
})

public class UnitTestsSuit {
}
