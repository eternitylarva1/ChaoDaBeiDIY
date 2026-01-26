package EchoForm.potions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class MilkPotion extends com.megacrit.cardcrawl.potions.AbstractPotion {
    public static final String POTION_ID = "echoForm:MilkPotion";
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    private static final int HEAL_AMOUNT = 2;

    public MilkPotion() {
        super(potionStrings.NAME, POTION_ID, PotionRarity.UNCOMMON, PotionSize.HEART, PotionColor.WHITE);
        this.isThrown = false;
    }

    @Override
    public void initializeData() {
        this.potency = getPotency();
        this.description = potionStrings.DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            // 获得2点生命
            addToBot((AbstractGameAction)new HealAction(AbstractDungeon.player, AbstractDungeon.player, HEAL_AMOUNT));
            
            // 清除自身所有负面状态
            for (AbstractPower power : AbstractDungeon.player.powers) {
                if (power.type == PowerType.DEBUFF) {
                    addToBot((AbstractGameAction)new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, power.ID));
                }
            }
        }
    }

    @Override
    public int getPotency(int ascensionLevel) {
        return HEAL_AMOUNT;
    }

    @Override
    public com.megacrit.cardcrawl.potions.AbstractPotion makeCopy() {
        return new MilkPotion();
    }
}
