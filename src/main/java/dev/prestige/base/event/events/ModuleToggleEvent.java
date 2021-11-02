package dev.prestige.base.event.events;

import dev.prestige.base.modules.Module;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ModuleToggleEvent extends Event {

    public static class Enable extends ModuleToggleEvent {
        Module module;

        public Enable(Module module) {
            this.module = module;
        }

        public Module getModule() {
            return module;
        }
    }

    public static class Disable extends ModuleToggleEvent {
        Module module;

        public Disable(Module module) {
            this.module = module;
        }

        public Module getModule() {
            return module;
        }
    }
}
