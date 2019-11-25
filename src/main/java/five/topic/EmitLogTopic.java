package five.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class EmitLogTopic {

  private static final String EXCHANGE_NAME = "topic_logs";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    try (Connection connection = factory.newConnection();
         Channel channel = connection.createChannel()) {

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true){
            String message = reader.readLine();
            String[] arr = message.split(" ");//通过空格分隔routing_key和消息
            channel.basicPublish(EXCHANGE_NAME, arr[0], null, arr[1].getBytes("UTF-8"));//指定交换机，指定routing_key，指定消息
            System.out.println(" [x] Sent '" + arr[0] + "':'" + arr[1] + "'");
        }
    }
  }
}