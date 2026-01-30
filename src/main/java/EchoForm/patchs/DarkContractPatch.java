package EchoForm.patchs;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DarkContractPatch {
    // 暗黑契约遗物：当你使用抽牌数大于一的卡牌时，所抽的牌减少一张
    @SpirePatch(clz = DrawCardAction.class, method = "update")
    public static class ReduceDrawAmount {
        @SpirePrefixPatch
        public static void patch(DrawCardAction __instance) {

            //思路：
            //patch一下draw card action
            //加一个字段
            //然后在构造方法里判断，如果来自于use就设置为true
            //用get stacktrace
            //如果来自于draw card action就复制那个action的

            /*
            // 检查玩家是否有暗黑契约遗物
            if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic("echoForm:DarkContract")) {
                // 检查当前抽牌数是否大于1
                if (__instance.amount > 1) {
                    // 判断是否是卡牌效果引起的抽牌
                    // 通过检查最近打出的卡牌是否是抽牌卡
                    boolean isCardDraw = false;
                    
                    // 检查动作列表中是否有卡牌相关的抽牌动作
                    if (!AbstractDungeon.actionManager.actions.isEmpty()) {
                        // 获取最近执行的动作
                        for (int i = AbstractDungeon.actionManager.actions.size() - 1; i >= 0; i--) {
                            if (AbstractDungeon.actionManager.actions.get(i) instanceof DrawCardAction) {
                                DrawCardAction drawAction = (DrawCardAction) AbstractDungeon.actionManager.actions.get(i);
                                // 如果是同一个抽牌动作，跳过
                                if (drawAction == __instance) continue;
                                
                                // 如果抽牌数量大于0，说明是卡牌效果引起的抽牌
                                if (drawAction.amount > 0) {
                                    isCardDraw = true;
                                    break;
                                }
                            }
                        }
                    }
                    
                    // 如果是卡牌效果引起的抽牌，则减少一张
                    if (isCardDraw) {
                        __instance.amount -= 1;
                    }
                }
            }*/
        }
    }
}
