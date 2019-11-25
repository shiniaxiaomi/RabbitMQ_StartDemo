package two.distribute_messages;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//发送多条消息
public class NewTask {

    private final static String TASK_QUEUE_NAME = "task_queue";

    public static void main(String[] args) throws Exception {


        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String s = reader.readLine();

                channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
                String message = String.join(" ", s);
                channel.basicPublish("", TASK_QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,
                        message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");

            }

        }

    }


}
