package net.dollar.simplegear.item.custom.crossbow;

import com.google.common.collect.Lists;
import net.dollar.simplegear.util.ModUtils;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

/**
 * Class corresponding specifically to the Tungsten-Carbide Crossbow Item. Derived from CrossbowItem but implements
 *  most of its functions manually to allow custom modification of generated arrow. Must derive from CrossbowItem
 *  to allow Crossbow rendering.
 */
public class ModTungstenCarbideCrossbowItem extends CrossbowItem {
    private static final String TAG_CHARGED = "Charged";
    private static final String TAG_CHARGED_PROJECTILES = "ChargedProjectiles";
    private static final int MAX_CHARGE_DURATION = 30;
    public static final int DEFAULT_RANGE = 8;
    private boolean startSoundPlayed = false;
    private boolean midLoadSoundPlayed = false;
    private static final float START_SOUND_PERCENT = 0.2F;
    private static final float MID_SOUND_PERCENT = 0.5F;
    private static final float ARROW_POWER = 3.15F;     //default 3.15
    private static final float FIREWORK_POWER = 1.6F;   //default 1.6



    public ModTungstenCarbideCrossbowItem(Properties properties) {
        super(properties);
    }



    /**
     * Returns ItemStack Predicate for all supported HELD projectiles.
     * @return The ItemStack Predicate
     */
    public Predicate<ItemStack> getSupportedHeldProjectiles() {
        return ARROW_OR_FIREWORK;
    }

    /**
     * Returns ItemStack Predicate for all supported FIRED projectiles.
     * @return The ItemStack Predicate
     */
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }


    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemstack = player.getItemInHand(interactionHand);
        if (isCharged(itemstack)) {
            performShooting(level, player, interactionHand, itemstack, getShootingPower(itemstack), 1.0F);
            setCharged(itemstack, false);
            return InteractionResultHolder.consume(itemstack);
        } else if (!player.getProjectile(itemstack).isEmpty()) {
            if (!isCharged(itemstack)) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
                player.startUsingItem(interactionHand);
            }

            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    private static float getShootingPower(ItemStack itemStack) {
        return containsChargedProjectile(itemStack, Items.FIREWORK_ROCKET) ? FIREWORK_POWER : ARROW_POWER;
    }

    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity userEntity, int useRemainingTicks) {
        int i = this.getUseDuration(itemStack) - useRemainingTicks;
        float f = getPowerForTime(i, itemStack);
        if (f >= 1.0F && !isCharged(itemStack) && tryLoadProjectiles(userEntity, itemStack)) {
            setCharged(itemStack, true);
            SoundSource soundsource = userEntity instanceof Player ? SoundSource.PLAYERS : SoundSource.HOSTILE;
            level.playSound((Player)null, userEntity.getX(), userEntity.getY(), userEntity.getZ(), SoundEvents.CROSSBOW_LOADING_END, soundsource, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.5F + 1.0F) + 0.2F);
        }

    }

    private static boolean tryLoadProjectiles(LivingEntity userEntity, ItemStack itemStack) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.MULTISHOT, itemStack);
        int j = i == 0 ? 1 : 3;
        boolean flag = userEntity instanceof Player && ((Player)userEntity).getAbilities().instabuild;
        ItemStack itemstack = userEntity.getProjectile(itemStack);
        ItemStack itemstack1 = itemstack.copy();

        for(int k = 0; k < j; ++k) {
            if (k > 0) {
                itemstack = itemstack1.copy();
            }

            if (itemstack.isEmpty() && flag) {
                itemstack = new ItemStack(Items.ARROW);
                itemstack1 = itemstack.copy();
            }

            if (!loadProjectile(userEntity, itemStack, itemstack, k > 0, flag)) {
                return false;
            }
        }

        return true;
    }

    private static boolean loadProjectile(LivingEntity userEntity, ItemStack weaponItemStack, ItemStack ammoItemStack,
                                          boolean isMultishot, boolean isInstabuild) {
        if (ammoItemStack.isEmpty()) {
            return false;
        } else {
            boolean flag = isInstabuild && ammoItemStack.getItem() instanceof ArrowItem;
            ItemStack ammoItemStackCopy;
            if (!flag && !isInstabuild && !isMultishot) {
                ammoItemStackCopy = ammoItemStack.split(1);
                if (ammoItemStack.isEmpty() && userEntity instanceof Player) {
                    ((Player)userEntity).getInventory().removeItem(ammoItemStack);
                }
            } else {
                ammoItemStackCopy = ammoItemStack.copy();
            }

            addChargedProjectile(weaponItemStack, ammoItemStackCopy);
            return true;
        }
    }

    public static boolean isCharged(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTag();
        return compoundtag != null && compoundtag.getBoolean(TAG_CHARGED);
    }

    public static void setCharged(ItemStack itemStack, boolean isCharged) {
        CompoundTag compoundtag = itemStack.getOrCreateTag();
        compoundtag.putBoolean(TAG_CHARGED, isCharged);
    }

    private static void addChargedProjectile(ItemStack weaponItemStack, ItemStack ammoItemStack) {
        CompoundTag compoundtag = weaponItemStack.getOrCreateTag();
        ListTag listtag;
        if (compoundtag.contains(TAG_CHARGED_PROJECTILES, 9)) {
            listtag = compoundtag.getList(TAG_CHARGED_PROJECTILES, 10);
        } else {
            listtag = new ListTag();
        }

        CompoundTag compoundtag1 = new CompoundTag();
        ammoItemStack.save(compoundtag1);
        listtag.add(compoundtag1);
        compoundtag.put(TAG_CHARGED_PROJECTILES, listtag);
    }

    private static List<ItemStack> getChargedProjectiles(ItemStack itemStack) {
        List<ItemStack> list = Lists.newArrayList();
        CompoundTag compoundtag = itemStack.getTag();
        if (compoundtag != null && compoundtag.contains(TAG_CHARGED_PROJECTILES, 9)) {
            ListTag listtag = compoundtag.getList(TAG_CHARGED_PROJECTILES, 10);
            if (listtag != null) {
                for(int i = 0; i < listtag.size(); ++i) {
                    CompoundTag compoundtag1 = listtag.getCompound(i);
                    list.add(ItemStack.of(compoundtag1));
                }
            }
        }

        return list;
    }

    private static void clearChargedProjectiles(ItemStack itemStack) {
        CompoundTag compoundtag = itemStack.getTag();
        if (compoundtag != null) {
            ListTag listtag = compoundtag.getList(TAG_CHARGED_PROJECTILES, 9);
            listtag.clear();
            compoundtag.put(TAG_CHARGED_PROJECTILES, listtag);
        }

    }

    public static boolean containsChargedProjectile(ItemStack itemStack, Item seekItem) {
        return getChargedProjectiles(itemStack).stream().anyMatch((seekItemStack) -> {
            return seekItemStack.is(seekItem);
        });
    }

    private static void shootProjectile(Level level, LivingEntity userEntity, InteractionHand interactionHand,
                                        ItemStack weaponItemStack, ItemStack ammoItemStack, float p_40900_,
                                        boolean p_40901_, float p_40902_, float p_40903_, float p_40904_) {
        if (!level.isClientSide) {
            boolean flag = ammoItemStack.is(Items.FIREWORK_ROCKET);
            Projectile projectile;
            if (flag) {
                projectile = new FireworkRocketEntity(level, ammoItemStack, userEntity, userEntity.getX(), userEntity.getEyeY() - (double)0.15F, userEntity.getZ(), true);
            } else {
                projectile = getArrow(level, userEntity, weaponItemStack, ammoItemStack);
                if (p_40901_ || p_40904_ != 0.0F) {
                    ((AbstractArrow)projectile).pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                }
            }

            if (userEntity instanceof CrossbowAttackMob) {
                CrossbowAttackMob crossbowattackmob = (CrossbowAttackMob)userEntity;
                crossbowattackmob.shootCrossbowProjectile(crossbowattackmob.getTarget(), weaponItemStack, projectile, p_40904_);
            } else {
                Vec3 vec31 = userEntity.getUpVector(1.0F);
                Quaternionf quaternionf = (new Quaternionf()).setAngleAxis((double)(p_40904_ * ((float)Math.PI / 180F)), vec31.x, vec31.y, vec31.z);
                Vec3 vec3 = userEntity.getViewVector(1.0F);
                Vector3f vector3f = vec3.toVector3f().rotate(quaternionf);
                projectile.shoot((double)vector3f.x(), (double)vector3f.y(), (double)vector3f.z(), p_40902_, p_40903_);
            }

            weaponItemStack.hurtAndBreak(flag ? 3 : 1, userEntity, (p_40858_) -> {
                p_40858_.broadcastBreakEvent(interactionHand);
            });
            level.addFreshEntity(projectile);
            level.playSound((Player)null, userEntity.getX(), userEntity.getY(), userEntity.getZ(), SoundEvents.CROSSBOW_SHOOT, SoundSource.PLAYERS, 1.0F, p_40900_);
        }
    }

    /**
     * Generates the AbstractArrow to fire from the Crossbow. Allows custom modification or replacement of
     *  the default generated Arrow.
     * @param level The current active level
     * @param userEntity The user LivingEntity
     * @param weaponItemStack The ItemStack corresponding to this CrossbowItem
     * @param ammoItemStack The ItemStack corresponding to the current ammo
     * @return The modified or replaced AbstractArrow
     */
    private static AbstractArrow getArrow(Level level, LivingEntity userEntity, ItemStack weaponItemStack,
                                          ItemStack ammoItemStack) {
        ArrowItem arrowitem = (ArrowItem)(ammoItemStack.getItem() instanceof ArrowItem ? ammoItemStack.getItem() : Items.ARROW);
        AbstractArrow abstractarrow = arrowitem.createArrow(level, ammoItemStack, userEntity);
        if (userEntity instanceof Player) {
            abstractarrow.setCritArrow(true);
        }

        abstractarrow.setSoundEvent(SoundEvents.CROSSBOW_HIT);
        abstractarrow.setShotFromCrossbow(true);
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PIERCING, weaponItemStack);
        if (i > 0) {
            abstractarrow.setPierceLevel((byte)i);
        }

        //increase arrow's damage by BASE 60% (base is 2.0)
        abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + 1.2d);
        return abstractarrow;
    }





    public static void performShooting(Level level, LivingEntity userEntity, InteractionHand interactionHand,
                                       ItemStack weaponItemStack, float p_40892_, float p_40893_) {
        if (userEntity instanceof Player player && net.minecraftforge.event.ForgeEventFactory.onArrowLoose(weaponItemStack, userEntity.level(), player, 1, true) < 0) return;
        List<ItemStack> list = getChargedProjectiles(weaponItemStack);
        float[] afloat = getShotPitches(userEntity.getRandom());

        for(int i = 0; i < list.size(); ++i) {
            ItemStack itemstack = list.get(i);
            boolean flag = userEntity instanceof Player && ((Player)userEntity).getAbilities().instabuild;
            if (!itemstack.isEmpty()) {
                if (i == 0) {
                    shootProjectile(level, userEntity, interactionHand, weaponItemStack, itemstack, afloat[i], flag, p_40892_, p_40893_, 0.0F);
                } else if (i == 1) {
                    shootProjectile(level, userEntity, interactionHand, weaponItemStack, itemstack, afloat[i], flag, p_40892_, p_40893_, -10.0F);
                } else if (i == 2) {
                    shootProjectile(level, userEntity, interactionHand, weaponItemStack, itemstack, afloat[i], flag, p_40892_, p_40893_, 10.0F);
                }
            }
        }

        onCrossbowShot(level, userEntity, weaponItemStack);
    }

    private static float[] getShotPitches(RandomSource random) {
        boolean flag = random.nextBoolean();
        return new float[]{1.0F, getRandomShotPitch(flag, random), getRandomShotPitch(!flag, random)};
    }

    private static float getRandomShotPitch(boolean bool, RandomSource random) {
        float f = bool ? 0.63F : 0.43F;
        return 1.0F / (random.nextFloat() * 0.5F + 1.8F) + f;
    }

    private static void onCrossbowShot(Level level, LivingEntity userEntity, ItemStack itemStack) {
        if (userEntity instanceof ServerPlayer serverplayer) {
            if (!level.isClientSide) {
                CriteriaTriggers.SHOT_CROSSBOW.trigger(serverplayer, itemStack);
            }

            serverplayer.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
        }

        clearChargedProjectiles(itemStack);
    }

    public void onUseTick(Level level, LivingEntity userEntity, ItemStack itemStack, int useRemainingTicks) {
        if (!level.isClientSide) {
            int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, itemStack);
            SoundEvent soundevent = this.getStartSound(i);
            SoundEvent soundevent1 = i == 0 ? SoundEvents.CROSSBOW_LOADING_MIDDLE : null;
            float f = (float)(itemStack.getUseDuration() - useRemainingTicks) / (float)getChargeDuration(itemStack);
            if (f < 0.2F) {
                this.startSoundPlayed = false;
                this.midLoadSoundPlayed = false;
            }

            if (f >= 0.2F && !this.startSoundPlayed) {
                this.startSoundPlayed = true;
                level.playSound((Player)null, userEntity.getX(), userEntity.getY(), userEntity.getZ(), soundevent, SoundSource.PLAYERS, 0.5F, 1.0F);
            }

            if (f >= 0.5F && soundevent1 != null && !this.midLoadSoundPlayed) {
                this.midLoadSoundPlayed = true;
                level.playSound((Player)null, userEntity.getX(), userEntity.getY(), userEntity.getZ(), soundevent1, SoundSource.PLAYERS, 0.5F, 1.0F);
            }
        }

    }

    public int getUseDuration(ItemStack weaponItemStack) {
        return getChargeDuration(weaponItemStack) + 3;
    }

    public static int getChargeDuration(ItemStack weaponItemStack) {
        int i = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.QUICK_CHARGE, weaponItemStack);
        return i == 0 ? MAX_CHARGE_DURATION : MAX_CHARGE_DURATION - 5 * i;
    }

    private SoundEvent getStartSound(int soundIndex) {
        return switch (soundIndex) {
            case 1 -> SoundEvents.CROSSBOW_QUICK_CHARGE_1;
            case 2 -> SoundEvents.CROSSBOW_QUICK_CHARGE_2;
            case 3 -> SoundEvents.CROSSBOW_QUICK_CHARGE_3;
            default -> SoundEvents.CROSSBOW_LOADING_START;
        };
    }

    /**
     * Gets power as float out of 1.0 based on the amount of time charged. Manually implemented in this class
     *  specifically to use custom charge duration (static method cannot technically be overridden).
     * @param chargeTicks Number of ticks currently drawn
     * @param weaponItemStack The ItemStack corresponding to this CrossbowItem (used for getChargeDuration)
     * @return Power as float out of 1.0
     */
    private static float getPowerForTime(int chargeTicks, ItemStack weaponItemStack) {
        float f = (float)chargeTicks / (float)getChargeDuration(weaponItemStack);
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    /**
     * Appends text to the Item's hover tooltip. Also appends Crossbow data like charged ammo.
     * @param itemStack ItemStack corresponding to this Item
     * @param level Active level
     * @param components List of Components that make up the tooltip
     * @param flag TooltipFlag determining whether NORMAL or ADVANCED
     */
    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.literal("§8> +60% damage"));
        components.add(Component.literal("§8> Charges 20% slower"));
        super.appendHoverText(itemStack, level, components, flag);
    }

    public int getDefaultProjectileRange() {
        return DEFAULT_RANGE;
    }
}
