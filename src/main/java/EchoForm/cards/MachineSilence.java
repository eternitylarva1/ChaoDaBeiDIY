package EchoForm.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class MachineSilence extends CustomCard {
    public static final String ID = "MachineSilence";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "echoFormResources/images/cards/MachineSilence_skill.png";
    private static final int COST = 1;
    private static final int EXTRA_ORBS = 1;
    private static final int UPGRADE_PLUS_EXTRA_ORBS = 1;

    public MachineSilence() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = EXTRA_ORBS;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 激发所有充能球
        addToBot((AbstractGameAction)new EvokeAllOrbsAction());
        
        // 添加一个临时能力，使下次生成充能球时额外生成
        addToBot((AbstractGameAction)new ApplyPowerAction(p, p, new MachineSilencePower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_EXTRA_ORBS);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MachineSilence();
    }
    
    // 创建一个临时能力类
    private static class MachineSilencePower extends AbstractPower {
        public static final String POWER_ID = "MachineSilencePower";
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        public static final String NAME = powerStrings.NAME;
        public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        
        private boolean triggered = false;
        
        public MachineSilencePower(AbstractCreature owner, int amount) {
            this.name = NAME;
            this.ID = POWER_ID;
            this.owner = owner;
            this.amount = amount;
            this.updateDescription();
            this.loadRegion("focus");
            this.type = PowerType.BUFF;
        }
        
        public void updateDescription() {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
        
        public void onChannel(AbstractOrb orb) {
            if (!triggered && owner.isPlayer) {
                triggered = true;
                // 额外生成充能球
                for (int i = 0; i < amount; i++) {
                    addToBot(new ChannelAction(orb.makeCopy()));
                }
                // 移除这个能力
                addToBot((AbstractGameAction)new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(owner, owner, this.ID));
            }
        }
    }
}