package art.arcane.d20;

import art.arcane.d20.roll.Roll;
import art.arcane.d20.roll.RollResult;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class D20 extends JavaPlugin implements Listener {
    private D20Config config = new D20Config();
    private Map<String, Roll> rollCache = new HashMap<>();

    public void onEnable()
    {
        registerListener(this);
    }

    public void onDisable()
    {
        unregisterListener(this);
    }

    public RollResult roll(String conf){
        Roll r = rollCache.computeIfAbsent(conf, Roll::decode);
        RollResult rr = r.roll();
        System.out.println("Roll: " + r + " -> " + rr.getResult() + " (" + rr + ")");

        return rr;
    }

    @EventHandler
    public void on(BlockPlaceEvent e) {
        RollResult r = roll(config.blockPlace);

        if(!r.check(10)) {
            effectSavingThrowFailed(e.getPlayer(), e.getBlock().getLocation(), r);
            e.getBlock().getLocation().getWorld().playSound(e.getBlock().getLocation(), Sound.BLOCK_DEEPSLATE_BRICKS_BREAK, 1, 0.6f);
            getServer().getScheduler().scheduleSyncDelayedTask(this, () -> e.getBlock().breakNaturally(), 0);
        }
    }

    @EventHandler
    public void on(BlockBreakEvent e) {
        RollResult r = roll(config.blockBreak);

        if(!r.check(10)) {
            effectSavingThrowFailed(e.getPlayer(), e.getBlock().getLocation(), r);
            e.getBlock().getLocation().getWorld().playSound(e.getBlock().getLocation(), Sound.BLOCK_DEEPSLATE_BRICKS_BREAK, 1, 0.6f);
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void on(EntityDamageEvent e) {
        if(e.getEntity() instanceof LivingEntity l) {
            if(l.getHealth() - e.getFinalDamage() <= 0) {
                RollResult r = roll(config.deathSavingThrow); // d20

                if(r.check(10)) {
                    e.setCancelled(true);

                    if(r.countNaturalMaxes() > 0) {
                        l.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
                        return;
                    }

                    l.setHealth(1);
                    effectSavingThrowPassed(e.getEntity(), e.getEntity().getLocation(), r);
                }else
                {
                    effectSavingThrowFailed(e.getEntity(), e.getEntity().getLocation(), r);
                }
            }
        }
    }

    public void effectSavingThrowPassed(Entity entity, Location location, RollResult r) {
        location.getWorld().spawnParticle(Particle.FLASH, location, 1);
    }

    public void effectSavingThrowFailed(Entity entity, Location location, RollResult r) {
        location.getWorld().spawnParticle(Particle.SCRAPE, location, 1);
    }
}
