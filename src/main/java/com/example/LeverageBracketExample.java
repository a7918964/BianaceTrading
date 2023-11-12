package com.example;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.general.ExchangeInfo;
import io.github.binance.api.client.BinanceApiClientFactory;
import io.github.binance.api.client.BinanceApiRestClient;
import io.github.binance.api.client.constant.BinanceApiConstants;
import io.github.binance.api.client.domain.market.ExchangeInfo;

/**
 * Class name : LeverageBracketExample
 * Description :
 *
 * @Author : Gary
 * @Create : 2023/11/12 4:00 PM
 * @Version : 1.0
 */

public class LeverageBracketExample {
    public static void main(String[] args) {
        // 替換成您的API密鑰和密碼
        String apiKey = "YOUR_API_KEY";
        String apiSecret = "YOUR_API_SECRET";

        // 創建 Binance API 客戶端
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiKey, apiSecret);
        BinanceApiRestClient client = factory.newRestClient();

        // 設置要查詢的交易對
        String symbol = "ETHUSDT"; // 您要查詢的交易對

        // 查詢特定交易對的杠杆設定
        ExchangeInfo exchangeInfo = client.getExchangeInfo(symbol);

        // 打印槓桿設定
        System.out.println("Leverage Settings for " + symbol);
        for (ExchangeInfo.SymbolInfo symbolInfo : exchangeInfo.getSymbols()) {
            if (symbolInfo.getSymbol().equals(symbol)) {
                System.out.println("Symbol: " + symbolInfo.getSymbol());
                System.out.println("Leverage Brackets:");
                for (ExchangeInfo.SymbolInfo.LeverageBracket bracket : symbolInfo.getLeverageBrackets()) {
                    System.out.println("  Bracket: " + bracket.getBracket());
                    System.out.println("  Initial Leverage: " + bracket.getInitialLeverage());
                    System.out.println("  Notional Cap: " + bracket.getNotionalCap());
                    System.out.println("  Notional Floor: " + bracket.getNotionalFloor());
                    System.out.println("  Maint Margin Ratio: " + bracket.getMaintMarginRatio());
                    System.out.println("  Cum: " + bracket.getCum());
                    System.out.println();
                }
                break;
            }
        }
    }
}