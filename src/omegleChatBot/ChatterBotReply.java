package omegleChatBot;

import com.google.code.chatterbotapi.*;

public class ChatterBotReply {
	
	private ChatterBotFactory factory;
	private ChatterBot bot;
	private ChatterBotSession botsession;
	
	ChatterBotReply() throws Exception
	{
		factory = new ChatterBotFactory();
		
        bot = factory.create(ChatterBotType.CLEVERBOT);
        
        botsession = bot.createSession();
	}
	
	public String reply(String lastChat)
	{
		try
		{
		        return botsession.think(lastChat);
		}
		catch(Exception e)
		{
			return "Sorry. I couldn't understand.";
		}
        
	}
	
}
