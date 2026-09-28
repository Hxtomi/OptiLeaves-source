package net.norevy.optileaves;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.AtomicMoveNotSupportedException;

public final class Config {
    public static final Config INSTANCE = new Config();
    public volatile boolean enabled = true;
    public volatile int depth = 2;
    public boolean debug = false;

    public static int clampDepth(int value) {
        return Math.max(1, Math.min(4, value));
    }

    public void load() {
        Path path = FMLPaths.CONFIGDIR.get().resolve("optileaves.json");
        if (!Files.exists(path)) { save(); return; }
        try {
            JsonObject json = JsonParser.parseString(Files.readString(path, StandardCharsets.UTF_8)).getAsJsonObject();
            boolean nextEnabled = json.has("enabled") ? json.get("enabled").getAsBoolean() : true;
            int nextDepth = json.has("depth") ? clampDepth(json.get("depth").getAsInt()) : 2;
            boolean nextDebug = json.has("debug") && json.get("debug").getAsBoolean();
            enabled = nextEnabled;
            depth = nextDepth;
            debug = nextDebug;
        } catch (IOException | RuntimeException e) {
            // Keep the user's file for repair, and leave the safe defaults in memory.
            OptiLeaves.LOGGER.warn("Cannot read OptiLeaves configuration; using defaults", e);
        }
    }

    public void save() {
        Path path = FMLPaths.CONFIGDIR.get().resolve("optileaves.json");
        Path temporary = path.resolveSibling("optileaves.json.tmp");
        depth = clampDepth(depth);
        JsonObject json = new JsonObject();
        json.addProperty("enabled", enabled);
        json.addProperty("depth", depth);
        json.addProperty("debug", debug);
        try {
            Files.createDirectories(path.getParent());
            Files.writeString(temporary, new GsonBuilder().setPrettyPrinting().create().toJson(json), StandardCharsets.UTF_8);
            try {
                Files.move(temporary, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temporary, path, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            OptiLeaves.LOGGER.error("Cannot save OptiLeaves configuration", e);
        }
    }
}
