package com.atguigu.java.ai.langchain4j.store;

import com.atguigu.java.ai.langchain4j.bean.ChatMessages;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MongoChatMemoryStore implements ChatMemoryStore {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Map<Object, List<ChatMessage>> localMemoryStore = new ConcurrentHashMap<>();

    @Override
    public List<ChatMessage> getMessages(Object memoryId) {
        try {
            Criteria criteria = Criteria.where("memoryId").is(memoryId);
            Query query = new Query(criteria);

            ChatMessages chatMessages = mongoTemplate.findOne(query, ChatMessages.class);
            if (chatMessages == null) {
                return new LinkedList<>(localMemoryStore.getOrDefault(memoryId, new LinkedList<>()));
            }
            return ChatMessageDeserializer.messagesFromJson(chatMessages.getContent());
        } catch (Exception e) {
            return new LinkedList<>(localMemoryStore.getOrDefault(memoryId, new LinkedList<>()));
        }
    }

    @Override
    public void updateMessages(Object memoryId, List<ChatMessage> messages) {
        localMemoryStore.put(memoryId, new ArrayList<>(messages));
        try {
            Criteria criteria = Criteria.where("memoryId").is(memoryId);
            Query query = new Query(criteria);
            Update update = new Update();

            update.set("content", ChatMessageSerializer.messagesToJson(messages));
            mongoTemplate.upsert(query, update, ChatMessages.class);
        } catch (Exception e) {
            // Keep local memory when MongoDB is unavailable.
        }
    }

    @Override
    public void deleteMessages(Object memoryId) {
        localMemoryStore.remove(memoryId);
        try {
            Criteria criteria = Criteria.where("memoryId").is(memoryId);
            Query query = new Query(criteria);
            mongoTemplate.remove(query, ChatMessages.class);
        } catch (Exception e) {
            // Ignore local MongoDB connectivity issues.
        }
    }
}
