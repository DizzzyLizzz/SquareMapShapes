package com.dizzzylizzz.sms.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import xyz.jpenilla.squaremap.api.*;
import xyz.jpenilla.squaremap.api.marker.Circle;
import xyz.jpenilla.squaremap.api.marker.Marker;

import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class shapeCommands {
    static SimpleLayerProvider SQmprovider = SimpleLayerProvider.builder("ShapesMarkerLayer")
            .showControls(true)
            .defaultHidden(false)
            .layerPriority(1)
            .zIndex(250)
            .build();

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {

        commandDispatcher.register(Commands.literal("SMShapes")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(4))
                .then(Commands.argument("marker", StringArgumentType.string())
                        .then(Commands.literal("create")
                            .then(Commands.argument("shape", StringArgumentType.string()).suggests(new shapeSuggestionProvider()))
                                .then(Commands.argument("Circle", StringArgumentType.string())).then(Commands.argument("radius",IntegerArgumentType.integer())))
                        .executes(context -> {
                                final String shape = StringArgumentType.getString(context, "shape");
                            Squaremap SQmApi = SquaremapProvider.get();

                        })
                        .then(Commands.literal("remove"))

                ));

    }

    private void createMarker(){

        Marker Shape;

    };

    private static void setMarker(StringArgumentType key, Marker shape ){
        Squaremap SQmApi = SquaremapProvider.get();
        Key markerKey = Key.of(key.toString());

        SQmApi.getWorldIfEnabled(WorldIdentifier.parse("minecraft:overworld")).ifPresent(mapWorld -> {
            SQmprovider.addMarker(markerKey, shape);
            mapWorld.layerRegistry().register(markerKey, SQmprovider);

        });
    }

   private static void circle() {



    }
}
