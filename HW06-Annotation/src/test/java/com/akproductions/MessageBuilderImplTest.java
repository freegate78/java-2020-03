package com.akproductions;

import com.akproductions.app.MessageBuilder;
import com.akproductions.app.MessageBuilderImpl;
import com.akproductions.app.MessageTemplateProvider;
import com.akproductions.app.TemplateNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;



@DisplayName("надо ")
public class MessageBuilderImplTest {


    public static final String TEMPLATE_NAME = "AnyTemplate";
    public static final String TEMPLATE_TEXT = "Hi!\n %s \n With best regards, %s";
    public static final String MESSAGE_TEXT = "How you doing?";
    public static final String SIGN = "Vasya";
    private MessageTemplateProvider provider;
    private MessageBuilder messageBuilder;
    @BeforeEach
    void setUp() {
        provider = mock(MessageTemplateProvider.class);
        messageBuilder = new MessageBuilderImpl(provider);
    }



    @DisplayName("buildMessage1 - проверить что корректно работает логика метода buildMessage")
    @Test
    void buildMessage1() {
        when(provider.getMessageTemplate(TEMPLATE_NAME)).thenReturn(TEMPLATE_TEXT);
        String actualMessage = messageBuilder.buildMessage(TEMPLATE_NAME,
                MESSAGE_TEXT, SIGN);
        assertEquals(String.format(TEMPLATE_TEXT, MESSAGE_TEXT, SIGN),
                actualMessage);
    }

    @DisplayName(" buildMessage2 - проверить что хотя бы раз вызывается метод getMessageTemplate")
    @Test
    void buildMessage2() {
        when(provider.getMessageTemplate(TEMPLATE_NAME)).thenReturn(TEMPLATE_TEXT);
        messageBuilder.buildMessage(TEMPLATE_NAME, MESSAGE_TEXT, SIGN);
        verify(provider, atLeastOnce()).getMessageTemplate(TEMPLATE_NAME);
    }

    @DisplayName(" buildMessage3 - проверить генерацию исключения TemplateNotFoundException")
    @Test
    void buildMessage3() {
        //when(provider.getMessageTemplate(TEMPLATE_NAME)).thenReturn(TEMPLATE_TEXT);
        assertThrows(TemplateNotFoundException.class,
                () -> messageBuilder.buildMessage(TEMPLATE_NAME, MESSAGE_TEXT, SIGN));
    }
}
