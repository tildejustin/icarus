package me.duncanruns.icarus.mixin;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.duncanruns.icarus.IcarusConfig;
import me.duncanruns.icarus.compat.WorldPreviewCompat;
import net.minecraft.command.arguments.ItemStackArgumentType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    @Shadow
    @Final
    private ServerStatHandler statHandler;

    public ServerPlayerEntityMixin(World world, BlockPos blockPos, GameProfile gameProfile) {
        super(world, blockPos, gameProfile);
    }

    @Unique
    private ItemStack itemStackFromString(String string, int count) throws CommandSyntaxException {
        return new ItemStackArgumentType().parse(new StringReader(string)).createStack(count, false);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(CallbackInfo info) throws CommandSyntaxException {
        if (WorldPreviewCompat.isFakePlayer((ServerPlayerEntity) (Object) this)
                || statHandler != null && statHandler.getStat(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_ONE_MINUTE)) == 0) {
            // Item strings copy & pasted directly from jojoe's datapack
            ItemStack wings = itemStackFromString("minecraft:elytra{Unbreakable:1b}", 1);
            ItemStack rockets = itemStackFromString(String.format("minecraft:firework_rocket{Fireworks:{Flight:%db}}", IcarusConfig.flightDuration), 64);

            inventory.armor.set(EquipmentSlot.CHEST.getEntitySlotId(), wings);
            if (!IcarusConfig.offhand) {
                inventory.main.set(inventory.getEmptySlot(), rockets);
            } else {
                inventory.offHand.set(0, rockets);
            }
        }
    }
}
