package EchoForm.patchs;

import EchoForm.powers.NeurotoxinPower;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import javassist.CtBehavior;

import java.util.ArrayList;

public class NeurotoxinPatch {
    // 神经毒素效果：中毒的敌人攻击会命中随机目标
    @SpirePatch(
            clz = AbstractMonster.class,
            method = "takeTurn"
    )
    public static class RandomTargetPatch {
        @SpirePrefixPatch
        public static void patch(AbstractMonster __instance) {
            // 检查怪物是否有中毒和神经毒素效果
            if (__instance.hasPower(PoisonPower.POWER_ID) && __instance.hasPower(NeurotoxinPower.POWER_ID)) {
                // 获取所有可能的目标
                ArrayList<AbstractCreature> possibleTargets = new ArrayList<>();
                
                // 添加玩家
                possibleTargets.add(AbstractDungeon.player);
                
                // 添加其他怪物（除了自己）
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m != __instance && !m.isDead && !m.isDying) {
                        possibleTargets.add(m);
                    }
                }
                
                // 随机选择一个目标
                if (possibleTargets.size() > 1) {
                    AbstractCreature newTarget = possibleTargets.get(AbstractDungeon.cardRandomRng.random(possibleTargets.size()));
                    
                    // 如果新目标不是原目标，则修改攻击目标
                    if (newTarget != AbstractDungeon.player) {
                        // 显示目标改变的视觉效果
                        AbstractDungeon.topLevelEffects.add(new com.megacrit.cardcrawl.vfx.combat.RoomTintEffect(
                            new com.badlogic.gdx.graphics.Color(0.0f, 1.0f, 0.0f, 0.3f), 0.5f));
                        
                        // 修改怪物的攻击目标
                        __instance.setMove((byte) 0, __instance.intent,
                                       __instance.getIntentDmg(), 0, newTarget instanceof AbstractMonster);
                    }
                }
            }
        }
    }
}