package com.example;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.OrderSide;
import com.binance.api.client.domain.OrderType;
import com.binance.api.client.domain.TimeInForce;
import com.binance.api.client.domain.account.NewOrder;
import com.binance.api.client.domain.account.NewOrderResponse;
import com.binance.api.client.domain.account.NewOrderResponseType;

/**
 * Class name : BianaceContractExample
 * Description :
 *
 * @Author : Gary
 * @Create : 2023/11/12 3:25 PM
 * @Version : 1.0
 */
public class BianaceContractExample {
    public static void main(String[] args) {
        try {
            // 替换成您的API密钥和密钥
            AppConfig AppConfig = new AppConfig();
            String apiKey = AppConfig.getApiKey();
            String apiSecret = AppConfig.getApiSecret();

            // 创建 Binance API 客户端
            BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(apiKey, apiSecret);
            BinanceApiRestClient client = factory.newRestClient();

            // 設置合約交易對和下單參數
            String symbol = "ETHUSDT";  // 合約交易對，例如BTCUSD_PERP代表BTC合約交易對
            OrderSide orderSide = OrderSide.BUY; // 買入
            OrderType orderType = OrderType.MARKET; // 市價訂單
            TimeInForce timeInForce = TimeInForce.GTC; // Good 'Til Canceled

            // 創建市價買入訂單
            String quantity = "0.2";   // 買入數量
            NewOrderResponse newOrderResponse = client.newOrder(NewOrder.marketBuy(symbol, quantity)
                    .newOrderRespType(NewOrderResponseType.FULL) // 設置響應類型以獲取詳細的訂單信息
                    .side(orderSide) // 設置買入方向
                    .type(orderType) // 設置訂單類型
                    .timeInForce(timeInForce) // 設置有效期
                    .recvWindow(5000L));

            System.out.println("New Order Response: " + newOrderResponse);

            // 检查订单是否成功
            if (newOrderResponse != null) {
                System.out.println("Order successfully placed!");
                System.out.println("Order ID: " + newOrderResponse.getOrderId());
            } else {
                System.out.println("Order failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
