package EchoForm.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import EchoForm.powers.MachineFormatPower;

public class MachineFormat extends CustomCard {
    public static final String ID = "MachineFormat";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "echoFormResources/images/cards/MachineFormat_power.png";
    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 10;

    public MachineFormat() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, CardColor.BLUE, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = MAGIC_NUMBER;
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 给予玩家机格能力，每打出10张能力牌进入神格
        addToBot((AbstractGameAction)new ApplyPowerAction(p, p, new MachineFormatPower(p, this.magicNumber), this.magicNumber));
        
        // 将这张牌的复制放入抽牌堆
        AbstractCard copy = this.makeStatEquivalentCopy();
        addToBot((AbstractGameAction)new MakeTempCardInDrawPileAction(copy, 1, true, true));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new MachineFormat();
    }
}