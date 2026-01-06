package EchoForm.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MachineFormatPower extends AbstractPower {
    public static final String POWER_ID = "echoForm:MachineFormatPower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    
    private int skillCount = 0;

    public MachineFormatPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("focus");
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + (this.amount - this.skillCount) + DESCRIPTIONS[2];
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.SKILL) {
            this.skillCount++;
            this.updateDescription();
            
            if (this.skillCount >= this.amount) {
                this.flash();
                // 进入神格姿态
                addToBot((AbstractGameAction)new ChangeStanceAction("Divinity"));
                // 移除这个能力
                addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            }
        }
    }
}