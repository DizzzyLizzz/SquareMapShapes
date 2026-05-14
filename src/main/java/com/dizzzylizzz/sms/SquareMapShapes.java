package com.dizzzylizzz.sms;

import com.dizzzylizzz.sms.commands.shapeCommands;
import net.minecraft.server.MinecraftServer;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;


import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

import net.neoforged.neoforge.event.server.ServerStartingEvent;

import xyz.jpenilla.squaremap.api.*;
import xyz.jpenilla.squaremap.api.marker.Ellipse;

import static com.dizzzylizzz.sms.Config.radius1;
import static com.dizzzylizzz.sms.Config.shape1Center;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(SquareMapShapes.MODID)
public class SquareMapShapes {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "squaremapshapes";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public SquareMapShapes(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading





        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (SquareMapShapes) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);


        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
@SubscribeEvent
    private void onRegisterCommands(RegisterCommandsEvent event) {
        // Some common setup code

        shapeCommands.register(event.getDispatcher());

        LOGGER.info("SMShapes registered");




    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts

        Point shapeCenter = Point.of(shape1Center.get().indexOf(0),shape1Center.get().indexOf(1));
        @NonNull Ellipse circleMark = Ellipse.ellipse(shapeCenter, radius1.getAsInt(), radius1.getAsInt());

        Squaremap SQmApi = SquaremapProvider.get();
        Shapes.circleMarker marker2 = new Shapes.circleMarker();
        SimpleLayerProvider SQmprovider = SimpleLayerProvider.builder("SMShapes")
                .showControls(true)
                .defaultHidden(false)
                .layerPriority(1)
                .zIndex(250)
                .build();

        SQmApi.getWorldIfEnabled(WorldIdentifier.parse("minecraft:overworld")).ifPresent(mapWorld -> {

            Key keye = Key.of("ShapesMarkerLayerKey");
            SQmprovider.addMarker(keye, circleMark);
            mapWorld.layerRegistry().register(keye, SQmprovider);

        });





        LOGGER.info("HELLO from server starting");
    }
}
