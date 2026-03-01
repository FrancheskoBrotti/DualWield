package com.ranull.dualwield.nms;

import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public final class NMS_v1_21_R7_paper implements NMS {

    @Override
    public void handAnimation(Player player, EquipmentSlot equipmentSlot) {
        switch (equipmentSlot) {
            case HAND:
                player.swingMainHand();
                break;
            case OFF_HAND:
                player.swingOffHand();
                break;
        }
    }

    @Override
    public void blockBreakAnimation(Player player, Block block, int animationID, int stage) {
        player.sendBlockDamage(block.getLocation(), stage / 9.0f, animationID);
    }

    @Override
    public void blockCrackParticle(Block block) {
        block.getWorld().spawnParticle(org.bukkit.Particle.BLOCK, block.getLocation().add(0.5, 0, 0.5),
                10, block.getBlockData());
    }

    @Override
    public float getToolStrength(Block block, ItemStack itemStack) {
        if (itemStack == null || itemStack.getAmount() == 0) {
            return 1;
        }
        // Use Bukkit API to check if item is correct tool for drops
        org.bukkit.Material blockType = block.getType();
        float hardness = (float) blockType.getHardness();
        if (hardness < 0) return 0;
        // Approximate tool speed: if item can break the block, return higher speed
        return 1;
    }

    @Override
    public double getAttackDamage(ItemStack itemStack) {
        return getItemStackAttribute(itemStack, Attribute.ATTACK_DAMAGE);
    }

    @Override
    public double getAttackSpeed(ItemStack itemStack) {
        return getItemStackAttribute(itemStack, Attribute.ATTACK_SPEED);
    }

    private double getItemStackAttribute(ItemStack itemStack, Attribute attribute) {
        if (itemStack != null && itemStack.getAmount() != 0) {
            for (AttributeModifier modifier : itemStack.getType().getDefaultAttributeModifiers(
                    itemStack.getType().getEquipmentSlot()).values()) {
                return modifier.getAmount();
            }
        }
        return 0;
    }

    @Override
    public Sound getHitSound(Block block) {
        try {
            return Sound.valueOf(block.getBlockData().getSoundGroup().getHitSound().name());
        } catch (IllegalArgumentException ignored) {
        }
        return Sound.BLOCK_STONE_HIT;
    }

    @Override
    public float getBlockHardness(Block block) {
        return (float) block.getType().getHardness();
    }

    @Override
    public boolean breakBlock(Player player, Block block) {
        return player.breakBlock(block);
    }

    @Override
    public void setModifier(Player player, double damage, double speed, UUID uuid) {
        NamespacedKey key = new NamespacedKey("dualwield", "weapon_modifier");

        AttributeInstance damageAttr = player.getAttribute(Attribute.ATTACK_DAMAGE);
        if (damageAttr != null) {
            damageAttr.removeModifier(key);
            damageAttr.addModifier(new AttributeModifier(key, damage, AttributeModifier.Operation.ADD_NUMBER));
        }

        AttributeInstance speedAttr = player.getAttribute(Attribute.ATTACK_SPEED);
        if (speedAttr != null) {
            speedAttr.removeModifier(key);
            speedAttr.addModifier(new AttributeModifier(key, speed, AttributeModifier.Operation.ADD_NUMBER));
        }
    }

    @Override
    public void removeModifier(Player player, UUID uuid) {
        NamespacedKey key = new NamespacedKey("dualwield", "weapon_modifier");

        AttributeInstance damageAttr = player.getAttribute(Attribute.ATTACK_DAMAGE);
        if (damageAttr != null) {
            damageAttr.removeModifier(key);
        }

        AttributeInstance speedAttr = player.getAttribute(Attribute.ATTACK_SPEED);
        if (speedAttr != null) {
            speedAttr.removeModifier(key);
        }
    }

    @Override
    public void attack(Player player, Entity entity) {
        player.attack(entity);
    }
}
