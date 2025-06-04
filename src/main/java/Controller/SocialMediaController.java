package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import io.javalin.Javalin;
import io.javalin.http.Context;

import static org.mockito.ArgumentMatchers.nullable;

import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController() {
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }


    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::register);
        app.post("/login", this::login);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{messages_id}", this::updateMessageById);
        app.get("/accounts/{account_id}/messages", this::getMessagesByUser);

        //app.get("example-endpoint", this::exampleHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    //private void exampleHandler(Context context) {
    //    context.json("sample text");
    //}

    private void register(Context context) {
        Account account = context.bodyAsClass(Account.class);
        if (account.getUsername().isBlank() || account.getPassword().length() < 4 || account.getPassword() == null || account.getUsername() == null) {
            context.status(400);
            context.result("");
            return;
        }

        Account result = accountService.registerAccount(account);
        if (result == null) {
            context.status(400);
            context.result("");
        }
        else {
            context.json(result);
            context.status(200);
        }
    }

    private void login(Context context) {
        Account account = context.bodyAsClass(Account.class);
        Account result = accountService.login(account);

        if (result == null)
            context.status(401);
        else {
            context.json(result);
            context.status(200);
        }
    }

    private void createMessage(Context context) {
        Message message = context.bodyAsClass(Message.class);

        if (message.getMessage_text().isBlank() || message.getMessage_text().length() > 255 || message.getPosted_by() <= 0) {
            context.status(400);
            return;
        }

        Message result = messageService.createMessage(message);
        
        if (result == null)
            context.status(400);
        else {
            context.json(result);
            context.status(200);
        }
    }

    private void getAllMessages(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages);
        context.status(200);
    }

    private void getMessageById(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);

        if (message == null)
            context.status(200);
        else {
            context.json(message);
            context.status(200);
        }
    }

    private void deleteMessageById(Context context) {
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message deleted = messageService.deleteMessageById(messageId);

        if (deleted == null)
            context.status(200);
        else {
            context.json(deleted);
            context.status(200);
        }
    }

    private void updateMessageById(Context context) {
        int messageId = Integer.parseInt(context.pathParam("messages_id"));
        Message newMessage = context.bodyAsClass(Message.class);

        if (newMessage.getMessage_text().isBlank() || newMessage.getMessage_text().length() > 255) {
            context.status(400);
            return;
        }

        Message updated = messageService.updateMessageById(messageId, newMessage.getMessage_text());
        if (updated == null)
            context.status(400);
        else {
            context.json(updated);
            context.status(200);
        }
    }

    private void getMessagesByUser(Context context) {
        int accountId = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByUser(accountId);
        context.json(messages);
        context.status(200);
    }

    


}