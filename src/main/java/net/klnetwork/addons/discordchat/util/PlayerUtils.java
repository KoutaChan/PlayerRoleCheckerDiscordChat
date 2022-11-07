package net.klnetwork.addons.discordchat.util;

import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PlayerUtils {
    private static Method CURRENT_GAME_PROFILE_METHOD, CURRENT_PROPERTY_MAP_METHOD, CURRENT_GET_METHOD;

    public static Property getSkinUrl(Player player) {
        try {
            Object craftPlayer = player.getClass().getMethod("getHandle").invoke(player);
            if (CURRENT_GAME_PROFILE_METHOD == null) {
                findProfile(craftPlayer);
            }
            Object profile = CURRENT_GAME_PROFILE_METHOD.invoke(craftPlayer);
            if (CURRENT_PROPERTY_MAP_METHOD == null) {
                findProperty(profile);
            }
            Object propertyMap = CURRENT_PROPERTY_MAP_METHOD.invoke(profile);
            if (CURRENT_GET_METHOD == null) {
                findGet(propertyMap);
            }
            Object textures = CURRENT_GET_METHOD.invoke(propertyMap, "textures");
            Method iteratorMethod = textures.getClass().getMethod("iterator");
            iteratorMethod.setAccessible(true);
            Object iterator = iteratorMethod.invoke(textures);

            Method nextMethod = iterator.getClass().getMethod("next");
            nextMethod.setAccessible(true);
            Object property = nextMethod.invoke(iterator);

            return new Property((String) property.getClass().getMethod("getValue").invoke(property), (String) property.getClass().getMethod("getSignature").invoke(property));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        throw new IllegalStateException();
    }

    private static void findProfile(Object invoke) {
        for (Method method : invoke.getClass().getMethods()) {
            if (method.getParameterCount() == 0 && method.getReturnType().getName().contains("GameProfile")) {
                CURRENT_GAME_PROFILE_METHOD = method;
                break;
            }
        }
    }

    private static void findProperty(Object invoke) {
        for (Method method : invoke.getClass().getMethods()) {
            if (method.getParameterCount() == 0 && method.getReturnType().getName().contains("PropertyMap")) {
                CURRENT_PROPERTY_MAP_METHOD = method;
                break;
            }
        }
    }

    private static void findGet(Object invoke) {
        for (Method method : invoke.getClass().getMethods()) {
            if (method.getName().equals("get")) {
                CURRENT_GET_METHOD = method;
                break;
            }
        }
    }

    public static class Property {
        private final String texture, signature;

        public Property(String texture, String signature) {
            this.texture = texture;
            this.signature = signature;
        }

        public String getTexture() {
            return texture;
        }

        public String toURL() {
            return new String(Base64.getDecoder().decode(texture), StandardCharsets.UTF_8);
        }

        public String getSignature() {
            return signature;
        }
    }
}