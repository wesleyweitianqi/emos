package yrs.emos.task;

import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import yrs.emos.exception.EmosException;
import yrs.emos.generator.domain.MessageEntity;
import yrs.emos.generator.domain.MessageRefEntity;
import yrs.emos.generator.service.MessageService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class MessageTask {
    @Autowired
    private ConnectionFactory factory;
    @Autowired
    private MessageService messageService;

    public void send(String topic, MessageEntity entity){
        String id = messageService.insertMessage(entity);
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
        ){
            channel.queueDeclare(topic, true, false, false, null);
            HashMap map = new HashMap();
            map.put("message", id);
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().headers(map).build();
            channel.basicPublish("", topic,properties, entity.getMsg().getBytes());
            log.debug("message sent");
        }catch(Exception e){
            e.printStackTrace();
            log.error(String.valueOf(e));
        }
    }

    @Async
    public void sendAsync(String topic,  MessageEntity entity){
        send(topic, entity);
    }

    public Integer receive(String topic){
        int i = 0;
        try( Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()
        ){
            channel.queueDeclare(topic, true, false, false, null);
            while (true){
                GetResponse response = channel.basicGet(topic, false);
                if(response != null){
                    AMQP.BasicProperties properties = response.getProps();
                    Map<String, Object> map = properties.getHeaders();
                    String messageId = map.get("message").toString();
                    byte[] body = response.getBody();
                    String message = new String(body);
                    log.debug("message from rabbitMQ:+++++" + message);

                    MessageRefEntity entity = new MessageRefEntity();
                    entity.setMessageId(messageId);
                    entity.setReceiverId(String.valueOf(topic));
                    entity.setReadFlag(false);
                    entity.setLastFlag(true);
                    messageService.insertRef(entity);
                    long deliveryTag = response.getEnvelope().getDeliveryTag();
                    channel.basicAck(deliveryTag, false);
                    i++;
                }else {
                    break;
                }
            }
        }catch(Exception e){
            log.error(String.valueOf(e));
            throw new EmosException("failed to receive rabbitMQ message");
        }
        return i;
    }

    @Async
    public int receiveAsync(String topic){
        return receive(topic);
    }

    public void deleteQueue(String topic){
        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
        ){
            channel.queueDelete(topic);
            log.debug("deleted the " + topic + " in queue");
        }catch (Exception e){
            log.error(String.valueOf(e));
            throw new EmosException("failed to delete " + topic + " in queue");
        }
    }

    @Async
    public void delete(String topic){
        deleteQueue(topic);
    }
}
