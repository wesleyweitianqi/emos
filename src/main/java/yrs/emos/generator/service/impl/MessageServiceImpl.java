package yrs.emos.generator.service.impl;

import org.apache.shiro.crypto.hash.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yrs.emos.generator.domain.MessageEntity;
import yrs.emos.generator.domain.MessageRefEntity;
import yrs.emos.generator.mapper.MessageMapper;
import yrs.emos.generator.mapper.MessageRefMapper;
import yrs.emos.generator.service.MessageService;

import java.util.HashMap;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private MessageRefMapper messageRefMapper;

    @Override
    public String insertMessage(MessageEntity entity) {
        String id = messageMapper.insert(entity);
        return id;
    }

    @Override
    public List<HashMap> searchMessageByPage(int userId, long start, int length) {
        List<HashMap> list = (List<HashMap>) messageMapper.searchMessageByPage(userId, start, length);
        return list;
    }

    @Override
    public HashMap searchMessageById(String id) {
        HashMap map = messageMapper.searchMessageById(id);
        return map;
    }

    @Override
    public String insertRef(MessageRefEntity entity) {
        String id=messageRefMapper.insert(entity);
        return id;
    }

    @Override
    public long searchUnreadCount(int userId) {
        long count=messageRefMapper.searchUnreadCount(userId);
        return count;
    }

    @Override
    public long searchLastCount(int userId) {
        long count=messageRefMapper.searchLastCount(userId);
        return count;
    }

    @Override
    public long updateUnreadMessage(String id) {
        long rows=messageRefMapper.updateUnreadMessage(id);
        return rows;
    }

    @Override
    public long deleteMessageRefById(String id) {
        long rows=messageRefMapper.deleteMessageRefById(id);
        return rows;
    }

    @Override
    public long deleteUserMessageRef(int userId) {
        long rows=messageRefMapper.deleteUserMessageRef(userId);
        return rows;
    }
}
