package dev.prestige.base.modules.core;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.prestige.base.event.events.DeathEvent;
import dev.prestige.base.event.events.ModuleToggleEvent;
import dev.prestige.base.modules.Module;
import dev.prestige.base.modules.ModuleInfo;
import dev.prestige.base.settings.impl.BooleanSetting;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleInfo(name = "Chat Notifications", category = Module.Category.Core, description = "Send Notifications in chat when certain things happen.")
public class ChatNotifications extends Module {
    public BooleanSetting modules = new BooleanSetting("Modules", false, this);
 //   public BooleanSetting totemPops = new BooleanSetting("Totem Pops", false, this);
    public BooleanSetting deaths = new BooleanSetting("Deaths", false, this);

    @SubscribeEvent
    public void onModuleEnableEvent(ModuleToggleEvent.Enable event){
        if(modules.getValue())
            mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString("[PrestigeBase] " + ChatFormatting.BOLD + event.getModule().getName() + ChatFormatting.RESET + " has been " + ChatFormatting.GREEN + "Enabled" + ChatFormatting.RESET + "."), 1);
    }

    @SubscribeEvent
    public void onModuleDisableEvent(ModuleToggleEvent.Disable event){
        if(modules.getValue())
            mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString("[PrestigeBase] " + ChatFormatting.BOLD + event.getModule().getName() + ChatFormatting.RESET + " has been " + ChatFormatting.RED + "Disabled" + ChatFormatting.RESET + "."), 1);
    }

    @SubscribeEvent
    public void onDeathEvent(DeathEvent event){
        if(deaths.getValue())
            mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString("[PrestigeBase] " + ChatFormatting.BOLD + event.getPlayer().getName() + ChatFormatting.RESET + " has just died."), 1);

    }
}
