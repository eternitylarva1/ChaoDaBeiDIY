package EchoForm.patchs;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DarkContractPatch {
    // 暗黑契约遗物：当你使用抽牌数大于一的卡牌时，所抽的牌减少一张
    @SpirePatch(clz = DrawCardAction.class, method = "update")
    public static class ReduceDrawAmount {
        @SpirePrefixPatch
        public static void patch(DrawCardAction __instance) {
            // 检查玩家是否有暗黑契约遗物
            if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("echoForm:DarkContract")) {
                // 检查当前抽牌数是否大于1
                if (__instance.amount > 1) {
                    // 减少一张抽牌
                    __instance.amount -= 1;
                }
            }
        }
    }
}
