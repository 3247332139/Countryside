package net.bfybf.countryside.event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class VillagerDropsHandler {

    @SubscribeEvent
    public static void onMobDropsLoot(LivingDropsEvent event) {
        final LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (entity instanceof AbstractVillager villager && event.getSource().getEntity() instanceof Player player && !entity.isBaby()){
            if(level.random.nextDouble() < 0.25d + event.getLootingLevel() * 0.05d){
                final MerchantOffers offers = villager.getOffers();
                final int drops = level.random.nextInt(4) + 1;
                for (int i = 0; i < drops; i++) {
                    if(!offers.isEmpty()){
                        final MerchantOffer offer = offers.get(level.random.nextInt(offers.size()));
                        if(!offer.isOutOfStock()){
                            event.getDrops().add(new ItemEntity(level, villager.getOnPos().getX(), villager.getOnPos().getY(), villager.getOnPos().getZ(), offer.getResult().copy()));
                        }else {
                            event.getDrops().add(new ItemEntity(level, villager.getOnPos().getX(), villager.getOnPos().getY(), villager.getOnPos().getZ(), new ItemStack(Items.EMERALD, level.random.nextInt(3))));
                        }
                    }
                }
            }
            event.getDrops().add(new ItemEntity(level, villager.getOnPos().getX(), villager.getOnPos().getY(), villager.getOnPos().getZ(), new ItemStack(Items.EMERALD, level.random.nextInt(2) + level.random.nextInt(2))));
        }
    }
}
