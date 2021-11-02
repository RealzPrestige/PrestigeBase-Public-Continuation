package dev.prestige.base.event;

import dev.prestige.base.PrestigeBase;
import dev.prestige.base.event.events.DeathEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public class EventListener {

    public void init(boolean load) {
        if (load)
            MinecraftForge.EVENT_BUS.register(this);
        else MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        PrestigeBase.moduleInitializer.onTick();
        if (PrestigeBase.mc.world == null || PrestigeBase.mc.player == null)
            return;
        PrestigeBase.mc.world.playerEntities.stream().filter(player -> player != null && !(player.getHealth() > 0.0f)).map(DeathEvent::new).forEach(MinecraftForge.EVENT_BUS::post);
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState())
            PrestigeBase.moduleInitializer.onKey(Keyboard.getEventKey());
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        PrestigeBase.moduleInitializer.onWorldRender();
    }


    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Text event) {
        if (!PrestigeBase.hudComponentInitializer.getHudModules().isEmpty())
            PrestigeBase.hudComponentInitializer.drawText();
    }
}
