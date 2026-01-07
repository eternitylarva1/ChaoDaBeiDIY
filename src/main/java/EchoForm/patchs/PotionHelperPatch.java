package EchoForm.patchs;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import EchoForm.potions.MilkPotion;

public class PotionHelperPatch {
    @SpirePatch(clz = PotionHelper.class, method = "getPotion", paramtypez = {String.class})
    public static class GetPotion {
        @SpirePrefixPatch
        public static SpireReturn<AbstractPotion> patch(String name) {
            if (name != null && name.equals("echoForm:MilkPotion")) {
                return SpireReturn.Return(new MilkPotion());
            }
            return SpireReturn.Continue();
        }
    }
}
