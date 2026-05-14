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

    static SimpleLayerProvider SQmprovider = SimpleLayerProvider.builder("ShapesMarkerLayer")
            .showControls(true)
            .defaultHidden(false)
            .layerPriority(1)
            .zIndex(250)
            .build();


    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {

        commandDispatcher.register(Commands.literal("SMShapes")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(4))
                .then(Commands.argument("markerID", StringArgumentType.string())
                        .then(Commands.literal("create")
                                .then(Commands.literal("Circle")
                                    .then(Commands.argument("centerX", IntegerArgumentType.integer())
                                        .then(Commands.argument("centerZ", IntegerArgumentType.integer())
                                            .then(Commands.argument("radius",IntegerArgumentType.integer()))
                                                    .executes(context -> {

                                                        Circle marker = createCircleMarker(
                                                                IntegerArgumentType.getInteger(context, "radius"),
                                                                IntegerArgumentType.getInteger(context,"centerX"),
                                                                IntegerArgumentType.getInteger(context,"centerZ")
                                                        );
                                                        setMarker(StringArgumentType.getString(context, "markerID"),marker);
                                                        return 1;
                                            }))))))
                        .then(Commands.literal("remove"))

                );

    }

    private static Circle createCircleMarker(int radius, int centerX, int centerZ){

        Point shapeCenter = Point.of(centerX, centerZ);

        return Circle.circle(shapeCenter, radius);
    };

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
