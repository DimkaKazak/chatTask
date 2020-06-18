package sql;

import data.Message;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import sql.service.MessageService;

import java.util.List;
import java.util.UUID;

public class MessageTest {

    private static final Logger LOGGER = Logger.getLogger(MessageTest.class);

    @Test(dataProvider = "setUpSpace")
    public void testMessageGetById(String id, String expectedText) {

        MessageService messageService = new MessageService();
        messageService.createMessage(new Message(id, expectedText, "1"));
        Message message = messageService.getMessageById(id);

        Assert.assertEquals(message.getMsg(), expectedText);

        messageService.deleteMessage(message);

    }

    @Test
    public void testMessageGetAll() {

        MessageService messageService = new MessageService();
        Message message = new Message(UUID.randomUUID().toString(), "testMessage", "1");
        messageService.createMessage(message);
        List<Message> messages = messageService.getAllMessages();
        messages.forEach(currMessage -> {
            LOGGER.info(message.getMsg());
        });

        Assert.assertFalse(messages.isEmpty());

        messageService.deleteMessage(message);

    }

    @Test
    public void testMessageGetHistory() {

        MessageService messageService = new MessageService();
        Message message = new Message(UUID.randomUUID().toString(), "testMessage", "1");
        messageService.createMessage(message);
        List<Message> messages = messageService.getAllMessages();
        messages.forEach(currMessage -> {
            LOGGER.info(message.getMsg());
        });

        Assert.assertFalse(messages.isEmpty());
        Assert.assertTrue(messages.size() <= 20);

        messageService.deleteMessage(message);

    }

    @Test
    public void testMessageCreate() {

        MessageService messageService = new MessageService();
        Message message = new Message(UUID.randomUUID().toString(), "testMessage", "1");
        messageService.createMessage(message);

        Message messageFromDB = messageService.getMessageById(message.getId());

        Assert.assertEquals(message.getId(), messageFromDB.getId());
        Assert.assertEquals(message.getMsg(), messageFromDB.getMsg());
        Assert.assertEquals(message.getClientId(), messageFromDB.getClientId());

        messageService.deleteMessage(message);

    }

    @Test
    public void testMessageUpdate() {

        MessageService messageService = new MessageService();
        Message prevMessage = new Message(UUID.randomUUID().toString(), "testMessage", "1");
        messageService.createMessage(prevMessage);

        Message newMessage = new Message(prevMessage.getId(), "newTestMessage", "1");
        messageService.updateMessage(newMessage);

        Message newMessageFromDB = messageService.getMessageById(prevMessage.getId());

        Assert.assertEquals(newMessage.getId(), newMessageFromDB.getId());
        Assert.assertEquals(newMessage.getMsg(), newMessageFromDB.getMsg());
        Assert.assertEquals(newMessage.getClientId(), newMessageFromDB.getClientId());

        messageService.deleteMessage(newMessage);
    }

    @Test
    public void testMessageDelete() {

        MessageService messageService = new MessageService();
        Message message = new Message(UUID.randomUUID().toString(), "testMessage", "1");
        messageService.createMessage(message);
        messageService.deleteMessage(message);

        Assert.assertNull(messageService.getMessageById(message.getId()));
    }

    @DataProvider
    public Object[][] setUpSpace(){

        Object[][] objects = new Object[10][2];
        for (int i = 0; i < 10; i++){
            objects[i][0] = (i + 1) + "";
            objects[i][1] = "testMessage" + (i + 1);
        }
        return objects;
    }

}

