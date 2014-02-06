package omegleChatBot;

import java.awt.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.internal.seleniumemulation.GetText;

public class mainClass {

	public final static int WAITING_TIME=2000;
	
	private static ChatterBotReply cReply;
	
	//start browser
	private static WebDriver driver = new FirefoxDriver();
	
	public static void main(String[] args) throws Exception
	{
		cReply=new ChatterBotReply();
		//open omegle
		openSite();
		
		//click on text button
		startChat();
		
		
		while(true)
		{
			//wait for "You're now chatting with a random stranger. Say hi!"
			waitUntilChatStarts();
			
			//start chat
			botChat();
			
			startNewChat();
		}
	}

	private static void startChat()
	{
		driver.findElement(By.id("textbtn")).click();
	}

	private static void openSite()
	{
		driver.get("http://www.omegle.com");
	}

	private static void startNewChat()
	{
		driver.findElement(By.className("disconnectbtn")).click();
	}

	private static void botChat()
	{
		while(true)
		{
			try
			{
				Thread.sleep(WAITING_TIME);
			}
			catch (InterruptedException e)
			{
				continue;
			}
			String ChatText=getChatText();
			if(ChatText.contains("Stranger has disconnected") || ChatText.contains("You have disconnected"))
				break;
			String lastChat=lastChatOfStranger();
			if(lastChat!="")
			{
				String replyMessage=cReply.reply(lastChat);
				sendMessage(replyMessage);
			}
		}
	}

	private static void waitUntilChatStarts()
	{
		while(!getChatText().contains("You're now chatting with a random stranger"))
		{
			try
			{
				Thread.sleep(WAITING_TIME);
			}
			catch (InterruptedException e)
			{
				continue;
			}
		}
	}

	private static void sendMessage(String replyMessage)
	{
		driver.findElement(By.cssSelector("textarea.chatmsg")).sendKeys(replyMessage);
		driver.findElement(By.className("sendbtn")).click();
	}

	private static String getChatText()
	{
		return driver.findElement(By.className("logwrapper")).getText();
	}
	
	private static String lastChatOfStranger()
	{
		java.util.List<WebElement> messages=driver.findElements(By.className("logitem"));
		
		WebElement lastMessage=messages.get(messages.size()-1);
//		System.out.println(lastMessage.getText());
		if(lastMessage.getText().startsWith("Stranger: "))
			return lastMessage.getText().substring("Stranger: ".length());
		else
			return "";
	}
	
}
