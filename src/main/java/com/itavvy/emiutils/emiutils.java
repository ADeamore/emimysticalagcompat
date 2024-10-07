package com.itavvy.emiutils;

import com.itavvy.emiutils.commands.emicommands;
import com.itavvy.emiutils.config.ClientConfig;
import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
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

        MinecraftForge.EVENT_BUS.register(new emicommands());
    }

    private void commonSetup(final FMLCommonSetupEvent event){
    }

    public static Boolean removeitem(ItemStack stack){

        ArrayList<String> tempitemlist = new ArrayList<String>();
        ArrayList<String> temptaglist = new ArrayList<String>();
        Iterator<ItemStack> iterator = bannedlist.iterator();

        String stacktags = "";
        try {
            stacktags = stack.getTag().toString();
        } catch (Exception e) {
        }

        String newitemname = ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();

        while(iterator.hasNext()){
            ItemStack nextitem = iterator.next();
            String tempitem = ForgeRegistries.ITEMS.getKey(nextitem.getItem()).toString();
            String temptags = "";
            try{
                temptags = nextitem.getTag().toString();
            }catch (Exception e){
            }

            if(tempitem.equals(newitemname) & temptags.equals(stacktags)) return false;

            tempitemlist.add(tempitem);
            temptaglist.add(temptags);
        }

        tempitemlist.add(newitemname);
        temptaglist.add(stacktags);

        List<ItemStack> tempbannedlist = bannedlist;

        int stacksize = stack.getCount();
        stack.setCount(1);
        tempbannedlist.add(stack);
        stack.setCount(stacksize);
        bannedlist = tempbannedlist;

        ClientConfig.COMPOUND_TAGS.set(temptaglist);
        ClientConfig.MASTER_LIST.set(tempitemlist);
        return true;
    }

    public static Boolean unhideitem(ItemStack stack){

        int stackcount = stack.getCount();
        stack.setCount(1);

        boolean works = false;
        List<ItemStack> tempitemlist = new ArrayList<ItemStack>();

        ArrayList<String> templist = new ArrayList<String>();
        ArrayList<String> temptagslist = new ArrayList<String>();
        Iterator<ItemStack> iterator = bannedlist.iterator();

        String newtags = "";
        String newitemname = ForgeRegistries.ITEMS.getKey(stack.getItem()).toString();
        try{
            newtags = stack.getTag().toString();
        } catch (Exception e) {
        }

        while(iterator.hasNext()){
            ItemStack tempitem = iterator.next();
            String tempstring = ForgeRegistries.ITEMS.getKey(tempitem.getItem()).toString();
            String temptags = "";
            try {
                temptags = tempitem.getTag().toString();
            }catch (Exception e){
            }

            if(!(tempstring.equals(newitemname) & temptags.equals(newtags))){
                templist.add(tempstring);
                tempitemlist.add(tempitem);
                temptagslist.add(temptags);
            }else{
                works = true;
            }
        }
        if(works){
            ClientConfig.MASTER_LIST.set(templist);
            ClientConfig.COMPOUND_TAGS.set(temptagslist);
            bannedlist = tempitemlist;
        }
        stack.setCount(stackcount);
        return works;
    }

    public static void getlist(){
        try{
            bannedlist.clear();
            List<String> list = ClientConfig.MASTER_LIST.get();
            List<String> tags = ClientConfig.COMPOUND_TAGS.get();
            Iterator<String> iterator = list.iterator();
            Iterator<String> tagiterator = tags.iterator();
            while(iterator.hasNext()) {
                ItemStack item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(iterator.next())).getDefaultInstance();
                String thistagsstring = tagiterator.next();
                if(!thistagsstring.equals("")) {
                    CompoundTag thistags = TagParser.parseTag(thistagsstring);
                    item.setTag(thistags);
                }
                bannedlist.add(item);
            }
        } catch (Exception e) {
        }
    }

}
