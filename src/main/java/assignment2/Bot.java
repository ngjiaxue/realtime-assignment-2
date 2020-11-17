package assignment2;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

/**
 * This is a class for Telegram Bot activities which extends TelegramLongPollingBot and Thread will be start here.
 *
 * @author Ng Jia Xue
 */
public class Bot extends TelegramLongPollingBot {

    /**
     * This method is to get the Bot Username from Telegram.
     *
     * @return The username of the Bot from Telegram.
     */
    @Override
    public String getBotUsername() {
        return "s262151_bot";
    }

    /**
     * This method is to get the Bot Token from Telegram.
     *
     * @return The token of the Bot from Telegram.
     */
    @Override
    public String getBotToken() {
        return "1408372533:AAFNpBYUX_iSU_FNtQd_69R_Mrxq04kMT_U";
    }

    /**
     * This method is communication between users and Telegram Bot.
     *
     * @param update The message sent by the users.
     */
    @Override
    public void onUpdateReceived(Update update) {
        new Thread(() -> {

            String[] studentInfo = {"", ""};
            int matricNo = 0;
            try {
                matricNo = Integer.parseInt(update.getMessage().getText());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (update.getMessage().getText().equals("/start")) {
                sendMessage("Welcome " + update.getMessage().getFrom().getFirstName() + " " + update.getMessage().getFrom().getLastName() + "\n Please insert matric number.", update);
            } else if (matricNo >= 200000 && matricNo <= 300000) {
                ArrayList<Data> studentList = Methods.scrapeFromSource("https://github.com/STIW3054-A201/Main-Data/wiki/List_of_Student", "div.markdown-body tr", 1);
                for (Data data : studentList) {
                    if (update.getMessage().getText().equals(data.getMatric())) {
                        studentInfo[0] = data.getMatric();
                        studentInfo[1] = data.getName();
                        break;
                    }
                }
                if (!studentInfo[0].equals("") && !studentInfo[1].equals("")) {
                    String[] temp = Methods.checkFrequency(studentInfo[1].replace(" ", "").toUpperCase());
                    sendMessage("Total student(s): " + studentList.size() + "\n" + studentInfo[0] + " --> " + studentInfo[1] + "\nLength: " + studentInfo[1].length() + "\n" + (temp[0].equals("") ? "No letter in the name that occur more than 3 times." : temp[0]), update);
                } else {
                    sendMessage("No record found.", update);
                }

            } else {
                sendMessage("Please insert the matric number correctly.", update);

            }
        }).start();
    }

    /**
     * This method is to send back the message to Telegram.
     * @param string The message to be send back to Telegram.
     * @param update The incoming update from Telegram.
     */
    private void sendMessage(String string, Update update) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(update.getMessage().getChatId()));
        message.setText(string);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

