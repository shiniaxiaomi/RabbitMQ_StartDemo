package four.route;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EmitLogDirect {

  private static final String EXCHANGE_NAME = "direct_logs";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    try (Connection connection = factory.newConnection();
         Channel channel = connection.createChannel()) {

        channel.exchangeDeclare(EXCHANGE_NAME, "direct");//设置交换机的类型为direct

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            String message = reader.readLine();
            String[] arr = message.split(" ");
            if("error".equals(arr[0])){// 如果是error的消息，则将routing key设置为"error"
                channel.basicPublish(EXCHANGE_NAME, "error", null, arr[1].getBytes("UTF-8"));
            }else if("info".equals(arr[0])){// 如果是info的消息，则将routing key设置为"info"
                channel.basicPublish(EXCHANGE_NAME, "info", null, arr[1].getBytes("UTF-8"));
            }

            System.out.println(" [x] Sent '" + arr[0] + "':'" + arr[1] + "'");
        }
    }
  }
}