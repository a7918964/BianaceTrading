package com.example;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.OrderSide;
import com.binance.api.client.domain.OrderType;
import com.binance.api.client.domain.TimeInForce;
import com.binance.api.client.domain.account.NewOrder;
import com.binance.api.client.domain.account.NewOrderResponse;

public class BinanceTickerExample {

    public static void main(String[] args) {
        try {
            // 印出 API 密鑰
            AppConfig AppConfig = new AppConfig();
            System.out.println("API Key: " + AppConfig.getApiKey());
            System.out.println("API Secret: " + AppConfig.getApiSecret());

            // 設置獲取行情的URL，這裡以ETH/USDT為例
            URL url = new URL("https://api.binance.com/api/v3/ticker/price?symbol=ETHUSDT");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            String symbol;

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 使用 Gson 解析 JSON
                Gson gson = new Gson();
                JsonObject tickerJson = gson.fromJson(response.toString(), JsonObject.class);

                // 提取價格信息
                symbol = tickerJson.get("symbol").getAsString();
                double price = tickerJson.get("price").getAsDouble();

                // 輸出價格信息
                System.out.println("Symbol: " + symbol);
                System.out.println("Price: " + price);


                // 判斷條件，如果價格大於 2000，執行市價買入
                if (price > 2000) {
                    // 在此添加下單邏輯
                    // 创建 Binance API 客户端
                    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(AppConfig.getApiKey(), AppConfig.getApiSecret());
                    BinanceApiRestClient client = factory.newRestClient();

                    // 设置交易对和买入数量
                    symbol = "ETHUSDT";  // 交易对
                    String quantity = "1.0";   // 买入数量

                    // 创建市价买入订单

                    NewOrderResponse newOrderResponse = client.newOrder(NewOrder.marketBuy(symbol, quantity).recvWindow(5000L));
                    System.out.println("New Order Response: " + newOrderResponse);
                    JsonObject orderResult = executeMarketBuyOrder("ETHUSDT", 1.0); // 1.0 是下單數量
                    handleOrderResult(orderResult);
                    System.out.println("Executing market buy order!");

                } else {
                    System.out.println("Price is not above 2000, no action taken.");
                }
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 執行市價買入
    private static JsonObject executeMarketBuyOrder(String symbol, double quantity) {
        // 在這裡實現下單邏輯，向 Binance API 發送下單請求
        // 返回包含下單結果的 JSON 對象
        // 真實應用中，您需要使用 Binance API 實現下單邏輯
        JsonObject orderResult = new JsonObject();
        orderResult.addProperty("symbol", symbol);
        orderResult.addProperty("orderId", 123456); // 假設下單成功，orderId 為下單的唯一標識
        orderResult.addProperty("status", "FILLED");
        orderResult.addProperty("transactTime", System.currentTimeMillis());
        return orderResult;
    }

    // 處理下單結果
    private static void handleOrderResult(JsonObject orderResult) {
        // 提取下單結果
        String symbol = orderResult.get("symbol").getAsString();
        long orderId = orderResult.get("orderId").getAsLong();
        String status = orderResult.get("status").getAsString();
        long transactTime = orderResult.get("transactTime").getAsLong();

        // 輸出下單結果
        System.out.println("Order Result:");
        System.out.println("Symbol: " + symbol);
        System.out.println("Order ID: " + orderId);
        System.out.println("Status: " + status);
        System.out.println("Transaction Time: " + transactTime);

        // 在這裡可以添加進一步處理下單結果的邏輯
    }
}
