import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private String name;
    private IStockMarket market;
    private List<Stock> stocks = new ArrayList<>();

    public StocksPortfolio(IStockMarket market) {
        this.market = market;
    }

    public IStockMarket getMarketService() {
        return this.market;
    }

    public void setMarketService(IStockMarket market) {
        this.market = market;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTotalValue() {
        double value = 0.0;
        for (Stock stock: this.stocks) {
            value += market.getPrice(stock.getName()) * stock.getQuantity();
        }
        return value;
    }

    public void addStock(Stock stock) {
        this.stocks.add(stock);
    }
}
