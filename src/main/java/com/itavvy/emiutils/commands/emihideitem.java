package com.itavvy.emiutils.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import com.itavvy.emiutils.emiutils;

@Mod.EventBusSubscriber(modid = emiutils.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class emihideitem {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        register(event.getDispatcher());
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("emihideitem").executes(emihideitem::executehide));
        dispatcher.register(Commands.literal("emiunhideitem").executes(emihideitem::executeunhide));
    }

    public static int executehide(CommandContext<CommandSourceStack> command) throws CommandSyntaxException {
        Player player = Minecraft.getInstance().player;
        ItemStack item = player.getMainHandItem();
        if(item.is(Items.AIR)){
            PlayerChatMessage chatMessage = PlayerChatMessage.unsigned(player.getUUID(), "You cannot hide an empty hand.");
            player.createCommandSourceStack().sendChatMessage(new OutgoingChatMessage.Player(chatMessage), false, ChatType.bind(ChatType.CHAT, player));
            return 0;
        }

        Boolean worked = emiutils.removeitem(item);

        if(worked){
            PlayerChatMessage chatMessage = PlayerChatMessage.unsigned(player.getUUID(), "Removed " + ForgeRegistries.ITEMS.getKey(item.getItem()).toString());
            player.createCommandSourceStack().sendChatMessage(new OutgoingChatMessage.Player(chatMessage), false, ChatType.bind(ChatType.CHAT, player));

            chatMessage = PlayerChatMessage.unsigned(player.getUUID(), "please do a /reload to see the changes made.");
            player.createCommandSourceStack().sendChatMessage(new OutgoingChatMessage.Player(chatMessage), false, ChatType.bind(ChatType.CHAT, player));

            return Command.SINGLE_SUCCESS;
        }else{
            Minecraft.getInstance().player.displayClientMessage(Component.literal("Item '" + ForgeRegistries.ITEMS.getKey(item.getItem()).toString() +"' is already hidden."), true);
        }


        return 0;
    }

    public static int executeunhide(CommandContext<CommandSourceStack> command) throws CommandSyntaxException {
        Player player = Minecraft.getInstance().player;
        ItemStack item = player.getMainHandItem();
        if(item.is(Items.AIR)){
            PlayerChatMessage chatMessage = PlayerChatMessage.unsigned(player.getUUID(), "You cannot hide an empty hand.");
            player.createCommandSourceStack().sendChatMessage(new OutgoingChatMessage.Player(chatMessage), false, ChatType.bind(ChatType.CHAT, player));
            return 0;
        }

        Boolean worked = emiutils.unhideitem(item);

        if(worked){
            PlayerChatMessage chatMessage = PlayerChatMessage.unsigned(player.getUUID(), "Unhid " + ForgeRegistries.ITEMS.getKey(item.getItem()).toString());
            player.createCommandSourceStack().sendChatMessage(new OutgoingChatMessage.Player(chatMessage), false, ChatType.bind(ChatType.CHAT, player));

            chatMessage = PlayerChatMessage.unsigned(player.getUUID(), "please do a /reload to see the changes made.");
            player.createCommandSourceStack().sendChatMessage(new OutgoingChatMessage.Player(chatMessage), false, ChatType.bind(ChatType.CHAT, player));

            return Command.SINGLE_SUCCESS;
        }else{
            Minecraft.getInstance().player.displayClientMessage(Component.literal("Item '" + ForgeRegistries.ITEMS.getKey(item.getItem()).toString() +"' isn't hidden."), true);
        }


        return 0;
    }
}
