package com.dizzzylizzz.sms.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import xyz.jpenilla.squaremap.api.marker.Circle;

import java.util.concurrent.CompletableFuture;

public class shapeSuggestionProvider implements SuggestionProvider<CommandSourceStack> {


    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) throws CommandSyntaxException {
        builder.suggest("circle");
        builder.suggest("ellipse");
        builder.suggest("polygon");
        builder.suggest("rectangle");
        return null;
    }

}
