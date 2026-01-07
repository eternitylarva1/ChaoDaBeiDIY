package EchoForm.patchs;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlatePatch {
    // 盘子遗物：因敌人攻击失去格挡时对所有敌人造成相当于失去格挡的伤害
    @SpirePatch(clz = AbstractCreature.class, method = "loseBlock", paramtypez = {int.class, boolean.class})
    public static class OnLoseBlock {
        @SpirePrefixPatch
        public static SpireReturn<Void> patch(AbstractCreature __instance, int amount, boolean noAnimation) {
            // 检查是否是玩家
            if (__instance == AbstractDungeon.player) {
                // 检查玩家是否有盘子遗物
                if (AbstractDungeon.player.hasRelic("echoForm:Plate")) {
                    // 检查是否因敌人攻击失去格挡（amount > 0）
                    if (amount > 0) {
                        // 对所有敌人造成相当于失去格挡的伤害
                        AbstractDungeon.actionManager.addToBottom(
                            new DamageAllEnemiesAction(
                                null,
                                DamageInfo.createDamageMatrix(amount, true),
                                DamageInfo.DamageType.THORNS,
                                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL
                            )
                        );
                    }
                }
            }
            return SpireReturn.Continue();
        }
    }
}
