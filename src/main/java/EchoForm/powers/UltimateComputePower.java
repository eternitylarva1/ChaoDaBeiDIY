package EchoForm.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class UltimateComputePower extends AbstractPower {
    public static final String POWER_ID = "echoForm:UltimateComputePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public UltimateComputePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("focus");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onEvokeOrb(AbstractOrb orb) {
        this.flash();
        
        // 激发充能球时，其他充能球使用1次被动能力
        for (AbstractOrb otherOrb : AbstractDungeon.player.orbs) {
            if (otherOrb != orb && otherOrb != null) {
                otherOrb.onStartOfTurn();
            }
        }
    }
}