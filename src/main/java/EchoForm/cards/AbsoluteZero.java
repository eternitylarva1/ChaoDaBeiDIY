package EchoForm.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AbsoluteZero extends CustomCard {
    public static final String ID = "AbsoluteZero";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "echoFormResources/images/cards/AbsoluteZero_power.png";
    private static final int COST = 1;
    private static final int FROST_ORBS = 2;
    private static final int UPGRADE_PLUS_FROST_ORBS = 1;

    public AbsoluteZero() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber = FROST_ORBS;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 生成冰霜充能球
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot((AbstractGameAction)new ChannelAction(new Frost()));
        }
        
        // 施加绝对零度能力
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p, (AbstractPower)new AbsoluteZeroPower((AbstractCreature)p), -1, true));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_FROST_ORBS);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new AbsoluteZero();
    }
    
    // 绝对零度能力
    private static class AbsoluteZeroPower extends AbstractPower {
        public static final String POWER_ID = "echoForm:AbsoluteZeroPower";
        private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        public static final String NAME = powerStrings.NAME;
        public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
        
        public AbsoluteZeroPower(AbstractCreature owner) {
            this.name = NAME;
            this.ID = POWER_ID;
            this.owner = owner;
            this.amount = -1;
            this.updateDescription();
            this.loadRegion("focus");
            this.type = PowerType.BUFF;
        }
        
        public void updateDescription() {
            this.description = DESCRIPTIONS[0];
        }
        
        // 回合结束时，每有一个冰霜充能球保留一张牌
        public void atEndOfTurn(boolean isPlayer) {
            if (isPlayer && owner.isPlayer) {
                int frostCount = 0;
                // 计算冰霜充能球数量
                if (owner.isPlayer) {
                    AbstractPlayer p = (AbstractPlayer)owner;
                    for (int i = 0; i < p.orbs.size(); i++) {
                        if (p.orbs.get(i) instanceof Frost) {
                            frostCount++;
                        }
                    }
                    
                    // 保留相应数量的卡牌
                    if (frostCount > 0 && !p.hand.isEmpty()) {
                        addToBot((AbstractGameAction)new RetainCardsAction(p, frostCount));
                    }
                }
            }
        }
    }
}