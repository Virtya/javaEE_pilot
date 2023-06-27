package ru.ds.education.currency.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.ds.education.currency.dto.message.ResponseMessageDto;

@Component
public class CurrencyListener {

    private final JmsTemplate jmsTemplate;

    public CurrencyListener(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "response-queue")
    public void getCurrencyRequest(ResponseMessageDto messageDto) {

    }
}
