import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StocksPortfolioTest {

    @Mock
    private IStockMarket market;
    @InjectMocks
    private StocksPortfolio portfolio;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getTotalValue() {
        // 1.Prepare a mock to substitute the remote service (@Mock annotation)
        IStockMarket market = mock(IStockMarket.class);

        // 2.Create an instance of the subject under test (SuT) and use the mock to set the (remote) service instance.
        StocksPortfolio portfolio = new StocksPortfolio(market);

        // 3.Load the mock with the proper expectations (when...thenReturn)
        when(market.getPrice("EBAY")).thenReturn(3.0);
        when(market.getPrice("MSFT")).thenReturn(1.5);

        // 4.Execute the test (use the service in the SuT)
        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("MSFT", 2));
        double result = portfolio.getTotalValue();

        // 5.Verify the result (assert) and the use of the mock (verify)
        assertEquals(9.0, result);
        assertThat(result, is(9.0));
        verify( market, times(2)).getPrice(anyString());
    }

    @Test
    void getTotalValueWithAnnot() {
        when(market.getPrice("EBAY")).thenReturn(3.0);
        when(market.getPrice("MSFT")).thenReturn(1.5);

        portfolio.addStock(new Stock("EBAY", 2));
        portfolio.addStock(new Stock("MSFT", 2));
        double result = portfolio.getTotalValue();

        assertEquals(9.0, result);
        assertThat(result, is(9.0));
        verify( market, times(2)).getPrice(anyString());
    }

}