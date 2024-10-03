package com.itavvy.emiutils;

import com.itavvy.emiutils.commands.emihideitem;
import com.itavvy.emiutils.config.ClientConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(emiutils.MODID)
public class emiutils
{
    static List<ItemStack> bannedlist = new ArrayList<ItemStack>();
    // Define mod id in a common place for everything to reference
    public static final String MODID = "emiutils";

    private static final Logger LOGGER = LogUtils.getLogger();

    public emiutils()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC,"EMIUitils/RemovalList.toml");

        MinecraftForge.EVENT_BUS.register(new emihideitem());
    }

    private void commonSetup(final FMLCommonSetupEvent event){
    }

    public static Boolean removeitem(ItemStack stack){
        List<ItemStack> tempbannedlist = bannedlist;
        tempbannedlist.add(stack);

        ArrayList<String> templist = new ArrayList<String>();
        Iterator<ItemStack> iterator = tempbannedlist.iterator();

        String newitemname = ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();

        while(iterator.hasNext()){
            String tempitem = ForgeRegistries.ITEMS.getKey(iterator.next().getItem()).toString();

            if(tempitem.equals(newitemname)) return false;

            templist.add(tempitem);
        }
        bannedlist = tempbannedlist;
        ClientConfig.MASTER_LIST.set(templist);
        return true;
    }

    public static Boolean unhideitem(ItemStack stack){

        boolean works = false;
        List<ItemStack> tempitemlist = new ArrayList<ItemStack>();

        ArrayList<String> templist = new ArrayList<String>();
        Iterator<ItemStack> iterator = bannedlist.iterator();

        String newitemname = ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();

        while(iterator.hasNext()){
            ItemStack tempitem = iterator.next();
            String tempstring = ForgeRegistries.ITEMS.getKey(tempitem.getItem()).toString();

            if(!tempstring.equals(newitemname)){
                templist.add(tempstring);
                tempitemlist.add(tempitem);
            }else{
                works = true;
            }
        }
        if(works){
            ClientConfig.MASTER_LIST.set(templist);
            bannedlist = tempitemlist;
        }
        return works;
    }

    public static void getlist(){
        try{
            bannedlist.clear();
            List<String> list = ClientConfig.MASTER_LIST.get();
            Iterator<String> iterator = list.iterator();
            while(iterator.hasNext()) {
                bannedlist.add(ForgeRegistries.ITEMS.getValue(new ResourceLocation(iterator.next())).getDefaultInstance());
            }
        } catch (Exception e) {
        }
    }

}
