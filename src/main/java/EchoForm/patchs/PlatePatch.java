package EchoForm.patchs;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlatePatch {
    // 盘子遗物：因敌人攻击失去格挡时对所有敌人造成相当于失去格挡的伤害
    @SpirePatch(clz = AbstractPlayer.class, method = "damage", paramtypez = {DamageInfo.class})
    public static class OnLoseBlock {
        @SpireInsertPatch(rloc=19, localvars = {"damageAmount"})
        public static void patch(AbstractPlayer __instance, DamageInfo info, int damageAmount) {
            // 检查是否是玩家且不是HP损失类型伤害
            if (__instance == AbstractDungeon.player && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null) {
                // 检查玩家是否有盘子遗物
                if (AbstractDungeon.player.hasRelic("echoForm:Plate")) {
                    // 计算失去的格挡值：原始伤害值(info.output)减去decrementBlock后的伤害值(damageAmount)
                    int blockLost = info.output - damageAmount;
                    // 如果失去了格挡值（大于0）
                    if (blockLost > 0) {
                        // 添加短暂延迟确保伤害计算正确
                        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
                        AbstractDungeon.actionManager.addToBottom(
                            new DamageAllEnemiesAction(
                                AbstractDungeon.player,
                                DamageInfo.createDamageMatrix(blockLost, true),
                                DamageInfo.DamageType.THORNS,
                                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
                            )
                        );
                    }
                }
            }
        }
    }
}
