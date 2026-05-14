package com.dizzzylizzz.sms.commands;


import com.dizzzylizzz.sms.Config;
import com.dizzzylizzz.sms.Shapes;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.kyori.adventure.text.event.ClickEvent;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import xyz.jpenilla.squaremap.api.*;
import xyz.jpenilla.squaremap.api.marker.Circle;
import xyz.jpenilla.squaremap.api.marker.Ellipse;
import xyz.jpenilla.squaremap.api.marker.Marker;
import xyz.jpenilla.squaremap.api.marker.Rectangle;

import javax.print.DocFlavor;

import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class shapeCommands {

    public shapeCommands(IEventBus EventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        EventBus.addListener(this::onRegisterCommands);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (SquareMapShapes) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
    }

    public void onRegisterCommands(RegisterCommandsEvent event){

        shapeCommands.register(event.getDispatcher());

    }

    static SimpleLayerProvider SQmprovider = SimpleLayerProvider.builder("SMShapesMod")
            .showControls(true)
            .defaultHidden(false)
            .layerPriority(1)
            .zIndex(250)
            .build();


    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {

        commandDispatcher.register(Commands.literal("SMShapes")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(4))

                        .then(Commands.literal("create")
                                .then(Commands.argument("markerID", StringArgumentType.string())
                                    .then(Commands.literal("Circle")
                                        .then(Commands.argument("centerX", IntegerArgumentType.integer())
                                            .then(Commands.argument("centerZ", IntegerArgumentType.integer())
                                                .then(Commands.argument("radius",IntegerArgumentType.integer())
                                                    .executes(commandSourceStack -> createCircleMarker(
                                                            StringArgumentType.getString(commandSourceStack,"markerID"),
                                                            IntegerArgumentType.getInteger(commandSourceStack, "radius"),
                                                            IntegerArgumentType.getInteger(commandSourceStack, "centerX"),
                                                            IntegerArgumentType.getInteger(commandSourceStack, "centerZ")
                                                            )
                                                    )))))
                                        .then(Commands.literal("Rectangle")
                                                .then(Commands.argument("corner1X", IntegerArgumentType.integer())
                                                        .then(Commands.argument("corner1Z", IntegerArgumentType.integer())
                                                                .then(Commands.argument("corner2X",IntegerArgumentType.integer())
                                                                        .then(Commands.argument("corner2Z", IntegerArgumentType.integer())
                                                                        .executes(commandSourceStack -> createRectangleMarker(
                                                                                StringArgumentType.getString(commandSourceStack,"markerID"),
                                                                                IntegerArgumentType.getInteger(commandSourceStack, "corner1X"),
                                                                                IntegerArgumentType.getInteger(commandSourceStack, "corner1Z"),
                                                                                IntegerArgumentType.getInteger(commandSourceStack, "corner2X"),
                                                                                IntegerArgumentType.getInteger(commandSourceStack,"corner2Z")
                                                                        )))))))))
                        .then(Commands.literal("remove"))

                );

    }

    private static int createCircleMarker(String key, int radius, int centerX, int centerZ){

        Point shapeCenter = Point.of(centerX, centerZ);

        Marker CircleMark = Circle.circle(shapeCenter, radius);

        setMarker(key, CircleMark);
        return 0;
    }

    private static int createRectangleMarker(String key, int p1X, int p1Z, int p2X, int p2Z){

        Point corner1 = Point.of(p1X, p1Z);
        Point corner2 = Point.of(p2X, p2Z);

        Marker sqMark = Rectangle.rectangle(corner1, corner2);

        setMarker(key, sqMark);
        return 0;
    }

    private static void setMarker(String key, Marker shape ){
        Squaremap SQmApi = SquaremapProvider.get();
        Key markerKey = Key.of(key);

        SQmApi.getWorldIfEnabled(WorldIdentifier.parse("minecraft:overworld")).ifPresent(mapWorld -> {
            SQmprovider.addMarker(markerKey, shape);
            mapWorld.layerRegistry().register(markerKey, SQmprovider);

        });
    }

   private static void circle() {



    }
}
