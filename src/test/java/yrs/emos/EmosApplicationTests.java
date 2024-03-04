package yrs.emos;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import com.mongodb.client.model.UnwindOptions;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Hash;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import yrs.emos.generator.domain.MessageEntity;
import yrs.emos.generator.domain.MessageRefEntity;
import yrs.emos.generator.domain.MessageWithRefs;
import yrs.emos.generator.service.MessageService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@Slf4j
class EmosApplicationTests {
    @Autowired
    private MessageService messageService;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    void contextLoads() {
        for(int i = 0; i < 100; i++){
            MessageEntity entity = new MessageEntity();
            entity.setUuid(IdUtil.simpleUUID());
            entity.setSenderId(0);
            entity.setSenderName("system message");
            entity.setMsg("this is "+ i + " message");
            entity.setSendTime(new Date());
            String id = messageService.insertMessage(entity);

            MessageRefEntity ref = new MessageRefEntity();
            ref.setMessageId(id);
            ref.setReceiverId("51");
            ref.setLastFlag(true);
            ref.setReadFlag(false);
            messageService.insertRef(ref);

        }
    }

    @Test
    void mongoDBTest(){
        try {
            // Access a collection using MongoTemplate
            String collectionName = "message_ref";
            long count = mongoTemplate.getCollection(collectionName).countDocuments();

            if (count > 0) {
                System.out.println("Connection to MongoDB successful. Found " + count + " documents in collection.");
            } else {
                System.out.println("Failed to retrieve data from MongoDB collection.");
            }
        } catch (Exception e) {
            System.err.println("Failed to connect to MongoDB: " + e.getMessage());
            e.printStackTrace();

        }
    }

    @Test
    void mongoTemplateTest(){
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.lookup("message_ref", "_id", "messageId", "refs")
        );
        AggregationResults results = mongoTemplate.aggregate(aggregation, "message", HashMap.class);
        List<HashMap> list = results.getMappedResults();
        System.out.println(list.get(0).toString());







    }

}
