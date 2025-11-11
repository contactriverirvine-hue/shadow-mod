package com.arsenal.shadows.entities;

import com.arsenal.shadows.ModItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

public class JavelinEntity extends AbstractArrow {
    private static final float DAMAGE = 10.0f;

    public JavelinEntity(EntityType<? extends JavelinEntity> type, Level level) {
        super(type, level);
        this.pickup = Pickup.ALLOWED; // Allow pickup
    }

    public JavelinEntity(Level level, LivingEntity shooter) {
        super(ModEntities.JAVELIN.get(), shooter, level);
        this.pickup = Pickup.ALLOWED; // Allow pickup
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (result.getEntity() instanceof LivingEntity target) {
            // Pierce through armor
            target.hurt(this.damageSources().arrow(this, this.getOwner()), DAMAGE);

            // Sound effect
            this.level().playSound(null, target.getX(), target.getY(), target.getZ(),
                    SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 1.0f, 1.0f);

            // Make it stick in the ground/entity briefly
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.1, 0.1, 0.1));
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        // Return the javelin item so players can pick it back up
        return new ItemStack(ModItems.JAVELIN.get());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public boolean shouldRender(double x, double y, double z) {
        return true;
    }
}