package com.akproductions;

public class MessageBuilderImpl implements MessageBuilder {

  //переменные для тестов reflection
  int var1;
  int var2;
  int getMessageTemplateCalled;

  //метод для тестов reflection
  String getValue(){
    return "var1="+ var1+ ", var2="+ var2;
  }

  public static final String TEMPLATE_NAME = "AnyTemplate";
  public static final String TEMPLATE_TEXT = "Hi!\n %s \n With best regards, %s";
  public static final String MESSAGE_TEXT = "How you doing?";
  public static final String SIGN = "Vasya";
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

  //метод для тестов reflection
  @Before
private void doBefore()
  {
    System.out.println("Before called!!! var1=" + var1+ ", var2="+ var2);
  }
  //метод для тестов reflection
  @After
  private void doAfter()
  {
    System.out.println("After called!!! var1=" + var1+ ", var2="+ var2);
  }

  //метод для тестов reflection
  @Test
  private void doTest()
  {
    var1 += var2;
    System.out.println("Test called!!! var1=" + var1+ ", var2="+ var2);
  }

  @Test
  @DisplayName(name = "buildMessage1 - проверить что корректно работает логика метода buildMessage")
  void buildMessage1() throws Exception {
    //when(provider.getMessageTemplate(TEMPLATE_NAME)).thenReturn(TEMPLATE_TEXT);
    String actualMessage = buildMessage(TEMPLATE_TEXT,MESSAGE_TEXT, SIGN);
    if (!(String.format(TEMPLATE_TEXT, MESSAGE_TEXT, SIGN).contentEquals(actualMessage)))
    {
      throw new Exception("[FATAL ERROR] buildMessage working incorrect!!!");
    }
  }

  @Test
  @DisplayName(name = " buildMessage2 - проверить что хотя бы раз вызывается метод getMessageTemplate")
  void buildMessage2() throws Exception {
    String actualMessage = buildMessage(TEMPLATE_TEXT, MESSAGE_TEXT, SIGN);
    if (this.getMessageTemplateCalled == 0)
    {
      throw new Exception("[FATAL ERROR] getMessageTemplate not invoked during this test!!!");
    }
  }

  @Test
  @DisplayName(name = " buildMessage3 - проверить генерацию исключения TemplateNotFoundException")
  void buildMessage3() throws Exception {
    //when(provider.getMessageTemplate(TEMPLATE_NAME)).thenReturn(TEMPLATE_TEXT);
    throw new Exception("[ERROR] " + TemplateNotFoundException.class.toString());
  }

}
