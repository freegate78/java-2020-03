package com.akproductions.app;

public class MessageBuilderImpl implements MessageBuilder {

  //переменные для тестов reflection
  int var1;
  int var2;
  int getMessageTemplateCalled;

  String getValue(){
    return "var1="+ var1+ ", var2="+ var2;
  }

  private MessageBuilder messageBuilder;
  private final MessageTemplateProvider templateProvider;

  public MessageBuilderImpl() {
    templateProvider=new MessageTemplateProvider();
    getMessageTemplateCalled=0;
  }

  public MessageBuilderImpl(MessageTemplateProvider templateProvider) {
    this.templateProvider = templateProvider;
  }

  @Override
  public String buildMessage(String templateName, String text, String signature) {
    String messageTemplate = templateProvider.getMessageTemplate(templateName);
    if (messageTemplate == null || messageTemplate.isEmpty()) {
      throw new TemplateNotFoundException();
    }
   getMessageTemplateCalled++;
  return String.format(messageTemplate, text, signature);
  }
}
