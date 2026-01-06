package EchoForm.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import EchoForm.powers.NeurotoxinPower;

public class Neurotoxin extends AbstractCard {
    public static final String ID = "echoForm:Neurotoxin";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String IMG_PATH = "echoFormResources/images/cards/Neurotoxin.png";
    private static final int COST = 1;
    private static final int POISON = 5;
    private static final int UPGRADE_PLUS_POISON = 3;

    public Neurotoxin() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, CardColor.GREEN, CardRarity.RARE, CardTarget.ENEMY);
        this.baseMagicNumber = POISON;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 施加中毒
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)p, (AbstractPower)new PoisonPower((AbstractCreature)m, (AbstractCreature)p, this.magicNumber), this.magicNumber));
        
        // 施加神经毒素能力
        addToBot((AbstractGameAction)new ApplyPowerAction((AbstractCreature)m, (AbstractCreature)p, (AbstractPower)new NeurotoxinPower((AbstractCreature)m, this.magicNumber), this.magicNumber));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_POISON);
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Neurotoxin();
    }
}