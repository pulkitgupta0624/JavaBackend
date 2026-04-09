import Loose.EmailNotificationService;
import Loose.NotificationService;
import Tight.UserService;
import Loose.SMSNotificationService;

public class AppMain {
    public static void main(String[] args) {

        // Tight
        UserService userService = new UserService();
        userService.notifyUser("Order Placed!");

        // Loose
        NotificationService emailService = new EmailNotificationService();
        NotificationService smsService = new SMSNotificationService();
        Loose.UserService userServiceLoose = new Loose.UserService(smsService);
        userServiceLoose.notifyUser("Order Processed!");

        /*
        Constructor Injection – dependency is provided via constructor
        Setter Injection – dependency is provided via setter method
        Field Injection – dependency is assigned directly to a field
         */
        Loose.UserService userServiceLooseSetter = new Loose.UserService();
        userServiceLooseSetter.setNotificationService(emailService);
        userServiceLooseSetter.notificationService = smsService;

    }
}
