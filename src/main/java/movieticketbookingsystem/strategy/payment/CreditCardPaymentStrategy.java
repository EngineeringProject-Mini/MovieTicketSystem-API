package movieticketbookingsystem.strategy.payment;

import movieticketbookingsystem.entities.Payment;
import movieticketbookingsystem.enums.PaymentStatus;

import java.util.UUID;

public class CreditCardPaymentStrategy implements PaymentStrategy {
    public String cardNumber;
    public String cvv;

    public CreditCardPaymentStrategy(String cardNumber, String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    public String getCvv() {
        return cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public Payment pay(double amount) {
        System.out.printf("Processing credit card payment of $%.2f%n", amount);
        // Simulate payment gateway interaction
        boolean paymentSuccess = Math.random() > 0.05; // 95% success rate
        return new Payment(
                amount,
                paymentSuccess ? PaymentStatus.SUCCESS : PaymentStatus.FAILURE,
                "TXN_" + UUID.randomUUID()
        );
    }
}
